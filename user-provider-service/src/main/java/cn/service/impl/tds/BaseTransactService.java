package cn.service.impl.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class BaseTransactService {

	@Autowired
	private DataSourceTransactionManager transactionManager;
    
	
	/**
	 * 开启事物
	 * @return
	 */
	public TransactionStatus begin() {
		TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
		return transaction;
	}
    
	/**
	 * 提交事物
	 * @param transaction
	 */
	public void commit(TransactionStatus transaction) {
		transactionManager.commit(transaction);
	}

	/**
	 * 回滚事物
	 * @param transaction
	 */
	public void rollback(TransactionStatus transaction) {
		transactionManager.rollback(transaction);
	}

}
