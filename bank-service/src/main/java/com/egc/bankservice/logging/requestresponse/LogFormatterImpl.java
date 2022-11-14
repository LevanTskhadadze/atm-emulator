package com.egc.bankservice.logging.requestresponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class LogFormatterImpl implements LogFormatter {

	@Override
	public String formatRequest(ContentCachingRequestWrapper request) {
		StringBuilder req = new StringBuilder();
		Map<String,String> parameters = getParameters(request);
		String body = getBody(request.getContentAsByteArray(), request.getCharacterEncoding());

		req.append("REQUEST:");
		req.append(" Caller = ").append("IP: ").append(request.getRemoteAddr()).append(",");
		req.append(" Method = [").append(request.getMethod()).append("],");
		req.append(" Path = [").append(request.getRequestURI()).append("],");

		if(!parameters.isEmpty()) {
			req.append(" Parameters = [").append(parameters).append("],");
		}

		if (StringUtils.isNotBlank(body)) {
			req.append(" Body = [").append(body).append("]");
		}

		return req.toString();
	}

	@Override
	public String formatResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long timeTaken) {
		StringBuilder resp = new StringBuilder();
		Map<String,String> headers = getHeaders(response);
		String body = getBody(response.getContentAsByteArray(), response.getCharacterEncoding());

		resp.append("RESPONSE:");
		resp.append(" Time Taken: ").append(timeTaken).append("ms,");
		resp.append(" Method = [").append(request.getMethod()).append("],");
		if(!headers.isEmpty()) {
			resp.append(" ResponseHeaders = [").append(headers).append("],");
		}
		resp.append(" ResponseBody = [").append(body).append("]");

		return resp.toString();
	}

	private Map<String,String> getHeaders(HttpServletResponse response) {
		Map<String,String> headers = new HashMap<>();
		Collection<String> headerMap = response.getHeaderNames();
		for(String str : headerMap) {
			headers.put(str,response.getHeader(str));
		}
		return headers;
	}

	private Map<String,String> getParameters(HttpServletRequest request) {
		Map<String,String> parameters = new HashMap<>();
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()) {
			String paramName = params.nextElement();
			String paramValue = request.getParameter(paramName);
			parameters.put(paramName,paramValue);
		}
		return parameters;
	}

	private String getBody(byte[] contentAsByteArray, String characterEncoding) {
		try {
			return new String(contentAsByteArray, characterEncoding);
		} catch (UnsupportedEncodingException e) {
			log.info("Cannot parse request/response body");
		}
		return "";
	}
}
