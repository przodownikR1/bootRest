package pl.java.scalatech.web

import groovyx.net.http.RESTClient
import spock.lang.Specification

class SimpleRestControllerTest extends Specification {
    def "Should return 200 & a message with the input appended"() {
        setup:
        def endpoint = new RESTClient( 'http://localhost:8080/' )
        when:
        def resp = endpoint.get([ path: 'restSample'])
        then:
        with(resp) {
            status == 200
            contentType == "application/json"
        }
        with(resp.data) {
            resp.data.message == "test"
        }
    }
}


