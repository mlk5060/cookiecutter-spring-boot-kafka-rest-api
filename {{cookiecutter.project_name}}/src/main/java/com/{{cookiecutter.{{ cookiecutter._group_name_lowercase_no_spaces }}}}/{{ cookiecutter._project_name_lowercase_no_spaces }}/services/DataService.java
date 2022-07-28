package com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.services;

import com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.dtos.DataDto;
import com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.repositories.KafkaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataService {

  private final KafkaRepository kafkaRepository;

  public void createData(DataDto dataDto) {
    kafkaRepository.saveRecord(dataDto);
  }

  public Optional<DataDto> getDataWithKey(String key) {
    return kafkaRepository.getRecordByKey(key);
  }
}