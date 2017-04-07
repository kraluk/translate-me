package com.lid.intellij.translateme.yandex

import com.lid.intellij.translateme.rest.RestServiceInvoker
import spock.lang.Specification

/**
 * Specification suite for class {@link YandexService}
 *
 * @author lukasz
 */
class YandexServiceIntegrationSpec extends Specification {

    YandexService service

    def setup() {
        RestServiceInvoker invoker = new RestServiceInvoker()

        service = new YandexService(invoker)
    }

    def "should get languages"() {
        given:
        def uiLanguage = "pl"

        when:
        def languages = service.getLanguages(uiLanguage)

        then:
        with(languages) {
            getLangs() != null
            !getLangs().isEmpty()
        }

    }

    def "should detect a language"() {
        given:
        def text = "there was a car behind the our favourite tree"

        when:
        def detected = service.detect(text)

        then:
        with(detected) {
            getCode() == 200
            getLang() == "en"
        }
    }

    def "should do a translation"() {
        when:
        def translation = service.translate("samochód", "pl", "en")

        then:
        with(translation) {
            getCode() == 200
            getLang() == "pl-en"
            getText() != null
            !getText().isEmpty()
        }
    }
}
