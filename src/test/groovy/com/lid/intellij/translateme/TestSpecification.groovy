package com.lid.intellij.translateme

import spock.lang.Specification

/**
 * Example Specification
 *
 * @author lukasz
 */
class TestSpecification extends Specification {

    def "just an example Spec"() {
        given:
        def value = "true"

        when:
        def result = Boolean.parseBoolean(value)

        then:
        result == true
    }
}
