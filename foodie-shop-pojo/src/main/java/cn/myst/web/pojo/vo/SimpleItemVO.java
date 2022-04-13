package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/3
 * 6个最新商品的简单数据类型
 */
@Schema(title = "6个最新商品的简单数据类型")
@Data
public class SimpleItemVO implements Serializable {

    @Schema(title = "商品id")
    private String itemId;

    @Schema(title = "商品名称")
    private String itemName;

    @Schema(title = "图片地址")
    private String itemUrl;

    private static final long serialVersionUID = -1524404605421841016L;
}
