package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{

    @Modifying
    @Query("update PersonEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Person> findPersonByDeletedFalse();

    Optional<Person> findPersonByIdAndDeletedFalse(Long id);

    List<Person> findPersonByNameAndLastnameAndDeletedFalse(String name, String lastName);

    Optional<Person> findPersonByNationalIdAndDeletedFalse(String nationalId);

    Long countByDeletedFalse();
}
