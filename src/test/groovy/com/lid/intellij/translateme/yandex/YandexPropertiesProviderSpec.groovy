package com.lid.intellij.translateme.yandex

import spock.lang.Specification

/**
 * Specification suite for class {@link YandexPropertiesProvider}
 *
 * @author lukasz
 */
class YandexPropertiesProviderSpec extends Specification {

    def "should get all required properties"() {
        when:
            def properties = YandexPropertiesProvider.getProperties()

        then:
            assert properties != null
            assert properties.size() == YandexPropertiesProvider.YandexProperty.values().length
    }
}
