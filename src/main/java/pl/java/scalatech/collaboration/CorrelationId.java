package pl.java.scalatech.collaboration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CorrelationId {
    public static String CORRELATION_ID_HEADER_KEY = "X-Correlation-Id";
    private String correlationId;
}