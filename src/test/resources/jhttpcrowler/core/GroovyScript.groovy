package jhttpcrowler.core

import jhttpcrowler.core.plugin.Http
import jhttpcrowler.core.plugin.Xpath
import jhttpcrowler.core.plugin.HtmlToXml
import org.w3c.dom.Node

class GroovyScript implements Script {

    public Object execute(Map<String, Plugin> plugins, Map<String, Object> params) {
        Http http = (Http)plugins.get("http")
        Xpath xpath = (Xpath)plugins.get("xpath")
        HtmlToXml htmlToXml = (HtmlToXml)plugins.get("htmlToXml")

        def mainPage = htmlToXml.clean(http.fetchAsString("http://localhost:8000/index.html"));
        List<Node> links = xpath.nodeList(mainPage, "//a/@href");

        return links
    }
}
