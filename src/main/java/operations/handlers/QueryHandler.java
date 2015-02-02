package operations.handlers;

/**
 * Created on 02.02.15.
 */
public abstract class QueryHandler<T> {

    QueryHandler next;

    abstract void handle(T request);

    public void setNext(QueryHandler queryHandler) {
        next = queryHandler;
    }

}
