package mesh_group.test.repository;

import mesh_group.test.model.PhoneDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PhoneDataRepository extends JpaRepository<PhoneDataEntity, Long> {
    List<PhoneDataEntity> findByPhoneIn(Collection<String> phones);
}