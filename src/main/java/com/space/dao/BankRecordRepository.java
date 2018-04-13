package com.space.dao;

import com.space.entity.BankRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BankRecordRepository extends JpaRepository<BankRecord,Integer>, JpaSpecificationExecutor<BankRecord> {


    /**
     * 寻找跟机构相关的转账记录
     * @param fromAccount
     * @param toAccount
     * @return
     */
    List<BankRecord> findAllByFromAccountOrToAccount(String fromAccount, String toAccount);
}
