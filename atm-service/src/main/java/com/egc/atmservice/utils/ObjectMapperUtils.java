package com.egc.atmservice.utils;

import com.egc.atmservice.domain.exception.AtmException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import static com.egc.atmservice.domain.exception.ExceptionMassage.UNEXPECTED_ERROR;

public class ObjectMapperUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T deserialize(String string, Class<T> clazz) throws AtmException {
        if (StringUtils.isEmpty(string)) return null;

        try {
            return objectMapper.readValue(string, clazz);
        } catch (JsonProcessingException e) {
            throw new AtmException(UNEXPECTED_ERROR);
        }
    }
}
