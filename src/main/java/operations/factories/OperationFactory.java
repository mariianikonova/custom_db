package operations.factories;

import operations.service.*;

/**
 * Created on 30.01.15.
 */
public interface OperationFactory {

    CreateService createCreateService();

    DeleteService createDeleteService();

    RetrieveService createRetrieveService();

    UpdateService createUpdateService();
}
