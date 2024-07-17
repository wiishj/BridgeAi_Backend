package bridge.backend.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/check/healthy")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok("This is Bridge Ai HomePage");
    }
}
