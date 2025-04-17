package Util;

import java.security.MessageDigest;

public class MD5Util {

    public static String toMD5(String input) {
        try {
            // 建立 MD5 加密器
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 執行加密（回傳位元組陣列）
            byte[] digest = md.digest(input.getBytes());

            // 把每個 byte 轉成 16 進位字串（例如 ab12cd...）
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString(); // 回傳 MD5 字串
        } catch (Exception e) {
            throw new RuntimeException("MD5 加密失敗", e);
        }
    }
}
