// Unused code:
// Koristi se samo na ordering-service-type.xhtml strani.
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

// Koristi se samo na ordering-service-type.xhtml strani.
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

// Koristi se samo na ordering-service-type.xhtml strani.
function uncheckElement(checkboxId) {
    var checkbox = document.getElementById(checkboxId);
    $(checkbox).prop("checked", false);
}
// Koristi se samo na ordering-service-type.xhtml strani.
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

// Koristi se samo na ordering-service-type.xhtml strani.
function buttonOk(checkboxId) {
    hideOrShowPreviewElement(checkboxId);
}

// Koristi se samo na ordering-service-type.xhtml strani.
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

// Koristi se samo na ordering-service-type.xhtml strani.
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

// Koristi se samo na ordering-service-type.xhtml strani.
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

// Koristi se samo na ordering-service-type.xhtml strani.
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
// Sve dovde se koristi samo na ordering-service-type.xhtml stranici.

// ==============================================================================================================

// Used code
// Odavde krece kod koji se aktivno koristi u aplikaciji.
function hidePopupWin(winId) {
    var inputToShow = document.getElementById(winId);
    inputToShow.style.display = "none";
}

function showAgentsPopupWin() {
    var inputToShow = document.getElementById("agentPopupBack");
    inputToShow.style.display = "block";
}

