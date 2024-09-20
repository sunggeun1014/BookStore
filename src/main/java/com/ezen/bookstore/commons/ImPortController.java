package com.ezen.bookstore.commons;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/user/api/payment")
public class ImPortController {
    private final IamportClient importClient;

    @Value("${import.api.key}")
    private String apiKey;

    @Value("${import.api.secret}")
    private String secretApi;

    public ImPortController() {
        this.importClient = new IamportClient(apiKey, secretApi);
    }

//    @PostMapping("/verify/{imp_uid}")
//    @ResponseBody
//    public IamportResponse<Payment> paymentByImpUid(Model model, Locale locale, HttpSession session
//            , @PathVariable(value= "imp_uid") String imp_uid) throws IamportResponseException, IOException {
//
//    }


}
