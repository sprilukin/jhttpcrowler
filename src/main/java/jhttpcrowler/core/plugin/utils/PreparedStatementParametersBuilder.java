package jhttpcrowler.core.plugin.utils;

import jhttpcrowler.core.plugin.Sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey
 * Date: 20.07.11
 * Time: 9:23
 * To change this template use File | Settings | File Templates.
 */
public class PreparedStatementParametersBuilder {
    private List<Sql.PreparedStatementParameter> params = new ArrayList<Sql.PreparedStatementParameter>();

    public PreparedStatementParametersBuilder add(Sql.PreparedStatementParameter param) {
        this.params.add(param);
        return this;
    }

    public List<Sql.PreparedStatementParameter> get() {
        return this.params;
    }
}
