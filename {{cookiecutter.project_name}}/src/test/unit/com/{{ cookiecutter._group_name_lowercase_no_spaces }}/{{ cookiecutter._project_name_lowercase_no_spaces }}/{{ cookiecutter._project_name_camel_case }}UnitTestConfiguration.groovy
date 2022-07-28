package com.{{ cookiecutter._group_name_lowercase_no_spaces }}.{{ cookiecutter._project_name_lowercase_no_spaces }}

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@Configuration
class {{ cookiecutter._project_name_camel_case }}UnitTestConfiguration {

    public static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))

    static class KafkaServerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        void initialize(final ConfigurableApplicationContext applicationContext) {
            kafka.start()
            TestPropertyValues.of(
                    "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers())
                    .applyTo(applicationContext.getEnvironment())
        }
    }
}
