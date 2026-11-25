package com.Atos.Webaccess.Approve.transition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransitionRequest(
        @NotBlank String sens,
        @NotNull Integer instrument,
        @NotNull Integer fromState,
        @NotNull Integer toState,
        @Size(max = 3) String motifRejet   // required only when toState is a rejection state
) {}