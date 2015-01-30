package operations.service;

import com.sun.corba.se.spi.orb.Operation;
import operations.operationDeterminators.OperationState;

/**
 * Created on 30.01.15.
 */
public interface CrudService {

    public OperationState perform(String data);
}
