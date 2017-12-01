package cn.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.CreUserAccountMapper;
import cn.dao.CreUserMapper;
import cn.dao.TrdOrderMapper;
import cn.entity.CreUser;
import cn.entity.CreUserAccount;
import cn.entity.TrdOrder;
import cn.service.CreUserAccountService;
import cn.utils.CommonUtils;
import cn.utils.Constant;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.ErpTradeDomain;
import main.java.cn.domain.TrdOrderDomain;
import main.java.cn.domain.UserAccountDomain;

@Service
public class CreUserAccountServiceImpl implements CreUserAccountService {

	private final static Logger logger = LoggerFactory.getLogger(CreUserAccountServiceImpl.class);

	@Autowired
	private CreUserAccountMapper creUserAccountMapper;

	@Autowired
	private CreUserMapper creUserMapper;

	@Autowired
	private TrdOrderMapper trdOrderMapper;

	@Override
	public CreUserAccount findCreUserAccountByUserId(Integer creUserId) {
		List<CreUserAccount> list = creUserAccountMapper.findCreUserAccountByUserId(creUserId);
		return CommonUtils.isNotEmpty(list) ? null : list.get(0);
	}

	@Override
	public int saveCreUserAccount(CreUserAccount creUserAccount) {
		return creUserAccountMapper.saveCreUserAccount(creUserAccount);
	}

	@Override
	public int updateCreUserAccount(CreUserAccount creUserAccount) {
		return creUserAccountMapper.updateCreUserAccount(creUserAccount);
	}

