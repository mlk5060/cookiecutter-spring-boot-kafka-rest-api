package com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.dtos;

import com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.models.KafkaRecord;
import lombok.Data;

@Data
public class DataDto implements KafkaRecord {
    private final String key;
    private final String value;

  @Override
  public String kafkaKey() {
    return key;
  }
}