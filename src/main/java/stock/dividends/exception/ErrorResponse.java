package stock.dividends.exception;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
public class ErrorResponse {
    private int code;
    private String message;
}
