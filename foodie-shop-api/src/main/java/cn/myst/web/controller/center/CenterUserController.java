package cn.myst.web.controller.center;

import cn.myst.web.controller.BaseController;
import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumCookie;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.center.CenterUserBO;
import cn.myst.web.pojo.vo.UsersVO;
import cn.myst.web.resource.FileUploadResource;
import cn.myst.web.service.UserService;
import cn.myst.web.service.center.CenterUserService;
import cn.myst.web.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/7/16
 */
@Slf4j
@Tag(name = "用户信息", description = "用户信息的相关接口")
@RestController
@RequestMapping("userInfo")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CenterUserController extends BaseController {
    private final CenterUserService centerUserService;
    private final FileUploadResource fileUploadResource;
    private final UserService userService;

    @Operation(summary = "更新用户信息", description = "更新用户信息")
    @PostMapping("update")
    public IMOOCJSONResult update(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId,
            @Parameter(description = "用户中心-用户BO")
            @RequestBody @Validated CenterUserBO centerUserBO,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ValidationUtils.getErrors(bindingResult);
            return IMOOCJSONResult.errorMap(errorMap);
        }

        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        userService.setNullProperty(users);
        // 增加令牌token，会整合进redis，分布式回话
        UsersVO usersVO = userService.convertUsersVO(users);

        CookieUtils.setCookie(request, response, EnumCookie.USER.cookieName, JsonUtils.objectToJson(usersVO), true);

        return IMOOCJSONResult.ok(users);
    }

    @Operation(summary = "用户更换头像", description = "用户更换头像")
    @PostMapping(value = "uploadFace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public IMOOCJSONResult uploadFace(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId,
            @Parameter(description = "文件")
            @RequestPart MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
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
        // 定义头像保存的地址
//        String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUploadResource.getImageUserFaceLocation();
        // 在路径上为每个用户增加一个userId，用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;
        // 开始文件上传
        // 文件重命名 user-face.png -> ["imooc-face", "png"]
        String[] fileNameArray = filename.split("\\.");
        // 获取文件的后缀名
        String suffix = fileNameArray[fileNameArray.length - 1];
        // 文件名称重组：face-{userId}.png；覆盖式上传（用户每次上传会覆盖旧的文件），增量式上传（额外拼接当前时间）
        String newFileName = "face-" + userId + "." + suffix;
        // 上传的头像最终保存的位置
        String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

        uploadPathPrefix += (File.separator + newFileName);

        File outFile = new File(finalFacePath);
        if (Objects.nonNull(outFile.getParentFile())) {
            // 创建文件夹
            boolean mkdir = outFile.getParentFile().mkdirs();
            log.debug("文件夹创建结果：" + mkdir);
        }
        try (InputStream inputStream = file.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(outFile)
        ) {
            // 文件输出保存到目录
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取图片服务地址
        String imageServerUrl = fileUploadResource.getImageServerUrl();

        // 由于浏览器可能存在缓存的情况，所以我们需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFacePathUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + DateUtil.formattedDate(LocalDateTime.now(), DateUtil.DATETIME_PATTERN);

        // 更新用户头像到数据库
        Users users = centerUserService.updateUserFace(userId, finalUserFacePathUrl);

        userService.setNullProperty(users);
        // 增加令牌token，会整合进redis，分布式回话
        UsersVO usersVO = userService.convertUsersVO(users);

        CookieUtils.setCookie(request, response, EnumCookie.USER.cookieName, JsonUtils.objectToJson(usersVO), true);

        return IMOOCJSONResult.ok(users);
    }
}
