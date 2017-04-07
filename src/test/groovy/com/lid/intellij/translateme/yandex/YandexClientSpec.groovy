package com.lid.intellij.translateme.yandex

import com.lid.intellij.translateme.rest.RestServiceInvoker
import org.apache.commons.lang.StringUtils
import spock.lang.Specification

/**
 * Specification suite for class {@link YandexClient}
 *
 * @author lukasz
 */
class YandexClientSpec extends Specification {

    def "should get languages"() {
        given:
        RestServiceInvoker invoker = Mock()

        YandexClient client = new YandexClient()
        client.yandexServiceInvoker = invoker

        when:
        def languages = client.getLanguages("en") as String

        then:
        assert StringUtils.isNotBlank(languages)
        and:
        1 * invoker.get(_) >> "ok"
    }

    def "should detect a language"() {
        given:
        RestServiceInvoker invoker = Mock()

        YandexClient client = new YandexClient()
        client.yandexServiceInvoker = invoker

        when:
        def languages = client.detect("car") as String

        then:
        assert StringUtils.isNotBlank(languages)
        and:
        1 * invoker.get(_) >> "en"
    }

    def "should do a translation"() {
        given:
        RestServiceInvoker invoker = Mock()

        YandexClient client = new YandexClient()
        client.yandexServiceInvoker = invoker

        when:
        def languages = client.translate("samochod", "pl", "en") as String

        then:
        assert StringUtils.isNotBlank(languages)
        and:
        1 * invoker.get(_) >> "car"
    }
}
