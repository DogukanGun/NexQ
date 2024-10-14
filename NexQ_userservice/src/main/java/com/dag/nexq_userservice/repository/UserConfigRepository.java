package com.dag.nexq_userservice.repository;

import com.dag.nexq_userservice.data.entity.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfig,String> {
    @Modifying
    @Transactional
    @Query("UPDATE user_config u SET u.language = :language WHERE u.id = :id")
    void updateLanguageById(String id, String language);
}
