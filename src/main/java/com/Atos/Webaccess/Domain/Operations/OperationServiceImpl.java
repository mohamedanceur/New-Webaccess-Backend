
package com.Atos.Webaccess.Domain.Operations;

import com.Atos.Webaccess.Domain.Operations.DTO.OperationSearchRequest;
import com.Atos.Webaccess.Domain.Operations.Specifications.OperationSpecifications;
import com.Atos.Webaccess.Domain.Operations.Specifications.OperationSpecifications.SearchContext;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Override
    public List<Operation> searchOperations(
            OperationSearchRequest request,
            SearchContext context) {

        return operationRepository.findAll(
                OperationSpecifications.search(request, context)
        );
    }
}
