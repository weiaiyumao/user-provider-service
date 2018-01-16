package cn.user.provider.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import cn.UserProviderServiceApp;
import cn.dao.tds.TdsStateInfoMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.entity.tds.TdsAccountBank;
import cn.entity.tds.TdsModular;
import cn.service.tds.TdsAccountBankService;
import cn.service.tds.TdsDepartmentService;
import cn.service.tds.TdsFunctionService;
import cn.service.tds.TdsMoneyApprovalService;
import cn.service.tds.TdsStateInfoSerrvice;
import cn.service.tds.TdsUserRoleService;
import cn.service.tds.TdsUserService;
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
	  
	  @Test
	  public void test1(){ 
		  tdsUserCustomerMapper.subMoneyAndCommission(1, "400.12");
		
	  }
	  
	  @Test
	  public void list(){
		   TdsDepartmentDomain tds=new TdsDepartmentDomain();
		   BackResult<List<TdsDepartmentDomain>>  list=tdsDepartmentService.selectAll(tds);
		   System.out.println(list);
	  }
	  
	  @Test
	  public void list2(){
		  tdsUserRoleService.queryUserByRoleName("");
	  }
	  
	  @Test
	  public void list3(){
		  TdsStateInfoDomain domai=new TdsStateInfoDomain();
		  domai.setStateOpe("OPE003");
		  domai.setStatePro("PRO002");
		 // tdsStateInfoSerrvice.pageTdsStateInfo(domai,1,2,"2017-12-20");
	  }
	   
	   
	   
	  @Test
	  public void login(){
	  }
	  
	  
	  @Test
	  public void page(){
		  tdsFunctionService.moduleLoadingByUsreId(1);
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
		 BackResult<TdsFunctionDomain> d=tdsFunctionService.loadById(67);
		 System.out.println(d);
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
	  public void update(){
		  TdsModular entity=new TdsModular();
		  entity.setId(2);
		  entity.setName("f");
		//  tdsModularService.updateTdsModular(entity);
	  }
	  
	   @Test
	   public void selectAll(){
		   TdsUserRoleDomain entity=new TdsUserRoleDomain();
		   entity.setUserId(1);
		   BackResult<List<TdsUserRoleDomain>> obj=tdsUserRoleService.selectAll(entity);
		   System.out.println(obj.getResultObj().size());
	   }
	   
	   
	   
	  
	  
}
