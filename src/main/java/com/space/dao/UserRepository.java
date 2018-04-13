package com.space.dao;

import com.space.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * 用户账号Repository接口
 */
public interface UserRepository extends JpaRepository<User,Integer> ,JpaSpecificationExecutor<User> {

    @Query(value = "select * from t_user where account = ?1 and password = ?2",nativeQuery = true)
    public User getUserByActAndPsd(String account,String password);



    /**
     * 根据账号获取user
     * @param account
     * @return
     */
    @Query(value = "select account from t_user where account = ?1",nativeQuery = true)
    public String getAccount(String account);

    /**
     * 取消会员资格或者回复会员资格
     * @param isMember
     * @param userId
     */
    @Modifying@Transactional
    @Query(value = "update t_user set is_member = ?1 where  user_id = ?2",nativeQuery = true)
    public void cancelMember(int isMember,String userId);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    @Query(value = "select * from t_user where user_name = ?1",nativeQuery = true)
    public User findUserByUserName(String userName);


    /**
     * 根据token和account来判断是否正确
     * @param account
     * @param token
     * @return
     */
    public User findByAccountAndToken(String account,String token);

    /**
     * 查询是否存在次用户
     * @param account
     * @return
     */
    public User findByAccount(String account);
}
