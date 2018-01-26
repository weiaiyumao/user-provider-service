package cn;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import rocketmq.annotation.RocketmqEvent;

/**
 * 
 * @author rain
 *
 */
@Component
public class consumerDemo {
	
	private final static Logger logger = LoggerFactory.getLogger(consumerDemo.class);
	
	
	@Value("${server.port}")
    String port;
	
	@EventListener(condition = "#event.topic=='TopicTest' && #event.tag=='TagA'")
	public void rocketmqMsgListen(RocketmqEvent event) {
		DefaultMQPushConsumer consumer = event.getConsumer();
		try {
			logger.error( "端口号" + port + "成功消费消息" + event.getMsg("utf-8"));
			//TODO 进行业务处理
		} catch (Exception e) {
			if(event.getMessageExt().getReconsumeTimes()<=3){//重复消费3次
				try {
					consumer.sendMessageBack(event.getMessageExt(), 2);
				} catch (Exception e1) {
					//TODO 消息消费失败，进行日志记录
					logger.error("端口号" + port + "消费消息失败"+e.getMessage());
				}
			} else {
				//TODO 消息消费失败，进行日志记录
				logger.error("端口号" + port + "消费消息失败"+e.getMessage());
			}
		}
	}
	
	@EventListener(condition = "#event.topic=='TopicTest' && #event.tag=='TagC'")
	public void rocketmqMsgListen2(RocketmqEvent event) {
		DefaultMQPushConsumer consumer = event.getConsumer();
		try {
			logger.error("端口号" + port + "成功消费消息" + event.getMsg("utf-8"));
			//TODO 进行业务处理
		} catch (Exception e) {
			if(event.getMessageExt().getReconsumeTimes()<=3){//重复消费3次
				try {
					consumer.sendMessageBack(event.getMessageExt(), 2);
				} catch (Exception e1) {
					//TODO 消息消费失败，进行日志记录
					logger.error("端口号" + port + "消费消息失败"+e.getMessage());
				}
			} else {
				//TODO 消息消费失败，进行日志记录
				logger.error("端口号" + port + "消费消息失败"+e.getMessage());
			}
		}
	}
	
}
