package com.safetynet.safetynetalerts.responseentity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timestamp;
	private String statusCode;
	private String causedBy;
	private String message;
	
	public ExceptionResponse(LocalDateTime timestamp, String statusCode, String causedBy, String message) {
		super();
		this.timestamp = timestamp;
		this.statusCode = statusCode;
		this.causedBy = causedBy;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getCausedBy() {
		return causedBy;
	}

	public void setCausedBy(String causedBy) {
		this.causedBy = causedBy;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [timestamp=" + timestamp + ", statusCode=" + statusCode + ", causedBy=" + causedBy
				+ ", message=" + message + "]";
	}


	


	
	
	
	
	
}
