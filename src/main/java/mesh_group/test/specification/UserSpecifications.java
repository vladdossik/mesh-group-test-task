package mesh_group.test.specification;

import mesh_group.test.model.EmailDataEntity;
import mesh_group.test.model.PhoneDataEntity;
import mesh_group.test.model.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;


public class UserSpecifications {

    private UserSpecifications() {
        throw new UnsupportedOperationException("Утилитарный класс не может быть инстанцирован");
    }

    public static Specification<UserEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), name.toLowerCase() + "%");
        };
    }

    public static Specification<UserEntity> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<UserEntity, EmailDataEntity> emails = root.join("emails", JoinType.LEFT);
            return criteriaBuilder.equal(emails.get("email"), email);
        };
    }

    public static Specification<UserEntity> hasPhone(String phone) {
        return (root, query, criteriaBuilder) -> {
            if (phone == null || phone.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<UserEntity, PhoneDataEntity> phones = root.join("phones", JoinType.LEFT);
            return criteriaBuilder.equal(phones.get("phone"), phone);
        };
    }

    public static Specification<UserEntity> hasDateOfBirthAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThan(root.get("dateOfBirth"), date);
        };
    }
}