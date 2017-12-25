package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsStateInfoMapper;
import cn.entity.tds.TdsStateInfo;
import cn.service.tds.TdsStateInfoSerrvice;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsStateInfoDomain;

@Service
public class TdsStateInfoSerrviceImpl implements TdsStateInfoSerrvice {
	
	private final static Logger logger = LoggerFactory.getLogger(TdsStateInfoSerrviceImpl.class);

	@Autowired
	private TdsStateInfoMapper tdsStateInfoMapper;
	
	@Transactional
	@Override
	public BackResult<Integer> update(TdsStateInfoDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		domain.setUpdateTime(new Date());
		TdsStateInfo tds = new TdsStateInfo();
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsStateInfoMapper.update(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}
    
	@Transactional
	@Override
	public BackResult<Integer> deleteById(Integer id) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			tdsStateInfoMapper.deleteById(id);
			result.setResultObj(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据删除失败");
		}
		return result;
	}
	
	
	@Override
	public BackResult<PageDomain<TdsStateInfoDomain>> pageTdsStateInfo(PageAuto auto) {
		BackResult<PageDomain<TdsStateInfoDomain>> result = new BackResult<PageDomain<TdsStateInfoDomain>>();
		PageDomain<TdsStateInfoDomain> listDomain = null;
		List<TdsStateInfoDomain> list = new ArrayList<TdsStateInfoDomain>();
		try {
			// TODO
			// yyyy-mm-dd 天数加1
			if (null != auto.getStatTime() && !"".equals(auto.getStatTime())) {
				Date endTime = DateUtils.addDay(auto.getStatTime(), 1);
				auto.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			Integer count = tdsStateInfoMapper.queryCount(auto);
			if (count == 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有信息");
			}
			Integer cur=auto.getCurrentPage()<=0?1:auto.getCurrentPage();
			auto.setPageNumber((cur-1)*auto.getNumPerPage());
			List<TdsStateInfo> pageList = tdsStateInfoMapper.pageTdsStateInfo(auto);
			if (pageList.size() <= 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有账号权限信息");
			}
			TdsStateInfoDomain obj = null;
			for (TdsStateInfo item : pageList) {
				obj = new TdsStateInfoDomain();
				BeanUtils.copyProperties(item, obj);
				list.add(obj);
			}
			listDomain = new PageDomain<TdsStateInfoDomain>(auto.getCurrentPage(), auto.getNumPerPage(),count);
			listDomain.setTlist(list);
			result.setResultObj(listDomain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询功能出现系统异常：" + e.getMessage());
			return new BackResult<PageDomain<TdsStateInfoDomain>>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

}
