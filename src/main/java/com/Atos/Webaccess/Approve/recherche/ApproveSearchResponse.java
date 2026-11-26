package com.Atos.Webaccess.Approve.recherche;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ApproveSearchResponse {
    private String instrument;
    private String sens;
    private long total;
    private List<ApproveOperationDto> operations;
}
