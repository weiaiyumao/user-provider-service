package cn.task;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.controller.PayCallbackController;
import cn.dao.MessageInfoMapper;
import cn.entity.MessageInfo;
import cn.utils.DateUtils;


/**
 * 每日定时任务入库
 * 
 * @author ChuangLan
 *
 */
@Component
@Configuration
@EnableScheduling
public class TodayDataSaveDBTask {

	@Autowired
	private MessageInfoMapper messageInfoMapper;

	private final static Logger logger = LoggerFactory.getLogger(PayCallbackController.class);

	// 该任务执行一次 时间 秒 分 时 天 月 年
	@Scheduled(cron = "0 30 14 19 10 ?")
	public void ClDateSaveDbTask() {
		try {
			logger.info("------------------开始定时任务------------------");
			Settings settings = Settings.builder().put("cluster.name", "cl-es-cluster").put("client.transport.sniff", true)
					.put("client.transport.ping_timeout", "25s").build();

			@SuppressWarnings("resource")
			TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName("172.16.20.20"), 9300));

			SearchResponse scrollResp = client.prepareSearch("201705", "201705").addSort("_doc", SortOrder.ASC)
					.setScroll(new TimeValue(60000)).setSize(1000).get();
			
			long startTime = System.currentTimeMillis();
			long endTime;
            int i=1;
			do {
				for (SearchHit hit : scrollResp.getHits().getHits()) 
				{
					String json = hit.getSourceAsString();
					JSONObject backjson = (JSONObject) JSONObject.parse(json);
					String threestr = "130,131,132,133,134,135,136,137,138,139,145,147,150,151,152,153,155,156,157,158,159,171,173,175,176,177,178,180,181,182,183,184,185,186,187,188,189";
					String fourstr = "1700,1701,1702,1703,1704,1705,1706,1707,1708,1709";
					String mobile = backjson.getString("mobile");
					String mobile_three = mobile.substring(0, 3);
					String mobile_four = mobile.substring(0, 4);
					Map<String,Object> param = new HashMap<>();	
					param.put("moblie", mobile);
					param.put("delivrd", backjson.getString("delivrd"));
					param.put("reportTime", DateUtils.parseDate(backjson.getString("reportTime"), "yyyy-MM-dd hh:mm:ss"));
					if(threestr.contains(mobile_three) && !fourstr.contains(mobile_four)){
						param.put("tablename", "message_"+mobile_three);
					}else if(!threestr.contains(mobile_three) && fourstr.contains(mobile_four)){
						param.put("tablename", "message_"+mobile_four);
					}else{
						param.put("tablename", "message_000");
					}
					Date reportTime = null;
					MessageInfo temple= messageInfoMapper.findByMobile(param);
					if(temple == null){
						reportTime = DateUtils.parseDate("1990-01-01 12:00:00", "yyyy-MM-dd hh:mm:ss");
					}else{
						reportTime = DateUtils.parseDate(DateUtils.formatDate(temple.getReportTime(), "yyyy-MM-dd hh:mm:ss"),"yyyy-MM-dd hh:mm:ss");
					}					
					Date ss = DateUtils.parseDate(backjson.getString("reportTime"), "yyyy-MM-dd hh:mm:ss");
					if(mobile.length()==11){
						if(ss.getTime()>reportTime.getTime()){
							messageInfoMapper.save(param);	
							endTime = System.currentTimeMillis();
							System.out.println("已运行"+(endTime - startTime)/1000+"秒，数据正在导入，已导入: " + i + " 行, " + "clientMsgId的值为: " + backjson.getString("clientMsgId"));
						}
					}
					i++;
										
				}
				
				scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000))
						.execute().actionGet();
			} while (scrollResp.getHits().getHits().length != 0);
			System.out.println("数据导入mysql已完成");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====执行创蓝数据入库出现异常：" + e.getMessage());
		}


	}
	


}
