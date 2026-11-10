package com.Atos.Webaccess.Domain.Operations;

import com.Atos.Webaccess.Domain.Operations.DTO.OperationSearchRequest;
import com.Atos.Webaccess.Domain.Operations.Specifications.OperationSpecifications.SearchContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final OperationSearchContextService contextService;

    @PostMapping("/search")
    public List<Operation> searchOperations(
            @RequestBody OperationSearchRequest request) {

        SearchContext context =
                contextService.getCurrentUserContext();

        return operationService.searchOperations(request, context);
    }
}