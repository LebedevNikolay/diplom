package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.DbHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CardPaymentPage;
import page.HomePage;

class DebitCardPaymentTest {
    HomePage mainPage;
    CardPaymentPage debitCardPage;

    @BeforeEach
    void shouldOpenPage() {
        DbHelper.cleanTables();
        mainPage = Selenide.open("http://localhost:8080", HomePage.class);
        debitCardPage = mainPage.clickDebitCard();
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
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getApprovedCard());
        debitCardPage.notificationOk();

        Assertions.assertEquals("APPROVED", DbHelper.getPaymentStatusDB());
        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getPaymentCount());
    }


    @Test
    @DisplayName("2.Покупка тура при вводе данных карты со статусом DECLINED")
    public void shouldDeclinedCard() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getDeclinedCard());
        debitCardPage.notificationFailed();

        Assertions.assertEquals("DECLINED", DbHelper.getPaymentStatusDB());
        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("3.Покупка тура при отправке пустых значений в полях формы")
    public void shouldAllFieldsAreEmpty() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getAllFieldsAreEmpty());
        debitCardPage.allFieldsWithErrors();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    //Проверка валидации полей ввода данных по вкладке "Купить в кредит"

    //Поле "Номер карты"

    @Test
    @DisplayName("4.Поле номер карты пустое")
    public void shouldEmptyCardNumberField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldCardNumberEmpty());
        debitCardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("5.Поле номер карты заполнено значением меньше 16 цифр")
    public void should15DigitsInTheCardNumberField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldCardNumber15Digits());
        debitCardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("6.Поле номер карты заполнено значением больше 16 цифр")
    public void should17DigitInTheCardNumberField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldCardNumber17Digits());
        debitCardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("7.Поле номер карты заполнено буквами")
    public void shouldLettersDigitInTheCardNumberField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldCardNumberLetters());
        debitCardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("8.Поле номер карты заполнено спецсимволами")
    public void shouldSpecialCharactersDigitInTheCardNumberField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldCardNumberSpecialCharacters());
        debitCardPage.invalidFormatCardNumberField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("9.Поле номер карты заполнено нулевым значением")
    public void shouldZeroDigitInTheCardNumberField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldCardNumberZero());
        debitCardPage.invalidFormatCardNumberField();
        debitCardPage.allFieldsWithErrors();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

