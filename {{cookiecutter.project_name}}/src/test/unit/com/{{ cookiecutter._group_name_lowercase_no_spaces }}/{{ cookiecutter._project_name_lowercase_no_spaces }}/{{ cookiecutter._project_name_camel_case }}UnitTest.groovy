package com.{{ cookiecutter._group_name_lowercase_no_spaces }}.{{ cookiecutter._project_name_lowercase_no_spaces }}

import com.{{ cookiecutter._group_name_lowercase_no_spaces }}.{{ cookiecutter._project_name_lowercase_no_spaces }}.dtos.DataDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.ResponseEntity
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ContextConfiguration(initializers = {{ cookiecutter._project_name_camel_case }}UnitTestConfiguration.KafkaServerInitializer.class, classes = {{ cookiecutter._project_name_camel_case }}.class)
@SpringBootTest(classes = {{ cookiecutter._project_name_camel_case }}.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit")
@DirtiesContext
class {{ cookiecutter._project_name_camel_case }}UnitTest extends Specification {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private CountDownLatch databaseTopicCountdownLatch

    def "test"() throws Exception {
        given: "Data to save"
            String key = "foo"
            String value = "bar"
            DataDto dataDto = new DataDto(key, value)
            databaseTopicCountdownLatch = new CountDownLatch(1)
            this.restTemplate.postForObject("http://localhost:" + port + "/data", dataDto, Void.class)
            databaseTopicCountdownLatch.await(10, TimeUnit.SECONDS)

        when: "The data is requested via its key"
            ResponseEntity<DataDto> result = this.restTemplate.getForEntity("http://localhost:" + port + "/data/" + key, DataDto.class)

        then: "The data is returned"
            result.statusCodeValue == 200
            result.hasBody()
            result.body.toString() == dataDto.toString()
    }

    @KafkaListener(topics = "\${spring.cloud.stream.bindings.process-in-0.destination}", groupId = "\${spring.application.name}-group")
    void databaseTopicListener(){
        databaseTopicCountdownLatch.countDown()
    }
}
