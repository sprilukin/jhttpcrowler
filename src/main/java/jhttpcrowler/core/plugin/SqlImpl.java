package jhttpcrowler.core.plugin;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey
 * Date: 15.07.11
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public class SqlImpl implements Sql {

    public static final String NAME = "sql";

    public static final String DRIVER = "driver";
    public static final String URL = "url";
    public static final String USER = "user";
    public static final String PASSWORD = "password";

    public static final Integer MAX_ROWS = 100;
    public static final String MAX_ROWS_KEY = "maxRows";

    public static final String JNDI_LOOKUP_PREFIX = "java:comp/env/";

    private Connection getJdbcConnection(Map<String, String> connectionParams) throws Exception {
        String driver = connectionParams.get(DRIVER);
        String url = connectionParams.get(URL);
        String user = connectionParams.get(USER);
        String password = connectionParams.get(PASSWORD);

        //Test if class exists
        Class.forName(driver);

        return DriverManager.getConnection(url, user, password);
    }

    private PreparedStatement prepareStatement(
            Connection connection, String sql, List<PreparedStatementParameter> params) throws Exception {

        PreparedStatement statement = connection.prepareStatement(sql);

        if (params != null) {
            for (int i = 1; i <= params.size(); i++) {
                PreparedStatementParameter param = params.get(i - 1);
                statement.setObject(i, param.getValue(), param.getType(), param.getScaleOrLength());
            }
        }

        return statement;
    }

    private Object executeInTransaction(Statement statement, DoInTransaction inTransaction) throws Exception {
        Object res = null;
        if (statement == null) {
            return null;
        }

        if (statement.getConnection() == null) {
            throw new SQLException("Can not get connection from statement");
        }

        Connection conn = statement.getConnection();

        try {
            conn.setAutoCommit(false);
            res = inTransaction.execute();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.err);

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException re) {
                    re.printStackTrace(System.err);
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            conn.setAutoCommit(true);
        }

        return res;
    }

    private Connection getJndiConnection(String jndiString) throws Exception {
        if (jndiString == null) {
            return null;
        }

        InitialContext context = new InitialContext();
        DataSource dataSource = (DataSource) context
                .lookup(jndiString.startsWith(JNDI_LOOKUP_PREFIX) ? jndiString : JNDI_LOOKUP_PREFIX + jndiString);
        return dataSource.getConnection();
    }

    public boolean execute(final Statement statement, final String sql) throws Exception {
        Object res = executeInTransaction(statement, new DoInTransaction() {
            public Object execute() throws Exception {
                return statement.execute(sql);
            }
        });

        return (Boolean)res;
    }

    public boolean execute(final PreparedStatement statement) throws Exception {
        Object res = executeInTransaction(statement, new DoInTransaction() {
            public Object execute() throws Exception {
                return statement.execute();
            }
        });

        return (Boolean)res;
    }

    public boolean execute(Connection connection, String sql, List<PreparedStatementParameter> params) throws Exception {
        final PreparedStatement statement = prepareStatement(connection, sql, params);
        return execute(statement);
    }

    public boolean execute(Map<String, String> connectionParams, final String sql, List<PreparedStatementParameter> params) throws Exception {
        Connection connection = getJdbcConnection(connectionParams);
        return execute(connection, sql, params);
    }

    public boolean execute(Map<String, String> connectionParams, String sql) throws Exception {
        return execute(connectionParams, sql, null);
    }

    public boolean execute(Connection connection, String sql) throws Exception {
        return execute(connection, sql, null);
    }

    public boolean execute(String jndiString, String sql) throws Exception {
        return execute(jndiString, sql, null);
    }

    public boolean execute(String jndiString, String sql, List<PreparedStatementParameter> params) throws Exception {
        return execute(getJndiConnection(jndiString), sql, params);
    }

    public int executeUpdate(final Statement statement, final String sql) throws Exception {
        Object res = executeInTransaction(statement, new DoInTransaction() {
            public Object execute() throws Exception {
                return statement.executeUpdate(sql);
            }
        });

        return (Integer)res;
    }

    public int executeUpdate(final PreparedStatement statement) throws Exception {
        Object res = executeInTransaction(statement, new DoInTransaction() {
            public Object execute() throws Exception {
                return statement.executeUpdate();
            }
        });

        return (Integer)res;
    }

    public int executeUpdate(Connection connection, String sql, List<PreparedStatementParameter> params) throws Exception {
        final PreparedStatement statement = prepareStatement(connection, sql, params);
        return executeUpdate(statement);
    }

    public int executeUpdate(
            Map<String, String> connectionParams,
            final String sql,
            List<PreparedStatementParameter> params) throws Exception {

        Connection connection = getJdbcConnection(connectionParams);
        return executeUpdate(connection, sql, params);
    }

    public int executeUpdate(String jndiString, String sql, List<PreparedStatementParameter> params) throws Exception {
        return executeUpdate(getJndiConnection(jndiString), sql, params);
    }

    public int executeUpdate(Map<String, String> connectionParams, String sql) throws Exception {
        return executeUpdate(connectionParams, sql, null);
    }

    public int executeUpdate(Connection connection, String sql) throws Exception {
        return executeUpdate(connection, sql, null);
    }

    public int executeUpdate(String jndiString, String sql) throws Exception {
        return executeUpdate(jndiString, sql, null);
    }

    private List<Map<String, Object>> convertResultSet(ResultSet resultSet, Set<String> columnNames) throws Exception {
        List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();

        while (resultSet.next()) {
            Map<String, Object> resMap = new HashMap<String, Object>(columnNames.size());
            for (String columnName: columnNames) {
                resMap.put(columnName, resultSet.getObject(columnName));
            }

            res.add(resMap);
        }

        return res;
    }

    public List<Map<String, Object>> executeQuery(
            final Statement statement, final String sql, int maxRows, final Set<String> columnNames) throws Exception {

        statement.setMaxRows(maxRows);

        Object res = executeInTransaction(statement, new DoInTransaction() {
            public Object execute() throws Exception {

                ResultSet resultSet = statement.executeQuery(sql);
                return convertResultSet(resultSet, columnNames);
            }
        });

        return (List<Map<String, Object>>)res;
    }

    public List<Map<String, Object>> executeQuery(
            final PreparedStatement statement, int maxRows, final Set<String> columnNames) throws Exception {
        statement.setMaxRows(maxRows);

        Object res = executeInTransaction(statement, new DoInTransaction() {
            public Object execute() throws Exception {

                ResultSet resultSet = statement.executeQuery();
                return convertResultSet(resultSet, columnNames);
            }
        });

        return (List<Map<String, Object>>)res;
    }

    public List<Map<String, Object>> executeQuery(
            Connection connection, String sql, int maxRows,
            List<PreparedStatementParameter> params, final Set<String> columnNames) throws Exception {

        final PreparedStatement statement = prepareStatement(connection, sql, params);
        return executeQuery(statement, maxRows, columnNames);
    }

    public List<Map<String, Object>> executeQuery(
            Map<String, String> connectionParams,
            final String sql,
            List<PreparedStatementParameter> params,
            final Set<String> columnNames) throws Exception {

        Connection connection = getJdbcConnection(connectionParams);
        Integer maxRows = connectionParams.containsKey(MAX_ROWS_KEY)
                ? Integer.parseInt(connectionParams.get(MAX_ROWS_KEY))
                : MAX_ROWS;

        return executeQuery(connection, sql, maxRows, params, columnNames);
    }

    public List<Map<String, Object>> executeQuery(String jndiString, String sql, int maxRows, List<PreparedStatementParameter> params, Set<String> columnNames) throws Exception {
        return executeQuery(getJndiConnection(jndiString), sql, maxRows, params, columnNames);
    }

    public List<Map<String, Object>> executeQuery(Map<String, String> connectionParams, String sql, Set<String> columnNames) throws Exception {
        return executeQuery(connectionParams, sql, null, columnNames);
    }

    public List<Map<String, Object>> executeQuery(Connection connection, String sql, int maxRows, Set<String> columnNames) throws Exception {
        return executeQuery(connection, sql, maxRows, null, columnNames);
    }

    public List<Map<String, Object>> executeQuery(String jndiString, String sql, int maxRows, Set<String> columnNames) throws Exception {
        return executeQuery(jndiString, sql, maxRows, null, columnNames);
    }

    public String getName() {
        return NAME;
    }

    interface DoInTransaction {
        public Object execute() throws Exception;
    }
}
