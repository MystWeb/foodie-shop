package cn.myst.web.pojo.vo;

import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/15
 * 商品详情VO
 */
@ApiModel(value = "商品详情VO")
@Data
public class ItemInfoVO {

    @ApiModelProperty(value = "商品")
    private Items item;

    @ApiModelProperty(value = "商品图片列表")
    private List<ItemsImg> itemImgList;

    @ApiModelProperty(value = "商品规格列表")
    private List<ItemsSpec> itemSpecList;

    @ApiModelProperty(value = "商品参数")
    private ItemsParam itemParams;

}
