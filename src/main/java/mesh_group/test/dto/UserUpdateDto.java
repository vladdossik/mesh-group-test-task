package mesh_group.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
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