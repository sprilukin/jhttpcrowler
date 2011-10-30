package jhttpcrowler.core.plugin;

import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.inject.annotation.TestedObject;

import static junit.framework.Assert.assertEquals;

/**
 * Tests for {@link JavaScriptImpl} plugin
 *
 * @author Sergey Prilukin
 */
public class JavaScriptPluginTest extends UnitilsJUnit4 {

    @TestedObject
    private JavaScriptImpl javaScript;

    @Test
    public void testPlugin() throws Exception {
        assertEquals("ggg1", javaScript.eval("var a = params[0] + 1; a", "ggg"));
        assertEquals("ggg11ggg", javaScript.eval("a + 1 + params[0]"));

        javaScript.envjs("for (var i = 0; i < 4; i++) {print(params[i])}", "one", "two", "tree", new Integer(4));

        String script = "window.google = {kEI:\"sJopTqHoD6bz0gGE68z0Cg\",kEXPI:\"17291,28290,28505,28663,28936,29774,30464,30727,31215,31406,31608,31623,31661,31718,31725,31726\",kCSI:{e:\"17291,28290,28505,28663,28936,29774,30464,30727,31215,31406,31608,31623,31661,31718,31725,31726\",ei:\"sJopTqHoD6bz0gGE68z0Cg\",expi:\"17291,28290,28505,28663,28936,29774,30464,30727,31215,31406,31608,31623,31661,31718,31725,31726\"},authuser:0,ml:function() {\n" +
                "},pageState:\"#\",kHL:\"ru\",time:function() {\n" +
                "    return(new Date).getTime()\n" +
                "},\n" +
                "    log:function(c, d, b) {\n" +
                "        var a = new Image,e = google,g = e.lc,f = e.li;\n" +
                "        a.onerror = (a.onload = (a.onabort = function() {\n" +
                "            delete g[f]\n" +
                "        }));\n" +
                "        g[f] = a;\n" +
                "        b = b || \"/gen_204?atyp=i&ct=\" + c + \"&cad=\" + d + \"&zx=\" + google.time();\n" +
                "        a.src = b;\n" +
                "        e.li = f + 1\n" +
                "    },lc:[],li:0,j:{en:1,l:function() {\n" +
                "        google.fl = true\n" +
                "    },e:function() {\n" +
                "        google.fl = true\n" +
                "    },b:location.hash && location.hash != \"#\",bv:19,pm:\"p\",\n" +
                "        pl:[],mc:0,sc:0.5,u:\"76ac99\"},Toolbelt:{}\n" +
                "};\n" +
                "\n" +
                "(function() {\n" +
                "    var c = google.j;\n" +
                "    window.onpopstate = function() {\n" +
                "        c.psc = 1\n" +
                "    };\n" +
                "    for (var d = 0,b; b = [\"ad\",\n" +
                "        \"bc\",\"is\",\"p\",\"pa\",\"ac\",\"pc\",\"pah\",\"ph\",\"sa\",\"slp\",\"spf\",\"xx\",\"zc\",\"zz\"][d++];)(function(a) {\n" +
                "        c[a] = function() {\n" +
                "            c.pl.push([a,arguments])\n" +
                "        }\n" +
                "    })(b)\n" +
                "})();\n" +
                "if (!window.chrome)window.chrome = {};\n" +
                "window.chrome.sv = 1.00;\n" +
                "window.google.sn = \"webhp\";\n" +
                "var i = window.google.timers = {};\n" +
                "window.google.startTick = function(a, b) {\n" +
                "    i[a] = {t:{start:(new Date).getTime()},bfr:!(!b)}\n" +
                "};\n" +
                "window.google.tick = function(a, b, c) {\n" +
                "    if (!i[a])google.startTick(a);\n" +
                "    i[a].t[b] = c || (new Date).getTime()\n" +
                "};\n" +
                "google.startTick(\"load\", true);\n" +
                "try {\n" +
                "    window.google.pt = window.chrome && window.chrome.csi && Math.floor(window.chrome.csi().pageT);\n" +
                "} catch(v) {\n" +
                "}\n" +
                "";

        javaScript.envjs(script);
    }
}
