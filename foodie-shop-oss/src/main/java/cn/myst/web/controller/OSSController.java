package cn.myst.web.controller;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumCookie;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.vo.UsersVO;
import cn.myst.web.resource.FileResource;
import cn.myst.web.service.OSSService;
import cn.myst.web.service.UserService;
import cn.myst.web.service.center.CenterUserService;
import cn.myst.web.utils.CookieUtils;
import cn.myst.web.utils.FileUtils;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2022/3/31
 */
@Slf4j
@Tag(name = "对象存储系统", description = "对象存储系统的相关接口")
@RestController
@RequestMapping("oss")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OSSController {
    private final OSSService ossService;
    private final FileResource fileResource;
    private final UserService userService;
    private final CenterUserService centerUserService;

    @Operation(summary = "用户更换头像", description = "用户更换头像")
    @PostMapping(value = "uploadFace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IMOOCJSONResult uploadFace(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId,
            @Parameter(description = "文件")
            @RequestPart MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (Objects.isNull(file)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.FILE_CANNOT_BE_EMPTY.zh);
        }
        // 校验文件类型
        if (!FileUtils.isImage(file)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.PICTURE_FORMAT_ERROR.zh);
        }
        // 获得文件上传的文件名称
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.FILE_CANNOT_BE_EMPTY.zh);
        }
        // 开始文件上传
        // 文件重命名 user-face.png -> ["imooc-face", "png"]
        String[] fileNameArray = filename.split("\\.");
        // 获取文件的后缀名
        String suffix = fileNameArray[fileNameArray.length - 1];
        // 上传的头像最终保存的位置
        String finalFacePath = ossService.upload(file, suffix);

        // 校验文件上传结果
        if (StringUtils.isBlank(finalFacePath)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.FILE_UPLOAD_ERROR.zh);
        }

        // 获取文件服务地址
        String serverUrl = fileResource.getServerUrl();

        // 文件完整路径（含服务器地址）
        String finalUserFacePathUrl = serverUrl + finalFacePath;

        // 更新用户头像到数据库
        Users users = centerUserService.updateUserFace(userId, finalUserFacePathUrl);

        userService.setNullProperty(users);
        // 增加令牌token，会整合进redis，分布式回话
        UsersVO usersVO = userService.convertUsersVO(users);

        CookieUtils.setCookie(request, response, EnumCookie.USER.cookieName, JsonUtils.objectToJson(usersVO), true);

        return IMOOCJSONResult.ok(users);
    }
}
