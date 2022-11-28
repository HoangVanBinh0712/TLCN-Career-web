package com.be.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.resetBuffer();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader("Content-Type", "application/json");
		response.getOutputStream().write(String
				.format("{\"message\":\"%s\",\"detail\":%s}", "You do not have permission to access this resource",
						new ObjectMapper().writer().withDefaultPrettyPrinter()
								.writeValueAsString(new AccessDeniedExceptionInfo(accessDeniedException)))
				.getBytes(StandardCharsets.UTF_8));
		response.flushBuffer();
	}

	@Getter
	@Setter
	private class AccessDeniedExceptionInfo {
		private String message;
		private Throwable[] suppressed;
		private String localizedMessage;

		public AccessDeniedExceptionInfo(AccessDeniedException accessDeniedException) {
			this.message = accessDeniedException.getMessage();
			this.suppressed = accessDeniedException.getSuppressed();
			this.localizedMessage = accessDeniedException.getLocalizedMessage();
		}
	}

}