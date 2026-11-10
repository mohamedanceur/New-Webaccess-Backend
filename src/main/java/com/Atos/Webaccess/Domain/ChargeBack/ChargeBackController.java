
package com.Atos.Webaccess.Domain.ChargeBack;

import com.Atos.Webaccess.Domain.ChargeBack.DTO.ChargeBackResponse;
import com.Atos.Webaccess.Domain.ChargeBack.DTO.ChargeBackSearchRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chargebacks")
@RequiredArgsConstructor
public class ChargeBackController {

    private final ChargeBackService chargeBackService;

    @PostMapping("/search")
    public ResponseEntity<List<ChargeBackResponse>> search(
            @RequestBody ChargeBackSearchRequest request) {

        return ResponseEntity.ok(
                chargeBackService.search(request)
        );
    }
}
