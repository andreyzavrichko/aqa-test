package ru.apteka.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {

    public SelenideElement
            header = $("h1"),
            catalogTitle = $("#pagetitle"),
            favoritesAlert = $(".alert-warning", 1),
            favoritesButton = $("[title=Отложить]"),
            clearBasketButton = $(".delete_all"),
            addToCardButton = $("[data-entity=basket-item-remove-delayed]"),
            summaryPriceTitle = $(".basket-coupon-block-total-price-current"),
            countGoods = $(".count"),
            basketLink = $(".basket-link"),
            menuItem = $(byText("Косметика")),
            emptySearchTitle = $(byText("Сожалеем, но ничего не найдено."));


    ElementsCollection blockItem = $$(".item_block");

    public void checkCatalogTitle(String value) {
        catalogTitle.shouldHave(text(value));
    }


    public void checkSummaryPriceTitle(String value) {
        summaryPriceTitle.shouldHave(text(value));
    }

    public void checkFavoritesAlert(String value) {
        favoritesAlert.shouldHave(text(value));
    }

    public void clickFavoritesButton() {
        favoritesButton.click();
    }

    public void clickClearBasketButton() {
        clearBasketButton.click();
    }


    public void clickAddToCardButton() {
        addToCardButton.click();
    }

    public void hoverFavoritesButton() {
        basketLink.hover();
    }

    public void checkBasketLink(String value) {
        basketLink.shouldHave(attribute("title", value));
    }

    public void checkCountGoods(Integer value) {
        countGoods.shouldHave(text(String.valueOf(value)));
    }

    public void clickCountGoods() {
        countGoods.click();
    }

    public void checkCountGoodsInBasket(Integer value) {
        countGoods.shouldHave(text(String.valueOf(value)));
    }

    public void checkEmptySearchTitle(String value) {
        emptySearchTitle.shouldHave(text(value));
    }

    public void checkMenuItem(String value) {
        menuItem.shouldHave(text(value));
    }

    public void checkBlockItem() {
        blockItem.shouldHave(sizeGreaterThan(1));
    }

    public void checkBlockItemInSearch() {
        blockItem.shouldHave(sizeGreaterThan(5));
    }

    public void hoverBlockItemInSearch() {
        blockItem.get(0).hover();
    }


}
