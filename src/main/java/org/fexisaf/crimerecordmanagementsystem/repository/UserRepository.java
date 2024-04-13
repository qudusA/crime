package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.Role;
import org.fexisaf.crimerecordmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);


    @Query(value = """
SELECT * FROM user_entity WHERE email = ?1 AND role = ?2
""", nativeQuery = true)
    Optional<UserEntity> findByEmailAndRole(String name, String role);

    Optional<UserEntity> findByFirstNameAndLastName(String judgeFirstName, String judgeLastName);

    List<UserEntity> findByRole(Role role);
}
