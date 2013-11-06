<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat App</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap-theme.min.css">
</head>
<body>
<div class="container">
    <h1>Chat-app</h1>

    <div class="form-group">
        <input id="apiUrl" type="text" class="form-control col-xs-4" value="<c:url value="/mock-api" />">
    </div>
    <pre id="chat"></pre>
    <input id="message" class="form-control">
    <button id="sendMessage" class="btn btn-block">send</button>

</div>


<script src="//code.jquery.com/jquery-2.0.3.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
<script>
    function fetchMessages() {
        $.getJSON($("#apiUrl").val() + "/messages")
                .done(function (data) {
                    $("#chat").empty()
                    var text = "";
                    $.each(data.content, function (index, element) {
                        text += element.text + "\n";
                    })
                    $("#chat").text(text);
                })
                .error(function (error) {
                    $("#chat").text(error.status + " " + error.statusText)
                });
    }
    $("#sendMessage").click(function () {
        $.ajax({
            type: "POST",
            url: $("#apiUrl").val() + "/messages",
            contentType: "application/json",
            data: JSON.stringify({"text": $("#message").val()})
        }).done(function(){
                    fetchMessages()
                    $("#message").val("");
                });
    });
    fetchMessages()
    setInterval(fetchMessages, 2500);
</script>
</body>
</html>