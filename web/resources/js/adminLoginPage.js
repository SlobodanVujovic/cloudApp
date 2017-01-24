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

function showServicePopupWin(){
    var inputToShow = document.getElementById("servicesPopupBack");
    inputToShow.style.display = "block";
}

//Pri pokusaju brisanja posledjeg activity-ja kompanije javlja se obavestenje da to nije dozvoljeno.
function companyMustHaveAtLeastOneActivity(){
    var moreThenOneRow = false;
    var activityTable = document.getElementById("companyActivityTable");
    var numberOfRows= activityTable.rows.length;
//    Uzimamo uslov "> 2" jer se broji red u kome je ispisan activity ali i header, zato je minimalni
//    broj redova u tabeli 2.
    if(numberOfRows > 2){
        moreThenOneRow = true;
    } else{
        alert("Company must have at least one activity.");
    }
    return moreThenOneRow;
}

// Za globalFilter polje i p:calendar tag (u okviru "Reservation Date" kolone) u clientOrdersTableId tabeli (na adminLoginPage strani),
// kada se pritisne Enter key, u slucaju kada u polju ne postoji uneta vrednost, dolazi do pozivanja metoda
// deleteClientOrderFromTable() cime se brise prvi red u tabeli (razlog nepoznat i neshvacen). Da bi se ovo sprecilo za ova 2 elementa je disable-ovano
// default ponasanje Enter key-a time sto se ova funkcija poziva kroz "onkeypress" atribut. Enter key ima code = 13.
function preventEnterBehaviour(event){
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