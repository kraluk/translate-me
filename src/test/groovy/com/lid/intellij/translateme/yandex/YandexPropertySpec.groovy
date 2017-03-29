package com.lid.intellij.translateme.yandex

import spock.lang.Specification
import spock.lang.Unroll

import static com.lid.intellij.translateme.yandex.YandexPropertiesProvider.YandexProperty

/**
 * Specification suite for class {@link YandexProperty}
 *
 * @author lukasz
 */
@Unroll
class YandexPropertySpec extends Specification {

    def "should get #expected from #key"() {
        when:
        def result = YandexProperty.getByKey(key)

        then:
        result == expected

        where:
        key << YandexProperty.values().toList().collect { e -> e.key }
        expected << YandexProperty.values()
    }

    def "should thrown an exception when illegal key is used"() {
        when:
        YandexProperty.getByKey("dummy")

        then:
        thrown(IllegalArgumentException)
    }
}
