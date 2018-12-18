document.onload = closeAlerts();

function closeAlerts() {

    setTimeout(function() {

    $(".alert").alert('close');
    }, 3000);
}

function showModal() {
    $("#savedLinkData").modal();
}