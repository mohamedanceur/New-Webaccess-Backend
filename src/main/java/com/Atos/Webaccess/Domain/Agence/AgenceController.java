package com.Atos.Webaccess.Domain.Agence;




import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final AgenceService agenceService;

    @GetMapping
    public List<AgenceResponse> findAll() {
        return agenceService.findAll();
    }

    @GetMapping("/{id}")
    public AgenceResponse findById(@PathVariable Integer id) {
        return agenceService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AgenceResponse> create(
            @RequestBody AgenceRequest request) {

        AgenceResponse created = agenceService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public AgenceResponse update(
            @PathVariable Integer id,
            @RequestBody AgenceRequest request) {

        return agenceService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public AgenceResponse changeStatus(
            @PathVariable Integer id,
            @RequestParam Integer statut) {

        return agenceService.changeStatus(id, statut);
    }

    @GetMapping("/by-bank/{idBanque}")
    public List<AgenceResponse> findByBank(
            @PathVariable Integer idBanque) {

        return agenceService.findByBank(idBanque);
    }

    @GetMapping("/by-group/{idGroupe}")
    public List<AgenceResponse> findByGroup(
            @PathVariable Integer idGroupe) {

        return agenceService.findByGroup(idGroupe);
    }

    @GetMapping("/valid")
    public List<AgenceResponse> findAllValid() {
        return agenceService.findAllValid();
    }

    @GetMapping("/{id}/name")
    public String getAgenceName(@PathVariable Integer id) {
        return agenceService.getAgenceName(id);
    }

    @GetMapping("/by-code/{code}/libelle")
    public String getAgenceLibelle(@PathVariable String code) {
        return agenceService.getAgenceLibelle(code);
    }
}
