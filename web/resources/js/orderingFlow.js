function hideOrShowPreviewElement(checkboxId) {
    var isChecked = document.getElementById(checkboxId).checked;
    var elementToShowId1;
    var elementToShowId2;
    switch (checkboxId) {
        case "reservations":
            elementToShowId1 = "reservationsInput";
            elementToShowId2 = "reservationsPanel";
            break;
        case "ticketing":
            elementToShowId1 = "ticketingInput";
            elementToShowId2 = "ticketingPanel";
            break;
        case "reminder":
            elementToShowId1 = "reminderInput";
            elementToShowId2 = "reminderPanel";
            break;
        case "r1":
            elementToShowId1 = "your_name_label";
            elementToShowId2 = "your_name";
            break;
        case "r2":
            elementToShowId1 = "your_phone_label";
            elementToShowId2 = "your_phone";
            break;
        case "r3":
            elementToShowId1 = "datepicker_label";
            elementToShowId2 = "datepicker";
            break;
        case "r4":
            elementToShowId1 = "timepicker_label";
            elementToShowId2 = "timepicker";
            break;
        case "r5":
            elementToShowId1 = "agentpicker_label";
            elementToShowId2 = "agentpicker";
            break;
        case "t1":
            elementToShowId1 = "client_name_label";
            elementToShowId2 = "client_name";
            break;
        case "t2":
            elementToShowId1 = "client_phone_label";
            elementToShowId2 = "client_phone";
            break;
        case "t3":
            elementToShowId1 = "client_email_label";
            elementToShowId2 = "client_email";
            break;
        case "t5":
            elementToShowId1 = "emergency_level_label";
            elementToShowId2 = "emergency_level";
            break;
        case "t6":
            elementToShowId1 = "problem_description_label";
            elementToShowId2 = "problem_description";
            break;
        default:
            elementToShowId1 = "thisIsErrorValue";
    }
    var elementToShow1 = document.getElementById(elementToShowId1);
    var elementToShow2 = document.getElementById(elementToShowId2);
    if (isChecked) {
        $(elementToShow1).slideDown(500, function () {
            elementToShow1.style.display = "block";
        });
        $(elementToShow2).slideDown(500, function () {
            elementToShow2.style.display = "block";
        });
    } else {
        $(elementToShow1).slideUp(500, function () {
            elementToShow1.style.display = "none";
        });
        $(elementToShow2).slideUp(500, function () {
            elementToShow2.style.display = "none";
        });
    }
}

function showReminder(checkboxId) {
    var isChecked = document.getElementById(checkboxId).checked;
    var inputToShowId;
    switch (checkboxId) {
        case "rm1":
            inputToShowId = "reminderSms";
            break;
        case "rm2":
            inputToShowId = "reminderEmail";
            break;
    }
    var inputToShow = document.getElementById(inputToShowId);
    if (isChecked) {
        $(inputToShow).slideDown(500, function () {
            (inputToShow).style.display = "block";
        });
    } else {
        $(inputToShow).slideUp(500, function () {
            (inputToShow).style.display = "none";
        });
    }
}

function showAddressWithMapPreview() {
    var isChecked = document.getElementById("t4").checked;
    var inputToShowId1 = "client_address_label",
            inputToShowId2 = "client_address",
            inputToShowId3 = "client_locateMe_button",
            inputToShowId4 = "geoGmap";
    var inputToShow1 = document.getElementById(inputToShowId1),
            inputToShow2 = document.getElementById(inputToShowId2),
            inputToShow3 = document.getElementById(inputToShowId3),
            inputToShow4 = document.getElementById(inputToShowId4);
    if (isChecked) {
        $(inputToShow1).slideDown(500, function () {
            inputToShow1.style.display = "block";
        });
        $(inputToShow2).slideDown(500, function () {
            inputToShow2.style.display = "block";
        });
        $(inputToShow3).slideDown(500, function () {
            inputToShow3.style.display = "block";
        });
        $(inputToShow4).slideDown(500, function () {
            inputToShow4.style.display = "block";
        });

    } else {
        $(inputToShow1).slideUp(500, function () {
            inputToShow1.style.display = "none";
        });
        $(inputToShow2).slideUp(500, function () {
            inputToShow2.style.display = "none";
        });
        $(inputToShow3).slideDown(500, function () {
            inputToShow3.style.display = "none";
        });
        $(inputToShow4).slideDown(500, function () {
            inputToShow4.style.display = "none";
        });
    }
}

