package mesh_group.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {
    @NotNull(message = "From id must not be null")
    Long fromId;
    @NotNull(message = "To id must not be null")
    Long toId;
    @NotNull(message = "Amount must not be null")
    BigDecimal amount;
}
