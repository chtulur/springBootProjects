package com.servermanagerfromscratch.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder //Accepts parent class. Doesn't extend anything but actually accepts Object now and also future proofs the app
@JsonInclude(NON_NULL) //null fields are not going to be included when building the json
public class Response {
  protected LocalDateTime timeStamp;
  protected int statusCode;
  protected HttpStatus status;
  protected String reason;
  protected String message;
  protected String developerMessage;
  protected Map<?, ?> data;
}
