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