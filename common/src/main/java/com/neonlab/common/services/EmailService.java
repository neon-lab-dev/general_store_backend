package com.neonlab.common.services;
import com.neonlab.common.dto.EmailSendDto;
import com.neonlab.common.models.RestRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class EmailService {
    @Autowired
    private WebfluxRestRequestService webfluxRestRequestService;

    private static final String EMAIL_API_URL = "https://send-email-two-beta.vercel.app/api/v1/sendemail";

    public String sendEmail(EmailSendDto emailSendDto){
        var multipartBody = new MultipartBodyBuilder();
        multipartBody.part("to",emailSendDto.getTo());
        multipartBody.part("cc",emailSendDto.getCc());
        multipartBody.part("subject",emailSendDto.getSubject());
        multipartBody.part("body",emailSendDto.getBody());
        var header = new HttpHeaders();
//        header.add("Content-Type",MediaType.MULTIPART_FORM_DATA_VALUE);
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        var request = RestRequestModel.buildRestRequest()
                .url(EMAIL_API_URL)
                .headers(header)
                .method(HttpMethod.POST)
                .multipartBody(multipartBody.build());

        return webfluxRestRequestService.post(request.build());
    }
}
