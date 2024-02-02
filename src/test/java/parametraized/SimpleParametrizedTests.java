package parametraized;

import com.codeborne.selenide.Configuration;
import data.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SimpleParametrizedTests{

    @BeforeEach
    void beforeEach() {
        open("https://music.yandex.ru");
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000;
        Configuration.pageLoadStrategy = "eager";
        if ($(".pay-promo").exists()) {
            $("pay-promo-close-btn").click();
        }
        executeJavaScript("$('.d-overhead').remove()");
        executeJavaScript("$('.ads-block').remove()");

    }

    @EnumSource(Language.class)
    @ParameterizedTest(name = "Заглавная страница должна отображаться в соответствии с выбранным языком")
    void yaMusicSiteShouldShowCorrectText(Language language) {
        $$(".d-lang-switcher").find(text(language.name())).click();
        $(".d-header__title").shouldHave(text(language.description));
    }

    static Stream<Arguments> yaMusicShouldShowCorrectButtons() {
        return Stream.of(
                Arguments.of(
                        Language.EN,
                        List.of("All", "New releases", "Charts", "Mixes", "Neuromusic")
                ),
                Arguments.of(
                        Language.RU,
                        List.of("Всё", "Новые релизы", "Чарт", "Подборки", "Нейромузыка")
                )
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Кнопки на заглавной странице должны соответствовать выбранному языку")
    void yaMusicShouldShowCorrectButtons(Language language, List<String> expectedButtons) {
        $$(".d-lang-switcher").find(text(language.name())).click();
        $$(".d-tabs").filter(visible).shouldHave(texts(expectedButtons));
    }

    @ValueSource(strings = {"plenka", "Ghostemane", "Rammstein"})
    @ParameterizedTest(name = "Для поискового запроса по исполнителю {0} должен отображаться не пустой список треков")
    void searchResultsShouldNotBeEmpty(String searchQuery) {
        $(".d-search__button").click();
        $(".d-input__field").should(appear).setValue(searchQuery).pressEnter();
        $$(".d-track").shouldBe(sizeGreaterThan(0));
    }
}
