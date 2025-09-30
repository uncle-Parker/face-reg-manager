package com.zzp.faceregmanager.req;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.req
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:05
 * @Description: TODO
 * @Version: 1.0
 */
public class FaceUpdateRequest {
    private Long userId;
    private String faceData;

    // 构造函数、getter和setter
    public FaceUpdateRequest() {}

    public FaceUpdateRequest(Long userId, String faceData) {
        this.userId = userId;
        this.faceData = faceData;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFaceData() { return faceData; }
    public void setFaceData(String faceData) { this.faceData = faceData; }
}
