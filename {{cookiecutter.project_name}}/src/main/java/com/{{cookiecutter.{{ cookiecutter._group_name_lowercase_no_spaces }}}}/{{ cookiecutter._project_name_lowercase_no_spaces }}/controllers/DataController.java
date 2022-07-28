package com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.controllers;

import com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.dtos.DataDto;
import com.{{cookiecutter._group_name_lowercase_no_spaces}}.{{cookiecutter._project_name_lowercase_no_spaces}}.services.Service;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping(value = "/data")
    public HttpEntity<Object> createData(@RequestBody DataDto dataDto) {
        log.info("POST data {}", dataDto);
        dataService.createData(dataDto);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/data/{key}")
    public ResponseEntity<DataDto> getData(@PathVariable String key) {
      log.info("GET data with key {}", key);
      Optional<DataDto> result = dataService.getDataWithKey(key);
      if (result.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok(result.get());
    }
}