function showPopupWin(checkboxId) {
    var isChecked = document.getElementById(checkboxId).checked;
    var divToShowId;
    switch (checkboxId) {
        case "r3":
            divToShowId = "calendarPopupBack";
            break;
        case "r4":
            divToShowId = "timePopupBack";
            break;
        case "r5":
            divToShowId = "agentPopupBack";
            break;
    }
    var inputToShow = document.getElementById(divToShowId);
    if (isChecked) {
        inputToShow.style.display = "block";
    } else {
        hideOrShowPreviewElement(checkboxId);
    }
}


function showAgentsPopupWin() {
    var inputToShow = document.getElementById("agentPopupBack");
        inputToShow.style.display = "block";
}

function hideOrderingInputFields() {
        document.getElementById("company_name").style.display = "none";
        document.getElementById("phone").style.display = "none";
        document.getElementById("email").style.display = "none";
        document.getElementById("address").style.display = "none";
        document.getElementById("city").style.display = "none";
        document.getElementById("zip").style.display = "none";
        document.getElementById("state").style.display = "none";
}

function showOrderingInputFields() {
    document.getElementById("company_name").style.display = "block";
    document.getElementById("phone").style.display = "block";
    document.getElementById("email").style.display = "block";
    document.getElementById("address").style.display = "block";
    document.getElementById("city").style.display = "block";
    document.getElementById("zip").style.display = "block";
    document.getElementById("state").style.display = "block";
}

function showReminderPopupWin(checkboxId) {
    var checkbox = document.getElementById(checkboxId);
    var isChecked = checkbox.checked;
    var divToShowId = "reminderPopupBack";
    var inputToShow = document.getElementById(divToShowId);
    if (isChecked) {
        inputToShow.style.display = "block";
    } else {
        showReminder(checkboxId);
    }
}

function hidePopupWin(winId) {
    var inputToShow = document.getElementById(winId);
    inputToShow.style.display = "none";
}

function uncheckElement(checkboxId) {
    var checkbox = document.getElementById(checkboxId);
    $(checkbox).prop("checked", false);
}

function uncheckReminder() {
    var smsReminderElement = document.getElementById("rm1");
    var emailReminderElement = document.getElementById("rm2");
    var checkedId;
    if (smsReminderElement.checked) {
        checkedId = "#rm1";
    } else if (emailReminderElement.checked) {
        checkedId = "#rm2";
    }
    $(checkedId).prop("checked", false);
}

function buttonOk(checkboxId) {
    hideOrShowPreviewElement(checkboxId);
}

function reminderButtonOk() {
    var smsReminderElement = document.getElementById("rm1");
    var emailReminderElement = document.getElementById("rm2");
    var checkedId;
    if (smsReminderElement.checked) {
        checkedId = "rm1";
        showReminder(checkedId);
    }
    if (emailReminderElement.checked) {
        checkedId = "rm2";
        showReminder(checkedId);
    }
}

$(function () {
    $("#datepicker").datepicker();
});

$(function () {
    $('#timepicker').timepicker();
});

function showMoreServices() {
    var serviceIds = ["service_2", "service_3"];
    var serviceId;
    var divToShow;
    for (var i = 0; i < serviceIds.length; i++) {
        serviceId = serviceIds[i];
        var serviceDiv = document.getElementById(serviceId);
        //jQuery za proveravanje da li je element vidljiv (vidljiv je ako ima sirinu ili visinu >0).
        var divIsVisible = $(serviceDiv).is(":visible");
        if (!divIsVisible) {
            break;
        }
    }
    divToShow = document.getElementById(serviceId);
    $(divToShow).slideDown(500, function () {
        (divToShow).style.display = "block";
    });
    return false;
}

