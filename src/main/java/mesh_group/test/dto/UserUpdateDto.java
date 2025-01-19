package mesh_group.test.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    @NotEmpty(message = "At least 1 email is required")
    private Set<String> emails;

    @NotEmpty(message = "At least 1 phone is required")
    private Set<String> phones;
}