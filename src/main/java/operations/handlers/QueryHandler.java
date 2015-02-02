package operations.handlers;

import java.util.Map;

/**
 * Created on 02.02.15.
 */
public abstract class QueryHandler<T> {

    QueryHandler next;

    abstract Map.Entry<String, String> handle(Object request);

    public void setNext(QueryHandler queryHandler) {
        next = queryHandler;
    }

}
