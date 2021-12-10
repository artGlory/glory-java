package com.glory.gloryUtils;

import com.glory.gloryUtils.utils.ByteUtil;

public class Main2 {
    public static void main(String[] args) throws Exception {
        String str = "\\xe4\\xb8\\xad\\xe5\\x9b\\xbd";
        byte[] bytes = new byte[]{(byte) 0xe4, (byte) 0xb8, (byte) 0xad, (byte) 0xe5, (byte) 0x9b, (byte) 0xbd};
        System.err.println(new String("\\xe4\\xb8\\xad\\xe5\\x9b\\xbd"));
        System.err.println(ByteUtil.bytesToHex("中国".getBytes()));

    }
}
