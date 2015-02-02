package operations.handlers;

/**
 * Created on 02.02.15.
 */
public interface QueryHandlerChain<T> {

    QueryHandlerChain<T> addToChain(QueryHandlerChain<T> extension);
}
