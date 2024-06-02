package bridge.backend.domain.payment.service;

import bridge.backend.domain.payment.entity.PrePayment;
import bridge.backend.domain.payment.entity.dto.PrePaymentRequestDTO;
import bridge.backend.domain.payment.entity.dto.ValidatePaymentRequestDTO;
import bridge.backend.domain.payment.repository.PrePaymentRepository;
import bridge.backend.domain.plan.repository.ItemRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Transactional(readOnly = true)
@Service
public class PrePaymentService {
    private IamportClient api;

    @Autowired
    private PrePaymentRepository paymentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Value("${imp.api.key}")
    private String apiKey;
    @Value("${imp.api.secret}")
    private String secretKey;

    public PrePaymentService(){
        this.api = new IamportClient(apiKey, secretKey);
    }

    @Transactional
    public void postPrepare(PrePaymentRequestDTO request) throws IamportResponseException,IOException{
        PrepareData prepareData = new PrepareData(request.getMerchantUid(), request.getAmount());
        api.postPrepare(prepareData);

        PrePayment entity = new PrePayment();
        entity.setAmount(request.getAmount());
        entity.setMerchantUid(request.getMerchantUid());

        paymentRepository.save(entity);
    }

    public Payment validatePayment(ValidatePaymentRequestDTO request) throws IamportResponseException, IOException{
        //예외처리 필요
        PrePayment prePayemnt = paymentRepository.findByMerchantUid(request.getMerchantUid());
        BigDecimal preAmount = prePayemnt.getAmount();//결제 요청 금액

        IamportResponse<Payment> iamportResponse = api.paymentByImpUid(request.getImpUid());
        BigDecimal paidAmount = iamportResponse.getResponse().getAmount();//사용자가 결제한 금액

        if(!preAmount.equals(paidAmount)){
            CancelData cancelData = new CancelData(iamportResponse.getResponse().getImpUid(), true);
            api.cancelPaymentByImpUid(cancelData);
        }
        return iamportResponse.getResponse();
    }

}
