package accodom.dom.Service;
import accodom.dom.DTO.SignatureRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class YousignService {

    private final WebClient webClient;

    public YousignService(@Value("${yousign.api.base-url}") String baseUrl,
                          @Value("${yousign.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Mono<String> createSignatureRequest(SignatureRequest request) {
        return webClient.post()
                .uri("/signature_requests")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }
}
