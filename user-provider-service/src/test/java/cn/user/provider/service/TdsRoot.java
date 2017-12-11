package cn.user.provider.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.netflix.discovery.converters.Auto;

import cn.UserProviderServiceApp;
import cn.entity.TdsFunction;
import cn.entity.TdsFunctionRole;
import cn.entity.TdsModular;
import cn.entity.TdsUserRole;
import cn.service.TdsFunctionRoleService;
import cn.service.TdsFunctionService;
import cn.service.TdsModularService;
import cn.service.TdsUserRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsFunctionDomain;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=UserProviderServiceApp.class)// 指定spring-boot的启动类   
public class TdsRoot {
	   
	  
	  @Autowired
	  private TdsUserRoleService tdsUserRoleService;  //用户与角色
	  
	  @Autowired
	  private TdsFunctionService  tdsFunctionService;  //功能
	  
	  @Autowired
	  private TdsFunctionRoleService  tdsFunctionRoleService;   
	  
	  @Autowired
	  private TdsModularService tdsModularService;
	  
	  
	 //  @Autowired
	  // private TdsFunctionService tdsFunctionService;
	  
	  @Test
	  public void save(){
		   TdsUserRole tds=new TdsUserRole();
		   tds.setCreater(1);
		   tds.setUpdater(1);
		   tds.setRoleId(1);
		   tds.setUserId(1);
		   tdsUserRoleService.saveTdsUserRole(tds);
	  }
	  
	 @Test
	  public void queryTdsUser(){
		  tdsFunctionService.deleteById(3);
	  }
	  
	  @Test
	  public void insertTdsUser(){
		  TdsFunction tf=new TdsFunction();
		  tf.setUrl("/url/info2");
		  tf.setParentId(1);
		  tf.setRemarks("12备注3");
		  tf.setName("fds");
		 // System.out.println(tdf);
	  }
	  
	  
	  @Test
	  public void update(){
		  TdsModular entity=new TdsModular();
		  entity.setId(2);
		  entity.setName("f");
		  tdsModularService.updateTdsModular(entity);
	  }
	  
	   @Test
	   public void selectAll(){
		   TdsFunctionDomain entity=new TdsFunctionDomain();
		   entity.setName("fds");
		   BackResult<List<TdsFunctionDomain>> obj=tdsFunctionService.selectAll(entity);
		   System.out.println(obj.getResultObj().size());
	   }
	  
	  
}
