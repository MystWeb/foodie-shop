package cn.myst.web.service;

import cn.myst.web.pojo.UserAddress;
import cn.myst.web.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/29
 */
public interface AddressService {

    /**
     * 根据用户id查询用户的收货地址列表
     */
    List<UserAddress> queryAllByUserId(String userId);

    /**
     * 用户新增地址
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户id、地址id删除对应的用户地址信息
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 根据用户id、地址id修改对应的用户默认地址
     */
    void updateUserAddressToBeDefault(String userId, String addressId);
}
