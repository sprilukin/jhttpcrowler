#print "hello world"
#resultWrapper.result = "This is a result"

html = http.fetchAsString("http://localhost:8000/index.html")
resultWrapper.result = xpath.nodeList(htmlToXml.clean(html), "//a/@href")

