package com.zzp.faceregmanager.service.impl;

import com.zzp.faceregmanager.service.FaceRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.service.impl
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:08
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class FaceRecognitionServiceimpl implements FaceRecognitionService {

    @Value("${face.api.base-url}")
    private String API_BASE_URL;

    @Value("${face.api.key}")
    private String API_KEY;

    @Value("${face.api.secret}")
    private String API_SECRET;
    @Resource
    private RestTemplate restTemplate;

    // 人脸注册
    @Override
    public boolean registerFace(String faceId, String faceName, String faceImg) {
        String url = API_BASE_URL + "/registerFaceByFaceDataBase";

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("api-secret", API_SECRET);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("faceId", faceId);
        requestBody.put("faceName", faceName);
        requestBody.put("faceImg", "data:image/jpeg;base64," + faceImg);
        requestBody.put("checkRGBLiveDetection", false);
        requestBody.put("checkIRLiveDetection", false);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            return response.getStatusCode() == HttpStatus.OK &&
                    Boolean.TRUE.equals(response.getBody().get("success"));
        } catch (Exception e) {
            throw new RuntimeException("人脸注册失败: " + e.getMessage());
        }
    }

    // 人脸更新
    @Override
    public boolean updateFace(String faceId, String faceName, String faceImg) {
        String url = API_BASE_URL + "/updateFaceByFaceDataBase";

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("api-secret", API_SECRET);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("faceId", faceId);
        requestBody.put("faceName", faceName);
        requestBody.put("faceImg", "data:image/jpeg;base64," + faceImg);
        requestBody.put("checkRGBLiveDetection", false);
        requestBody.put("checkIRLiveDetection", false);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            return response.getStatusCode() == HttpStatus.OK &&
                    Boolean.TRUE.equals(response.getBody().get("success"));
        } catch (Exception e) {
            throw new RuntimeException("人脸更新失败: " + e.getMessage());
        }
    }

    // 人脸识别
    @Override
    public List<Map<String, Object>> recognizeFace(String faceImg) {
        String url = API_BASE_URL + "/recognizeFaceByFaceDataBase";

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("api-secret", API_SECRET);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("faceImg", "data:image/jpeg;base64," + faceImg);
        requestBody.put("checkRGBLiveDetection", false);
        requestBody.put("checkIRLiveDetection", false);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (Boolean.TRUE.equals(responseBody.get("success"))) {
                    return (List<Map<String, Object>>) responseBody.get("data");
                }
            }

            return Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException("人脸识别失败: " + e.getMessage());
        }
    }

    // 人脸删除
    @Override
    public boolean deleteFace(String faceId) {
        String url = API_BASE_URL + "/deleteFaceByFaceDataBase?faceId=" + faceId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", API_KEY);
        headers.set("api-secret", API_SECRET);

        HttpEntity<?> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url, HttpMethod.GET, request, Map.class);

            return response.getStatusCode() == HttpStatus.OK &&
                    Boolean.TRUE.equals(response.getBody().get("success"));
        } catch (Exception e) {
            throw new RuntimeException("人脸删除失败: " + e.getMessage());
        }
    }
}
