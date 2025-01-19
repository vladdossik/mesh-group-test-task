package mesh_group.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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