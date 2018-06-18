package com.space.dao;


import com.space.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 银行账户的repository的接口
 */
public interface BankRepository extends JpaRepository<Bank,Integer> , JpaSpecificationExecutor<Bank>{

    /**
     * 根据account找到Bank账号
     * @param account
     * @return
     */
    @Query(value = "select * from t_bank where account = ?1",nativeQuery = true)
    public Bank queryBankByAccount(String account);

    /**
     * 登陆网上银行
     * @param account
     * @param password
     * @return
     */
//    @Query(value = "select * from t_bank where account = ?1 and password = ?2",nativeQuery = true)
    Bank findByAccountAndPassword(String account,String password);


}
