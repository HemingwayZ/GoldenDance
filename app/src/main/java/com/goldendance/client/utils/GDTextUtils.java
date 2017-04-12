package com.goldendance.client.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hemingway on 2017/1/12.
 */

public class GDTextUtils {
    /**
     * 明文加密
     *
     * @param str
     * @return
     */
    public static final String base64Decode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    }

    /**
     * 密码MD5加密
     *
     * @param realPsw
     * @return
     */
    public static String getMD5(String realPsw) {

        return realPsw;
//
//        try {
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            md5.update(realPsw.getBytes("UTF-8"));
//            byte[] encryption = md5.digest();
//
//            StringBuffer strBuf = new StringBuffer();
//            for (int i = 0; i < encryption.length; i++) {
//                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
//                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
//                } else {
//                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
//                }
//            }
//
//            return strBuf.toString();
//        } catch (NoSuchAlgorithmException e) {
//            return "";
//        } catch (UnsupportedEncodingException e) {
//            return "";
//        }
    }
}
