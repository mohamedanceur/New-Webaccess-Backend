package com.Atos.Webaccess.Domain.parametres;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parametres")
@RequiredArgsConstructor
public class ParametresController {

    private final ParametresService parametresService;

    @GetMapping("/filtre-incidents-paiement")
    public ResponseEntity<String> getIncidentFilter() {
        return ResponseEntity.ok(
                parametresService.getIncidentFilter());
    }
}
