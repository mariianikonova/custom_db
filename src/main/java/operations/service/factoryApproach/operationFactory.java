package operations.service.factoryApproach;

import operations.service.*;

/**
 * Created on 30.01.15.
 */
public interface operationFactory {

    CreateService createCreateService();

    DeleteService createDeleteService();

    RetrieveService createRetrieveService();

    UpdateService createUpdateService();
}