function showLessServices() {
    var serviceIds = ["service_3", "service_2"];
    var serviceId;
    var divToShow;
    for (var i = 0; i < serviceIds.length; i++) {
        serviceId = serviceIds[i];
        var serviceDiv = document.getElementById(serviceId);
        //jQuery za proveravanje da li je element vidljiv (vidljiv je ako ima sirinu ili visinu >0).
        var divIsVisible = $(serviceDiv).is(":visible");
        if (divIsVisible) {
            break;
        }
    }
    divToShow = document.getElementById(serviceId);
    $(divToShow).slideUp(500, function () {
        (divToShow).style.display = "none";
    });
    return false;
}

function defineServicesValidation(){
    var areServicesDefined = false;
    var serviceIds = ["service_name_1", "service_name_2", "service_name_3"];
    for (var i = 0; i < serviceIds.length; i++) {
        var element = document.getElementById(serviceIds[i]);
        if (element.value !== "") {
            areServicesDefined = true;
        }
    }
    if(!areServicesDefined){
        alert("Please define at least one Service Name.");
    } else{
        definedServiceNames();
        definedReservations();
    }
    return areServicesDefined;
}

function definedServiceNames() {
    var serviceNames = [];
    var serviceIds = ["service_name_1", "service_name_2", "service_name_3"];
    for (var i = 0; i < serviceIds.length; i++) {
        var element = document.getElementById(serviceIds[i]);
        if (element.value) {
            serviceNames.push(element.value);
        }
    }
    document.getElementById("hiddenInput1").value = serviceNames;
}

function definedReservations() {
    var servicesWithReservation = [];
    var reservationIds = ["service_checkbox_1", "service_checkbox_2", "service_checkbox_3"];
    for (var i = 0; i < reservationIds.length; i++) {
        var element = document.getElementById(reservationIds[i]);
        if (element.checked) {
            var serviceId = reservationIds[i].substr(reservationIds[i].length - 1, 1);
            servicesWithReservation.push(serviceId);
        }
    }
    document.getElementById("hiddenInput2").value = servicesWithReservation;
}

function companyInfoValidation(){
    var isInputValid = true;
    var companyName = document.getElementById("company_name");
    if(companyName.value === ""){
        alert("Please enter Company Name.");
        isInputValid = false;
        return isInputValid;
    }
    var companyEmail = document.getElementById("email");
    if(companyEmail.value === ""){
        alert("Please enter E-Mail.");
        isInputValid = false;
        return isInputValid;
    }
    return isInputValid;
}

function administratorInfoValidation(){
    var isInputValid = true;
    var firstName = document.getElementById("first_name");
    if(firstName.value === ""){
        alert("Please enter First Name.");
        isInputValid = false;
        return isInputValid;
    }
    var lastName = document.getElementById("last_name");
    if(lastName.value === ""){
        alert("Please enter Last Name.");
        isInputValid = false;
        return isInputValid;
    }
    var username = document.getElementById("user_name");
    if(username.value === ""){
        alert("Please enter Username.");
        isInputValid = false;
        return isInputValid;
    }
    var password = document.getElementById("password");
    if(password.value === ""){
        alert("Please enter Password.");
        isInputValid = false;
        return isInputValid;
    }
    var confirmPassword = document.getElementById("confirm_password");
    if(confirmPassword.value === ""){
        alert("Please enter Confirm Password.");
        isInputValid = false;
        return isInputValid;
    }
    var email = document.getElementById("email");
    if(email.value === ""){
        alert("Please enter E-Mail.");
        isInputValid = false;
        return isInputValid;
    }
    
    
}
