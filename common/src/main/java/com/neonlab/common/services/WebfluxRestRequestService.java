package com.neonlab.common.services;
import com.neonlab.common.annotations.Loggable;
import com.neonlab.common.expectations.BadRequestException;
import com.neonlab.common.expectations.ServerException;
import com.neonlab.common.models.RestRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Objects;
import static com.neonlab.common.constants.GlobalConstants.MULTIPART_FORM_DATA;


@Slf4j
@Service
@Loggable
public class WebfluxRestRequestService {

    private final WebClient client = WebClient.create();

    public String get(RestRequestModel request){
        return client.get()
                .uri(request.getUrl())
                .headers(headers -> {
                    if (Objects.nonNull(request.getHeaders())){
                        headers.addAll(request.getHeaders());
                    }
                })
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response
                                .bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new BadRequestException(error))))
                .onStatus(HttpStatusCode::isError, response ->
                        response
                                .bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ServerException(error))))
                .bodyToMono(String.class)
                .block();
    }

    public String post(RestRequestModel request) {
        String contentType = String.valueOf(request.getHeaders().getContentType());
       return switch (contentType){
            case MULTIPART_FORM_DATA ->
                handleMultipartFormRequest(request);
            default -> handleRequest(request);
       };
    }

    private String handleMultipartFormRequest(RestRequestModel request){
        return client.post()
                .uri(request.getUrl())
                .headers(httpHeaders -> httpHeaders.addAll(request.getHeaders()))
                .bodyValue(request.getMultipartBody())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response
                                .bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new BadRequestException(error))))
                .onStatus(HttpStatusCode::isError, response ->
                        response
                                .bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ServerException(error))))
                .bodyToMono(String.class)
                .block();
    }

    private String handleRequest(RestRequestModel request) {
        return client.post()
                .uri(request.getUrl())
                .headers(httpHeaders -> httpHeaders.addAll(request.getHeaders()))
                .body(Mono.just(request.getRequestBody()), request.getRequestClazz())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response
                                .bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new BadRequestException(error))))
                .onStatus(HttpStatusCode::isError, response ->
                        response
                                .bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new ServerException(error))))
                .bodyToMono(String.class)
                .block();
    }

}
