package com.wia1002.eGringottsBackEnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wia1002.eGringottsBackEnd.model.UserAvatar;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, String> {
    @Query("SELECT t.image_path FROM UserAvatar t WHERE t.account_number=:test")
    byte[] getUserAvatars(@Param("test") String test);
}
