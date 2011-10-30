html = $http.fetchAsString("http://localhost:8000/index.html")
result = $xpath.nodeList($htmlToXml.clean(html), "//a/@href")

return result

