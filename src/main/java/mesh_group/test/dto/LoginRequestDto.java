package mesh_group.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotEmpty(message = "Email or phone cannot be empty")
    private String emailOrPhone;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}