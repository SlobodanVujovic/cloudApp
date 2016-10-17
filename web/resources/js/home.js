/*
 * Identicno je ako napisemo:
 * $(document).ready(function () {
 * ili:
 * $(function () {
 * Druga verzija je samo skracenje.
 */
$(document).ready(function () {
    $("#firstLine").vTicker("init", {speed: 2000});
    $("#firstLine").vTicker("next", {animate: true});
    $('#firstLine').vTicker("pause", true);
    $("#secondLine").vTicker("init", {speed: 2000});
    $("#secondLine").vTicker("prev", {animate: true});
    $('#secondLine').vTicker("pause", true);
});