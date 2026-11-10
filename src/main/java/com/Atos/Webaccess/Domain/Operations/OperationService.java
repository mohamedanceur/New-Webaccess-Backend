
package com.Atos.Webaccess.Domain.Operations;

import com.Atos.Webaccess.Domain.Operations.DTO.OperationSearchRequest;
import com.Atos.Webaccess.Domain.Operations.Specifications.OperationSpecifications.SearchContext;

import java.util.List;

public interface OperationService {

    List<Operation> searchOperations(
            OperationSearchRequest request,
            SearchContext context
    );
}
