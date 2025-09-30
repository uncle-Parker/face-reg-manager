package com.zzp.faceregmanager.controller;

import com.zzp.faceregmanager.po.User;
import com.zzp.faceregmanager.service.FaceRecognitionService;
import com.zzp.faceregmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.controller
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:16
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;
    
    @Resource
    private FaceRecognitionService faceRecognitionService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "size", required = false) Integer size) {
        try {
            if (page == null || size == null) {
                List<User> users = userService.getAllUsers();
                return ResponseEntity.ok(users);
            }
            Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1));
            Page<User> pageResult = userService.getUsersPage(pageable);
            return ResponseEntity.ok(Collections.singletonMap("data", pageResult));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "获取用户列表失败: " + e.getMessage()));
        }
    }


    // 添加更新用户信息的接口
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<User> userOpt = userService.getUserById(id);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "用户不存在"));
            }

            User user = userOpt.get();
            
            // 更新用户信息
            if (updates.containsKey("name")) {
                user.setName((String) updates.get("name"));
            }
            
            if (updates.containsKey("phone")) {
                user.setPhone((String) updates.get("phone"));
            }
            
            // 如果提供了新的人脸数据，则更新识度AI和数据库
            if (updates.containsKey("faceData")) {
                String newFaceData = (String) updates.get("faceData");
                boolean faceUpdated;
                if(userOpt.get().getFaceData()!=null){
                    // 调用识度AI更新人脸
                     faceUpdated = faceRecognitionService.updateFace(
                            user.getFaceId(),
                            user.getName(),
                            newFaceData
                    );
                }else {
                    // 调用识度AI注册人脸
                    faceUpdated = faceRecognitionService.registerFace(
                            user.getFaceId(),
                            user.getName(),
                            newFaceData
                    );
                }
                
                if (faceUpdated) {
                    // 更新用户的人脸数据
                    user.setFaceData(newFaceData);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Collections.singletonMap("message", "识度AI人脸更新失败"));
                }
            }
            
            // 保存用户信息到数据库
            userService.updateUser(user);

            return ResponseEntity.ok(Collections.singletonMap("message", "用户信息更新成功"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "更新用户信息失败: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> userOpt = userService.getUserById(id);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "用户不存在"));
            }
            
            User user = userOpt.get();
            boolean faceDeleted =true;
            if(userOpt.get().getFaceData() != null){
                // 调用识度AI删除人脸
                faceDeleted =faceRecognitionService.deleteFace(user.getFaceId());
            }
            
            if (faceDeleted) {
                // 删除数据库中的用户
                boolean deleted = userService.deleteUser(id);
                if (deleted) {
                    return ResponseEntity.ok(Collections.singletonMap("message", "用户删除成功"));
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Collections.singletonMap("message", "数据库用户删除失败"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "识度AI人脸删除失败"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "删除用户失败: " + e.getMessage()));
        }
    }
}