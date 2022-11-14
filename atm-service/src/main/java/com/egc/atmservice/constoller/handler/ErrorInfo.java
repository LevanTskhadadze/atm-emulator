package com.egc.atmservice.constoller.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorInfo {

    private String message;

    private String field;
}
