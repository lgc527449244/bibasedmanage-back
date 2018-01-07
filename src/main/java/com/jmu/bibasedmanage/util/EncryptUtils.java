package com.jmu.bibasedmanage.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密工具类
 * Created by ljc on 2018/1/6.
 */
public class EncryptUtils {
    /**
     * 获取加密后的密码
     * @param saltSource 盐值原值
     * @param hashAlgorithmName 加密方式（MD5）
     * @param credentials 密码
     * @return
     */
    public static String getEncryptPwd(String saltSource, String hashAlgorithmName, String credentials){
        Object salt = new Md5Hash(saltSource);
        int hashIterations = 1024;//加密循环次数1024次
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        return result.toString();
    }

}
