
package com.zzp.faceregmanager.po;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.PrePersist;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.po
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:12
 * @Description: TODO
 * @Version: 1.0
 */
// User实体类
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Lob
    @Column(nullable = false)
    private String faceData; // Base64编码的人脸图像数据

    @Column
    private String faceId; // 识度AI返回的faceId

    @Column(nullable = false)
    private Boolean deleted = false; // 逻辑删除标记

    // 构造函数、getter和setter
    public User() {}

    public User(String name, String phone, String faceData,String faceId) {
        this.name = name;
        this.phone = phone;
        this.faceData = faceData;
        this.faceId = faceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @PrePersist
    public void prePersist() {
        if (deleted == null) {
            deleted = false;
        }
    }
}