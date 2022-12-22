package org.lab.core.base;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import java.util.Date;

public class CommonFields {
    /**
     * 主键(ASSIGN_ID雪花算法)
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 乐观锁版本号
     */
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改人
     */
    private String modifier;

    /**
     * 逻辑删除字段
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    public CommonFields() {

    }

    public CommonFields(Integer version, Date gmtCreate, Date gmtModified, String creator, String modifier, Boolean deleted) {
        this.version = version;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.modifier = modifier;
        this.deleted = deleted;
    }


    public CommonFields(Long id, Integer version, Date gmtCreate, Date gmtModified, String creator, String modifier, Boolean deleted) {
        this.id = id;
        this.version = version;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.modifier = modifier;
        this.deleted = deleted;
    }

    public CommonFields(Long id, Integer version, Date gmtCreate, Date gmtModified, String creator, String modifier) {
        this.id = id;
        this.version = version;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.modifier = modifier;
    }

    public CommonFields(Integer version, Date gmtCreate, Date gmtModified, String creator, String modifier) {
        this.version = version;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.modifier = modifier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
