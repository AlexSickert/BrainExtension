/*
 
 Utility object for simple actions like
 
 - hiding and showing HTML elements
 - set style sheet elements via javascript
 - 
 
 */


function BeUtility() {


    //----------------------------------------------------------------------

    // functions etc for file upload loop

    var reader = new FileReader();
    reader.parentObject = this;
    var dataAccess;
    var filesArray;
    var file;
    var fileNamesArray = new Array();
    var fileBase64Map = {};
    var currentFileName;
    var currentFileCounter;
    var controller;
    var session;
    var id;
    var parser = new BeMdParser();

    var logLounter = 0;

    this.init = function (con, objDataAccess, files, s, i) {
        controller = con;
        session = s;
        id = i;
        dataAccess = objDataAccess;
        filesArray = files;
        file = files[0];
        currentFileCounter = 0;
        console.log("preparing file: " + file.name);
        currentFileName = file.name;
        fileNamesArray.push(file.name);
        reader.readAsDataURL(file);
        return;
    };


    this.next = function () {
        console.log("in method next() for next file");
        currentFileCounter = currentFileCounter + 1;
        file = filesArray[currentFileCounter];
        console.log("preparing file: " + file.name);
        currentFileName = file.name;
        fileNamesArray.push(file.name);
        reader.readAsDataURL(file);
        return "";
    };

    this.send = function () {
        // now we need to access the data access object
        //  this.uploadNodeFile = function (con, session, idNode, idHtml, fileNameArray, fileHashMap) 
        console.log("this.send = function () {");
        dataAccess.uploadNodeFile(controller, session, id, fileNamesArray, fileBase64Map);
    };

    reader.onload = function (e) {
        console.log("current file converted");
        fileBase64Map[currentFileName] = reader.result;
//        console.log("file content: " + fileBase64Map[currentFileName]);
        if (filesArray.length > (currentFileCounter + 1)) {
            // move to next
            console.log("moving to next file for processing");
            this.parentObject.next();

        } else {
            // stop loop and send stuff for ajax
            console.log("finished converting files - now sending");
            this.parentObject.send();
        }

    }

    // ---------------------------------------------------------------------

    this.getFooter = function () {
        return "<i>footer template</i>";
    };

    this.getLogIn = function () {
        return " \
        <input type='text' id='u' name='u'  />  \
        <input type='text' id='p' name='p'  />  \
        <input type='text' id='x' name='x'  />  \
        <input type='button' id='ok' name='OK'  onclick='con.submitLogin(this)' />  \
        ";
    };

    this.getLog = function () {
        return "<i>log html template</i>";
    };

    this.getValue = function (id) {
        return document.getElementById(id).value;

    };

    this.setHtmlInDiv = function (id, html) {

        if (id === null) {
            this.log("id for this.setHtmlInDiv is null");
        } else {
            this.log("id to insert html into is: " + id);
        }

        if (html === null) {
            this.log("HTML content for this.setHtmlInDiv is null");
        } else {
            this.log("HTML content length for  this.setHtmlInDiv is: " + html.length);
        }
        document.getElementById(id).innerHTML = html;

        // now we need to ensure font size is correct. 
        // this is a dirty hack... not good 
        this.updateFontSize();

        return;
    };


    this.updateFontSize = function () {
        this.log("new font size is: " + globalFontSize);
        var all = document.getElementsByTagName("*");
        for (var i = 0, max = all.length; i < max; i++) {
            try {
                all[i].style.fontSize = globalFontSize + "px";
            } catch (e) {
                // to do;
            }
        }
    };

    this.log = function (x) {
        var s;
        logLounter = logLounter + 1;
        console.log(x);
        s = document.getElementById("mainLog").innerHTML;
        s = logLounter + ": " + x + "<br>" + s.substring(0, 1000);

        document.getElementById("mainLog").innerHTML = s;
        return;
    };

    this.parseMdToHtml = function (s, id) {
        var r;
        r = parser.parse(s, id);
        return r;
    };

}