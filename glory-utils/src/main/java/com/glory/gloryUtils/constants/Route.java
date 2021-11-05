package com.glory.gloryUtils.constants;

public interface Route {

    String main = "/";
    String path = "/api";


    interface Admin {
        String path = "/admin";
    }

    /**
     * 开放接口
     */
    interface OpenApi {
        String path = "/OpenApi";

    }
}
