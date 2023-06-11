package com.influence.webservermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResponse {
   protected LocalDateTime timeStamp;
   protected int statusCode;
   protected HttpStatus status;
   protected String message;
   protected String developerMessage;
   protected Map<?,?> data;

}