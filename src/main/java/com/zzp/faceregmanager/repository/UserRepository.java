package com.zzp.faceregmanager.repository;

import com.zzp.faceregmanager.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * @BelongsProject: face-reg-manager
 * @BelongsPackage: com.zzp.faceregmanager
 * @Author: zhouzpa
 * @CreateTime: 2025-09-29  16:11
 * @Description: TODO
 * @Version: 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
    Optional<User> findByFaceId(String faceId);
    Page<User> findAllByDeletedFalse(Pageable pageable);
    List<User> findAllByDeletedFalse();
}
