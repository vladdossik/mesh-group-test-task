package mesh_group.test.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotEmpty(message = "Email or phone cannot be empty")
    private String emailOrPhone;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}