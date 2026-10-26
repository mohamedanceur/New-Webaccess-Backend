package com.Atos.Webaccess.Domain.Banque;




import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banques")
@RequiredArgsConstructor
public class BanqueController {

    private final BanqueService banqueService;

    @GetMapping
    public List<BanqueResponse> findAll() {
        return banqueService.findAll();
    }

    @GetMapping("/{id}")
    public BanqueResponse findById(@PathVariable Integer id) {
        return banqueService.findById(id);
    }

    @PostMapping
    public BanqueResponse create(@Valid @RequestBody BanqueRequest request) {
        return banqueService.create(request);
    }

    @PutMapping("/{id}")
    public BanqueResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody BanqueRequest request) {
        return banqueService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public BanqueResponse changeStatus(
            @PathVariable Integer id,
            @RequestParam Integer statut) {
        return banqueService.changeStatus(id, statut);
    }

    @GetMapping("/{id}/name")
    public String getBanqueName(@PathVariable Integer id) {
        return banqueService.getBanqueName(id);
    }

    @GetMapping("/by-groupe/{idGroupe}")
    public List<BanqueResponse> findByGroupe(
            @PathVariable Integer idGroupe) {
        return banqueService.findByGroupe(idGroupe);
    }

    @GetMapping("/banque-mere/id")
    public Integer getBanqueMereId() {
        return banqueService.getBanqueMereId();
    }
}
