package cn.myst.web.controller;

import cn.myst.web.enums.EnumAddressValidation;
import cn.myst.web.enums.EnumException;
import cn.myst.web.pojo.UserAddress;
import cn.myst.web.pojo.bo.AddressBO;
import cn.myst.web.service.AddressService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/6/29
 */
@Api(value = "地址", tags = "地址的相关接口")
@RestController
@RequestMapping("address")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AddressController {
    private final AddressService addressService;

    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */
    @ApiOperation(value = "根据用户id查询用户的收货地址列表", notes = "根据用户id查询用户的收货地址列表")
    @GetMapping("list")
    public IMOOCJSONResult list(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId) {
        List<UserAddress> list = addressService.queryAllByUserId(userId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址")
    @PostMapping("add")
    public IMOOCJSONResult add(@RequestBody AddressBO addressBO) {
        IMOOCJSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.addNewUserAddress(addressBO);
        return IMOOCJSONResult.ok();
    }

    private IMOOCJSONResult checkAddress(AddressBO addressBO) {
        if (Objects.isNull(addressBO)) {
            return IMOOCJSONResult.errorMsg(EnumException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        String userId = addressBO.getUserId();
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.USER_ID_CANNOT_BE_EMPTY.zh);
        }
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.CONSIGNEE_CANNOT_BE_EMPTY.zh);
        }
        if (receiver.length() > 12) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.RECEIVER_NAME_IS_NOT_TOO_LONG.zh);
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.CONSIGNEE_MOBILE_PHONE_NUMBER_CANNOT_BE_EMPTY.zh);
        }
        if (mobile.length() != 11) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.CONSIGNEE_MOBILE_PHONE_NUMBER_LENGTH_IS_INCORRECT.zh);
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.CONSIGNEE_MOBILE_PHONE_NUMBER_IS_INCORRECT.zh);
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.RECEIPT_ADDRESS_INFORMATION_CANNOT_BE_EMPTY.zh);
        }

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址")
    @PutMapping("update")
    public IMOOCJSONResult update(@RequestBody AddressBO addressBO) {
        if (Objects.isNull(addressBO) || StringUtils.isBlank(addressBO.getAddressId())) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.ADDRESS_ID_CANNOT_BE_EMPTY.zh);
        }
        IMOOCJSONResult checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.updateUserAddress(addressBO);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "根据用户id、地址id删除对应的用户地址信息", notes = "根据用户id、地址id删除对应的用户地址信息")
    @DeleteMapping("delete")
    public IMOOCJSONResult delete(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "地址id", required = true)
            @RequestParam String addressId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.USER_ID_CANNOT_BE_EMPTY.zh);
        }
        if (StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.ADDRESS_ID_CANNOT_BE_EMPTY.zh);
        }
        addressService.deleteUserAddress(userId, addressId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "根据用户id、地址id删除对应的用户地址信息", notes = "根据用户id、地址id删除对应的用户地址信息")
    @PutMapping("setDefault")
    public IMOOCJSONResult setDefault(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "地址id", required = true)
            @RequestParam String addressId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.USER_ID_CANNOT_BE_EMPTY.zh);
        }
        if (StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg(EnumAddressValidation.ADDRESS_ID_CANNOT_BE_EMPTY.zh);
        }
        addressService.updateUserAddressToBeDefault(userId, addressId);
        return IMOOCJSONResult.ok();
    }
}
