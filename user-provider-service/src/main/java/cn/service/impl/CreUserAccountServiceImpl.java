package cn.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import main.java.cn.domain.page.PageDomain;

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

			if (CommonUtils.isNotEmpty(user)) {
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
			accountDomian.setApiAccount(account.getApiAccount());
			result.setResultObj(accountDomian);
		} catch (Exception e) {
			logger.error("用户手机号：【" + mobile + "】执行获取账户信息发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		return result;
	}

	@Transactional
	public synchronized BackResult<ErpTradeDomain> rechargeOrRefunds(TrdOrderDomain trdOrderDomain) {

		BackResult<ErpTradeDomain> result = new BackResult<ErpTradeDomain>();

		// 账号检测
		List<CreUser> userList = creUserMapper.findCreUserByUserPhone(trdOrderDomain.getMobile());

		if (CommonUtils.isNotEmpty(userList)) {
			result.setResultMsg("系统未查询到该用户");
			result.setResultCode(ResultCode.RESULT_SUCCEED);
			result.setResultObj(null);
			return result;
		}

		// 账户检测
		CreUserAccount creUserAccount = this.findCreUserAccountByUserId(userList.get(0).getId());

		if (null == creUserAccount) {
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("数据库不存在该用户的账户记录");
			result.setResultObj(null);
			return result;
		}

		// 设置用户id 和订单号
		trdOrderDomain.setCreUserId(userList.get(0).getId());
		trdOrderDomain.setOrderNo("CLSH_" + System.currentTimeMillis());

		switch (trdOrderDomain.getType()) {
		case Constant.TRD_ORDER_TYPE_RECHARGE:
			result = this.recharge(trdOrderDomain, creUserAccount);
			break;
		case Constant.TRD_ORDER_TYPE_REFUNDS:
			result = this.refunds(trdOrderDomain, creUserAccount);
			break;
		default:
			result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
			result.setResultMsg(
					"用户ID为" + trdOrderDomain.getCreUserId() + "无法解析的数据类型：【" + trdOrderDomain.getType() + "】");
			result.setResultObj(null);
			break;
		}

		return result;
	}

	@Transactional
	public BackResult<ErpTradeDomain> recharge(TrdOrderDomain trdOrderDomain, CreUserAccount creUserAccount) {

		BackResult<ErpTradeDomain> result = new BackResult<ErpTradeDomain>();

		try {

			// 检查订单编号防止重复提交
			List<TrdOrder> orderList = trdOrderMapper.findByClOrderNo(trdOrderDomain.getClOrderNo());

			if (!CommonUtils.isNotEmpty(orderList)) {
				result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
				result.setResultMsg("该订单已经处理，不能重复处理");
				result.setResultObj(null);
				return result;
			}

			TrdOrder order = new TrdOrder();

			// 保存充值记录
			BeanUtils.copyProperties(trdOrderDomain, order);

			if (trdOrderDomain.getProductsId() == 1) {

				if (new BigDecimal(950).equals(trdOrderDomain.getMoney())) {
					order.setNumber(500000);
					order.setMoney(new BigDecimal(950));
				} else {
					result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
					result.setResultMsg("产品1充值金额必须为950");
					result.setResultObj(null);
					return result;
				}

			}

			if (trdOrderDomain.getProductsId() == 2) {

				if (new BigDecimal(9000).equals(trdOrderDomain.getMoney())) {
					order.setNumber(5000000); // 根据条数计算 具体金额
					order.setMoney(new BigDecimal(9000));
				} else {
					result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
					result.setResultMsg("产品2充值金额必须为9000");
					result.setResultObj(null);
					return result;
				}

			}

			if (trdOrderDomain.getProductsId() == 3) {

				if (new BigDecimal(16000).equals(trdOrderDomain.getMoney())) {
					order.setNumber(10000000); // 根据条数计算 具体金额
					order.setMoney(new BigDecimal(16000));
				} else {
					result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
					result.setResultMsg("产品3充值金额必须为16000");
					result.setResultObj(null);
					return result;
				}

			}

			if (trdOrderDomain.getProductsId() == 4) {
				order.setMoney(trdOrderDomain.getMoney());
				order.setNumber(trdOrderDomain.getMoney().divide(new BigDecimal(0.002), 0).intValue());
			}

			order.setCreUserId(trdOrderDomain.getCreUserId());
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			order.setPayType("3");
			order.setStatus(Constant.TRD_ORDER_STATUS_SUCCEED);
			order.setVersion(0);

			trdOrderMapper.saveTrdOrder(order);

			logger.info("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行充值，充值金额：【" + trdOrderDomain.getMoney()
					+ "】，充值条数；【" + order.getNumber() + "】");

			// 账户充值
			creUserAccount.setAccount(creUserAccount.getAccount() + order.getNumber());
			this.updateCreUserAccount(creUserAccount);

			ErpTradeDomain domain = new ErpTradeDomain();
			domain.setMoney(trdOrderDomain.getMoney());
			domain.setCount(order.getNumber());
			result.setResultObj(domain);
		} catch (Exception e) {
			logger.error("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行充值，充值金额：【" + trdOrderDomain.getMoney()
					+ "】，充值条数；【" + trdOrderDomain.getNumber() + "】，发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
			result.setResultObj(null);
		}

		return result;
	}

	@Transactional
	public BackResult<ErpTradeDomain> refunds(TrdOrderDomain trdOrderDomain, CreUserAccount creUserAccount) {

		BackResult<ErpTradeDomain> result = new BackResult<ErpTradeDomain>();

		try {

			// 检查订单编号防止重复提交
			List<TrdOrder> orderList = trdOrderMapper.findByClOrderNo(trdOrderDomain.getClOrderNo());

			if (!CommonUtils.isNotEmpty(orderList)) {
				result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
				result.setResultMsg("该订单已经处理，不能重复处理");
				result.setResultObj(null);
				return result;
			}

			TrdOrder order = new TrdOrder();

			// 保存流水记录
			BeanUtils.copyProperties(trdOrderDomain, order);

			order.setMoney(trdOrderDomain.getMoney());
			order.setNumber(trdOrderDomain.getNumber());
			order.setCreUserId(creUserAccount.getCreUserId());
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			order.setStatus(Constant.TRD_ORDER_STATUS_SUCCEED);
			order.setPayType("3");
			order.setVersion(0);

			if (creUserAccount.getAccount() < order.getNumber()) {
				result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
				result.setResultMsg("账户余额不足，本次退款失败！");

				logger.info("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行退款，退款金额：【" + trdOrderDomain.getMoney()
						+ "】，退款条数；【" + order.getNumber() + "】；当前账户剩余条数【" + creUserAccount.getAccount() + "】不支持本次退款");
				result.setResultObj(null);

				return result;
			}

			// 账户退款
			creUserAccount.setAccount(creUserAccount.getAccount() - order.getNumber());
			this.updateCreUserAccount(creUserAccount);

			trdOrderMapper.saveTrdOrder(order);

			logger.info("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行退款，退款金额：【" + trdOrderDomain.getMoney()
					+ "】，退款条数；【" + order.getNumber() + "】");

			ErpTradeDomain domain = new ErpTradeDomain();
			domain.setMoney(trdOrderDomain.getMoney());
			domain.setCount(order.getNumber());
			result.setResultObj(domain);
		} catch (Exception e) {
			logger.error("用户ID：【" + trdOrderDomain.getCreUserId() + "】执行退款，退款金额：【" + trdOrderDomain.getMoney()
					+ "】，退款条数；【" + trdOrderDomain.getNumber() + "】，发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
			result.setResultObj(null);
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

	@Transactional
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
			account.setUpdateTime(new Date());
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

	@Transactional
	public BackResult<Boolean> consumeApiAccount(String creUserId, String count) {

		BackResult<Boolean> result = new BackResult<Boolean>();

		CreUserAccount account = this.findCreUserAccountByUserId(Integer.valueOf(creUserId));

		try {
			if (null == account) {
				logger.error("用户ID：【" + creUserId + "】，不存在账户记录，数据落地异常");
				result.setResultCode(ResultCode.RESULT_API_NOTACCOUNT);
				result.setResultObj(Boolean.FALSE);
				result.setResultMsg("API商户信息不存在，或者已经删除请联系数据中心客户人员！");
				return result;
			}

			if (Integer.valueOf(count) > account.getApiAccount()) {
				logger.error("用户ID：【" + creUserId + "】，当前用户余额条数不够本次消费");
				result.setResultObj(Boolean.FALSE);
				result.setResultCode(ResultCode.RESULT_API_NOTCOUNT);
				result.setResultMsg("API商户信息剩余可消费条数为：" + account.getAccount() + "本次执行消费：" + count + "无法执行消费，请充值！");
				return result;
			}

			account.setApiAccount(account.getApiAccount() - Integer.valueOf(count));
			account.setUpdateTime(new Date());
			creUserAccountMapper.updateCreUserAccount(account);
			result.setResultObj(Boolean.TRUE);
			logger.info("用户id" + creUserId + "进行账户2次清洗本次成功消费：" + count + "条，剩余："
					+ (account.getApiAccount() - Integer.valueOf(count)) + "条");
		} catch (Exception e) {
			logger.error("用户ID：【" + creUserId + "】查询修改账户信息系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
		}

		return result;
	}

	@Override
	public BackResult<PageDomain<TrdOrderDomain>> pageFindTrdOrderByCreUserId(Integer creUserId, Integer pageSize,
			Integer pageNum) {
		BackResult<PageDomain<TrdOrderDomain>> result = new BackResult<PageDomain<TrdOrderDomain>>();
		List<TrdOrderDomain> list = new ArrayList<TrdOrderDomain>();
		PageDomain<TrdOrderDomain> listDomain = new PageDomain<TrdOrderDomain>();
		Map<String, Object> map = new HashMap<>();
		int totalNumber = 0; // 总条数
		int totalPages=0;   //总页数
		try {
			map.put("creUserId", creUserId);   //用户号码
			map.put("numPerPage", pageSize); // 显示行数
			map.put("currentPage",(pageNum-1)*pageSize); // 当前页码
			totalNumber = trdOrderMapper.quertCountTrdOrder(creUserId); // 总条数
			if (totalNumber == 0) {
				result.setResultMsg("该用户没有订单信息");
			} else {
				List<TrdOrder> orderList = trdOrderMapper.pageFindTrdOrderByCreUserId(map);
				for (TrdOrder trdOrder : orderList) {
					TrdOrderDomain domain = new TrdOrderDomain();
					BeanUtils.copyProperties(trdOrder, domain);
					list.add(domain);
				}
			   totalPages=(totalNumber/pageSize);
			   if ((totalNumber%pageSize)!=0) {
				   totalPages++;
			    }
			    
				listDomain.setTotalPages(totalPages);
				listDomain.setCurrentPage(pageNum);
				listDomain.setNumPerPage(pageSize);
				listDomain.setTotalNumber(totalNumber);
				listDomain.setTlist(list);
				result.setResultObj(listDomain);
			}
		} catch (Exception e) {
			logger.error("用户ID：【" + creUserId + "】查询订单信息发生系统异常：" + e.getMessage());
			e.printStackTrace();
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据落地异常");
		}

		return result;
	}

}
