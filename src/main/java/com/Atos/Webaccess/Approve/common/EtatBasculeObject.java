package com.Atos.Webaccess.Approve.common;


import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a bascule state and indicates whether
 * a rejection reason is mandatory.
 *
 * Migrated from the legacy Approve application.
 */
@Getter
@Setter
@NoArgsConstructor
public class EtatBasculeObject {

    private String etatBascule;

    private String libEtatBascule;

    /**
     * 1 = Rejection reason required.
     * 0 = Rejection reason not required.
     */
    private int motifObl;

    /**
     * Determines whether the specified bascule state
     * requires a rejection reason.
     *
     * Legacy behavior:
     * - Non-null map value: motifObl = 1
     * - Null or missing map value: motifObl = 0
     *
     * @param mapEtatRejet map containing rejection states
     * @param etatBascule bascule state to check
     */
    public void setMotifObl(
            Map<Integer, ?> mapEtatRejet,
            String etatBascule) {

        if (mapEtatRejet.get(Integer.valueOf(etatBascule)) == null) {
            this.motifObl = 0;
        } else {
            this.motifObl = 1;
        }
    }
}
