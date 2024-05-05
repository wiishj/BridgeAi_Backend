package bridge.backend.domain.controller;

import bridge.backend.domain.entity.dto.BusinessRequestDTO;
import bridge.backend.domain.entity.dto.BusinessResponseDTO;
import bridge.backend.domain.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<?> getBusinessesByFilter(@RequestParam("idx") List<Integer> idxList, @RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10);
        try{
            List<BusinessResponseDTO> res = businessService.findBusinessByType(idxList, pageable);
            return ResponseEntity.ok(Map.of("data",res, "size", res.size()));
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/byMonthAndFilter")
    public ResponseEntity<?> getBusinessesByMonth(@RequestParam(name="date", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) YearMonth date, @RequestParam(name="idx", required = false) List<Integer> idxList){
        YearMonth searchDate = (date==null)?YearMonth.from(LocalDate.now()):date;
        try{
            List<BusinessResponseDTO> res = businessService.findBusinessByMonthAndFilter(searchDate, idxList);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/byNew")
    public ResponseEntity<?> getBusinessesByNew(@RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        try{
            List<BusinessResponseDTO> res = businessService.findAll(pageable);
            return ResponseEntity.ok(Map.of("data",res, "size", res.size()));
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/byDeadline")
    public ResponseEntity<?> getBusinessesByDeadline(@RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("dDay").ascending());
        try{
            List<BusinessResponseDTO> res = businessService.findBusinessByDDayGreaterThanZero(pageable);
            return ResponseEntity.ok(Map.of("size", res.size(),"data",res));
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
