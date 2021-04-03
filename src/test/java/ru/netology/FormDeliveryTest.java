package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;


public class FormDeliveryTest {

    private LocalDate getFutureDate(int n) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(n);
        return date;
    }

    @Test
    void shouldSendForm() {
        open("http://localhost:7777");
        $$("[type=text]").first().setValue("Воронеж");
        $("[placeholder='Дата встречи']").sendKeys("\b\b\b\b\b\b\b\b");
        String futureDate = getFutureDate(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(futureDate);
        $("[name=phone]").setValue("+79850000000");
        $("[data-test-id=name] [type=text]").setValue("Иванов Николай");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + futureDate));

    }

    @Test
    void shoulSelectCityFromList() {
        open("http://localhost:7777");
        $$("[type=text]").first().sendKeys("н", "о", "в");
        $(byText("Новосибирск")).click();
        $("[placeholder='Дата встречи']").sendKeys("\b\b\b\b\b\b\b\b");
        String futureDate = getFutureDate(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[placeholder='Дата встречи']").setValue(futureDate);
        $("[name=phone]").setValue("+79850000000");
        $("[data-test-id=name] [type=text]").setValue("Иванов Николай");
        $(".checkbox__box").click();
        $("div.form-field>[type=button]").submit();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + futureDate));

    }
}


