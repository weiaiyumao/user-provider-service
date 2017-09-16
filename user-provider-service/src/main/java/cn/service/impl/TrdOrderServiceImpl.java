package cn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.TrdOrderMapper;
import cn.entity.TrdOrder;
import cn.service.TrdOrderService;
import cn.utils.CommonUtils;

@Service
public class TrdOrderServiceImpl implements TrdOrderService{

	@Autowired
	private TrdOrderMapper trdOrderMapper;
	
	@Override
	public TrdOrder findByOrderNo(String orderNo) {
		List<TrdOrder> list = trdOrderMapper.findByOrderNo(orderNo);
		return CommonUtils.isNotEmpty(list) ? null : list.get(0);
	}

	@Override
	public TrdOrder findByClOrderNo(String clOrderNo) {
		List<TrdOrder> list = trdOrderMapper.findByClOrderNo(clOrderNo);
		return CommonUtils.isNotEmpty(list) ? null : list.get(0);
	}

	@Override
	public int saveTrdOrder(TrdOrder trdOrder) {
		return trdOrderMapper.saveTrdOrder(trdOrder);
	}

	@Override
	public int updateTrdOrder(TrdOrder trdOrder) {
		return trdOrderMapper.updateTrdOrder(trdOrder);
	}

}
