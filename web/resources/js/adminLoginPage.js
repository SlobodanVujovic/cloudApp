function showCompanyInformation() {
    var companyInfoDiv = document.getElementById("companyInformations");
    $(companyInfoDiv).slideToggle(500);
    return false;
}

function hideCompanyInformation() {
    var companyInfoDiv = document.getElementById("companyInformations");
    $(companyInfoDiv).slideUp(500);
    return false;
}

function showServicePopupWin() {
    var inputToShow = document.getElementById("servicesPopupBack");
    inputToShow.style.display = "block";
}

function showEmailPopupWin() {
    var inputToShow = document.getElementById("emailPopupBack");
    inputToShow.style.display = "block";
}

//Pri pokusaju brisanja posledjeg activity-ja kompanije javlja se obavestenje da to nije dozvoljeno.
function companyMustHaveAtLeastOneActivity() {
    var moreThenOneRow = false;
    var activityTable = document.getElementById("companyActivityTable");
    var numberOfRows = activityTable.rows.length;
//    Uzimamo uslov "> 2" jer se broji red u kome je ispisan activity ali i header, zato je minimalni
//    broj redova u tabeli 2.
    if (numberOfRows > 2) {
        moreThenOneRow = true;
    } else {
        alert("Company must have at least one activity.");
    }
    return moreThenOneRow;
}

// Za globalFilter polje i p:calendar tag (u okviru "Reservation Date" kolone) u clientOrdersTableId tabeli (na adminLoginPage strani),
// kada se pritisne Enter key, u slucaju kada u polju ne postoji uneta vrednost, dolazi do pozivanja metoda
// deleteClientOrderFromTable() cime se brise prvi red u tabeli (razlog nepoznat i neshvacen). Da bi se ovo sprecilo za ova 2 elementa je disable-ovano
// default ponasanje Enter key-a time sto se ova funkcija poziva kroz "onkeypress" atribut. Enter key ima code = 13.
function preventEnterBehaviour(event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
}

// Biblioteka se nalazi u: C:\Libraries\jquery-timepicker-99cfc2d. Postoje i primeri na index.html stranici.
// Obrati paznju kako se vrsi selekcija elementa: trazimo elemente tipa "input" koji imaju atribut "id" koji 
// u sebi sadrzi string "changeReservationTime".
function showReservationTimeList() {
    $("input[id*='changeReservationTime']").timepicker({
        'timeFormat': 'H:i',
        'minTime': '8:00',
        'maxTime': '19:30'
    });
}

// Kada kliknemo na email, sa tim email-om se popunjava polje sa id-em "to_input".
function populateEmailToInput(toEmailId) {
    var toEmailString = document.getElementById(toEmailId).innerHTML.trim();
    document.getElementById("emailForm:to_input").value = toEmailString;
}

function showOrderIdPopupWin() {
    var inputToShow = document.getElementById("orderPopupBack");
    inputToShow.style.display = "block";
}

// Ovaj metod se poziva u services.xhtml-u u p:calendar-u i odredjuje koji dan u nedelji ce
// biti disable-ovan.
function disableNonWorkingDays(date) {
    var day = date.getDay();
    // Funkcija val() vraca vrednost input polja kao string. Potrebno je ovu vrednost prebaciti u niz int-ova.
    // Detaljan opis koraka se nalazi u services.js u funkciji checkIfServiceRequireReservation().
    var workingDays = $("#workingDaysInput").val();
    workingDays = workingDays.substring(1);
    workingDays = workingDays.slice(0, -1);
    workingDays = workingDays.split(", ").map(function (item) {
        return parseInt(item);
    });
    // Posto smo dobili radne dane, sada treba napraviti niz neradnih dana, jer zapravo oni treba da budu
    // disable-ovani.
    var nonWorkingDays = [];
    // U PF p:calendar-u oznacavanje dana ID-jevima pocinje od nedelje koja ima ID 0 pa do subote sa ID-em 6. Posto ID-jevi dana u nasoj bazi
    // pocinu od 1 (ponedeljak) do 7 (nedelja), proveru koji dani su neradni radimo iz 2 koraka. Prvo proverimo od ponedeljka do subote
    // (1 - 6) posto se te vrednosti slazu i u bazi i u PF-u.
    for (var x = 1; x < 7; x++) {
        // Proverimo da li niz radnih dana sadrzi odredjenu vrednost. Ako je rezultat "-1" to znaci da ne sadrzi
        // i onda dodajemo tu vrednost u niz neradnih dana.
        if (workingDays.indexOf(x) === -1) {
            nonWorkingDays.push(x);
        }
    }
    // 2. korak je da proverimo da li u radnim danima imamo 7-cu (DB nedelja) i ako nemamo treba da dodamo 0 (PF nedelja) u niz neradnih dana.
    if (workingDays.indexOf(7) === -1) {
        nonWorkingDays.push(0);
    }
    // Sada odredjujemo da li ce dan u sedmici biti enable-ovan ili disable-ovan.
    // Ako je rezultat true onda se dan enable-uje. Pocinjemo od pretpostavke da
    // su svi dani enable-ovani.
    var checkDay = true;
    // Iterisemo kroz array brojeva, koji govore koje dane treba disable-ovati, i poredimo ih sa trenutnim danom
    // (vrednost promenljive "day").
    for (var i = 0; i < nonWorkingDays.length; i++) {
        checkDay = checkDay && day !== nonWorkingDays[i];
    }
    // Vracamo rezultat iteracije, dok 2. parametar optional style class to be added to date cell. 
    return [checkDay, ''];
}

