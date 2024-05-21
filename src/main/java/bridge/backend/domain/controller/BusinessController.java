package bridge.backend.domain.controller;

import bridge.backend.domain.entity.dto.BusinessRequestDTO;
import bridge.backend.domain.entity.dto.BusinessResponseDTO;
import bridge.backend.domain.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business")
public class BusinessController {
    private final BusinessService businessService;

    /*for admin*/
    @PostMapping()
    public ResponseEntity<?> createBusiness(@RequestBody BusinessRequestDTO req){
        Long id = businessService.saveBusiness(req);

        Map<String, Long> res = new HashMap<>();
        res.put("businessId", id);
        return ResponseEntity.ok(res);
    }
    @PutMapping("/{businessId}")
    public ResponseEntity<?> updateBusiness(@PathVariable("businessId") Long id, @RequestBody BusinessRequestDTO req){
        businessService.updateBusiness(id, req);
        return new ResponseEntity<>("사업 목록이 정상적으로 수정되었습니다.", HttpStatus.CREATED);

    }
    @DeleteMapping("/{businessId}")
    public ResponseEntity<?> deleteBusiness(@PathVariable("businessId") Long id){
        businessService.deleteBusiness(id);
        return new ResponseEntity<>("사업 목록을 정상적으로 삭제했습니다.", HttpStatus.CREATED);
    }


    /*for user*/
    @GetMapping("/byFilter")
    public ResponseEntity<?> getBusinessesByFilter(@RequestParam("idx") List<Integer> idxList, @RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10);
        List<BusinessResponseDTO> res = businessService.findBusinessByType(idxList, pageable);
        return ResponseEntity.ok(Map.of("size", res.size(),"data",res));

    }

    @GetMapping("/byMonthAndFilter")
    public ResponseEntity<?> getBusinessesByMonth(@RequestParam(name="date", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) YearMonth date, @RequestParam(name="idx", required = false) List<Integer> idxList){
        YearMonth searchDate = (date==null)?YearMonth.from(LocalDate.now()):date;
        List<BusinessResponseDTO> res = businessService.findBusinessByMonthAndFilter(searchDate, idxList);
        return ResponseEntity.ok(res);

    }
    
    @GetMapping("/byFilterAndSortingNew")
    public ResponseEntity<?> getBusinessesByNew(@RequestParam(name="idx", required = false) List<Integer> idxList, @RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        List<BusinessResponseDTO> res = businessService.findAll(idxList, pageable);
        return ResponseEntity.ok(Map.of("size", res.size(),"data",res));

    }
    @GetMapping("/byFilterAndSortingDeadline")
    public ResponseEntity<?> getBusinessesByDeadline(@RequestParam(name="idx", required = false) List<Integer> idxList, @RequestParam(name="page", required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("dDay").ascending());
        List<BusinessResponseDTO> res = businessService.findBusinessByDDayGreaterThanZero(idxList, pageable);
        return ResponseEntity.ok(Map.of("size", res.size(),"data",res));

    }
}
