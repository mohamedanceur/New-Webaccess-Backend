package com.Atos.Webaccess.Domain.Tache;


import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
public class TacheController {

    private final TacheService tacheService;

    @GetMapping
    public ResponseEntity<List<Tache>> getActiveTaches() {
        return ResponseEntity.ok(tacheService.getActiveTaches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTache(@PathVariable Integer id) {
        Tache tache = tacheService.getTache(id);

        if (tache == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tache);
    }

    @PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        Tache createdTache = tacheService.createTache(tache);

        URI location = URI.create("/api/taches/" + createdTache.getId());

        return ResponseEntity.created(location).body(createdTache);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTache(
            @PathVariable Integer id,
            @RequestBody Tache tache) {

        boolean updated = tacheService.updateTache(id, tache);

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Integer id) {
        boolean deleted = tacheService.deleteTache(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
