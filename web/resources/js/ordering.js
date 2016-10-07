

$(document).ready(function () {
    $("#email").on("input", function () {
        var email = $("#email").val();
        var number = /\d+/g;
        if (email.match(number) != null) {
            alert("Ima broj");
        }
    });
});