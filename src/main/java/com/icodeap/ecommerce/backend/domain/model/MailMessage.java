package com.icodeap.ecommerce.backend.domain.model;

import lombok.Data;

@Data
public class MailMessage {
	private String to;
	private String title;
	private String message;
}
