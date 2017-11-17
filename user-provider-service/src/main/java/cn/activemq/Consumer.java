package cn.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import main.java.cn.domain.UserAccountDomain;

@Component
public class Consumer {

	@JmsListener(destination = "mytest.queue")
	@SendTo("out.queue")
	public UserAccountDomain receiveQueue(UserAccountDomain domain) {
		System.out.println("Consumer2收到的报文为:" + domain.getCreUserId() + "--" + domain.getAccount() + "--" + domain.getApiAccount());
		return domain;
	}
}