//    Поле "Месяц"

    @Test
    @DisplayName("10.Поле месяц пустое")
    public void shouldEmptyMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthEmpty());
        debitCardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("11.Поле месяц заполнено значением меньше 2 цифр")
    public void should1DigitInTheMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthOne());
        debitCardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("12.Поле месяц заполнено значением больше 2 цифр")
    public void shouldThanTwoDigitInTheMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthMoreThanTwo());
        debitCardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("13.Поле месяц заполнено буквами")
    public void shouldLettersDigitInTheMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthLetters());
        debitCardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("14.Поле месяц заполнено спецсимволами")
    public void shouldSpecialCharactersDigitInTheMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthSpecialCharacters());
        debitCardPage.invalidFormatMonthField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("15.Поле месяц заполнено несуществующим месяцем")
    public void shouldNotExistMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthDoesNotExist());
        debitCardPage.invalidCardExpirationDateMonth();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("16.Поле месяц заполнено прошлым месяцем текущего года")
    public void shouldPreviousThisYearMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthPreviousThisYear());
        debitCardPage.invalidCardExpirationDateMonth();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("17.Поле месяц заполнено нулевым значением")
    public void shouldZeroTheMonthField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldMonthZero());
        debitCardPage.invalidCardExpirationDateMonth();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    //Поле "Год"

    @Test
    @DisplayName("18.Поле год пустое")
    public void shouldEmptyYearField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldYearEmpty());
        debitCardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("19.Поле год заполнено значением меньше 2 цифр")
    public void should1DigitInTheYearField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.get1DigitInTheYearField());
        debitCardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("20.Поле год заполнено значением больше 2 цифр")
    public void shouldThanTwoDigitInTheYearField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getDigitInTheYearFieldMoreThanTwo());
        debitCardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("21.Поле год заполнено буквами")
    public void shouldLetterDigitInTheYearField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getInTheYearFieldLetter());
        debitCardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("22.Поле год заполнено спецсимволами")
    public void shouldSpecialCharactersDigitInTheYearField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getDigitInTheYearFieldSpecialCharacters());
        debitCardPage.invalidFormatYearField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("23.Поле год заполнено нулевым значением")
    public void shouldZeroInTheYearField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getInTheYearFieldZero());
        debitCardPage.invalidFormatCardExpired();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("24.Поле год заполнено значением предыдущего года")
    public void shouldYearPrecedingTheCurrentOne() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getYearBeforeCurrent());
        debitCardPage.invalidFormatCardExpired();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("25.Поле год заполнено значением отстоящим от текущего более чем на 6 лет")
    public void shouldYear6YearsAheadOfCurrentYear() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getExceeds6YearsField());
        debitCardPage.invalidCardExpirationDateYear();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }


    //Поле "Владелец"

    @Test
    @DisplayName("26.Поле владелец пустое")
    public void shouldEmptyOwnerField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldOwnerEmpty());
        debitCardPage.invalidFormatRequiredOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("27.Поле владелец заполнено 1 буквой")
    public void shouldOneLetterOwnerField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldOwnerOneLetter());
        debitCardPage.invalidFormatOwnerFieldValueMustContainMoreThanOneLetter();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("28.Поле владелец заполнено значением через пробел, состоящим из двойного имени")
    public void shouldFullNameInTheOwnerField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldOwnerFullName());
        debitCardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("29.Поле владелец заполнено значением через пробел, состоящим из имени через дефис")
    public void shouldNameSeparatedByAHyphenInTheOwnerField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldOwnerNameSeparatedByAHyphen());
        debitCardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("30.Поле владелец заполнено значением на кириллице")
    public void shouldInCyrillicOwnerField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldOwnerInCyrillic());
        debitCardPage.invalidFormatOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("31.Поле владелец заполнено цифрами")
    public void shouldNumbersInTheOwnerField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldOwnerInNumbers());
        debitCardPage.invalidFormatOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("32.Поле владелец заполнено спецсимволами")
    public void shouldSpecialCharacterOwnerField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldOwnerSpecialCharacter());
        debitCardPage.invalidFormatOwnerField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    //Поле "CVC/CVV"

    @Test
    @DisplayName("33.Поле CVC пустое")
    public void shouldEmptyCVCField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getFieldCVCEmpty());
        debitCardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("34.Поле CVC заполнено значением меньше 3 цифр")
    public void shouldLessThan3DigitsCVCField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getCVCFieldLessThan3Digits());
        debitCardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("35.Поле CVC заполнено значением больше 3 цифр")
    public void shouldMoreThan3DigitsCVCField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getCVCFieldMoreThan3Digits());
        debitCardPage.notificationOk();

        Assertions.assertEquals(1, DbHelper.getOrderCount());
        Assertions.assertEquals(1, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("36.Поле CVC заполнено буквами")
    public void shouldLettersCVCField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getCVCFieldLetters());
        debitCardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("37.Поле CVC заполнено спецсимволами")
    public void shouldSpecialCharactersCVCField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getCVCFieldSpecialCharacters());
        debitCardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }

    @Test
    @DisplayName("38.Поле CVC заполнено нулевым значением")
    public void shouldZeroTheCVCField() {
        mainPage.clickDebitCard();
        debitCardPage.debitCardData(DataHelper.getCVCFieldZero());
        debitCardPage.invalidFormatCVCField();

        Assertions.assertEquals(0, DbHelper.getOrderCount());
        Assertions.assertEquals(0, DbHelper.getPaymentCount());
    }
}