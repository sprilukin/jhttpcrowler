package jhttpcrowler;

import jhttpcrowler.core.GroovyEngineTest;
import jhttpcrowler.core.GroovyShellEngineTest;
import jhttpcrowler.core.JavaScriptEngineTest;
import jhttpcrowler.core.PythonEngineTest;
import jhttpcrowler.core.RubyEngineTest;
import jhttpcrowler.core.plugin.EngineExecutorPluginTest;
import jhttpcrowler.core.plugin.JavaScriptPluginTest;
import jhttpcrowler.core.plugin.SqlJdbcImplTest;
import jhttpcrowler.utils.properties.PropertiesUtilTest;
import jhttpcrowler.utils.xml.XmlUtilTest;
import jhttpcrowler.utils.xml.html.HtmlCleanerUtilTest;
import jhttpcrowler.utils.xml.html.TidyCleanerUtilTest;
import jhttpcrowler.utils.xml.xpath.XpathUtilTest;
import jhttpcrowler.utils.xml.xslt.XsltUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import jhttpcrowler.utils.file.FileUtilTest;
import jhttpcrowler.utils.strings.StringUtilTest;

/**
 * Test suite for running overall tests for utils
 *
 * @author Sergey Pilukin
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    //Utils tests
    FileUtilTest.class,
    PropertiesUtilTest.class,
    StringUtilTest.class,
    HtmlCleanerUtilTest.class,
    TidyCleanerUtilTest.class,
    XpathUtilTest.class,
    XsltUtilTest.class,
    XmlUtilTest.class,
    //Plugins
    EngineExecutorPluginTest.class,
    JavaScriptPluginTest.class,
    SqlJdbcImplTest.class,
    //Core tests
    GroovyEngineTest.class,
    GroovyShellEngineTest.class,
    JavaScriptEngineTest.class,
    PythonEngineTest.class,
    RubyEngineTest.class
    })
public class JhttpcrowlerTestSuite {
}
