package cn.user.provider.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.UserProviderServiceApp;
import cn.entity.CreUserAccount;
import cn.service.CreUserAccountService;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=UserProviderServiceApp.class)// 指定spring-boot的启动类   
public class CreUserAccountServiceTest {
	
	@Autowired  
    private CreUserAccountService creUserAccountService;  
	
	@Test  
    public void saveCreUser()  {  
    	CreUserAccount creUserAccount = new CreUserAccount();
    	creUserAccount.setCreUserId(1575);
		creUserAccount.setCreateTime(new Date());
		creUserAccount.setUpdateTime(new Date());
		int row = creUserAccountService.saveCreUserAccount(creUserAccount);
		System.out.println(row);
    } 
	
//	@Test  
    public void findAllUsers()  {  
		CreUserAccount creUserAccount = creUserAccountService.findCreUserAccountByUserId(1575);
		creUserAccount.setAccount(10000);
		creUserAccount.setUpdateTime(new Date());
		int row = creUserAccountService.updateCreUserAccount(creUserAccount);
		System.out.println(row);
    } 

}
