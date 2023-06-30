package ru.apteka.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.apteka.helpers.Attach;
import ru.apteka.pages.*;

import java.net.MalformedURLException;
import java.net.URI;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static io.qameta.allure.Allure.step;

@DisplayName("Тесты на сайте Эконом-аптека")
class AptekaTest {
    @BeforeAll
    static void setUp() throws MalformedURLException {
        Configuration.remote = "http://localhost:4444/wd/hub";
//        boolean isRemote = true;
//        if (isRemote) {
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setBrowserName("chrome");
//            capabilities.setCapability("enableVNC:", true);
//            WebDriver driver = new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), capabilities);
//            setWebDriver(driver);
//        } else {
//            Configuration.browser = "firefox";
//        }
    }


    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        closeWebDriver();
    }
    MainPage mainPage = new MainPage();
    Search search = new Search();
    CityPopup cityPopup = new CityPopup();
    CatalogPage catalogPage = new CatalogPage();
    CartPage cartPage = new CartPage();

    @BeforeEach
    public void setSelenide() {
        open("https://aptekaeconom.com");
        Selenide.webdriver().driver().getWebDriver().manage().addCookie(new Cookie("current_region", "103006"));
        refresh();
        cityPopup.modal.shouldNotBe(visible);
    }

    @Test
    @Feature("Каталог товаров")
    @Story("Подкатегории")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Переход по подкатегориям в каталоге товаров")
    void shouldOpenCatalogTabTest() {
        step("Навести курсор на вкладку", () -> mainPage.navItemHover());
        step("Кликнуть на появившуюся подкатегорию", () -> mainPage.navItemTabClick());
        step("Проверить, что произошел переход на страницу товаров категории", () -> {
            catalogPage.header.shouldHave(text("Пластыри"));
        });
        step("Проверить, что хотя бы один товар присутствует", () -> catalogPage.checkBlockItem());
        step("Проверить хлебные крошки", () -> mainPage.checkBreadcrumbs());
        step("Проверить подкатегорию в левой и верхней части страницы", () -> {
            catalogPage.checkCatalogTitle("Пластыри");
            catalogPage.checkMenuItem("Косметика");
        });
    }


    @Test
    @Feature("Поиск")
    @Story("Поиск")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка поиска - позитивный сценарий")
    void shouldSearchPositiveTest() {
        step("Ввести в поисковую строку значение", () -> search.typeSearchInput("Лейкопластырь"));
        step("Проверить отображение больше 5 элементов на странице", () -> catalogPage.checkBlockItemInSearch());
    }


    @Test
    @Feature("Поиск")
    @Story("Поиск")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Проверка поиска - негативный сценарий")
    void shouldSearchNegativeTest() {
        step("Ввести в поисковую строку значение", () -> search.typeSearchInput(" "));
        step("Проверить отображение пустого результата запросов", () ->
                catalogPage.checkEmptySearchTitle("Сожалеем, но ничего не найдено."));
    }


    @Test
    @Feature("Отложить товар")
    @Story("Товар")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка возможности отложить товар")
    void shouldFavoritTest() {
        step("Ввести в поисковую строку значение", () -> search.typeSearchInput("Лейкопластырь"));
        step("Наводим на карточку", () -> catalogPage.hoverBlockItemInSearch());
        step("Добавить в избранное первый товар", () -> catalogPage.clickFavoritesButton());
        step("Проверить счетчик товаров", () -> catalogPage.checkCountGoods(1));
        step("Перейти в раздел Избранное", () -> catalogPage.clickCountGoods());
        step("Проверить что товар отложен", () -> catalogPage.checkFavoritesAlert("Товар отложен."));
        step("Наводим на кнопку", () -> catalogPage.hoverFavoritesButton());
        step("Проверяем текст", () -> catalogPage.checkBasketLink("В отложенных товаров на 6 руб."));
    }


    @Test
    @Feature("Корзина")
    @Story("Перенос в корзину")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Проверка возможности перенести товар в корзину")
    void shouldAddToCartTest() {
        step("Ввести в поисковую строку значение", () -> search.typeSearchInput("Лейкопластырь"));
        step("Наводим на карточку", () -> catalogPage.hoverBlockItemInSearch());
        step("Добавить в избранное первый товар", () -> catalogPage.clickFavoritesButton());
        step("Перейти в раздел Избранное", () -> catalogPage.clickCountGoods());
        step("Проверить что товар отложен", () -> catalogPage.checkFavoritesAlert("Товар отложен."));
        step("Проверяем текст", () -> catalogPage.clickAddToCardButton());
        step("Проверяем увеличение счетчика в корзине", () -> catalogPage.checkCountGoodsInBasket(1));
        step("Проверяем общую сумму в корзине", () -> catalogPage.checkSummaryPriceTitle("6 руб."));
    }

    @Test
    @Feature("Очистка корзины")
    @Story("Корзина")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Проверка очистки корзины")
    void shouldClearCardTest() {
        step("Ввести в поисковую строку значение", () -> search.typeSearchInput("Лейкопластырь"));
        step("Наводим на карточку", () -> catalogPage.hoverBlockItemInSearch());
        step("Добавить в избранное первый товар", () -> catalogPage.clickFavoritesButton());
        step("Перейти в раздел Избранное", () -> catalogPage.clickCountGoods());
        step("Проверить что товар отложен", () -> catalogPage.checkFavoritesAlert("Товар отложен."));
        step("Проверяем текст", () -> catalogPage.clickAddToCardButton());
        step("Проверяем увеличение счетчика в корзине", () -> catalogPage.checkCountGoodsInBasket(1));
        step("Проверяем общую сумму в корзине", () -> catalogPage.checkSummaryPriceTitle("6 руб."));
        step("Нажимаем кнопку Очистить", () -> catalogPage.clickClearBasketButton());
        step("Проверяем что корзина очистилась", () -> {
            cartPage.checkEmptyCartBlock();
            cartPage.checkEmptyCart("Ваша корзина пуста");
        });
    }


}
