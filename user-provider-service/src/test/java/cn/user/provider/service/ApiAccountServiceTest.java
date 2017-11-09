package cn.user.provider.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.SynthesizedAnnotation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.UserProviderServiceApp;
import cn.entity.ApiAccountInfo;
import cn.service.ApiAccountInfoService;
import cn.thread.ThreadExecutorService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserProviderServiceApp.class) // 指定spring-boot的启动类
public class ApiAccountServiceTest {

	@Autowired
	private ApiAccountInfoService apiAccountInfoService;

	@Autowired
	private ThreadExecutorService threadExecutorService;

	// @Test
	public void saveCreUser() {
		ApiAccountInfo info = new ApiAccountInfo();
		info.setCreUserId(1604);
		info.setBdIp("1111111");
		info.setId(1);
		info.setVersion(0);
		// int row = apiAccountInfoService.update(info);
		// System.out.println(row);
	}

	@Test
	public void threadTask() {
		int i = 0;
		while(true){
			final int s = i;
			Runnable command = new Runnable() {
				@Override
				public void run() {
					System.out.println(s);
				}
			};
			i = i + 1;
			threadExecutorService.execute(command);
		}
	}

}
