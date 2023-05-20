var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    var headers = {
            name : "chulsoo"
        };
    stompClient.connect(headers, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
        stompClient.send("/app/enter", {}, JSON.stringify({'content': $("#content").val()}));
    });
}

function disconnect() {
    if (stompClient !== null) {
//        exit();
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendContent() {
    stompClient.send("/app/chat", {}, JSON.stringify({'content': $("#content").val()}));
}
function enter(){
    stompClient.send("/app/enter", {}, JSON.stringify({}));
}
//function exit(){
//    stompClient.send("/app/exit", {}, JSON.stringify({}));
//}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendContent(); });
});