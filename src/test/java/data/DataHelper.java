package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataHelper {
    public static Faker fakerEn = new Faker(new Locale("en"));
    public static Faker fakerRu = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    public static String getApprovedNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getMonthFaker(int plusMonth) {
        return LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateMinusMonth(int minusMonth, String formatPattern) {
        return LocalDate.now().minusMonths(minusMonth).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public static String getYearFaker(int plusYear) {
        return LocalDate.now().plusYears(plusYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateDateMinusYears(int minusYears, String formatPattern) {
        return LocalDate.now().minusYears(minusYears).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public static String generateDatePlusYears(int plusYears, String formatPattern) {
        return LocalDate.now().plusYears(plusYears).format(DateTimeFormatter.ofPattern(formatPattern));
    }

    public static String getOwnerFakerEng() {
        return fakerEn.name().fullName().toUpperCase();
    }

    public static String getInvalidOwnerFakerRu() {
        return fakerRu.name().firstName().toUpperCase() + " " + fakerRu.name().lastName().toUpperCase();
    }

    public static String getInvalidOwnerFullName() {
        return fakerEn.name().firstName().toUpperCase() + " " + fakerEn.name().firstName().toUpperCase() + " " + fakerEn.name().lastName().toUpperCase();
    }

    public static String getValidOwnerHyphenated() {
        return fakerEn.name().firstName().toUpperCase() + "-" + fakerEn.name().firstName().toUpperCase() + " " + fakerEn.name().lastName().toUpperCase();
    }

    public static String getOneLetter() {
        return fakerEn.name().name().substring(0, 1).toUpperCase();
    }

    public static String getTwoLetter() {
        return fakerEn.name().name().substring(0, 2).toLowerCase();
    }

    public static String getThreeLetter() {
        return fakerEn.name().name().substring(0, 3).toLowerCase();
    }

    public static String getInvalidRandomNumber() {
        return String.valueOf(fakerEn.number().randomNumber());
    }

    public static String getCVCFaker() {
        return fakerEn.numerify("###");
    }


    //Карта со статусом Approved
    public static CardInfoData getApprovedCard() {
        return new CardInfoData(getApprovedNumber(), "12", "22", "Irina Novikova", "888");
    }

    //Карта со статусом Declined
    public static CardInfoData getDeclinedCard() {
        return new CardInfoData(getDeclinedNumber(), "03", "25", "Platon Solntsev", "123");
    }

    //Все поля пусты
    public static CardInfoData getAllFieldsAreEmpty() {

        return new CardInfoData("", "", "", "", "");
    }


    //Поле "Номер карты"
    //Пустое поле
    public static CardInfoData getFieldCardNumberEmpty() {
        return new CardInfoData("", getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }


    //Заполнить поле значением меньше 16 цифр
    public static CardInfoData getFieldCardNumber15Digits() {
        return new CardInfoData("4444 4444 4444 444", getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле значением больше 16 цифр
    public static CardInfoData getFieldCardNumber17Digits() {
        return new CardInfoData("4444 4444 4444 4441 1", getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле буквами
    public static CardInfoData getFieldCardNumberLetters() {
        return new CardInfoData("фффф фффф ыыыы ыыыы", getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле спецсимволами
    public static CardInfoData getFieldCardNumberSpecialCharacters() {
        return new CardInfoData("*? |,.; :'! #%^&", getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле нулевыми значениями
    public static CardInfoData getFieldCardNumberZero() {
        return new CardInfoData("0000 0000 0000 0000", getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }


    //Поле "Месяц"
    //Пустое поле
    public static CardInfoData getFieldMonthEmpty() {
        return new CardInfoData(getApprovedNumber(), "", getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле значением меньше 2 цифр
    public static CardInfoData getFieldMonthOne() {
        return new CardInfoData(getApprovedNumber(), "1", getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле значением больше 2 цифр
    public static CardInfoData getFieldMonthMoreThanTwo() {
        return new CardInfoData(getApprovedNumber(), "122", getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле буквами
    public static CardInfoData getFieldMonthLetters() {
        return new CardInfoData(getApprovedNumber(), getTwoLetter(), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле спецсимволами
    public static CardInfoData getFieldMonthSpecialCharacters() {
        return new CardInfoData(getApprovedNumber(), "%%", getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле несуществующим месяцем
    public static CardInfoData getFieldMonthDoesNotExist() {
        return new CardInfoData(getApprovedNumber(), "13", getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    } // Несуществующий месяц

    //Заполнить поле прошлым месяцем текущего года
    public static CardInfoData getFieldMonthPreviousThisYear() {
        return new CardInfoData(getApprovedNumber(), generateMinusMonth(1, "MM"), "22", getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле нулевым значением
    public static CardInfoData getFieldMonthZero() {
        return new CardInfoData(getApprovedNumber(), "00", getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }


    //Поле "Год"
    //Пустое поле
    public static CardInfoData getFieldYearEmpty() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), "", getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле значением меньше 2 цифр
    public static CardInfoData get1DigitInTheYearField() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), "2", getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле значением больше 2 цифр
    public static CardInfoData getDigitInTheYearFieldMoreThanTwo() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), "222", getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле буквами
    public static CardInfoData getInTheYearFieldLetter() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getTwoLetter(), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле спецсимволами
    public static CardInfoData getDigitInTheYearFieldSpecialCharacters() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), "№;", getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле нулевым значением
    public static CardInfoData getInTheYearFieldZero() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), "00", getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле значением предыдущего года
    public static CardInfoData getYearBeforeCurrent() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), generateDateMinusYears(1, "yy"), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле значением отстоящим от текущего более чем на 6 лет
    public static CardInfoData getExceeds6YearsField() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), generateDatePlusYears(7, "yy"), getOwnerFakerEng(), getCVCFaker());
    }


    //Поле "Владелец"
    //Пустое поле
    public static CardInfoData getFieldOwnerEmpty() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), "", getCVCFaker());
    }

    //Заполнить поле 1 буквой
    public static CardInfoData getFieldOwnerOneLetter() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getOneLetter(), getCVCFaker());
    }

    //Заполнить поле значением через пробел, состоящим из ФИО
    public static CardInfoData getFieldOwnerFullName() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getInvalidOwnerFullName(), getCVCFaker());
    }

    //Заполнить поле значением через пробел, состоящим из имени через дефис
    public static CardInfoData getFieldOwnerNameSeparatedByAHyphen() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getValidOwnerHyphenated(), getCVCFaker());
    }

    //Заполнить поле значением на кириллице
    public static CardInfoData getFieldOwnerInCyrillic() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getInvalidOwnerFakerRu(), getCVCFaker());
    }

    //Заполнить поле цифрами
    public static CardInfoData getFieldOwnerInNumbers() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getInvalidRandomNumber(), getCVCFaker());
    }

    //Заполнить поле спецсимволами
    public static CardInfoData getFieldOwnerSpecialCharacter() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), "%:;;№ ", getCVCFaker());
    }


    //Поле "CVC/CVV"
    //Пустое поле
    public static CardInfoData getFieldCVCEmpty() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), "");
    }

    //Заполнить поле значением меньше 3 цифр
    public static CardInfoData getCVCFieldLessThan3Digits() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), "2");
    }

    //Заполнить поле значением больше 3 цифр
    public static CardInfoData getCVCFieldMoreThan3Digits() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getCVCFaker());
    }

    //Заполнить поле буквами
    public static CardInfoData getCVCFieldLetters() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), getThreeLetter());
    }

    //Заполнить поле спецсимволами
    public static CardInfoData getCVCFieldSpecialCharacters() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), ":?*");
    }

    //Заполнить поле нулевым значением
    public static CardInfoData getCVCFieldZero() {
        return new CardInfoData(getApprovedNumber(), getMonthFaker(0), getYearFaker(0), getOwnerFakerEng(), "000");
    }
}