package actiOn.config.payments;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentsConfiguration {
    @Value("${payment.toss.test-client-key}")
    private String testClientKey;

    @Value("${payment.toss.test-secret-key}")
    private String testSecretKey;

    @Value("${payment.toss.success-url}")
    private String successUrl;

    @Value("${payment.toss.failure-url}")
    private String failureUrl;

    @Value("${payment.toss.url}")
    private String url;
}
