package parametrixed;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class TestBase {
    @BeforeEach
    void beforeEach() {
        Configuration.baseUrl = "https://music.yandex.ru/home";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000;
        Configuration.pageLoadStrategy = "eager";
        if ($(".pay-promo").exists()) {
            $("pay-promo-close-btn").click();
        }
        executeJavaScript("$('.d-overhead').remove()");
        executeJavaScript("$('.ads-block').remove()");

    }
}