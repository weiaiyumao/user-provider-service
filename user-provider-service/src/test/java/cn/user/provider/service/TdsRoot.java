package cn.user.provider.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import cn.UserProviderServiceApp;
import cn.entity.tds.TdsModular;
import cn.service.tds.TdsFunctionService;
import cn.service.tds.TdsUserRoleService;
import cn.service.tds.TdsUserService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.TdsUserDomain;
import main.java.cn.domain.tds.TdsUserRoleDomain;
import main.java.cn.hhtp.util.MD5Util;

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
	  
	   
	  @Test
	  public void login(){
	  }
	  
	  
	  @Test
	  public void page(){
		  TdsUserDomain entity=new TdsUserDomain();
		  entity.setName("xx");
		  BackResult<PageDomain<TdsUserDomain>> page=tdsUserService.pageSelectAll(entity,2,3);
		  System.out.println(page);
	  }
	  
	  
	  @Test
	  public void save(){
		  TdsUserDomain entity=new TdsUserDomain();
		  entity.setName("xx");
		 // entity.setIp("127.0.0.1");
		  entity.setPassword("12345678a");
		  BackResult<TdsUserDomain> obj=tdsUserService.save(entity);
		  System.out.println(obj);
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
