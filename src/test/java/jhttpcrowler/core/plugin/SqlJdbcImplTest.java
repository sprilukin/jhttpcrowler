package jhttpcrowler.core.plugin;

import jhttpcrowler.core.plugin.utils.ConnectionParametersBuilder;
import jhttpcrowler.core.plugin.utils.PreparedStatementParametersBuilder;
import org.junit.Before;
import org.junit.Test;
import org.unitils.database.config.PropertiesDataSourceFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

import static junit.framework.Assert.*;

/**
 * Tests {@link SqlImpl} plugin
 *
 * @author Sergey Prilukin
 */
public class SqlJdbcImplTest {

    private SqlImpl sql = new SqlImpl();

    @Before
    public void fillDb() throws Exception {
        System.setProperty("java.naming.factory.initial", "jhttpcrowler.core.plugin.TestInitialContextFactory");

        Map<String, String> params = (new ConnectionParametersBuilder())
                .add(PropertiesDataSourceFactory.PROPKEY_DATASOURCE_DRIVERCLASSNAME, "org.hsqldb.jdbcDriver")
                .add(PropertiesDataSourceFactory.PROPKEY_DATASOURCE_URL, "jdbc:hsqldb:mem:hsqldb")
                .add(PropertiesDataSourceFactory.PROPKEY_DATASOURCE_USERNAME, "sa")
                .add(PropertiesDataSourceFactory.PROPKEY_DATASOURCE_PASSWORD, "")
                .get();


        Properties properties = new Properties();
        properties.putAll(params);

        PropertiesDataSourceFactory dataSourceFactory = new PropertiesDataSourceFactory();
        dataSourceFactory.init(properties);

        InitialContext initialContext = new InitialContext();
        initialContext.bind("java:comp/env/jdbc/hsql", dataSourceFactory.createDataSource());

        DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/hsql");

        boolean result = sql.execute(ds.getConnection(),
                "drop table if exists customer");
        assertFalse("Result of execution should not be a ResultSet", result);

        result = sql.execute(ds.getConnection(),
                "create table customer (\n" +
                "        customer_id bigint,\n" +
                "        lname varchar(100),\n" +
                "        fname varchar(100),\n" +
                "        address1 varchar(100),\n" +
                "        city varchar(100),\n" +
                "        state_province varchar(100),\n" +
                "        country varchar(100),\n" +
                "        gender varchar(100),\n" +
                "        date_accnt_opened datetime\n" +
                "    )");
        assertFalse("Result of execution should not be a ResultSet", result);

        result = sql.execute(ds.getConnection(),
                "insert into customer values (1, 'lname1', 'fname1', 'address1', 'city1', 'province1', 'country1', 'M', null)"
                );
        assertFalse("Result of execution should not be a ResultSet", result);

        result = sql.execute(ds.getConnection(),
                "insert into customer values (2, 'lname2', 'fname2', 'address2', 'city2', 'province2', 'country2', 'F', null)"
                );
        assertFalse("Result of execution should not be a ResultSet", result);
    }

    @Test
    public void test() throws Exception {
        Map<String, String> params = (new ConnectionParametersBuilder())
                .add("driver", "org.hsqldb.jdbcDriver")
                .add("url", "jdbc:hsqldb:mem:hsqldb")
                .add("user", "sa")
                .add("password", "")
                .add("maxRows", "10")
                .get();


        List<Sql.PreparedStatementParameter> statements = getPreparedStatements();
        Set<String> names = getColumnNames();
        String sql = getSql();

        List<Map<String, Object>> res = this.sql.executeQuery(params, sql, statements, names);
        printResult(res);

        assertNotNull(res);
        assertEquals(1, res.size());
    }

    private void printResult(List<Map<String, Object>> res) {
        for (Map<String, Object> row: res) {
            System.out.println();
            for (Map.Entry<String, Object> column: row.entrySet()) {
                System.out.print(String.format("%s, ", column.getValue()));
            }
        }
    }

    private String getSql() {
        return "select customer_id, lname, fname, " +
                    "address1, city, state_province, country, " +
                    "gender, date_accnt_opened from customer where gender = ?";
    }

    private Set<String> getColumnNames() {
        Set<String> names = new HashSet<String>();
        Collections.addAll(names,
                "customer_id",
                "lname",
                "fname",
                "address1",
                "city",
                "state_province",
                "country",
                "gender",
                "date_accnt_opened");
        return names;
    }

    private List<Sql.PreparedStatementParameter> getPreparedStatements() {
        return (new PreparedStatementParametersBuilder())
                    .add(new Sql.PreparedStatementParameter(Types.VARCHAR, "M"))
                    .get();
    }

    @Test
    public void testJndi() throws Exception {
        List<Sql.PreparedStatementParameter> statements = getPreparedStatements();
        Set<String> names = getColumnNames();
        String sql = getSql();

        List<Map<String, Object>> res = this.sql.executeQuery("jdbc/hsql", sql, 10, statements, names);
        printResult(res);

        assertNotNull(res);
        assertEquals(1, res.size());
    }
}
