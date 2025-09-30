package com.zzp.faceregmanager.req;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.req
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:05
 * @Description: TODO
 * @Version: 1.0
 */
public class FaceRecognizeRequest {
    private String faceData;

    // 构造函数、getter和setter
    public FaceRecognizeRequest() {}

    public FaceRecognizeRequest(String faceData) {
        this.faceData = faceData;
    }

    public String getFaceData() { return faceData; }
    public void setFaceData(String faceData) { this.faceData = faceData; }
}
