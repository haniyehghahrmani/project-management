package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Person;
import com.example.projectManagement.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    @Modifying
    @Query("update UserEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<User> findUserByDeletedFalse();

    Optional<User> findUserByIdAndDeletedFalse(Long id);

    List<User> findUserByNameAndLastnameAndDeletedFalse(String name, String lastName);

    Optional<User> findUserByNationalIdAndDeletedFalse(String nationalId);

    Long countByDeletedFalse();
}
