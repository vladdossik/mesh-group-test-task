package mesh_group.test.repository;

import mesh_group.test.model.EmailDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailDataEntity, Long> {

    List<EmailDataEntity> findByEmailIn(Collection<String> emails);
}