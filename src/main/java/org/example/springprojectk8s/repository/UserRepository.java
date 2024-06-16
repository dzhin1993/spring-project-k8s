package org.example.springprojectk8s.repository;

import jakarta.transaction.Transactional;
import org.example.springprojectk8s.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id =:id")
    int deleteById(@Param("id") long id);
}
