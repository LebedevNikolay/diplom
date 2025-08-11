package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private static SelenideElement mainPage = $(("div.App_appContainer__3jRx1"));
    private static SelenideElement debitCardPayment = $(withText("Купить"));
    private static SelenideElement visibleDebitCardPaymentText = $(withText("Оплата по карте"));
    private static SelenideElement creditCardPayment = $(withText("Купить в кредит"));
    private static SelenideElement visibleCreditCardPaymentText = $(withText("Кредит по данным карты"));


    public CardPaymentPage clickDebitCard() {
        mainPage.shouldHave(text("Путешествие дня")).shouldBe(visible);
        debitCardPayment.shouldHave(text("Купить")).click();
        visibleDebitCardPaymentText.shouldHave(text("Оплата по карте")).shouldBe(visible);
        return new CardPaymentPage();
    }

    public CreditAccordingToTheCardPage clickCreditCard() {
        mainPage.shouldHave(text("Путешествие дня")).shouldBe(visible);
        creditCardPayment.shouldHave(text("Купить в кредит")).click();
        visibleCreditCardPaymentText.shouldHave(text("Кредит по данным карты")).shouldBe(visible);
        return new CreditAccordingToTheCardPage();
    }
}