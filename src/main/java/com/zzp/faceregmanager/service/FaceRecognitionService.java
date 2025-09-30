package com.zzp.faceregmanager.service;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.service
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:07
 * @Description: TODO
 * @Version: 1.0
 */
public interface FaceRecognitionService {
    // 人脸注册
    boolean registerFace(String faceId, String faceName, String faceImg);

    // 人脸更新
    boolean updateFace(String faceId, String faceName, String faceImg);

    // 人脸识别
    List<Map<String, Object>> recognizeFace(String faceImg);

    // 人脸删除
    boolean deleteFace(String faceId);
}
