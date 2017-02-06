var numberOfChoosenServicesWithReservation = 0;
function checkIfServiceRequireReservation(checkbox) {
    // U ovaj element cemo upisati vrednost zavisno od toga da li su elementi date i time vidljivi ili su sakriveni.
    var dateValidationElement = document.getElementById("dateValidationRequired");
    // Vrednost koju dobijamo je tipa string.
    var checkboxValue = checkbox.value;
    // Da bi mogli da pretrazujemo array po checkboxValue vrednosti moramo je 1. konvertovati u tip number.
    var checkboxNumber = parseInt(checkboxValue);
    // Uzimamo vrednost iz h:inputHidden tag-a, medjutim ta vrednost je predstavljena kao string. String predstavlja niz
    // i potrebno je vrednost prebaciti u niz brojeva.
    var servicesWithReservation = document.getElementById("servicesCheckboxes:servicesWithReservation").value;
    // Vrednost ima oblik: "[x, y, z]". Prvo skinemo otvorenu uglastu zagradu sa pocetka string-a.
    servicesWithReservation = servicesWithReservation.substring(1);
    // Zatim skinemo zatvorenu uglastu zagradu sa kraja string-a. The slice() method extracts parts 
    // of a string and returns the extracted parts in a new string. Kada je 2. argument negativan
    // onda se broje karakteri od kraja string-a.
    servicesWithReservation = servicesWithReservation.slice(0, -1);
    // The split() method is used to split a string into an array of substrings, and returns the new array.
    // Funkcija map() primenjuje funkciju, navedenu kao argument, na svaki clan niza string-ova.
    servicesWithReservation = servicesWithReservation.split(", ").map(function (item) {
        // 2. argument moze imati vrednost izmedju 2 i 36 i naziva se radix (the base in mathematical numeral systems).
        // Specify 10 for the decimal numeral system. 
        return parseInt(item, 10);
    });
    var isServiceWithReservation = false;
    // Sada konacno mozemo da proverimo da li se id izabranog servisa nalazi u nizu brojeva koji predstavljaju id-jeve
    // servisa koji zahtevaju rezervaciju.
    if (servicesWithReservation.indexOf(checkboxNumber) !== -1) {
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
                // Posto su date i time komponente vidljive upisujemo "true".
                dateValidationElement.value = "true";
                // Na ovaj nacin trigerujemo HTML DOM change event.
                $("#dateValidationRequired").trigger("change");
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
                    // Posto su date i time komponente sakrivene upisujemo "false".
                    dateValidationElement.value = "false";
                    // Na ovaj nacin trigerujemo HTML DOM change event.
                    $("#dateValidationRequired").trigger("change");
                });
            }
        }
    }
}

// Biblioteka se nalazi u: C:\Libraries\jquery-timepicker-99cfc2d. Postoje i primeri na index.html stranici.
$(document).ready(function () {
    $('#reservation_time').timepicker({
        'timeFormat': 'H:i',
        'minTime': '8:00',
        'maxTime': '19:30'
    });
});

function isAllInputOk() {
    var inputIsOk = false;
    var serviceIsSelected = isServiceSelected();
    var reservatioIsSelected = isReservationAppointmentChoosen();
    var clientNameIsEntered = isNameEntered();
    var clientPhoneIsEntered = isPhoneEntered();
    if (serviceIsSelected) {
        if (reservatioIsSelected) {
            if (clientNameIsEntered) {
                if (clientPhoneIsEntered) {
                    inputIsOk = true;
                } else {
                    alert("Please enter your phone number.");
                }
            } else {
                alert("Please enter your name.");
            }
        } else {
            alert("Please enter reservation date and time.");
        }
    } else {
        alert("Please choose at least one service.");
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

function isPhoneEntered() {
    var phoneIsEntered = false;
    var phoneInput = document.getElementById("clientPhone").value;
    if (phoneInput !== "") {
        phoneIsEntered = true;
    }
    return phoneIsEntered;
}

// Ova funkcija se poziva svaki put kada se klikne na servis. Ona uzima value servisa (ID servisa) i stavlja ga
// u hidden input polje ako je servis cekiran odnosno sklanja ga ako je rascekiran.
function transferValueToTextInput(checkbox) {
    // Nadjemo input polje.
    var selectedServicesElement = document.getElementById("selectedServices");
    // Uzmemo vrednost servisa i spojimo ga sa space-om, da bi imali razdvojene id-eve u input-u.
    var clickedServiceValue = checkbox.value + " ";
    // Uzmemo vrednost koja se nalazi u input-u (OBRATI PAZNJU, promene koje se vrse nad "chosenServicesInputValue"
    // nece uticati na vrednost koja se cuva u "selectedServicesElement"-u, zato sto smo mi uzeli vrednost input polja
    // i stavili je u novi objekat i sada menjamo taj novi objekat, originalni objekat iz koga smo uzeli vrednost se nece
    // promeniti, zato je neophodna poslednja linija u funkciji)
    var chosenServicesInputValue = selectedServicesElement.value;
    // i ako je checkbox cekiran
    if (checkbox.checked) {
        // dodamo vrednost servisa (sa sve space-om) u input polje.
        chosenServicesInputValue += clickedServiceValue;
        // Ako je pak servis rascekiran,
    } else {
        // 1. proverimo da li u input-u postoji vec ta vrednost serivsa (ovo je redundantno jer ako se servis rascekira onda mora da
        // je vec bio cekiran, odnosno njegova vrednost je vec bila u input polju).
        var serviceAlreadyChosen = chosenServicesInputValue.includes(clickedServiceValue);
        // Ako vrednost postoji u input polju
        if (serviceAlreadyChosen) {
            // onda se ona replace-uje sa "".
            chosenServicesInputValue = chosenServicesInputValue.replace(clickedServiceValue, "");
        }
    }
    // Na kraju se vrednost input elementa set-uje na vrednost "chosenServicesInputValue".
    selectedServicesElement.value = chosenServicesInputValue;
}

function checkIfCompanyHaveAgents() {
    var agentsListDiv = document.getElementById("agentsList");
    var agentsNodeList = agentsListDiv.childNodes;
    var numberOfAgents = agentsNodeList.length;
    if (numberOfAgents > 0) {
        var agentsFormDiv = document.getElementById("agentsForm");
        agentsFormDiv.style.display = "block";
    }
}

function checkIfThisIsServicesPage() {
    var pathname = window.location.pathname;
    if (pathname.indexOf("/services.xhtml") !== -1) {
        checkIfCompanyHaveAgents();
    }
}

window.onload = checkIfThisIsServicesPage;

var today = new Date();