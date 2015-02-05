package operations.factories;

import operations.handlers.QueryHandler;
import operations.handlers.QueryHandlerChain;

/**
 * Created on 02.02.15.
 */
public class CreateQuery implements Operation, QueryHandlerChain {

    private String query;

    public CreateQuery(String query) {
    }

    @Override
    public void process() {

    }


    @Override
    public QueryHandlerChain addToChain(QueryHandlerChain extension) {
        return null;
    }
}
