package core.controllers;

import core.models.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by user on 20.02.15.
 */

@RestController

public class QueryController extends BaseController {

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Query newQuery(@RequestBody Query query) {
        if (query != null) {
            return query;
        } else {
            throw new QueryDoesnExistExceptions("NOT SENDED");
        }
    }
}
