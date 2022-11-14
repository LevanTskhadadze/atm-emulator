package com.egc.bankservice.logging.requestresponse;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

public interface LoggingManager {

	void logReq(ContentCachingRequestWrapper request);

	void logResp(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long timeTaken);
}
