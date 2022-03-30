package cn.myst.web.pojo.vo;

import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/15
 * 商品详情VO
 */
@Schema(title = "商品详情VO")
@Data
public class ItemInfoVO {

    @Schema(name = "商品")
    private Items item;

    @Schema(name = "商品图片列表")
    private List<ItemsImg> itemImgList;

    @Schema(name = "商品规格列表")
    private List<ItemsSpec> itemSpecList;

    @Schema(name = "商品参数")
    private ItemsParam itemParams;

}
