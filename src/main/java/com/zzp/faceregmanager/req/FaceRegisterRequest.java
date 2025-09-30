package com.zzp.faceregmanager.req;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.req
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:06
 * @Description: TODO
 * @Version: 1.0
 */
// 请求DTO类
public class FaceRegisterRequest {
    private String name;
    private String phone;
    private String faceData;

    // 构造函数、getter和setter
    public FaceRegisterRequest() {}

    public FaceRegisterRequest(String name, String phone, String faceData) {
        this.name = name;
        this.phone = phone;
        this.faceData = faceData;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getFaceData() { return faceData; }
    public void setFaceData(String faceData) { this.faceData = faceData; }
}
