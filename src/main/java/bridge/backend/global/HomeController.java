package bridge.backend.global;

import bridge.backend.domain.payment.entity.dto.OrderRequestDTO;
import bridge.backend.domain.payment.entity.dto.OrderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/check/healthy")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok("This is Bridge Ai HomePage");
    }
}
