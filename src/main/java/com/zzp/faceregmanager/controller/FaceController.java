package com.zzp.faceregmanager.controller;

import com.zzp.faceregmanager.po.User;
import com.zzp.faceregmanager.req.FaceRecognizeRequest;
import com.zzp.faceregmanager.req.FaceRegisterRequest;
import com.zzp.faceregmanager.service.FaceRecognitionService;
import com.zzp.faceregmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.controller
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:18
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/faces")
public class FaceController {

    @Resource
    private FaceRecognitionService faceRecognitionService;

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFace(@RequestBody FaceRegisterRequest request) {
        try {
            // 验证手机号是否已存在
            Optional<User> existingUser = userService.getUserByPhone(request.getPhone());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("message", "手机号已存在"));
            }

            User user;
            if (request.getFaceData() == null || request.getFaceData().trim().isEmpty()) {
                // 人脸非必填：仅保存基础信息
                String faceId = UUID.randomUUID().toString();
                user = new User(request.getName(), request.getPhone(), null, faceId);
                userService.saveUser(user);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "用户保存成功");
                response.put("data", user);
                return ResponseEntity.ok(response);
            }
            // 生成faceId (使用UUID)
            String faceId = UUID.randomUUID().toString();

            // 调用识度AI注册人脸
            boolean success = faceRecognitionService.registerFace(
                    faceId,
                    request.getName(),
                    request.getFaceData()
            );

            if (success) {
                // 保存用户信息到数据库
                user = new User(request.getName(), request.getPhone(), request.getFaceData(),faceId);
                userService.saveUser(user);

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "人脸注册成功");
                response.put("data", user);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "人脸注册失败"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "人脸注册失败: " + e.getMessage()));
        }
    }


    @PostMapping("/recognize")
    public ResponseEntity<?> recognizeFace(@RequestBody FaceRecognizeRequest request) {
        try {
            // 调用识度AI识别人脸
            List<Map<String, Object>> recognitionResults =
                    faceRecognitionService.recognizeFace(request.getFaceData());

            if (recognitionResults != null && !recognitionResults.isEmpty()) {
                // 获取识别结果并关联用户信息
                List<Map<String, Object>> resultsWithUserInfo = new ArrayList<>();

                for (Map<String, Object> result : recognitionResults) {
                    String faceId = (String) result.get("faceId");
                    Optional<User> userOpt = userService.getUserByFaceId(faceId);

                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        Map<String, Object> resultWithUser = new HashMap<>(result);
                        resultWithUser.put("userName", user.getName());
                        resultWithUser.put("userPhone", user.getPhone());
                        resultsWithUserInfo.add(resultWithUser);
                    } else {
                        // 如果数据库中没有找到对应的用户，仍然返回识别结果
                        resultsWithUserInfo.add(result);
                    }
                }

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "人脸识别成功");
                response.put("data", resultsWithUserInfo);
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "未识别到匹配的人脸");
                response.put("data", Collections.emptyList());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "人脸识别失败: " + e.getMessage()));
        }
    }
}