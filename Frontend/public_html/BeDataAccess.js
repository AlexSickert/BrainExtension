/*
 
 Data Access layer to make interaction with controller easier
 
 
 */


function BeDataAccess() {


    /*
     * get the list of files and its download links for a specific id
     */
    this.getFileList = function (con, session, idNode, idHtml) {
        try {
            var r = new NodeFilesRequest(session, idNode, idHtml);
            var req = new BeAjaxRequest();
            req.onreadystatechange = function () {
                if (req.readyState === 4) {
                    try {
                        var resObj = JSON.parse(req.responseText);
                        con.callBack(resObj);
                    } catch (x) {
                        console.log("Error in BeDataAccess getFileList() - onreadystatechange");
                        console.log(x);
                    }
                }
            };
            var obj = encodeURIComponent(JSON.stringify(r));
            req.open("POST", "post", true);
            req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            req.send(obj);
            return;
        } catch (x) {
            console.log("Error in BeDataAccess getFileList()");
            console.log(x);
        }

    }
    /*
     * log in into the app
     * 
     * @param {type} con
     * @param {type} u
     * @param {type} p
     * @param {type} x
     * @returns {undefined}
     */
    this.logIn = function (con, u, p, x) {
        try {
            var r = new BeRequest(u, p, x);
            var req = new BeAjaxRequest();

            req.onreadystatechange = function () {
                if (req.readyState === 4) {
                    try {
                        var resObj = JSON.parse(req.responseText);
                        con.callBack(resObj);
                    } catch (x) {
                        console.log("Error in BeDataAccess logIn() - onreadystatechange");
                        console.log(x);
                    }
                }
            };

            var obj = encodeURIComponent(JSON.stringify(r));
            req.open("POST", "post", true);
            req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            req.send(obj);
            return;
        } catch (x) {
            console.log("Error in BeDataAccess logIn()");
            console.log(x);
        }
    };

    /*
     * get the tree or elements of it
     * 
     * @param {type} con
     * @param {type} session
     * @param {type} parent
     * @param {type} content
     * @returns {undefined}
     */
    this.getTree = function (con, session, parent, content) {
        var r = new TreeRequest(session, parent, content);
        var req = new BeAjaxRequest();

//        alert(content);

        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                console.log(req.responseText);
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        console.log("------------------------------");
        console.log(JSON.stringify(r));
        console.log("------------------------------");
        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };

    this.getTreeBySearch = function (con, session, parent, content, searchString) {
        var r = new TreeSearchRequest(session, parent, content, searchString);

        var req = new BeAjaxRequest();

//        alert(content);

        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                console.log(req.responseText);
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        console.log("------------------------------");
        console.log(JSON.stringify(r));
        console.log("------------------------------");
        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };


    /*
     * get a single node
     * @param {type} con
     * @param {type} session
     * @param {type} idNode
     * @param {type} idHtml
     * @param {type} editVersion
     * @returns {undefined}
     */

    this.getNode = function (con, session, idNode, idHtml, editVersion) {
        var r = new NodeRequest(session, idNode, idHtml, editVersion);
        var req = new BeAjaxRequest();
//        alert("BeDataAcces.ja this.getNode  editVersion = " + editVersion);

        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                console.log(req.responseText);
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };


    this.getChildNodes = function (con, session, idNode, idHtml) {
        var r = new ChildNodesRequest(session, idNode, idHtml);
        var req = new BeAjaxRequest();
//        alert("BeDataAcces.ja this.getNode  editVersion = " + editVersion);

        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                console.log(req.responseText);
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };


    this.getAnchestors = function (con, session, idNode, idHtml) {
        var r = new AnchestorsRequest(session, idNode, idHtml);
        var req = new BeAjaxRequest();
//        alert("BeDataAcces.ja this.getNode  editVersion = " + editVersion);

        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                console.log(req.responseText);
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };

    // create a new child node and then load the whole list of children
    this.makeChildNode = function (con, session, idNode, idHtml) {
        var r = new MakeChildAndNodesRequest(session, idNode, idHtml);
        var req = new BeAjaxRequest();
//        alert("BeDataAcces.ja this.getNode  editVersion = " + editVersion);

        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                console.log(req.responseText);
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    }



    /* Update a single value of a certain Node
     * 
     * @param {type} con
     * @param {type} session
     * @param {type} idNode
     * @param {type} idHtml
     * @param {type} field
     * @param {type} value
     * @returns {undefined}
     */
    this.updateNode = function (con, session, idNode, idHtml, field, value) {
        var r = new NodeUpdateRequest(session, idNode, idHtml, field, value);
        var req = new BeAjaxRequest();

        /*
         * now we define the general call back function
         */
        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };

    this.uploadNodeFile = function (con, session, id, fileNameArray, fileHashMap) {

        ids = id.split("_");

        var r = new NodeFileUploadRequest(session, ids[1], ids[0], fileNameArray, fileHashMap);



        var req = new BeAjaxRequest();

        /*
         * now we define the general call back function
         */
        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };



    this.moveOrLinkNode = function (con, session, action, childId, newConnectedId, idHtml) {

        var r = new NodeMoveOrLinkRequest(session, childId, newConnectedId, action, idHtml);

        var req = new BeAjaxRequest();

        /*
         * now we define the general call back function
         */
        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "post", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };


    this.loadCrm = function (con, session, search, category, close, watch, sort) {

        // search.trim(), category.trim(), close.trim(), watch.trim()
        // dataAccess.loadCrm(this, globalSession, search.trim(), category.trim(), close.trim(), watch.trim());

        var r = new CrmLoadRequest(session, "load");
        //r.rowId = id;

        r.filter_1_field = "search";
        r.filter_1_value = search;
        r.filter_1_logic = "search";

        r.filter_2_field = "category";
        r.filter_2_value = category;
        r.filter_2_logic = "like";

        r.filter_3_field = "closeness";
        r.filter_3_value = close;
        r.filter_3_logic = "equal";

        r.filter_4_field = "watchlist";
        r.filter_4_value = watch;
        r.filter_4_logic = "equal";
        
        r.filter_5_field = "sort";
        r.filter_5_value = sort;
        r.filter_5_logic = "sort";

        var req = new BeAjaxRequest();

        /*
         * now we define the general call back function
         */
        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "crm", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };


    this.loadCrmOneContact = function (con, session, id) {

        var r = new CrmLoadRequest(session, "loadOne");
        r.rowId = id;

        var req = new BeAjaxRequest();

        /*
         * now we define the general call back function
         */
        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "crm", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };


    this.uploadNew = function (con, session, txt) {

        var r = new CrmLoadRequest(session, "newData");
        r.newData = txt;

        var req = new BeAjaxRequest();

        /*
         * now we define the general call back function
         */
        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "crm", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;

    }


    this.uploadNew = function (con, session, txt) {

        var r = new CrmLoadRequest(session, "newData");
        r.newData = txt;

        var req = new BeAjaxRequest();

        /*
         * now we define the general call back function
         */
        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "crm", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;

    };


    // load one result CrmLoadRequest(session, action)
    this.updateCrmContact = function (con, session, id, field, val) {

        var r = new CrmLoadRequest(session, "update");
        r.rowId = id;
        r.fieldName = field;
        r.fieldValue = val;

        var req = new BeAjaxRequest();

        req.onreadystatechange = function () {
            if (req.readyState === 4) {
                var resObj = JSON.parse(req.responseText);
                con.callBack(resObj);
            }
        };

        var obj = encodeURIComponent(JSON.stringify(r));
        req.open("POST", "crm", true);
        req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        req.send(obj);
        return;
    };



    /*
     * =========================================================================
     * =========================================================================
     * =========================================================================
     * 
     * NOT SURE IF THIS CODE IS EVER USED
     * 
     * =========================================================================
     * =========================================================================
     * =========================================================================
     */
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


        mypostrequest.open("POST", "basicform.php", true);
        mypostrequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //mypostrequest.send(parameters);
        mypostrequest.send(formData);

        return;
    };


}