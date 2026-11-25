package com.Atos.Webaccess.Approve;

import com.Atos.Webaccess.Approve.recherche.ApproveSearchResponse;
import com.Atos.Webaccess.Approve.recherche.ApproveSearchService;
import com.Atos.Webaccess.Approve.recherche.response.ApproveAccessResponse;
import com.Atos.Webaccess.Approve.service.ApproveAccessService;
import com.Atos.Webaccess.Approve.transition.ApproveTransitionService;
import com.Atos.Webaccess.Approve.transition.TransitionRequest;
import com.Atos.Webaccess.Approve.transition.TransitionResult;
import com.Atos.Webaccess.Approve.transition.ValidateAllResult;
import com.Atos.Webaccess.Domain.Operations.DTO.ApproveSearchRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("/api/approve")
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "com.Atos.Webaccess.Approve")
public class ApproveController {

    private final ApproveAccessService approveAccessService;
    private final ApproveSearchService approveSearchService;
    private final ApproveTransitionService approveTransitionService;

    @GetMapping("/access")
    public ApproveAccessResponse getAccess() {
        return approveAccessService.getAccessData();
    }

    @PostMapping("/operations/search")
    public ApproveSearchResponse searchOperations(
            @Valid @RequestBody ApproveSearchRequest request) {
        return approveSearchService.search(request);
    }
    
    @PostMapping("/operations/{operationId}/transition")
    public TransitionResult transition(
            @PathVariable Integer operationId,
            @Valid @RequestBody TransitionRequest request,
            HttpServletRequest http) {
        return approveTransitionService.transition(operationId, request, http.getRemoteHost());
    }
    
    @PostMapping("/operations/validate-all")
    public ValidateAllResult validateAll(@RequestBody ApproveSearchRequest criteria, HttpServletRequest http) {
        return approveTransitionService.validateAll(criteria, http.getRemoteHost());
    }
}
