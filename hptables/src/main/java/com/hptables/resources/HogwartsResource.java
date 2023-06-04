package com.hptables.resources;

import java.util.Map;
import static java.time.LocalDateTime.now;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.OK;

import com.hptables.model.Response;
import com.hptables.services.implementations.HogwartsServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class HogwartsResource {
  private final HogwartsServiceImpl hogwartsService;

  @GetMapping("/list")
  public ResponseEntity<Response> getClasses() {
    return ResponseEntity.ok(
      Response.builder()
        .timeStamp(now())
        .data(Map.of("classes", hogwartsService.list()))
        .message("Classes retreived")
        .status(OK)
        .statusCode(OK.value())
        .build()
    );
  }
}
