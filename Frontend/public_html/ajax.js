
function ajaxRequest() {
    var activexmodes = ["Msxml2.XMLHTTP", "Microsoft.XMLHTTP"];//activeX versions to check for in IE
    if (window.ActiveXObject) { //Test for support for ActiveXObject in IE first (as XMLHttpRequest in IE7 is broken)
        for (var i = 0; i < activexmodes.length; i++) {
            try {
                return new ActiveXObject(activexmodes[i]);
            } catch (e) {
                //suppress error
            }
        }
    } else if (window.XMLHttpRequest) // if Mozilla, Safari etc
        return new XMLHttpRequest();
    else
        return false;
}



sendAjax = function () {

    var mypostrequest = new ajaxRequest();
    mypostrequest.onreadystatechange = function () {
        if (mypostrequest.readyState === 4) {
            if (mypostrequest.status === 200 || window.location.href.indexOf("http") === -1) {
                //document.getElementById("result").innerHTML = mypostrequest.responseText;
            } else {
                alert("An error has occured making the request");
            }
        }
    };


    var files = document.getElementById("datei").files;

    var formData = new FormData();
    var file = files[0];
    formData.append('photos[]', file, file.name);

    var namevalue = encodeURIComponent(document.getElementById("firstname").value);
    var agevalue = encodeURIComponent(document.getElementById("lastname").value);
    var parameters = "name=" + namevalue + "&age=" + agevalue;

    formData.append('name', namevalue);
    formData.append('age', agevalue);


//    mypostrequest.open("POST", "basicform.php", true);
//    mypostrequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//    //mypostrequest.send(parameters);
//    mypostrequest.send(formData);


    var agevalue = encodeURIComponent("asdfasdfasdf");
    var parameters = "name=" + agevalue + "&age=" + agevalue;
    mypostrequest.open("POST", "basicform.php", true);
    mypostrequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    mypostrequest.send(parameters);



    return;
};