package com.zzp.faceregmanager.service.impl;

import com.zzp.faceregmanager.po.User;
import com.zzp.faceregmanager.repository.UserRepository;
import com.zzp.faceregmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager.service
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:06
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByDeletedFalse();
    }
    
    @Override
    public Page<User> getUsersPage(Pageable pageable) {
        return userRepository.findAllByDeletedFalse(pageable);
    }
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
    @Override

    public Optional<User> getUserByFaceId(String faceId) {
        return userRepository.findByFaceId(faceId);
    }
    @Override

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    @Override

    public boolean deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setDeleted(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}