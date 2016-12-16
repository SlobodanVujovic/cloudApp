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