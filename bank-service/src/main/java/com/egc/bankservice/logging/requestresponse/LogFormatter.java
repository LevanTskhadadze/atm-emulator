package com.egc.bankservice.logging.requestresponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public interface LogFormatter {

	String formatRequest(ContentCachingRequestWrapper request);

	String formatResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long timeTaken);
}
