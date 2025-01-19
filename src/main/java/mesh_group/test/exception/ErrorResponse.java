package mesh_group.test.exception;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ErrorResponse {
    private  String message;
    private LocalDate timeStamp;
    private String details;

    public ErrorResponse(LocalDate date, String validationFailed, String details) {
        timeStamp = date;
        message = validationFailed;
        this.details = details;
    }
}