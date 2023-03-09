package task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void sendCorrectForm() {
        String date = Generator.getRandomCorrectDate();
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на " + date));
    }

    @Test
    void sendCorrectFormTwice() {
        String date = Generator.getRandomCorrectDate();
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на " + date));

        $$("button").find(exactText("Запланировать")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=replan-notification] .notification__title").shouldHave(text("Необходимо подтверждение"));

        $$("button").find(exactText("Перепланировать")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=success-notification] .notification__content").shouldHave(text("Встреча успешно запланирована на " + date));
    }

    @Test
    void sendBadCity() {
        $("[data-test-id='city'] input").setValue(Generator.getEngCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomCorrectDate());
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void sendIncorrectDate() {
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomIncorrectDate());
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void sendIncorrectPhone() {
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomCorrectDate());
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomIncorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void sendIncorrectName() {
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomCorrectDate());
        $("[data-test-id='name'] input").setValue(Generator.getRandomIncorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void sendWithoutAgreement() {
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomCorrectDate());
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void sendEmptyDate() {
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("");
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void sendEmptyName() {
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomCorrectDate());
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void sendEmptyPhone() {
        $("[data-test-id='city'] input").setValue(Generator.getRandomCorrectCity());
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomCorrectDate());
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void sendEmptyCity() {
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(Generator.getRandomCorrectDate());
        $("[data-test-id='name'] input").setValue(Generator.getRandomCorrectName());
        $("[data-test-id='phone'] input").setValue(Generator.getRandomCorrectPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
