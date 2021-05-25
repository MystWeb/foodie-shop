package cn.myst.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;

@Slf4j
public class MD5Utils {

    /**
     * @Title: MD5Utils.java
     * @Package com.imooc.utils
     * @Description: 对字符串进行md5加密
     */
    /*public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
    }*/
    public static String getMD5Str(String strValue) {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(strValue.getBytes());
        return Base64.encodeBase64String(md5DigestAsHex.getBytes());
    }

    public static void main(String[] args) {
        try {
            String md5 = getMD5Str("imooc");
            log.info(md5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
