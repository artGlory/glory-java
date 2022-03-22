package com.glory.gloryStorageMysql.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2022-03-17 14:25:55
*/
@Table(name = "basic_relation_resource")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicRelationResource implements Serializable {
    @Id
    private String uk;

    /**
     * 资源类别
     */
    @Column(name = "resource_type")
    private String resourceType;

    /**
     * 资源拥有表
     */
    @Column(name = "own_table_name")
    private String ownTableName;

    /**
     * 资源拥有表主键
     */
    @Column(name = "own_table_key")
    private String ownTableKey;

    /**
     * 资源拥有者主键值
     */
    @Column(name = "own_table_value")
    private String ownTableValue;

    /**
     * 资源表
     */
    @Column(name = "resource_table_name")
    private String resourceTableName;

    /**
     * 资源表主键
     */
    @Column(name = "resource_table_key")
    private String resourceTableKey;

    /**
     * 资源表主键值
     */
    @Column(name = "resource_table_value")
    private String resourceTableValue;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建账户
     */
    @Column(name = "create_account_uk")
    private String createAccountUk;

    /**
     * 最后更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 最后更新账户
     */
    @Column(name = "update_account_uk")
    private String updateAccountUk;

    private static final long serialVersionUID = 1L;
}