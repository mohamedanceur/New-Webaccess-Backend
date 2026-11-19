package com.Atos.Webaccess.Approve;

import com.Atos.Webaccess.Approve.recherche.ApproveSearchResponse;
import com.Atos.Webaccess.Approve.recherche.ApproveSearchService;
import com.Atos.Webaccess.Approve.recherche.response.ApproveAccessResponse;
import com.Atos.Webaccess.Approve.service.ApproveAccessService;
import com.Atos.Webaccess.Domain.Operations.DTO.ApproveSearchRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/approve")
@RequiredArgsConstructor
public class ApproveController {

    private final ApproveAccessService approveAccessService;
    private final ApproveSearchService approveSearchService;

    @GetMapping("/access")
    public ApproveAccessResponse getAccess() {
        return approveAccessService.getAccessData();
    }

    @PostMapping("/operations/search")
    public ApproveSearchResponse searchOperations(
            @Valid @RequestBody ApproveSearchRequest request) {
        return approveSearchService.search(request);
    }
}
