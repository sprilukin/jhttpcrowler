package jhttpcrowler.core.plugin;

import jhttpcrowler.core.Plugin;

import java.sql.*;
import java.util.*;

/**
 * @author Sergey
 * @since 05.12.2010 4:04:56
 *        <p/>
 *        TODO: add class description
 */
public interface Sql extends Plugin {
    public boolean execute(Map<String, String> connectionParams, String sql) throws Exception;
    public boolean execute(Map<String, String> connectionParams, String sql, List<PreparedStatementParameter> params) throws Exception;
    public int executeUpdate(Map<String, String> connectionParams, String sql, List<PreparedStatementParameter> params) throws Exception;
    public int executeUpdate(Map<String, String> connectionParams, String sql) throws Exception;
    public List<Map<String, Object>> executeQuery(
            Map<String, String> connectionParams, String sql,
            List<PreparedStatementParameter> params, Set<String> columnNames) throws Exception;
    public List<Map<String, Object>> executeQuery(
            Map<String, String> connectionParams, String sql,
            Set<String> columnNames) throws Exception;

    public boolean execute(Connection connection, String sql) throws Exception;
    public boolean execute(Connection connection, String sql, List<PreparedStatementParameter> params) throws Exception;
    public int executeUpdate(Connection connection, String sql, List<PreparedStatementParameter> params) throws Exception;
    public int executeUpdate(Connection connection, String sql) throws Exception;
    public List<Map<String, Object>> executeQuery(
            Connection connection, String sql, int maxRows,
            List<PreparedStatementParameter> params, Set<String> columnNames) throws Exception;
    public List<Map<String, Object>> executeQuery(
            Connection connection, String sql, int maxRows,
            Set<String> columnNames) throws Exception;

    public boolean execute(PreparedStatement statement) throws Exception;
    public int executeUpdate(PreparedStatement statement) throws Exception;
    public List<Map<String, Object>> executeQuery(PreparedStatement statement, int maxRows, Set<String> columnNames) throws Exception;

    public boolean execute(Statement statement, String sql) throws Exception;
    public int executeUpdate(Statement statement, String sql) throws Exception;
    public List<Map<String, Object>> executeQuery(
            Statement statement, String sql, int maxRows, Set<String> columnNames) throws Exception;

    public boolean execute(String jndiString, String sql) throws Exception;
    public boolean execute(String jndiString, String sql, List<PreparedStatementParameter> params) throws Exception;
    public int executeUpdate(String jndiString, String sql, List<PreparedStatementParameter> params) throws Exception;
    public int executeUpdate(String jndiString, String sql) throws Exception;
    public List<Map<String, Object>> executeQuery(
            String jndiString, String sql, int maxRows,
            List<PreparedStatementParameter> params, Set<String> columnNames) throws Exception;
    public List<Map<String, Object>> executeQuery(
            String jndiString, String sql, int maxRows,
            Set<String> columnNames) throws Exception;

    public class PreparedStatementParameter {
        private int type;
        private Object value;
        private int scaleOrLength;

        public PreparedStatementParameter(int type, Object value) {
            this.type = type;
            this.value = value;
        }

        public PreparedStatementParameter(int type, Object value, int scaleOrLength) {
            this.type = type;
            this.value = value;
            this.scaleOrLength = scaleOrLength;
        }

        public int getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        public int getScaleOrLength() {
            return scaleOrLength;
        }
    }
}
