package core.models;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by user on 20.02.15.
 */
public class Query {

    String conditions;
    String table;
    List <String> selectables;

    public Query (Query query) {

    }
}