// Kada se stranica ucita poziva se naredna funkcija. Posto se to desava na svakoj stranici, 1. proverimo da li smo na onoj na kojoj
// zelimo da se funkcija izvrsi.
$(function () {
    checkIfThisIsAdminLoginPage();
});

function checkIfThisIsAdminLoginPage() {
    var pathname = window.location.pathname;
    // Proverimo da li smo na adminLoginPage.xhtml stranici.
    if (pathname.indexOf("/adminLoginPage.xhtml") !== -1) {
        // Ako jesmo, 1. odredimo koliko kompanija ima order-a.
        var numberOfOrders = $("table[id$='daysInWeek']").length;
        // Kreiramo niz id-eva koji predstavljaju id-eve timepicker-a.
        var timepickerIds = ["workDayStartTime", "workDayStopTime", "weekendDayStartTime", "weekendDayStopTime"];
        // Kreiramo niz id-eva koji predstavljaju id-eve hidden input polja u koje upisujemo vrednosti iz baze. Ove vrednosti treba da setujemo kao
        // pocetne vrednosti u timepicker-ima.
        var timeInputIds = ["workDayStartInput", "workDayStopInput", "weekendDayStartInput", "weekendDayStopInput"];
        // Prolazimo kroz svaki order
        for (var i = 0; i < numberOfOrders; i++) {
            // i za svaki timepicker
            for (var j = 0; j < timepickerIds.length; j++) {
                // kreiramo njegov id koji je skup vise id-eva (zbog svojih parent tag-ova).
                // Kada se, u jQuery-ju, koristi id koji u sebi ima dvotacku (":") onda se ispred dvotacke mora staviti "\\". U suprotnom ce se
                // dvotacka tumaciti kao definicija pseudo stanja.
                var tempTimepickerId = "workingDaysTable\\:" + i + "\\:" + timepickerIds[j];
                // Kreiramo id i za input hidden polja u kome su vrednosti iz baze.
                var tempInputId = "workingDaysTable:" + i + ":" + timeInputIds[j];
                // Ova promenljiva definise parametre timepicker-a. Obrati paznju kako promenljivoj "now" dodeljujemo vrednost koja se
                // nalazi u hidden input polju u kome je vrednost iz baze.
                var options = {now: document.getElementById(tempInputId).value, //hh:mm 24 hour format only, defaults to current time,
                    twentyFour: true, //Display 24 hour format, defaults to false,
                    upArrow: 'wickedpicker__controls__control-up', //The up arrow class selector to use, for custom CSS,
                    ownArrow: 'wickedpicker__controls__control-down', //The down arrow class selector to use, for custom CSS,
                    close: 'wickedpicker__close', //The close class selector to use, for custom CSS,
                    hoverState: 'hover-state', //The hover state class to use, for custom CSS,
                    title: 'Pick time', //The Wickedpicker's title, 
                    showSeconds: false, //Whether or not to show seconds, 
                    secondsInterval: 1, //Change interval for seconds, defaults to 1, 
                    minutesInterval: 1, //Change interval for minutes, defaults to 1,
                    beforeShow: null, //A function to be called before the Wickedpicker is shown,
                    show: null, //A function to be called when the Wickedpicker is shown,
                    clearable: false //Make the picker's input clearable (has clickable "x").
                };
                // Na kraju, kroz jQuery, nalazimo element kome ce se attach-ovati timepicker, i pozivamo funkciju kojoj prosledjujemo, kao argument,
                // opcije koje smo definisali.
                $('#' + tempTimepickerId).wickedpicker(options);
            }
        }
    }
    enableWorkingTime();
}

