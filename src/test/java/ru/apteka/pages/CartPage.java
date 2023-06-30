package ru.apteka.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CartPage {
    SelenideElement
            emptyCartBlock = $(".bx-sbb-empty-cart-container"),
            emptyCart = $(".bx-sbb-empty-cart-text");

    public void checkEmptyCart(String value) {
        emptyCart.shouldHave(text(value));
    }

    public void checkEmptyCartBlock() {
        emptyCartBlock.shouldBe(visible);
    }
}
