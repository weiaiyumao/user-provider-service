package cn.controller.tds;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsDepartmentService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.UserRoleDepartmentViewDomain;

@RestController
@RequestMapping("/super")
public class TdsDeparTmentController{

	
	
	  @Autowired
	  private TdsDepartmentService  tdsDepartmentService;
	  
	  
	  /**
	   * 查询用户角色部门
	   * @param departName
	   * @param roleName
	   * @param createTime
	   * @param contact
	   * @param currentPage
	   * @param numPerPage
	   * @return
	   */
	  @RequestMapping(value="/pageUserRoleDepartmentView",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<PageDomain<UserRoleDepartmentViewDomain>> pageUserRoleDepartmentView(String departName,String roleName,String createTime,String contact,Integer currentPage,Integer numPerPage){
		  return  tdsDepartmentService.pageUserRoleDepartmentView(departName,roleName,createTime,contact,currentPage,numPerPage);
	   }
	  
	  /**
	   * 根据用户查询权限
	   * @param usreId
	   * @return
	   */
	  @RequestMapping(value="/funByUserId",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<List<TdsFunctionDomain>> funByuserId(Integer usreId){
		return tdsDepartmentService.funByuserId(usreId);
	  }

}

