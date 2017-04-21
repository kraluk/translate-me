package com.lid.intellij.translateme.yandex

import spock.lang.Specification

import static com.lid.intellij.translateme.rest.RestServiceInvoker.DEFAULT_ENCODING
import static com.lid.intellij.translateme.yandex.YandexRequestBuilder.YandexMethod.*

/**
 * Specification suite for class {@link YandexRequestBuilder}
 *
 * @author lukasz
 */
class YandexRequestBuilderSpec extends Specification {

    def "should build *detect* request"() {
        given:
            def text = "sample example text to detect"
            def expectedText = URLEncoder.encode(text, DEFAULT_ENCODING)

        when:
            def request = YandexRequestBuilder.forMethod(DETECT)
                    .withText(text)
                    .build()

        then:
            with(request) {
                contains("https")
                contains("yandex")
                contains("detect?key")
                contains(expectedText)
            }
    }

    def "should build *getLangs* request"() {
        given:
            def ui = "en"

        when:
            def request = YandexRequestBuilder.forMethod(GET_LANGUAGES)
                    .withUi(ui)
                    .build()

        then:
            with(request) {
                contains("https")
                contains("yandex")
                contains("getLangs?key")
                contains(ui)
            }
    }

    def "should build *translate* request"() {
        given:
            def langFrom = "en"
            def langTo = "pl"
            def text = "some example text to translate"
            def expectedText = URLEncoder.encode(text, DEFAULT_ENCODING)

        when:
            def request = YandexRequestBuilder.forMethod(TRANSLATE)
                    .withLangFrom(langFrom)
                    .withLangTo(langTo)
                    .withText(text)
                    .build()

        then:
            with(request) {
                contains("https")
                contains("yandex")
                contains("translate?key")
                contains(langFrom)
                contains(langTo)
                contains(expectedText)
            }
    }

    def "should not accept nullified method type"() {
        when:
            YandexRequestBuilder.forMethod(null)

        then:
            thrown(IllegalArgumentException)
    }

    def "should not accept nullified text parameter"() {
        when:
            YandexRequestBuilder.forMethod(TRANSLATE)
                    .withText(null)

        then:
            thrown(IllegalArgumentException)
    }

    def "should not accept nullified ui parameter"() {
        when:
            YandexRequestBuilder.forMethod(TRANSLATE)
                    .withUi(null)

        then:
            thrown(IllegalArgumentException)
    }

    def "should not accept nullified langFrom parameter"() {
        when:
            YandexRequestBuilder.forMethod(TRANSLATE)
                    .withLangFrom(null)

        then:
            thrown(IllegalArgumentException)
    }

    def "should not accept nullified langTo parameter"() {
        when:
            YandexRequestBuilder.forMethod(TRANSLATE)
                    .withLangTo(null)

        then:
            thrown(IllegalArgumentException)
    }
}
