package com.glory.gloryStorageMysql.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Created by Mybatis Generator on 2021-12-06 17:25:13
*/
@Table(name = "bis_customer_opt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BisCustomerOpt implements Serializable {
    /**
     * 操作ID
     */
    @Id
    @Column(name = "OptID")
    private Integer optid;

    /**
     * 厂商企业ID
     */
    @Column(name = "FtyEnterpriseID")
    private Integer ftyenterpriseid;

    /**
     * 对应厂商门店ID
     */
    @Column(name = "FtyStoreID")
    private Long ftystoreid;

    /**
     * 协同企业ID
     */
    @Column(name = "EnterpriseID")
    private Integer enterpriseid;

    /**
     * 协同企业客户ID
     */
    @Column(name = "CustomerID")
    private Integer customerid;

    /**
     * 操作企业类型
     */
    @Column(name = "OptEnterpriseType")
    private String optenterprisetype;

    /**
     * 变动类型
     */
    @Column(name = "ChangeType")
    private Integer changetype;

    /**
     * 变动原因
     */
    @Column(name = "ChangeReason")
    private Integer changereason;

    /**
     * 状态Y=已审核 N=待审核
     */
    @Column(name = "Status")
    private String status;

    /**
     * 备注
     */
    @Column(name = "Remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "CreateDateTime")
    private Date createdatetime;

    /**
     * 创建员工ID
     */
    @Column(name = "CreateEmployeeID")
    private Integer createemployeeid;

    /**
     * 创建用户ID
     */
    @Column(name = "CreateUserID")
    private Integer createuserid;

    /**
     * 创建用户名称
     */
    @Column(name = "CreateUserName")
    private String createusername;

    /**
     * 来源类型
     */
    @Column(name = "SourcesType")
    private String sourcestype;

    /**
     * 往来单位ID
     */
    @Column(name = "CurrentUnitID")
    private Integer currentunitid;

    /**
     * 重复客户ID
     */
    @Column(name = "RepeatCustomerID")
    private Integer repeatcustomerid;

    /**
     * 审核时间
     */
    @Column(name = "CheckDateTime")
    private Date checkdatetime;

    /**
     * 审核员工ID
     */
    @Column(name = "CheckEmployeeID")
    private Integer checkemployeeid;

    /**
     * 审核员工名称
     */
    @Column(name = "CheckEmployeeName")
    private String checkemployeename;

    /**
     * 审核用户ID
     */
    @Column(name = "CheckUserID")
    private Integer checkuserid;

    /**
     * 审核用户名称
     */
    @Column(name = "CheckUserName")
    private String checkusername;

    /**
     * 审核说明
     */
    @Column(name = "CheckRemark")
    private String checkremark;

    private static final long serialVersionUID = 1L;
}