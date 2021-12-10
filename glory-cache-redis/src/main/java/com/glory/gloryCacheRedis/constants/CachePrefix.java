package com.glory.gloryCacheRedis.constants;

public enum CachePrefix {

    Database("database", "数据库");
    private String prifix;
    private String desc;

    CachePrefix(String prifix, String desc) {
        this.prifix = prifix;
        this.desc = desc;
    }

    public String getPrifix() {
        return prifix;
    }

    public void setPrifix(String prifix) {
        this.prifix = prifix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
