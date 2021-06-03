package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/3
 * 6个最新商品的简单数据类型
 */
@ApiModel("6个最新商品的简单数据类型")
@Data
public class SimpleItemVO implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String itemId;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "图片地址")
    private String itemUrl;

    private static final long serialVersionUID = -1524404605421841016L;
}
