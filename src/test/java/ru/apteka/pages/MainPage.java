package ru.apteka.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    SelenideElement breadcrumbs = $(".breadcrumbs");
    SelenideElement navItem = $(byText("Косметика"));
    SelenideElement navItemTab = $x(("//span[text()='Пластыри']"));
    public ElementsCollection tabs = $$("#left_nav_main ul li");

    public ElementsCollection getSubtabs(SelenideElement tab) {
        return tab.$$("ul li");
    }


    public void checkBreadcrumbs() {
        breadcrumbs.shouldBe(visible);
    }

    public void navItemHover() {
        navItem.hover();
    }

    public void navItemTabClick() {
        navItemTab.click();
    }


}
