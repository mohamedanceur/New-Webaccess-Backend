package com.Atos.Webaccess.Domain.Groupes;





import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
@RequiredArgsConstructor
public class GroupeController {

    private final GroupeService groupeService;

    @GetMapping("/{id}")
    public GroupeResponse findById(@PathVariable Integer id) {
        return groupeService.findById(id);
    }

    @GetMapping
    public List<GroupeResponse> findAllActive() {
        return groupeService.findAllActive();
    }

    @PostMapping
    public GroupeResponse create(
            @Valid @RequestBody GroupeRequest request) {
        return groupeService.create(request);
    }

    @PutMapping("/{id}")
    public GroupeResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody GroupeRequest request) {
        return groupeService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public GroupeResponse changeStatus(
            @PathVariable Integer id,
            @RequestParam Integer statut) {
        return groupeService.changeStatus(id, statut);
    }
}
