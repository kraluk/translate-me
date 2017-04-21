package com.lid.intellij.translateme.rest

import org.apache.commons.lang.StringUtils
import spock.lang.Specification

/**
 * Specification suite for class {@link com.lid.intellij.translateme.rest.RestServiceInvoker}
 *
 * @author lukasz
 */
class RestServiceInvokerIntegrationSpec extends Specification {

    RestServiceInvoker invoker

    def setup() {
        invoker = new RestServiceInvoker()
    }

    def "should invoke the Yandex service"() {
        given:
            def request = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20150828T222514Z.96c635fa0967005b.781eebb21e0a7b0e9b3b4f2fb62a21a74400189f&ui=en"

        when:
            def result = invoker.get(request)

        then:
            assert StringUtils.isNotBlank(result)
    }

    def "should return an empty string when the request is badly constructed"() {
        given:
            def request = "https://dummy.service/"

        when:
            def result = invoker.get(request)

        then:
            assert StringUtils.isEmpty(result)
    }
}
