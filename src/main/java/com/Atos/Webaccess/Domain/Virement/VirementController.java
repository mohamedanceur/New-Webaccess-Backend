
package com.Atos.Webaccess.Domain.Virement;

import com.Atos.Webaccess.Domain.Virement.DTO.VirementRequest;
import com.Atos.Webaccess.Domain.Virement.DTO.VirementResponse;
import com.Atos.Webaccess.User.Entities.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/virements")
@RequiredArgsConstructor
public class VirementController {

    private final VirementService virementService;

    // 1. Create client transfer
    @PostMapping("/client")
    public ResponseEntity<VirementResponse> addVirementCli(
            @RequestBody VirementRequest request,
            @AuthenticationPrincipal User user,
            HttpServletRequest httpRequest) {

        VirementResponse response = virementService.addVirementCli(
                request,
                user,
                httpRequest.getRemoteAddr()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Create bank-to-bank transfer
    @PostMapping("/bank-to-bank")
    public ResponseEntity<VirementResponse> addVirementBab(
            @RequestBody VirementRequest request,
            @AuthenticationPrincipal User user,
            HttpServletRequest httpRequest) {

        VirementResponse response = virementService.addVirementBab(
                request,
                user,
                httpRequest.getRemoteAddr()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 3. Create international transfer
    @PostMapping("/international")
    public ResponseEntity<VirementResponse> addVirementEtr(
            @RequestBody VirementRequest request,
            @AuthenticationPrincipal User user,
            HttpServletRequest httpRequest) {

        VirementResponse response = virementService.addVirementEtr(
                request,
                user,
                httpRequest.getRemoteAddr()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 4. List pending transfers
    @GetMapping("/pending")
    public ResponseEntity<List<VirementResponse>> getPendingVirements() {

        return ResponseEntity.ok(
                virementService.getPendingVirements()
        );
    }

    // 5. Validate selected transfers
    @PostMapping("/validate")
    public ResponseEntity<Integer> validateVirements(
            @RequestBody List<Integer> pendingIds,
            @AuthenticationPrincipal User user,
            HttpServletRequest request) {

        int result = virementService.validateVirements(
                pendingIds,
                user,
                request.getRemoteAddr()
        );

        return ResponseEntity.ok(result);
    }

    // 6. Delete a pending transfer
    @DeleteMapping("/{virementId}/pending/{pendingOperationId}")
    public ResponseEntity<Integer> deleteVirement(
            @PathVariable Integer virementId,
            @PathVariable Integer pendingOperationId) {

        int result = virementService.deleteVirement(
                virementId,
                pendingOperationId
        );

        return ResponseEntity.ok(result);
    }
}
