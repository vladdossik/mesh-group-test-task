package mesh_group.test.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    private Set<String> emails;

    private Set<String> phones;
}