package mesh_group.test.repository;

import mesh_group.test.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByEmailsEmail(String email);

    Optional<UserEntity> findByPhonesPhone(String phone);

    @Modifying
    @Query("update UserEntity u set u.isLocked = :value where u.id in :userIds")
    void updateIsLockedByIdIn(@Param("userIds") List<Long> userIds, @Param("value") Boolean value);

    @Query(value = "SELECT " +
            "CASE WHEN COUNT(*) = 0 " +
            "THEN FALSE " +
            "ELSE TRUE " +
            "END AS all_unlocked " +
            "FROM" +
            " UserEntity u WHERE u.id IN (:userIds) AND u.isLocked = true")
    boolean checkIfLocked(@Param("userIds") List<Long> userIds);
}
