package com.icodeap.ecommerce.backend.domain.port;

import com.icodeap.ecommerce.backend.domain.model.MailMessage;

public interface IMailSenderService {
	public void sendSimpleMessage(MailMessage message);
}
