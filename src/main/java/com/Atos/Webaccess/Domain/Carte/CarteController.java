package com.Atos.Webaccess.Domain.Carte;

import java.util.List;

import com.Atos.Webaccess.Domain.Carte.DTO.CarteRequest;
import com.Atos.Webaccess.Domain.Carte.DTO.CarteResponse;
import com.Atos.Webaccess.User.Entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartes")
@RequiredArgsConstructor
public class CarteController {

    private final CarteService carteService;

    @Value("${carte.devise:DZD}")
    private String currency;

    // 1. Pending operations

    @GetMapping("/pending")
    public ResponseEntity<List<CarteResponse>> getPendingOperations() {
        return ResponseEntity.ok(
                carteService.getPendingOperations()
        );
    }

    // 2. Add GAB

    @PostMapping("/gab")
    public ResponseEntity<CarteResponse> addCarteGAB(
            @Valid @RequestBody CarteRequest request,
            @AuthenticationPrincipal User user,
            HttpServletRequest httpRequest) {

        CarteResponse response = carteService.addCarteGAB(
                request,
                user,
                user.getNom(),
                httpRequest.getRemoteHost(),
                currency
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 3. Add TPE

    @PostMapping("/tpe")
    public ResponseEntity<CarteResponse> addCarteTPE(
            @Valid @RequestBody CarteRequest request,
            @AuthenticationPrincipal User user,
            HttpServletRequest httpRequest) {

        CarteResponse response = carteService.addCarteTPE(
                request,
                user,
                user.getNom(),
                httpRequest.getRemoteHost(),
                currency
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 4. Add TPE refund

    @PostMapping("/tpe/refund")
    public ResponseEntity<CarteResponse> addCarteRefundTPE(
            @Valid @RequestBody CarteRequest request,
            @AuthenticationPrincipal User user,
            HttpServletRequest httpRequest) {

        CarteResponse response = carteService.addCarteRefundTPE(
                request,
                user,
                user.getNom(),
                httpRequest.getRemoteHost(),
                currency
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 5. Load GAB for modification

    @GetMapping("/{idOperation}/{idCarte}/gab")
    public ResponseEntity<CarteRequest> getCarteGABForModification(
            @PathVariable Integer idOperation,
            @PathVariable Integer idCarte,
            @RequestParam Integer bankCodeLength,
            @RequestParam Integer agencyCodeLength) {

        return ResponseEntity.ok(
                carteService.getCarteGABForModification(
                        idOperation,
                        idCarte,
                        bankCodeLength,
                        agencyCodeLength
                )
        );
    }

    // 6. Load TPE / refund for modification

    @GetMapping("/{idOperation}/{idCarte}/tpe")
    public ResponseEntity<CarteRequest> getCarteTPEForModification(
            @PathVariable Integer idOperation,
            @PathVariable Integer idCarte,
            @RequestParam Integer finalAgencyCodeLength,
            @RequestParam Integer accountNumberLength,
            @RequestParam Integer ribKeyLength) {

        return ResponseEntity.ok(
                carteService.getCarteTPEForModification(
                        idOperation,
                        idCarte,
                        finalAgencyCodeLength,
                        accountNumberLength,
                        ribKeyLength
                )
        );
    }

    // 7. Modify GAB

    @PutMapping("/{idOperation}/gab")
    public ResponseEntity<CarteResponse> modifyCarteGAB(
            @PathVariable Integer idOperation,
            @Valid @RequestBody CarteRequest request,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                carteService.modifyCarteGAB(
                        idOperation,
                        request,
                        user,
                        currency
                )
        );
    }

    // 8. Modify TPE

    @PutMapping("/{idOperation}/tpe")
    public ResponseEntity<CarteResponse> modifyCarteTPE(
            @PathVariable Integer idOperation,
            @Valid @RequestBody CarteRequest request,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                carteService.modifyCarteTPE(
                        idOperation,
                        request,
                        user,
                        currency
                )
        );
    }

    // 9. Modify TPE refund

    @PutMapping("/{idOperation}/tpe/refund")
    public ResponseEntity<CarteResponse> modifyCarteRefundTPE(
            @PathVariable Integer idOperation,
            @Valid @RequestBody CarteRequest request,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                carteService.modifyCarteRefundTPE(
                        idOperation,
                        request,
                        user,
                        currency
                )
        );
    }

    // 10. Validate pending operations

    @PostMapping("/validate")
    public ResponseEntity<Void> validateOperations(
            @RequestBody List<Integer> operationIds,
            @AuthenticationPrincipal User user,
            HttpServletRequest httpRequest) {

        carteService.validateOperations(
                operationIds,
                user,
                httpRequest.getRemoteHost()
        );

        return ResponseEntity.noContent().build();
    }
}