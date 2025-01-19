package mesh_group.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 500)
    @Length(min = 8)
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountEntity account;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<EmailDataEntity> emails = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<PhoneDataEntity> phones = new HashSet<>();

    @Column(name = "is_locked")
    private Boolean isLocked = Boolean.FALSE;

    public void addEmails(Set<EmailDataEntity> emails) {
        emails.forEach(email -> email.setUser(this));
        this.emails = emails;
    }

    public void addPhones(Set<PhoneDataEntity> phones) {
        phones.forEach(phone -> phone.setUser(this));
        this.phones = phones;
    }
}