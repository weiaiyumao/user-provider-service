package cn.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.service.TrdOrderService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.TrdOrderDomain;

@RestController
@RequestMapping("/trdorder")
public class TrdorderController {

	@Autowired
	private TrdOrderService trdOrderService;
	
	@RequestMapping("/alipayrecharge")
	public BackResult<String> alipayrecharge(Integer creUserId,Integer productsId,Integer number,BigDecimal money,String payType,String type){
		return trdOrderService.recharge(creUserId,productsId,number,money,payType,type);
	}
	
	@RequestMapping("/findOrderInfoByOrderNo")
	public BackResult<TrdOrderDomain> findOrderInfoByOrderNo(String orderNo){
		return trdOrderService.findOrderInfoByOrderNo(orderNo);
	}
}
