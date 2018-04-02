package cn.user.provider.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import cn.UserProviderServiceApp;
import cn.dao.CreUserAccountMapper;
import cn.dao.tds.TdsStateInfoMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.entity.CreUserAccount;
import cn.entity.tds.TdsAccountBank;
import cn.service.tds.TdsAccountBankService;
import cn.service.tds.TdsDepartmentService;
import cn.service.tds.TdsFunctionService;
import cn.service.tds.TdsMoneyApprovalService;
import cn.service.tds.TdsStateInfoSerrvice;
import cn.service.tds.TdsUserRoleService;
import cn.service.tds.TdsUserService;
import cn.utils.CommonUtils;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsAccountBankDomain;
import main.java.cn.domain.tds.TdsDepartmentDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
import main.java.cn.domain.tds.TdsStateInfoDomain;
import main.java.cn.domain.tds.TdsUserDomain;
import main.java.cn.domain.tds.TdsUserRoleDomain;
import main.java.cn.domain.tds.UserRoleDepartmentViewDomain;
import main.java.cn.enums.TdsEnum.ACCOUNT;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=UserProviderServiceApp.class)// 指定spring-boot的启动类   
public class TdsRoot {
	   
	  
	  @Autowired
	  private TdsUserRoleService tdsUserRoleService;  //用户与角色
	  
	  @Autowired
	  private TdsFunctionService  tdsFunctionService;  //功能
//	  
//	  @Autowired
//	  private TdsFunctionRoleService  tdsFunctionRoleService;   
//	  
//	  @Autowired
//	  private TdsModularService tdsModularService;
	  
	  @Autowired
	  private TdsUserService tdsUserService;
	  
	  @Autowired
	  private TdsDepartmentService tdsDepartmentService;
	  
	  @Autowired
	  private TdsStateInfoSerrvice tdsStateInfoSerrvice;
	  
	  @Autowired
	  private TdsAccountBankService tdsAccountBankService;
	  
	  @Autowired
	  private TdsMoneyApprovalService tdsMoneyApprovalService;
	  
	  
	  @Autowired
	  private TdsUserCustomerMapper tdsUserCustomerMapper;
	  
	  @Autowired
	  private CreUserAccountMapper creUserAccountMapper;
	
	  
	  
	  
	  
	  @Test
	  public void test2(){
		  CreUserAccount cua=new CreUserAccount();
		  cua.setCreUserId(1575);
		  cua.setRqAccount(1000);
		  creUserAccountMapper.subByUserId(cua);
		 
	  }
	  
	  @Test
	  public void list3(){
		try {
			System.out.println(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 // tdsStateInfoSerrvice.pageTdsStateInfo(domai,1,2,"2017-12-20");
	  }
	   
	   
	   
	  @Test
	  public void testbank(){
		  BackResult<List<Map<String, Object>>>  map=tdsAccountBankService.selectAllBankName();
		  System.out.println(map);
	  }
	  
	  
	  @Test
	  public void page(){
		System.out.println(ACCOUNT.ADD);
	  }
	  
	  
	  @SuppressWarnings("unchecked")
	  @Test
	  public void getStrMap(){
		    List<Map<String,String>> entity=new ArrayList<>();
			String jsonStr=tdsDepartmentService.selectDepartmentRoleByUserId(1);
		    Gson gson=new Gson();
		    entity = gson.fromJson(jsonStr,entity.getClass());
		    System.out.println(entity);
		    System.out.println(BackResult.ok(entity));
	  }
	  
	  
	  @Test
	  public void save(){
		  TdsUserDomain entity=new TdsUserDomain();
		  entity.setName("111122");
		 // entity.setIp("127.0.0.1");
		  entity.setPassword("12345678a");
		 // BackResult<TdsUserDomain> obj=tdsUserService.save(entity,"公司名称7","wwww.baidu.com4");
		  //System.out.println(obj);
	  }
	  
	  @Test
	  public void save1(){
		  Integer[] arr={1,2};
		  TdsUserDomain t=new TdsUserDomain();
		  t.setName("张44");
		  t.setPassword("1234567");
	//	 BackResult<Integer> obj=tdsDepartmentService.addUserConfig(t, 1,1, 1,arr,1);
		//  System.out.println(obj);
	  }
	  
	  
	 @Test
	  public void queryTdsUser(){
	  }
	  
	  @Test
	  public void insertTdsUser(){
		  TdsUserRoleDomain entity=new TdsUserRoleDomain();
		  entity.setUpdater(3);
		  entity.setCreater(1);
		  entity.setRoleId(1);
		  entity.setId(5);
		 // tdsFunctionService.saveTdsFunction(tf);
		  tdsUserRoleService.saveTdsUserRole(entity);
		 // System.out.println(tdf);
	  }
	  
	  
	  
	   @Test
	   public void selectAll(){
		   TdsUserRoleDomain entity=new TdsUserRoleDomain();
		   entity.setUserId(1);
		   BackResult<List<TdsUserRoleDomain>> obj=tdsUserRoleService.selectAll(entity);
		   System.out.println(obj.getResultObj().size());
	   }
	   
	   
	   
	  
	  
}
