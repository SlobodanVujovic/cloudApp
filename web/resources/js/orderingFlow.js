function hideOrShow(checkbox) {
    var checkboxId = checkbox.id;
    var isChecked = checkbox.checked;
    var listId;
    var panelId;
    switch (checkboxId) {
        case "reservations":
            listId = "reservationsInput";
            panelId = "reservationsPanel";
            break;
        case "ticketing":
            listId = "ticketingInput";
            panelId = "ticketingPanel";
            break;
        default:
            listId = "thisIsErrorValue";
    }
    // Get the for using its ID.
    var listToShow = document.getElementById(listId);
    var panelToShow = document.getElementById(panelId);
    if (isChecked) {
        $(listToShow).slideDown(500, function () {
            listToShow.style.display = "block";
        });
        $(panelToShow).slideDown(500, function () {
            panelToShow.style.display = "block";
        });
    } else {
        $(listToShow).slideUp(500, function () {
            listToShow.style.display = "none";
        });
        $(panelToShow).slideUp(500, function () {
            panelToShow.style.display = "none";
        });
    }
}

function showPreview(checkbox) {
    var checkboxId = checkbox.id;
    var isChecked = checkbox.checked;
    var inputToShowId;
    switch (checkboxId) {
        case "r1":
            inputToShowId = "custName";
            break;
        case "r2":
            inputToShowId = "custPhone";
            break;
        case "r3":
            inputToShowId = "calendar";
            break;
        case "r4":
            inputToShowId = "time";
            break;
    }
    var inputToShow = document.getElementById(inputToShowId);
    if (isChecked) {
        $(inputToShow).slideDown(500, function () {
            inputToShow.style.display = "block";
        });

    } else {
        $(inputToShow).slideUp(500, function () {
            inputToShow.style.display = "none";
        });

    }
}

function showPopupWin(checkbox) {
    var checkboxId = checkbox.id;
    var isChecked = checkbox.checked;
    var divToShowId;
    switch (checkboxId) {
        case "r3":
            divToShowId = "popupBack";
            break;
        case "r4":
            divToShowId = "timePopup";
            break;
    }
    var inputToShow = document.getElementById(divToShowId);
    if (isChecked) {
        inputToShow.style.display = "block";
        showPreview(checkbox);
    } else {
        inputToShow.style.display = "none";
        showPreview(checkbox);
    }
}

function hidePopupWin() {
    var inputToShow = document.getElementById("popupBack");
    inputToShow.style.display = "none";
}

$(function () {
    $("#datepicker").datepicker();
});

$(function () {
    $('#timeInput').timepicker();
});

/*
 $(document).ready(function () {
 $("#email").on("input", function () {
 var email = $("#email").val();
 var number = /\d+/g;
 if (email.match(number) != null) {
 alert("Ima broj");
 }
 });
 });
 */