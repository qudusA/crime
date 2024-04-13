package org.fexisaf.crimerecordmanagementsystem.repository;

import org.fexisaf.crimerecordmanagementsystem.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByToken(String jwtToken);

    @Query(value = """
      select t from TokenEntity t inner join UserEntity u
      on t.userEntity.id = u.id
      where u.id = :id and (t.isExpired = false)
      """)
    List<TokenEntity> findAllById(Long id);

}
