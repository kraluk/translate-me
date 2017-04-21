package com.lid.intellij.translateme.yandex

import com.lid.intellij.translateme.rest.RestServiceInvoker
import spock.lang.Specification

/**
 * Specification suite for class {@link YandexService}
 *
 * @author lukasz
 */
class YandexServiceSpec extends Specification {

    RestServiceInvoker invoker
    YandexService service

    def setup() {
        invoker = Mock()
        service = new YandexService(invoker)
    }

    def "should get languages"() {
        when:
            def languages = service.getLanguages("en")

        then:
            with(languages) {
                getLangs() != null
                getLangs().size() == 3
                getPairs() != null
                getPairs().size() == 2
            }
        and:
            1 * invoker.get(_) >> "{\"dirs\":[\"az-ru\",\"be-bg\"],\"langs\":{\"af\":\"Afrikaans\",\"am\":\"Amharic\",\"ar\":\"Arabic\"}}"
    }

    def "should detect a language"() {
        when:
            def detected = service.detect("car")

        then:
            with(detected) {
                getCode() == 200
                getLang() == "en"
            }
        and:
            1 * invoker.get(_) >> "{\"code\":200,\"lang\":\"en\"}"
    }

    def "should do a translation"() {
        when:
            def translation = service.translate("samochod", "pl", "en")

        then:
            with(translation) {
                getCode() == 200
                getLang() == "pl-en"
                getText() != null
                getText().size() == 1
                getText().get(0) == "car"
            }
        and:
            1 * invoker.get(_) >> "{\"code\":200,\"lang\":\"pl-en\",\"text\":[\"car\"]}"
    }
}