	@Override
	public BackResult<UserAccountDomain> findByMobile(String mobile) {

		BackResult<UserAccountDomain> result = new BackResult<UserAccountDomain>();

		try {
			List<CreUser> user = creUserMapper.findCreUserByUserPhone(mobile);

			if (null == user) {
				result.setResultMsg("系统未查询到该用户");
				result.setResultCode(ResultCode.RESULT_SUCCEED);
				return result;
			}

			CreUserAccount account = this.findCreUserAccountByUserId(user.get(0).getId());

			if (null == account) {
				result.setResultMsg("系统未查询到该用户账户信息");
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				return result;
			}

			UserAccountDomain accountDomian = new UserAccountDomain();
			accountDomian.setAccount(account.getAccount());
			accountDomian.setCreUserId(user.get(0).getId());

			result.setResultObj(accountDomian);
		} catch (Exception e) {
			logger.error("用户手机号：【" + mobile + "】执行获取账户信息发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		return result;
	}

	@Override
	public BackResult<ErpTradeDomain> rechargeOrRefunds(TrdOrderDomain trdOrderDomain) {

		BackResult<ErpTradeDomain> result = new BackResult<ErpTradeDomain>();

		// 账号检测
		CreUser creUser = creUserMapper.findByUserId(trdOrderDomain.getCreUserId());

		if (null == creUser) {
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("数据库不存在该用户");
			return result;
		}

		switch (trdOrderDomain.getType()) {
		case Constant.TRD_ORDER_TYPE_RECHARGE:
//			result = this.recharge(trdOrderDomain, creUser.getId());
			break;
		case Constant.TRD_ORDER_TYPE_REFUNDS:
//			result = this.refunds(trdOrderDomain, creUser.getId());
			break;
		default:
			result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
			result.setResultMsg(
					"用户ID为" + trdOrderDomain.getCreUserId() + "无法解析的数据类型：【" + trdOrderDomain.getType() + "】");
			break;
		}

		return result;
	}

	@Transactional
	public BackResult<Boolean> recharge(TrdOrderDomain trdOrderDomain, Integer creUserId) {
		logger.info("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行充值，充值金额：【" + trdOrderDomain.getMoney() + "】，充值条数；【"
				+ trdOrderDomain.getNumber() + "】");

		BackResult<Boolean> result = new BackResult<Boolean>();

		try {
			// 账户检测
			CreUserAccount creUserAccount = this.findCreUserAccountByUserId(creUserId);

			if (null == creUserAccount) {
				creUserAccount = new CreUserAccount();
				creUserAccount.setCreUserId(creUserId);
				creUserAccount.setCreateTime(new Date());
				creUserAccount.setUpdateTime(new Date());
				this.saveCreUserAccount(creUserAccount);
			}

			TrdOrder order = new TrdOrder();

			// 保存充值记录
			BeanUtils.copyProperties(trdOrderDomain, order);

			order.setCreUserId(creUserId);
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			order.setStatus(Constant.TRD_ORDER_STATUS_SUCCEED);
			order.setVersion(0);

			trdOrderMapper.saveTrdOrder(order);

			// 账户充值
			creUserAccount.setAccount(creUserAccount.getAccount() + order.getNumber());
			this.updateCreUserAccount(creUserAccount);

		} catch (Exception e) {
			logger.error("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行充值，充值金额：【" + trdOrderDomain.getMoney()
					+ "】，充值条数；【" + trdOrderDomain.getNumber() + "】，发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
		}

		return result;
	}

	@Transactional
	public BackResult<Boolean> refunds(TrdOrderDomain trdOrderDomain, Integer creUserId) {
		logger.info("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行退款，退款金额：【" + trdOrderDomain.getMoney() + "】，退款条数；【"
				+ trdOrderDomain.getNumber() + "】");

		BackResult<Boolean> result = new BackResult<Boolean>();

		try {
			// 账户检测
			CreUserAccount creUserAccount = this.findCreUserAccountByUserId(creUserId);

			if (null == creUserAccount) {
				creUserAccount = new CreUserAccount();
				creUserAccount.setCreUserId(creUserId);
				creUserAccount.setCreateTime(new Date());
				creUserAccount.setUpdateTime(new Date());
				this.saveCreUserAccount(creUserAccount);
			}

			TrdOrder order = new TrdOrder();

			// 保存充值记录
			BeanUtils.copyProperties(trdOrderDomain, order);

			order.setCreUserId(creUserId);
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			order.setStatus(Constant.TRD_ORDER_STATUS_SUCCEED);
			order.setVersion(0);

			trdOrderMapper.saveTrdOrder(order);

			// 账户充值
			creUserAccount.setAccount(creUserAccount.getAccount() - order.getNumber());
			this.updateCreUserAccount(creUserAccount);

		} catch (Exception e) {
			logger.error("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行退款，退款金额：【" + trdOrderDomain.getMoney()
					+ "】，退款条数；【" + trdOrderDomain.getNumber() + "】，发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
		}

		return result;
	}

	@Override
	public BackResult<List<TrdOrderDomain>> findTrdOrderByCreUserId(Integer creUserId) {

		BackResult<List<TrdOrderDomain>> result = new BackResult<List<TrdOrderDomain>>();
		
		List<TrdOrderDomain> list = new ArrayList<TrdOrderDomain>();
		
		try {
			
			List<TrdOrder> orderList = trdOrderMapper.findByCreUserId(creUserId);
			
			if (CommonUtils.isNotEmpty(orderList)) {
				result.setResultMsg("改用户没有订单信息");
			}
			
			for (TrdOrder trdOrder : orderList) {
				TrdOrderDomain domain = new TrdOrderDomain();
				BeanUtils.copyProperties(trdOrder, domain);
				list.add(domain);
			}
			
			result.setResultObj(list);
			
		} catch (Exception e) {
			logger.error("用户ID：【" + creUserId + "】查询订单信息发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
		}

		return result;
	}

	@Override
	public BackResult<Boolean> consumeAccount(String creUserId, String count) {
		
		BackResult<Boolean> result = new BackResult<Boolean>();

		CreUserAccount account = this.findCreUserAccountByUserId(Integer.valueOf(creUserId));
		
		try {
			if (null == account) {
				logger.error("用户ID：【" + creUserId + "】，不存在账户记录，数据落地异常");
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				result.setResultObj(Boolean.FALSE);
				result.setResultMsg("数据库不存在账户记录");
				return result;
			}
			
			if (Integer.valueOf(count) > account.getAccount()) {
				logger.error("用户ID：【" + creUserId + "】，当前用户余额条数不够本次消费");
				result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
				result.setResultObj(Boolean.FALSE);
				result.setResultMsg("当前用户余额条数不够本次消费");
				return result;
			}
			
			account.setAccount(account.getAccount() - Integer.valueOf(count));
			creUserAccountMapper.updateCreUserAccount(account);
			result.setResultObj(Boolean.TRUE);
		} catch (Exception e) {
			logger.error("用户ID：【" + creUserId + "】查询修改账户信息系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
		}
		
		return result;
	}

	@Override
	public BackResult<Boolean> consumeApiAccount(String creUserId, String count) {
		// TODO Auto-generated method stub
		return null;
	}

}
