import jhttpcrowler.core.plugin.Http
import jhttpcrowler.core.plugin.Xpath
import jhttpcrowler.core.plugin.HtmlToXml

Http http = (Http)http;
Xpath xpath = (Xpath)xpath;
HtmlToXml htmlToXml = (HtmlToXml)htmlToXml;

def mainPage = htmlToXml.clean(http.fetchAsString("http://localhost:8000/index.html"));
List<org.w3c.dom.Node> links = xpath.nodeList(mainPage, "//a/@href");

return links
