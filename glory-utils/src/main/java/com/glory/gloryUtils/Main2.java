package com.glory.gloryUtils;

import java.time.ZonedDateTime;

public class Main2 {
    public static void main(String[] args) {
        String uuid = ZonedDateTime.now().toString();
        System.err.println(uuid);
    }
}
