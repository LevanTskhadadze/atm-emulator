package com.egc.bankservice.logging.requestresponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Service
public class LoggingManagerImpl implements LoggingManager {

	@Autowired
	private LogFormatter logFormatter;

	@Override
	public void logReq(ContentCachingRequestWrapper request) {
		String requestLog = logFormatter.formatRequest(request);
		log.info(requestLog);
	}

	@Override
	public void logResp(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long timeTaken) {
		String responseLog = logFormatter.formatResponse(request, response, timeTaken);
		log.info(responseLog);
	}
}
