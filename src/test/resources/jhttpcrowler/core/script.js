function execute() {
    print("\n===== EXAMPLE 1. Using plugins ========");
    var html = http.fetchAsString("http://localhost:8000/index.html");

    Envjs.scriptTypes = {
        ''                  :true,
        "text/javascript"   :true,
        "text/envjs"        :true
    };

    print("==== Example 2 loading from string");
    document.getElementsByTagName("html")[0].innerHTML = html;
    print("Links count: " + $("a").length);


    print("==== Example 3 using DOM and jQuery");
    window.location = "http://localhost:8000/index.html";
    print($('#links').html());
    print("Links count: " + $("a").length);

    print("==== Example 4 using DOM and jQuery: fire click event");
    function fireEvent(obj, evt) {
        var fireOnThis = obj;
        if (document.createEvent) {
            var evObj = document.createEvent('MouseEvents');
            evObj.initEvent(evt, true, false);
            fireOnThis.dispatchEvent(evObj);
        } else if (document.createEventObject) {
            fireOnThis.fireEvent('on' + evt);
        }
    }

    $("a")[0].href = "http://localhost:8000/index.html";

    fireEvent($("a")[0],'click');

    print("Links count: " + $("a").length);
    print($('#links').html());

    return xpath.nodeList(htmlToXml.clean(html), "//a/@href");
}

execute();
