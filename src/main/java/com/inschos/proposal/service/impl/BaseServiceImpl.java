package com.inschos.proposal.service.impl;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class BaseServiceImpl {
	public void rollBack() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