// Ova funkcija se poziva kada se stranica ucita i broji koliko je cekirano radnih odnosno vikend dana i na osnovu
// toga disable-uje ili enable-uje timepicker input-e za radne i vikend dane.
function enableWorkingTime() {
    // 1. odredimo koliko na stranici postoji order-a (celija sa checkbox-ovima od ponedeljka do nedelje).
    var numberOfOrders = $("table[id$='daysInWeek']").length;
    var numberOfWorkDays = 0, numberOfWeekendDays = 0;
    var tempCheckbox, tempChecked;
    // Prodjemo kroz svaki order.
    for (var i = 0; i < numberOfOrders; i++) {
        // I kroz svaki dan u nedelji u okviru navedenog order-a.
        for (var j = 0; j < 7; j++) {
            var tempId = "workingDaysTable:" + i + ":daysInWeek:" + j;
            // Nadjemo checkbox element za navedeni order i dan.
            tempCheckbox = document.getElementById(tempId);
            // Proverimo da li je cekiran.
            tempChecked = tempCheckbox.checked;
            // I zavisno od toga koji je dan u pitanju povecavamo 1 od 2 brojaca.
            if (tempChecked && j >= 0 && j < 5) {
                numberOfWorkDays++;
            } else if (tempChecked && j >= 5 && j <= 6) {
                numberOfWeekendDays++;
            }
        }
        // Na osnovu rezultata prebrojavanja stavljamo timepicker input-e u odgovarajuce stanje.
        setWorkingTimepickerInProperState(i, numberOfWorkDays, numberOfWeekendDays);
    }
}

function setWorkingTimepickerInProperState(orderId, numberOfWorkDays, numberOfWeekendDays) {
    if (numberOfWorkDays) {
        document.getElementById("workingDaysTable:" + orderId + ":workDayStartTime").disabled = false;
        document.getElementById("workingDaysTable:" + orderId + ":workDayStopTime").disabled = false;

        // Kada se timepicker enable-uje, vrednost input-a se set-uje na vrednost timepicker-a.
        var workDayStartTimepicker = document.getElementById("workingDaysTable:" + orderId + ":workDayStartTime").value;
        // Kada uzmemo vrednost iz timepicker-a, ona ce biti oblika: "08 : 00". Pre nego upisemo ovu vrednost u input polje napravicemo
        // da bude oblika: "08:00", jer u suprotnom LocalTimeConverter.getAsObject() metod nece raditi, jer ne ocekuje space u string-u.
        workDayStartTimepicker = workDayStartTimepicker.replaceAll(" ", "");
        document.getElementById("workingDaysTable:" + orderId + ":workDayStartInput").value = workDayStartTimepicker;

        var workDayStopTimepicker = document.getElementById("workingDaysTable:" + orderId + ":workDayStopTime").value;
        workDayStopTimepicker = workDayStopTimepicker.replaceAll(" ", "");
        document.getElementById("workingDaysTable:" + orderId + ":workDayStopInput").value = workDayStopTimepicker;
    } else {
        document.getElementById("workingDaysTable:" + orderId + ":workDayStartTime").disabled = true;
        document.getElementById("workingDaysTable:" + orderId + ":workDayStopTime").disabled = true;
        // Kada se timepicker disable-uje, vrednost input-a se set-uje na "00:00" vrednost.
        document.getElementById("workingDaysTable:" + orderId + ":workDayStartInput").value = "00:00";
        document.getElementById("workingDaysTable:" + orderId + ":workDayStopInput").value = "00:00";
    }
    if (numberOfWeekendDays) {
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStartTime").disabled = false;
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStopTime").disabled = false;

        var weekendDayStartTimepicker = document.getElementById("workingDaysTable:" + orderId + ":weekendDayStartTime").value;
        weekendDayStartTimepicker = weekendDayStartTimepicker.replaceAll(" ", "");
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStartInput").value = weekendDayStartTimepicker;

        var weekendDayStopTimepicker = document.getElementById("workingDaysTable:" + orderId + ":weekendDayStopTime").value;
        weekendDayStopTimepicker = weekendDayStopTimepicker.replaceAll(" ", "");
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStopInput").value = weekendDayStopTimepicker;
    } else {
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStartTime").disabled = true;
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStopTime").disabled = true;
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStartInput").value = "00:00";
        document.getElementById("workingDaysTable:" + orderId + ":weekendDayStopInput").value = "00:00";
    }
}

function countDays(checkboxId) {
    var orderId = checkboxId.substring(17, 18);
    var numberOfWorkDays = 0, numberOfWeekendDays = 0;
    for (var j = 0; j < 7; j++) {
        var tempId = "workingDaysTable:" + orderId + ":daysInWeek:" + j;
        var tempCheckbox = document.getElementById(tempId);
        var tempChecked = tempCheckbox.checked;
        if (tempChecked && j >= 0 && j < 5) {
            numberOfWorkDays++;
        }
        if (tempChecked && j >= 5 && j <= 6) {
            numberOfWeekendDays++;
        }
    }
    setWorkingTimepickerInProperState(orderId, numberOfWorkDays, numberOfWeekendDays);
}

