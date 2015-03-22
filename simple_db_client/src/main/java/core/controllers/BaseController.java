package core.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by user on 20.02.15.
 */
@ControllerAdvice
public class BaseController {

    @ResponseBody
    @ExceptionHandler(QueryDoesnExistExceptions.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    QueryDoesnExistExceptions userNotFoundExceptionHandler(QueryDoesnExistExceptions ex) {
        return new QueryDoesnExistExceptions(ex.getMessage());
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    class QueryDoesnExistExceptions extends RuntimeException {
        public QueryDoesnExistExceptions(String message) {
            super("could not find user '" + message + "'.");
        }
    }
}
