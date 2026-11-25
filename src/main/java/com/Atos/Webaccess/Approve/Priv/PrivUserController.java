package com.Atos.Webaccess.Approve.Priv;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/approve/privileges")
@RequiredArgsConstructor
public class PrivUserController {

    public record TaskSelection(@NotNull Set<Integer> taskIds) {}

    private final PrivUserService privUserService;

    @GetMapping("/users")
    public List<PrivUserService.UserItem> users() {
        return privUserService.users();
    }

    @GetMapping("/users/{userId}/tasks")
    public List<Integer> userTasks(@PathVariable Long userId) {
        return privUserService.taskIds(userId);
    }

    @PutMapping("/users/{userId}/tasks")
    public ResponseEntity<Void> replaceTasks(@PathVariable Long userId,
                                             @Valid @RequestBody TaskSelection selection) {
        privUserService.replaceTasks(userId, selection.taskIds());
        return ResponseEntity.noContent().build();
    }
}