function showActivityPopupWin() {
    var inputToShow = document.getElementById("activityPopupBack");
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

$(function () {
    $("#datepicker").datepicker();
});

$(function () {
    $('#timepicker').timepicker();
});

function defineServicesValidation() {
    var areServicesDefined = false;
    var serviceIds = [];
    var rowNumber = document.getElementById("serviceDefinitionTableId_data").getElementsByTagName("tr");
    for (var i = 0; i < rowNumber.length; i++) {
        var tempServiceId = "serviceDefinitionTableId:" + i + ":service_name";
        serviceIds.push(tempServiceId);
    }
    for (var i = 0; i < serviceIds.length; i++) {
        var element = document.getElementById(serviceIds[i]);
        if (element.value !== "") {
            areServicesDefined = true;
        }
    }
    if (!areServicesDefined) {
        alert("Please define at least one Service Name.");
    }
    return areServicesDefined;
}

function companyInfoValidation() {
    var isInputValid = true;
    var companyName = document.getElementById("company_name");
    if (companyName.value === "") {
        alert("Please enter Company Name.");
        isInputValid = false;
        return isInputValid;
    }
    var companyEmail = document.getElementById("email");
    if (companyEmail.value === "") {
        alert("Please enter E-Mail.");
        isInputValid = false;
        return isInputValid;
    }
    return isInputValid;
}

function agentInfoValidation() {
    var isInputValid = true;
    var firstName = document.getElementById("agentForm:agent_first_name");
    if (firstName.value === "") {
        alert("Please enter First Name.");
        isInputValid = false;
        return isInputValid;
    }
    var lastName = document.getElementById("agentForm:agent_last_name");
    if (lastName.value === "") {
        alert("Please enter Last Name.");
        isInputValid = false;
        return isInputValid;
    }
    var agentEmail = document.getElementById("agentForm:agent_email");
    if (agentEmail.value === "") {
        alert("Please enter E-Mail.");
        isInputValid = false;
        return isInputValid;
    }
    return isInputValid;
}

function administratorInfoValidation() {
    var isInputValid = true;
    var firstName = document.getElementById("first_name");
    if (firstName.value === "") {
        alert("Please enter First Name.");
        isInputValid = false;
        return isInputValid;
    }
    var lastName = document.getElementById("last_name");
    if (lastName.value === "") {
        alert("Please enter Last Name.");
        isInputValid = false;
        return isInputValid;
    }
    var username = document.getElementById("user_name");
    if (username.value === "") {
        alert("Please enter Username.");
        isInputValid = false;
        return isInputValid;
    }
    var password = document.getElementById("password");
    if (password.value === "") {
        alert("Please enter Password.");
        isInputValid = false;
        return isInputValid;
    }
    var confirmPassword = document.getElementById("confirm_password");
    if (confirmPassword.value === "") {
        alert("Please enter Confirm Password.");
        isInputValid = false;
        return isInputValid;
    }
    var email = document.getElementById("email");
    if (email.value === "") {
        alert("Please enter E-Mail.");
        isInputValid = false;
        return isInputValid;
    }
}

// Metod koji se poziva kada se cekira "Reservation required" checkbox. Ovaj metod setuje ukupna broj servisa 
// koji zahtevaju rezervaciju.
function serviceWithReservation(checkboxId) {
    var hiddenInput3 = document.getElementById("hiddenInput3");
//    Iz polja koje cuva broj cekiranih servisa sa rezervacijama uzimamo vrednost. Ovo radimo da bi u slucaju kada,
//    nakon submit-a stranice, JSF validator vrati gresku, da ne bi izgubili broj cekiranih servisa sa rezervacijama.
    var numberOfServicesWithReservation = hiddenInput3.value;
    var serviceCheckbox = document.getElementById(checkboxId);
//Zavisno da li je rezervacija za servis cekirana ili ne, menjamo broj servisa sa rezervacijom.
    if (serviceCheckbox.checked) {
        ++numberOfServicesWithReservation;
//        Nakon promene broja, vrednost cuvamo u hiddenInput3 polju.
        hiddenInput3.value = numberOfServicesWithReservation;
        // Kada menjamo vrednost komponente kroz JS to ne izaziva HTML DOM event "onchange" tako da se metod koji
        // je mozda dodeljen ovom atributu u tag-u komponent se nece pozvati. Da bi izazvali "onchange" event
        // potrebno je pozvati onchange() metod nad komponentom ciju vrednost smo podesili.
        hiddenInput3.onchange();
    } else {
        --numberOfServicesWithReservation;
        hiddenInput3.value = numberOfServicesWithReservation;
        hiddenInput3.onchange();
    }
    // console.log(numberOfServicesWithReservation);
}

// Metod koji proverava da li postoji servis koji zahteva rezervaciju i na osnovu toga prikazuje ili sakriva
// notification div.
function showNotificationCheckbox() {
    var hiddenInput3 = document.getElementById("hiddenInput3");
    // Proverimo da li hiddenInput3 postoji.
    if (hiddenInput3) {
        var numberOfServicesWithReservation = hiddenInput3.value;
        var notificationElement = document.getElementById("notificationGrid");
        var notificationCheckbox = document.getElementById("notificationCheckbox");
//    if ce proci ako je broj servisa veci od 0.
        if (numberOfServicesWithReservation > 0) {
//        Proverimo da li je notifikacija vec vidljiva.
            var notificationIsVisible = $(notificationElement).is(":visible");
//        Ako nije prikazemo je i 
            if (!notificationIsVisible) {
                $(notificationElement).slideDown(500, function () {
                    notificationElement.style.display = "block";
                });
            }
//        A ako je broj servisa sa rezervacijom = 0
        } else {
//        proverimo da li je notifikacija vec prikazana
            var notificationIsVisible = $(notificationElement).is(":visible");
//       i ako jeste sklonimo je.
            if (notificationIsVisible) {
                $(notificationElement).slideUp(500, function () {
                    notificationElement.style.display = "none";
                });
//              Kada sklanjamo notifikaciju 1. proverimo da li je notification checkbox cekiran i ako jeste pozivamo
//              click() metod da bi simulirali klik na checkbox-u, odnosno da bi ga rascekirali. Time se notification
//              input disable-uje i vrednost se nece slati bean-u.
                if (notificationCheckbox.checked) {
                    notificationCheckbox.click();
                }
            }
        }
    }
}

// Ovako pozivamo funkciju kada se stranica ucita. Mana je sto se ovo poziva pri svakoj ucitanoj stranici.
// Zato u metodu koji pozivamo 1. proverimo da li smo na odgovarajucoj stranici pa onda izvravamo logiku.
// Shorthand za 
// $(document).ready(function () { 
// je
// $(function() {
$(document).ready(function () {
    checkIfThisIsOrderingDefineServicesPage();
});

// Zelimo da izvrsavamo metod kada smo na ordering-define-services.xtml (odnosno ordering-admin-info.xhtml).
function checkIfThisIsOrderingDefineServicesPage() {
    var pathname = window.location.pathname;
    // Medjutim posto koristimo POST mehanizam za navigaciju u flow-u, u URL-u se prikazuje adresa prethodne stranice
    // ("one URL behind" problem). Zato i testiramo na "ordering-admin-info.xhtml" stranicu. Ali ako se pri submit-u
    // javi validaciona greska, stranica se rerenderuje i URL ce sadrzati "ordering-define-services.xhtml" pa testiramo
    // i ovu vrednost.
    if (pathname.indexOf("/ordering-admin-info.xhtml") !== -1 || pathname.indexOf("/ordering-define-services.xhtml") !== -1) {
        showNotificationCheckbox();
    }
}
