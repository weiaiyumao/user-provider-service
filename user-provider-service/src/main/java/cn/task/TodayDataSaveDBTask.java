package cn.task;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.controller.PayCallbackController;
import cn.dao.CreUserMapper;
import cn.dao.MessageInfoMapper;
import cn.entity.CreUser;
import cn.entity.MessageInfo;
import cn.utils.DateUtils;
import main.java.cn.hhtp.service.SendRequestService;
import main.java.cn.hhtp.util.MD5Util;


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

	@Autowired
	private CreUserMapper creUserMapper;
	
	@Value("${run_date}")
	private String run_date;
	
	private final static Logger logger = LoggerFactory.getLogger(PayCallbackController.class);

	// 该任务执行一次 时间 秒 分 时 天 月 年
	@Scheduled(cron = "0 36 09 17 11 ?")
	public void ClDateSaveDbTask() {
		String[] strDate = run_date.split(",");			
		for (int i = 0; i < strDate.length; i++) {
			this.insertMysqlInfo(strDate[i]);
		}		
	}
	
	// 该任务执行一次 时间 秒 分 时 天 月 年
	public void insertMysqlInfo(String strdate) {

		try {
			logger.info("------------------开始定时任务------------------");
			Settings settings = Settings.builder().put("cluster.name", "cl-es-cluster")
					.put("client.transport.sniff", true).put("client.transport.ping_timeout", "25s").build();

			@SuppressWarnings("resource")
			TransportClient client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.20.20"), 9300));
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					QueryBuilder qb = QueryBuilders.termsQuery("reportTime", strdate);
//					QueryBuilder qb = QueryBuilders.rangeQuery("reportTime").from(strdate).to(DateUtils.addOneDay(strdate)).includeLower(true).includeUpper(false);

					Long a = System.currentTimeMillis();
					SearchResponse scrollResp = client.prepareSearch("201711").setQuery(qb)
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
						logger.info("------------------"+strdate+"数据导入mysql已完成------------------");
					} while (scrollResp.getHits().getHits().length != 0);
				}
			}, strdate+"线程开始执行定时任务入库").start();	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====执行创蓝数据入库出现异常：" + e.getMessage());
		} 

	}
	
	// 该任务执行一次 时间 秒 分 时 天 月 年
		@Scheduled(cron = "0 46 09 10 11 ?")
		public void getMessageNumTask() {
			String str1 = "15842552369,15776312082,18576081428,15998211128,13660387808,13618930028,13731116212,15945989290,,15185029864,18635767669,13822524730,13438660065,13924902802,18392526562,13649252460,13910331103,,13404563666,13693971596,15806029667,15801214259,15706329363,18838125816,15109912010,13906187406,18346145698";
			String str2 = ",13821958789,15093231986,15231722163,15801326538,13596976880,13583983830,13717184686,,13674000384,13433939962,13781113670,13938971983,13579446886,18266953693,,13837169123,13674000384,,13391016636,13391016636,15090037999,,18741896035,13724960406,13691824759";
			String str3 = ",13931515592,15076053072,15076053072,13674000384,13560659001";
			String mobilestr = str1+str2+str3;
			String[] mobileList = mobilestr.split(",");
			logger.info("------------------开始定时任务------------------");
			for(String mobile : mobileList){
				getMessageNum(mobile);
			}
			logger.info("------------------任务完成------------------");
			
		}
	
	// 该任务执行一次 时间 秒 分 时 天 月 年
		public void getMessageNum(String mobile) {

			try {
				
				Settings settings = Settings.builder().put("cluster.name", "cl-es-cluster")
						.put("client.transport.sniff", true).put("client.transport.ping_timeout", "25s").build();

				@SuppressWarnings("resource")
				TransportClient client = new PreBuiltTransportClient(settings)
						.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.16.20.20"), 9300));
				
						
				QueryBuilder qb = QueryBuilders.termsQuery("mobile", mobile);
				String monthstrs = "201710,201709,201708,201707,201706,201705,201704,201703,201702,201701";
				String[] monthList = monthstrs.split(",");
				for(String month: monthList){
					SearchResponse scrollResp = client.prepareSearch(month).setQuery(qb).setScroll(new TimeValue(60000)).setSize(1000).get();
					int num = scrollResp.getHits().getHits().length;
					System.out.println(mobile + "号码, 在" + month + "月, 出现的次数为： " + num);
				}				
				
					
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("=====执行查询出现异常：" + e.getMessage());
			} 

		}
		
		public void updateCreUseErpid(String mobile){
			net.sf.json.JSONObject josnObject = new net.sf.json.JSONObject();
			// 给erp 推送下单成功消息
			JSONObject jsonAccount = new JSONObject();
			String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
			String tokenValue = MD5Util.getInstance().getMD5Code(timestamp + apiKey);
			jsonAccount.put("account_name", user.getUserPhone());
			jsonAccount.put("money", order.getMoney());
			jsonAccount.put("amount", order.getNumber());
			jsonAccount.put("bank", "5");
			jsonAccount.put("pay_mode", "1");
			jsonAccount.put("remark", "手机号：" + user.getUserPhone() + "客户充值成功");
			jsonAccount.put("sequence", order.getTradeNo());
			jsonAccount.put("timestamp", timestamp);
			jsonAccount.put("token", tokenValue);
			logger.info("下单成功,请求参数:" + jsonAccount);
			String responseStr = HttpUtil.createHttpPost(apiHost + orderUrl, jsonAccount);
			logger.info("下单成功,请求结果:" + responseStr);
			JSONObject json = JSONObject.fromObject(responseStr);
			if (json.get("status").equals("success")) {
				// erp 请求成功 记录erpid
				JSONObject data = JSONObject.fromObject(json.get("data"));
				order.setOrderNo("ERPID_" + data.get("id").toString());
				this.updateTrdOrder(order);
			}

		}
		
		// 该任务执行一次 时间 秒 分 时 天 月 年
		@Scheduled(cron = "0 17 10 01 12 ?")
		public void updateCreUseErpid() {
			List<CreUser> creUserList= creUserMapper.findAll();		
			for (CreUser creUser: creUserList) {
				this.updateCreUseErpid(creUser.getUserPhone());
			}	
		}

}
