package ru.apteka.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Search {
    public SelenideElement

            searchInput = $("[name=q]");

    public void typeSearchInput(String value) {
        searchInput.setValue(value).pressEnter();
    }

}