// Funkcija koja se poziva kada se promeni vrednost timepicker-a. Ovom funkcijom se
// sinhronizuje vrednos u timepicker-u sa vrednoscu u input polju koje je vezano za
// bean.
function updateTimeInput(timepickerId) {
    // Da bi dobili ID input-a, potrebno je zameniti "Time" deo string-a sa "Input" string-om.
    var inptuId = timepickerId.slice(0, (timepickerId.length - 4)) + "Input";
    var timepickerValue = document.getElementById(timepickerId).value;
    // Pre upisivanja vrednosti u input polje izbacimo space-ove iz string-a.
    timepickerValue = timepickerValue.replaceAll(" ", "");
    // Vrednost timepicker-a set-ujemo kamo vrednost input polja.
    document.getElementById(inptuId).value = timepickerValue;
    // Posto cemo koristiti jQuery da bi nasli element prilagoditi selektor jQuery pravilima koja kazu
    // da ako u selektoru postoji dvotacka (":"), ispred nje mora ici dupli back-slash ("\\"). Ovo radi
    // ako unosimo ID rucno pa zemenimo ":" sa "\\:" medjutim ne radi ako kreiramo promenljivu sa string-om 
    // u kome je ":" zamenjeno sa "\\:". Zato je bolje koristiti sledece:
    $(document.getElementById(inptuId)).trigger("change");
}

// Funkcija koja kao 1. argument definise sta se trazi, kroz RegEx, dok je 2. argument zamena.
String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

// Ovaj metod se poziva u adminLoginPage.xhtml-u u p:calendar-ima, koji se nalaze u admin tabeli, i odredjuje koji dan u nedelji ce
// biti disable-ovan.
function disableNonWorkingDaysInAdminTable(date) {
    var day = date.getDay();
    // Funkcija val() vraca vrednost input polja kao string. Potrebno je ovu vrednost prebaciti u niz int-ova.
    // Detaljan opis koraka se nalazi u services.js u funkciji checkIfServiceRequireReservation().
    // Svi kalendari u redovima admin tabele na adminLoginPage.xhtml-u "gledaju" u isti hidden input
    // da bi odredili koji dani treba da budu enable-ovani a koji disable-vani.
    var workingDays = $(document.getElementById("clientOrdersForm:workingDaysInputForAdminTable")).val();
    // Sklanjamo "[" iz string-a.
    workingDays = workingDays.substring(1);
    // Sklanjamo "]" iz string-a.
    workingDays = workingDays.slice(0, -1);
    // Parsiramo string po ", " karakterima.
    workingDays = workingDays.split(", ").map(function (item) {
        return parseInt(item);
    });
    // Posto smo dobili radne dane, sada treba napraviti niz neradnih dana, jer zapravo oni treba da budu
    // disable-ovani.
    var nonWorkingDays = [];
    // U PF p:calendar-u oznacavanje dana ID-jevima pocinje od nedelje koja ima ID 0 pa do subote sa ID-em 6. Posto ID-jevi dana u nasoj bazi
    // pocinu od 1 (ponedeljak) do 7 (nedelja), proveru koji dani su neradni radimo iz 2 koraka. Prvo proverimo od ponedeljka do subote
    // (1 - 6) posto se te vrednosti slazu i u bazi i u PF-u.
    for (var x = 1; x < 7; x++) {
        // Proverimo da li niz radnih dana sadrzi odredjenu vrednost. Ako je rezultat "-1" to znaci da ne sadrzi
        // i onda dodajemo tu vrednost u niz neradnih dana.
        if (workingDays.indexOf(x) === -1) {
            nonWorkingDays.push(x);
        }
    }
    // 2. korak je da proverimo da li u radnim danima imamo 7-cu (DB nedelja) i ako nemamo treba da dodamo 0 (PF nedelja) u niz neradnih dana.
    if (workingDays.indexOf(7) === -1) {
        nonWorkingDays.push(0);
    }
    // Sada odredjujemo da li ce dan u sedmici biti enable-ovan ili disable-ovan.
    // Ako je rezultat true onda se dan enable-uje. Pocinjemo od pretpostavke da
    // su svi dani enable-ovani.
    var checkDay = true;
    // Iterisemo kroz array brojeva, koji govore koje dane treba disable-ovati, i poredimo ih sa trenutnim danom
    // (vrednost promenljive "day").
    for (var i = 0; i < nonWorkingDays.length; i++) {
        checkDay = checkDay && day !== nonWorkingDays[i];
    }
    // Vracamo rezultat iteracije, dok je 2. parametar, optional style class, koji se moze dodeliti date celiji. 
    return [checkDay, ''];
}