
/*
 * create AJAX object
 */

function BeAjaxRequest() {
    var http = false;
    try {
        http = new XMLHttpRequest();
    } catch (e1) {
        try {
            http = new ActiveXObject("Msxml2.xmlhttp");
        } catch (e2) {
            try {
                http = new ActiveXObject("Microsoft.xmlhttp");
            } catch (e3) {
                http = false;
            }
        }
    }
    return http;
}
