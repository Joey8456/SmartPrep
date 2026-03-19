package com.BTA.SmartPrep.repository;
import com.BTA.SmartPrep.domain.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <User, UUID> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO Users (user_ID, username, email, pass_Hash)
        VALUES (UUID(), :username, :email, :passHash)
        """, nativeQuery = true)
    void insertUser(String username, String email, String passHash);
}
