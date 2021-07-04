package cn.myst.web.service.impl;

import cn.myst.web.enums.EnumException;
import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.mapper.UserAddressMapper;
import cn.myst.web.pojo.UserAddress;
import cn.myst.web.pojo.bo.AddressBO;
import cn.myst.web.service.AddressService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/6/29
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AddressServiceImpl implements AddressService {
    private final UserAddressMapper userAddressMapper;
    private final Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAllByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddress::getUserId, userId);
        return userAddressMapper.selectList(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        if (Objects.isNull(addressBO)) {
            throw new BusinessException(EnumException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 1. 判断当前用户是否存在地址，如果没有，则新增为"默认地址"
        int isDefault = 0;
        List<UserAddress> list = this.queryAllByUserId(addressBO.getUserId());
        if (CollectionUtils.isEmpty(list)) {
            isDefault = 1;
        }
        // 2. 保存地址
        UserAddress newUserAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newUserAddress);
        // 生成 addressId（地址id）
        newUserAddress.setId(sid.nextShort());
        newUserAddress.setIsDefault(isDefault);
        userAddressMapper.insert(newUserAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        if (Objects.isNull(addressBO)) {
            throw new BusinessException(EnumException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);
        pendingAddress.setId(addressBO.getAddressId());
        // 获取当前时间
        Date nowDate = Date.from(Instant.now());
        pendingAddress.setUpdatedTime(nowDate);
        userAddressMapper.updateById(pendingAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            throw new BusinessException(EnumException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        userAddressMapper.delete(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 1. 查找默认地址，设置为不默认
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddress::getUserId, userId);
        queryWrapper.eq(UserAddress::getIsDefault, EnumYesOrNo.YES.type);
        // 数据库中可能出现数据紊乱，可能一个用户的默认地址会有多个。
        List<UserAddress> list = userAddressMapper.selectList(queryWrapper);
        list.forEach(userAddress -> {
            userAddress.setIsDefault(EnumYesOrNo.YES.type);
            userAddressMapper.updateById(userAddress);
        });
//        userAddressMapper.updateBatchSelective(list);
        // 2. 根据地址id修改为默认的地址
        UserAddress defaultUserAddress = new UserAddress();
        defaultUserAddress.setId(addressId);
        defaultUserAddress.setUserId(userId);
        defaultUserAddress.setIsDefault(EnumYesOrNo.YES.type);
        userAddressMapper.updateById(defaultUserAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return null;
        }
        LambdaQueryWrapper<UserAddress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAddress::getUserId, userId);
        queryWrapper.eq(UserAddress::getId, addressId);
        return userAddressMapper.selectOne(queryWrapper);
    }
}
