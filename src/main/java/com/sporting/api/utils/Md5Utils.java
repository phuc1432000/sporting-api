package com.sporting.api.utils;

import java.security.MessageDigest;

public class Md5Utils {
    public static String MD5(String original) throws Exception {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Add password bytes to digest
        md.update(original.getBytes());

        // Get the hash's bytes
        byte[] bytes = md.digest();

        // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static boolean checkLogin(String oldPassword, String newPassword) throws Exception {
        String newPass = MD5(newPassword);
        if (newPass.equals(oldPassword)) {
            return true;
        }
        return false;
    }
}

