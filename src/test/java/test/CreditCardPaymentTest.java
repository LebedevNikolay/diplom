package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.DbHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditAccordingToTheCardPage;
import page.HomePage;

class CreditCardPaymentTest {
    HomePage mainPage;
    CreditAccordingToTheCardPage cardPage;

    @BeforeEach
    void shouldOpenPage() {
        DbHelper.cleanTables();
        mainPage = Selenide.open("http://localhost:8080", HomePage.class);
        cardPage = mainPage.clickCreditCard();
    }

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //Покупка тура через вкладку "Купить в кредит"
    @Test
    @DisplayName("1.Покупка тура при вводе данных карты со статусом APPROVED")
    public void shouldApprovedCard() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getApprovedCard());
        cardPage.notificationOk();

        Assertions.assertEquals("APPROVED", DbHelper.getCreditStatusDB());
        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getCreditCount());
    }


    @Test
    @DisplayName("2.Покупка тура при вводе данных карты со статусом DECLINED")
    public void shouldDeclinedCard() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getDeclinedCard());
        cardPage.notificationFailed();

        Assertions.assertEquals("DECLINED", DbHelper.getCreditCount());
        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("3.Покупка тура при отправке пустых значений в полях формы")
    public void shouldAllFieldsAreEmpty() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getAllFieldsAreEmpty());
        cardPage.allFieldsWithErrors();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    //Проверка валидации полей ввода данных по вкладке "Купить в кредит"

    //Поле "Номер карты"

    @Test
    @DisplayName("4.Поле номер карты пустое")
    public void shouldEmptyCardNumberField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldCardNumberEmpty());
        cardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("5.Поле номер карты заполнено значением меньше 16 цифр")
    public void should15DigitsInTheCardNumberField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldCardNumber15Digits());
        cardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("6.Поле номер карты заполнено значением больше 16 цифр")
    public void should17DigitInTheCardNumberField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldCardNumber17Digits());
        cardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("7.Поле номер карты заполнено буквами")
    public void shouldLettersDigitInTheCardNumberField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldCardNumberLetters());
        cardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("8.Поле номер карты заполнено спецсимволами")
    public void shouldSpecialCharactersDigitInTheCardNumberField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldCardNumberSpecialCharacters());
        cardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("9.Поле номер карты заполнено нулевым значением")
    public void shouldZeroDigitInTheCardNumberField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldCardNumberZero());
        cardPage.invalidFormatCardNumberField();
        cardPage.allFieldsWithErrors();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

//    Поле "Месяц"

    @Test
    @DisplayName("10.Поле месяц пустое")
    public void shouldEmptyMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthEmpty());
        cardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("11.Поле месяц заполнено значением меньше 2 цифр")
    public void should1DigitInTheMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthOne());
        cardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("12.Поле месяц заполнено значением больше 2 цифр")
    public void shouldThanTwoDigitInTheMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthMoreThanTwo());
        cardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("13.Поле месяц заполнено буквами")
    public void shouldLettersDigitInTheMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthLetters());
        cardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("14.Поле месяц заполнено спецсимволами")
    public void shouldSpecialCharactersDigitInTheMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthSpecialCharacters());
        cardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("15.Поле месяц заполнено несуществующим месяцем")
    public void shouldNotExistMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthDoesNotExist());
        cardPage.invalidCardExpirationDateMonth();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("16.Поле месяц заполнено прошлым месяцем текущего года")
    public void shouldPreviousThisYearMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthPreviousThisYear());
        cardPage.invalidCardExpirationDateMonth();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("17.Поле месяц заполнено нулевым значением")
    public void shouldZeroTheMonthField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldMonthZero());
        cardPage.invalidCardExpirationDateMonth();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    //Поле "Год"

    @Test
    @DisplayName("18.Поле год пустое")
    public void shouldEmptyYearField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldYearEmpty());
        cardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("19.Поле год заполнено значением меньше 2 цифр")
    public void should1DigitInTheYearField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.get1DigitInTheYearField());
        cardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("20.Поле год заполнено значением больше 2 цифр")
    public void shouldThanTwoDigitInTheYearField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getDigitInTheYearFieldMoreThanTwo());
        cardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("21.Поле год заполнено буквами")
    public void shouldLetterDigitInTheYearField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getInTheYearFieldLetter());
        cardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("22.Поле год заполнено спецсимволами")
    public void shouldSpecialCharactersDigitInTheYearField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getDigitInTheYearFieldSpecialCharacters());
        cardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("23.Поле год заполнено нулевым значением")
    public void shouldZeroInTheYearField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getInTheYearFieldZero());
        cardPage.invalidFormatCardExpired();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("24.Поле год заполнено значением предыдущего года")
    public void shouldYearPrecedingTheCurrentOne() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getYearBeforeCurrent());
        cardPage.invalidFormatCardExpired();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("25.Поле год заполнено значением отстоящим от текущего более чем на 6 лет")
    public void shouldYear6YearsAheadOfCurrentYear() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getExceeds6YearsField());
        cardPage.invalidCardExpirationDateYear();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }


    //Поле "Владелец"

    @Test
    @DisplayName("26.Поле владелец пустое")
    public void shouldEmptyOwnerField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldOwnerEmpty());
        cardPage.invalidFormatRequiredOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("27.Поле владелец заполнено 1 буквой")
    public void shouldOneLetterOwnerField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldOwnerOneLetter());
        cardPage.invalidFormatOwnerFieldValueMustContainMoreThanOneLetter();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("28.Поле владелец заполнено значением через пробел, состоящим из двойного имени")
    public void shouldFullNameInTheOwnerField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldOwnerFullName());
        cardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("29.Поле владелец заполнено значением через пробел, состоящим из имени через дефис")
    public void shouldNameSeparatedByAHyphenInTheOwnerField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldOwnerNameSeparatedByAHyphen());
        cardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("30.Поле владелец заполнено значением на кириллице")
    public void shouldInCyrillicOwnerField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldOwnerInCyrillic());
        cardPage.invalidFormatOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("31.Поле владелец заполнено цифрами")
    public void shouldNumbersInTheOwnerField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldOwnerInNumbers());
        cardPage.invalidFormatOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("32.Поле владелец заполнено спецсимволами")
    public void shouldSpecialCharacterOwnerField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldOwnerSpecialCharacter());
        cardPage.invalidFormatOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    //Поле "CVC/CVV"

    @Test
    @DisplayName("33.Поле CVC пустое")
    public void shouldEmptyCVCField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getFieldCVCEmpty());
        cardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("34.Поле CVC заполнено значением меньше 3 цифр")
    public void shouldLessThan3DigitsCVCField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getCVCFieldLessThan3Digits());
        cardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("35.Поле CVC заполнено значением больше 3 цифр")
    public void shouldMoreThan3DigitsCVCField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getCVCFieldMoreThan3Digits());
        cardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("36.Поле CVC заполнено буквами")
    public void shouldLettersCVCField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getCVCFieldLetters());
        cardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("37.Поле CVC заполнено спецсимволами")
    public void shouldSpecialCharactersCVCField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getCVCFieldSpecialCharacters());
        cardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }

    @Test
    @DisplayName("38.Поле CVC заполнено нулевым значением")
    public void shouldZeroTheCVCField() {
        mainPage.clickCreditCard();
        cardPage.cardData(DataHelper.getCVCFieldZero());
        cardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getCreditCount());
    }
}