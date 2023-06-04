package com.hptables.resources;

import java.util.Map;
import static java.time.LocalDateTime.now;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.OK;

import com.hptables.model.Response;
import com.hptables.services.implementations.StudentServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentResource {
  private final StudentServiceImpl studentService;

  @GetMapping("/list")
  public ResponseEntity<Response> getClasses() {
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(Map.of("students", studentService.list()))
        .message("Students retreived")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }
}
