package operations.operationDeterminators;

import operations.Crud;
import operations.service.CreateService;
import operations.service.CrudService;

/**
 * Created on 30.01.15.
 */
public class CrudDeterminator {

    public static int handleInput(String str) {

        try {
            switch (Crud.valueOf(str)) {
                case create: return 1;
/*                new CreateService;*/
                case retrieve:return 1;
                   /* break;*/
                case update:return 1;
                /*    break;*/
                case delete:return 1;
          /*          break;*/
                default:
                    return 0;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Selected Operation: " + e.getMessage());
            return 0;
        }
    }

}
