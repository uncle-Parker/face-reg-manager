package com.zzp.faceregmanager.service;

import com.zzp.faceregmanager.po.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.service
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:06
 * @Description: TODO
 * @Version: 1.0
 */
public interface UserService {
    List<User> getAllUsers();
    Page<User> getUsersPage(Pageable pageable);
    Optional<User> getUserById(Long id);


    Optional<User> getUserByPhone(String phone);

    Optional<User> getUserByFaceId(String faceId);

    User saveUser(User user);

    boolean deleteUser(Long id);
    
    User updateUser(User user);
}