package com.egc.bankservice.controller.handler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorInfo {

    private String message;

    private String field;
}
