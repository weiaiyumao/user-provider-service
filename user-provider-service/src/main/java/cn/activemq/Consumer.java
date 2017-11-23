package cn.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import cn.thread.ThreadExecutorService;
import main.java.cn.domain.TrdOrderDomain;
import main.java.cn.domain.UserAccountDomain;

@Component
public class Consumer {
	
	@Autowired
	private ThreadExecutorService threadExecutorService;

	@JmsListener(destination = "mytest.queue")
	@SendTo("out.queue")
	public TrdOrderDomain receiveQueue(UserAccountDomain domain) {
		TrdOrderDomain trd = new TrdOrderDomain();
		trd.setClOrderNo(domain.getCreUserId().toString());
		
		Runnable command = new Runnable() {
			@Override
			public void run() {
				System.out.println("Consumer2收到的报文为:" + domain.getCreUserId() + "--" + domain.getAccount() + "--"
						+ domain.getApiAccount());
			}
		};
		threadExecutorService.execute(command);
		return trd;
	}
}
