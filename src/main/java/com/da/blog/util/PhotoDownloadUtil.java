//package com.da.blog.util;
//
//import com.qiniu.util.StringUtils;
//import com.qiniu.util.UrlSafeBase64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.GeneralSecurityException;
//
///**
// * 从七牛云私有空间下载图片
// */
//@Component
//public class PhotoDownloadUtil {
//    private static Logger logger = LoggerFactory.getLogger(PhotoDownloadUtil.class);
//
//    //设置好账号的accessKey和secretKey
//    @Value("${qiniu.accessKey}")
//    private String accessKey;
//    @Value("${qiniu.secretKey}")
////    private String secretKey;
//    private final SecretKeySpec secretKey;
//
//    //要上传的空间
//    @Value("${qiniu.bucketName}")
//    private String bucketName;
//
//    @Value("${qiniu.basePath}")
//    public String basePath;
//
//    private Auth(String accessKey, SecretKeySpec secretKeySpec) {
//        this.accessKey = accessKey;
//        this.secretKey = secretKeySpec;
//    }
//
//    private Mac createMac() {
//        Mac mac;
//        try {
//            mac = javax.crypto.Mac.getInstance("HmacSHA1");
//            mac.init(secretKey);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException(e);
//        }
//        return mac;
//    }
//    public String sign(byte[] data) {
//        Mac mac = createMac();
//        String encodedSign = UrlSafeBase64.encodeToString(mac.doFinal(data));
//        return this.accessKey + ":" + encodedSign;
//    }
//
//    /**
//     * 下载签名
//     *
//     * @param baseUrl 待签名文件url，如 http://img.domain.com/u/3.jpg
//     * @param expires 有效时长，单位秒。默认3600s
//     * @return
//     */
//    public String privateDownloadUrl(String baseUrl, long expires) {
//        long deadline = System.currentTimeMillis() / 1000 + expires;
//        return privateDownloadUrlWithDeadline(baseUrl, deadline);
//
//    }
//
//    public String privateDownloadUrlWithDeadline(String baseUrl, long deadline) {
//        StringBuilder b = new StringBuilder();
//        b.append(baseUrl);
//        //查找baseUrl字符串中，第一次出现"?"字符串的位置。
//        int pos = baseUrl.indexOf("?");
//        if (pos > 0) {
//            b.append("&e=");
//        } else {
//            b.append("?e=");
//        }
//        b.append(deadline);
//        String token = sign(StringUtils.utf8Bytes(b.toString()));
//        b.append("&token=");
//        b.append(token);
//        return b.toString();
//    }
//
//}
