package task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static task2.DataGenerator.Registration.getRegisteredUser;
import static task2.DataGenerator.Registration.getUser;
import static task2.DataGenerator.getRandomLogin;
import static task2.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.login);
        $("[data-test-id='password'] input").setValue(registeredUser.password);
        $$("button").find(exactText("Продолжить")).click();
        $("[class='heading heading_size_l heading_theme_alfa-on-white']").shouldHave(text("  Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.login);
        $("[data-test-id='password'] input").setValue(notRegisteredUser.password);
        $$("button").find(exactText("Продолжить")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.login);
        $("[data-test-id='password'] input").setValue(blockedUser.password);
        $$("button").find(exactText("Продолжить")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.password);
        $$("button").find(exactText("Продолжить")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.login);
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $$("button").find(exactText("Продолжить")).click();
        $("[class='notification__title']").shouldBe(visible, Duration.ofSeconds(10));
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}
