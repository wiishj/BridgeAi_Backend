package bridge.backend.domain.payment.controller;

import bridge.backend.domain.payment.entity.dto.PrePaymentRequestDTO;
import bridge.backend.domain.payment.entity.dto.ValidatePaymentRequestDTO;
import bridge.backend.domain.payment.service.PrePaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PrePaymentController {
    private final PrePaymentService paymentService;

    @PostMapping("/payment/prepare")
    public void preparePayment(@RequestBody PrePaymentRequestDTO requestDTO) throws IamportResponseException, IOException{
        paymentService.postPrepare(requestDTO);
    }

    @PostMapping("/payment/validate")
    public Payment validatePayment(@RequestBody ValidatePaymentRequestDTO requestDTO) throws IamportResponseException, IOException{
        return paymentService.validatePayment(requestDTO);
    }

}
