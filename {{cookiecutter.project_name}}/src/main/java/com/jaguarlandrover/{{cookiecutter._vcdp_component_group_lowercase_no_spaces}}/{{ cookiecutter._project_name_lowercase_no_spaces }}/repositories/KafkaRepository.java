package com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.dtos.DataDto;
import com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.models.KafkaRecord;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class KafkaRepository {

    public static final String UNABLE_TO_PARSE_INPUT = "Unable to parse input";
    public static final String UNABLE_TO_PARSE_SAVED_RECORD = "Unable to parse saved record";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;
    private final InteractiveQueryService interactiveQueryService;

    @Value("${spring.cloud.stream.kafka.streams.bindings.process-in-0.consumer.materializedAs}")
    private String storeName;

    @Value("${spring.cloud.stream.bindings.process-in-0.destination}")
    private String topicName;

    public void saveRecord(KafkaRecord kafkaRecord) {
      try {
          kafkaTemplate.send(topicName, kafkaRecord.kafkaKey(), mapper.writeValueAsString(kafkaRecord));
      } catch (JsonProcessingException e) {
          throw new RuntimeException(UNABLE_TO_PARSE_INPUT, e);
      }
    }

    public Optional<DataDto> getRecordByKey(String key) {
      ReadOnlyKeyValueStore<String, String> keyValueStore = interactiveQueryService.getQueryableStore(storeName, QueryableStoreTypes.keyValueStore());
      String result = keyValueStore.get(key);

      if (Objects.isNull(result)) {
        return Optional.empty();
      }

      try {
        return Optional.of(mapper.readValue(result, DataDto.class));
      } catch (JsonProcessingException e) {
        log.error(UNABLE_TO_PARSE_SAVED_RECORD);
        return Optional.empty();
      }
    }

}
