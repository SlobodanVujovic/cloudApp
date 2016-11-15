var numberOfChoosenServicesWithReservation = 0;
function checkIfServiceRequireReservation(checkbox) {
    var checkboxValue = checkbox.value;
    checkboxValue = checkboxValue.replace(" ", "");
    var servicesWithReservation = document.getElementById("servicesCheckboxes:servicesWithReservation").value;
    var isServiceWithReservation = false;
    if (servicesWithReservation.includes(checkboxValue)) {
        isServiceWithReservation = true;
    }
    var reservationLabelElement = document.getElementById("reservation_label");
    var rerervationDateElement = document.getElementById("reservation_date");
    var reservationTimeElement = document.getElementById("reservation_time");
    if (isServiceWithReservation) {
        if (checkbox.checked) {
            ++numberOfChoosenServicesWithReservation;
            $(rerervationDateElement).slideDown(500, function () {
                rerervationDateElement.style.display = "block";
            });
            $(reservationTimeElement).slideDown(500, function () {
                reservationTimeElement.style.display = "block";
            });
            $(reservationLabelElement).slideDown(500, function () {
                reservationLabelElement.style.display = "block";
            });
        } else {
            --numberOfChoosenServicesWithReservation;
            if (numberOfChoosenServicesWithReservation === 0) {
                $(rerervationDateElement).slideUp(500, function () {
                    rerervationDateElement.style.display = "none";
                    rerervationDateElement.value = "";
                });
                $(reservationTimeElement).slideUp(500, function () {
                    reservationTimeElement.style.display = "none";
                    reservationTimeElement.value = "";
                });
                $(reservationLabelElement).slideUp(500, function () {
                    reservationTimeElement.style.display = "none";
                });
            }
        }
    }
}

$(function () {
    $('.reservationTime').timepicker();
});

function isAllInputOk() {
    var inputIsOk = false;
    var serviceIsSelected = isServiceSelected();
    var reservatioIsSelected = isReservationAppointmentChoosen();
    var clientNameIsEntered = isNameEntered();
    if (serviceIsSelected && reservatioIsSelected && clientNameIsEntered) {
        inputIsOk = true;
    }
    return inputIsOk;
}

function isServiceSelected() {
    // Iako smo element izabrali koristeci ID, to ne znaci da u njemu nema drugih elemenata, odnonsno mozemo ga
    // posmatrati kao niz.
    var servicesForm = document.getElementById("servicesCheckboxes");
    var serviceIsChoosen = false;
    for (i = 0; i < servicesForm.length; i++) {
        // Kada proveravamo tip tag-a elementa string mora biti CAPS LOCK dok su atributi malim slovima.
        if (servicesForm[i].tagName === "INPUT" && servicesForm[i].type === "checkbox" && servicesForm[i].checked) {
            serviceIsChoosen = true;
        }
    }
    return serviceIsChoosen;
}

function isReservationAppointmentChoosen() {
    var reservationIsEntered = true;
    var reservationDate = document.getElementById("reservation_date_input");
    var reservationTime = document.getElementById("reservation_time");
    if ($(reservationDate).is(":visible")) {
        reservationIsEntered = false;
        var selectedDate = reservationDate.value;
        var selectedTime = reservationTime.value;
        if (selectedDate !== "" && selectedTime !== "") {
            reservationIsEntered = true;
        }
    }
    return reservationIsEntered;
}

function isNameEntered() {
    var nameIsEntered = false;
    var nameInput = document.getElementById("clientName").value;
    if (nameInput !== "") {
        nameIsEntered = true;
    }
    return nameIsEntered;
}

//console.log(checkboxValue);