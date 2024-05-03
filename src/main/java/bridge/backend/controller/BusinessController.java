package bridge.backend.controller;

import bridge.backend.entity.dto.BusinessRequestDTO;
import bridge.backend.entity.dto.BusinessResponseDTO;
import bridge.backend.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business")
public class BusinessController {
    private final BusinessService businessService;

    @GetMapping("/byFilter")
    public ResponseEntity<?> getBusinessesByFilter(@RequestParam("idx") List<Integer> IdxList){
        try{
            List<BusinessResponseDTO> res = businessService.findBusinessByType(IdxList);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/byMonth")
    public ResponseEntity<?> getBusinessesByMonth(@RequestParam(name="date", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) YearMonth date){
        YearMonth searchDate = (date==null)?YearMonth.from(LocalDate.now()):date;
        try{
            List<BusinessResponseDTO> res = businessService.findBusinessByMonth(searchDate);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping()
    public ResponseEntity<?> getAllBusinesses(){
        try{
            List<BusinessResponseDTO> res = businessService.findAll();
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<?> createBusiness(@RequestBody BusinessRequestDTO req){
        try{
            businessService.saveBusiness(req);
            return new ResponseEntity<>("사업 목록이 정상적으로 작성되었습니다.", HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/star/{businessId}")
    public ResponseEntity<?> updateStar(@PathVariable("businessId") Long id){
        try{
            businessService.toggleStar(id);
            return new ResponseEntity<>("즐겨찾기가 정상적으로 수정되었습니다.", HttpStatus.ACCEPTED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
