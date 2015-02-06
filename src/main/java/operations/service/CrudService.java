package operations.service;

import operations.operationDeterminators.OperationState;

/**
 * Created on 30.01.15.
 */
public interface CrudService {

    public OperationState perform(String ... queryParts);
}
