package com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}};

import java.util.function.Consumer;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class {{ cookiecutter._project_name_camel_case }} {

  public static void main(String[] args) {
    SpringApplication.run({{ cookiecutter._project_name_camel_case }}.class, args);
  }

  /*
	This bean is needed to enable the app to bind to the topic spring.application.cloud.stream.bindings.process-in-0.destination
	defined in application.properties. We can then create a state store for this topic to query its values when uploading queries.
 	*/
	@Bean
	public Consumer<GlobalKTable<String, String>> process() {
		return GlobalKTable::queryableStoreName;
	}
}
