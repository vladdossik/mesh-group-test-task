package mesh_group.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDto {
    @NotNull(message = "From id must not be null")
    Long fromId;
    @NotNull(message = "To id must not be null")
    Long toId;
    @NotNull(message = "Amount must not be null")
    BigDecimal amount;
}
