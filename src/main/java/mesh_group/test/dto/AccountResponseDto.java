package mesh_group.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    private Long id;
    @NotNull(message = "Initial balance cannot be null")
    private BigDecimal initialBalance;
    @NotNull(message = "Balance cannot be null")
    private BigDecimal balance;
}