/*
 
 Controller reacts on user interface activity or on recieption of data from websocket and callbacks it uses the 
 
 It decides if data is temporarily stored or synched with server. 
 
 It uses the module objects (BeNavigation, BeInfoWeb)
 
 
 */



function BeController() {

    var keyNavi = true;
    var session = '';
    var util;
    var server;

    var uxui;
    var util;
    var dataAccess;

    var idRelationship;
    var idMove;

    this.init = function () {

        uxui = new BeUxUi();
        util = new BeUtility();
        dataAccess = new BeDataAccess();

        if (globalSession === "") {
            uxui.initLogIn();
            uxui.initLog();
        }

        return;
    };

    // try to log in 
    /*
     * 
     * @returns {undefined}
     */
    this.submitLogin = function () {

        var u = util.getValue("user");
        var p = util.getValue("password");
        var x = "xxx";
        dataAccess.logIn(this, u, p, x);
    }

    /* 
     * 
     * @param {type} responseObject
     * @returns {undefined}
     * main call back for all replies from server
     */

    this.callBack = function (responseObject) {
        uxui.log("BeController:call back recieved");


        /* todo: distinguish between various response objects and handle login error
         * 
         */
        if (responseObject.m === "l") {
            if (responseObject.e === "OK") {
                if (responseObject.s.length > 2) {
                    globalSession = responseObject.s;
                    uxui.log("setting global session to " + globalSession);
                    uxui.initNavigation();
                    uxui.setDefaultMain();
                } else {
                    uxui.log("responseObject.s =  " + responseObject.s);
                }
            } else {
                uxui.log("error in callBack = function (responseObject) - File BeController.js: " + responseObject.e + "session is: " + responseObject.s);
            }
        }

        if (responseObject.m === "t") {
            if (responseObject.e === "OK") {
                if (responseObject.c === "rootNodes") {
                    // set the root doc in navi
                    uxui.log("responseObject.c == " + responseObject.c);
                    uxui.initTree(responseObject.r);

                }

                if (responseObject.c === "searchNodes") {
                    // set the root doc in navi
                    uxui.log("responseObject.c == " + responseObject.c);
                    uxui.initTree(responseObject.r);

                }

                //insert child nodes
                if (responseObject.c === "childNodes") {
                    // set the root doc in navi
                    uxui.log("responseObject.c == " + responseObject.c);
                    uxui.setChildNodes(responseObject);

                }


                //insert parent nodes - make breadcrumbs
                if (responseObject.c === "parentNodes") {
                    // set the root doc in navi
                    uxui.log("responseObject.c == " + responseObject.c);
                    uxui.setParentNodes(responseObject);

                }

                // call back when new child was created  makeChildNodes
                if (responseObject.c === "makeChildNodes") {
                    // set the root doc in navi
                    uxui.log("responseObject.c == " + responseObject.c);
                    uxui.setChildNodes(responseObject);

                }

                if (responseObject.c === "update") {
                    // set the root doc in navi
                    uxui.log("responseObject.c == " + responseObject.c);
                    uxui.log("responseObject.r == " + responseObject.i);
//                    uxui.initTree(responseObject.r);

                }

                if (responseObject.c === "file-upload") {
                    // set the root doc in navi
                    uxui.log("responseObject.c == " + responseObject.c);
                    uxui.log("responseObject.r == " + responseObject.r);

                    /*
                     * as things are OK we can reload the file list of this node
                     */

                    dataAccess.getFileList(this, globalSession, responseObject.idNode, responseObject.idHtml);

//                    uxui.initTree(responseObject.r);
                }

                /*
                 * this is when we need to update the file list
                 */
                if (responseObject.c === "file-list") {
                    uxui.log("updating file list: received response from server");
                    uxui.updateFileListOfNode(responseObject.fileNames, responseObject.filesHashMap, responseObject.idNode, responseObject.idHtml);
                }


                /**
                 * this is when we want to show a node
                 */
                if (responseObject.c === "node") {
                    // check if the version of the response is for an editable 
                    // node
                    if (responseObject.v === true) {
                        // set the root doc in navi
                        uxui.log("node to be edited");
                        uxui.log("responseObject.c == " + responseObject.c);
                        // idNode, idHtml
                        uxui.expandTreeNode(responseObject.r, responseObject.idNode, responseObject.idHtml, responseObject.v);
                    } else {
                        // set the root doc in navi
                        uxui.log("node NOT to be edited");
                        uxui.log("responseObject.c == " + responseObject.c);
                        // idNode, idHtml
                        uxui.expandTreeNode(responseObject.r, responseObject.idNode, responseObject.idHtml, responseObject.v);


                    }

                    // we also want to show the file list
                    uxui.log("now getting files for id: " + responseObject.idNode);
                    dataAccess.getFileList(this, globalSession, responseObject.idNode, responseObject.idHtml);


                    //now the node is expanded and we can load the child nodes   
                    uxui.log("now getting child nodes for id: " + responseObject.idNode);
                    dataAccess.getChildNodes(this, globalSession, responseObject.idNode, responseObject.idHtml);

                    // now we need to load also relationships here 

                    // now we set the bread crumb path
                    uxui.log("now we set the bread crumb path for id: " + responseObject.idNode);
                    dataAccess.getAnchestors(this, globalSession, responseObject.idNode, responseObject.idHtml);


                }

                // after moving or lnking a node
                if (responseObject.c === "move-or-link") {
                    uxui.log("moved or lnked node");

                    // now we need to refresh the node and its elements
                    this.expandNode(responseObject.idHtml);

                }

            } else {

                if (responseObject.e.match(/Error: Invalid session/g)) {
                    location.reload();
                } else {
                    alert("error in callBack = function (responseObject) - File BeController.js: " + responseObject.e);
                }
            }
        }
        ;




    };


    // this is run frum buttons on top menu
    this.navigate = function (s) {
        //alert(s);
        if (s === "tree") {
            dataAccess.getTree(this, globalSession, "", "rootNodes");
        }

        if (s === "search") {
            this.searchFlexible(1);
        }
        if (s === "searchLimited") {
            this.searchFlexible(6);
        }
    };

    this.searchFlexible = function (l) {
        var search = util.getValue("searchField");

        if (search.length >= l) {
            uxui.log("BeController: searching tree nodes for string: " + search);
            if (search.length > 2) {
                dataAccess.getTreeBySearch(this, globalSession, "", "searchNodes", search);
            } else {
                uxui.log("BeController: searching string too short: " + search);
            }
        }


    };


    this.makeChildren = function (id) {
        //alert(obj.id);
        //
        uxui.log("BeController: making children for id: " + id);
        var str = id.split("_");
//        dataAccess.getNode(this, globalSession, str[1], str[0], false);
        dataAccess.makeChildNode(this, globalSession, str[1], str[0]);
    };


    /*
     * this function expands a node in the sense that it goes from collapsed state
     * to expanded state
     */
    this.expandNode = function (objStr) {
        //alert(obj.id);
        var str = objStr.split("_");
        dataAccess.getNode(this, globalSession, str[1], str[0], false);
    };

    // collapseNode
    this.collapseNode = function (objStr, obj) {
             
        uxui.collapseTreeNode(objStr, obj);             
          
    };

    this.jumpToNode = function (id) {
        alert("not implemented: this.jumpToNode = function(id) ");
    };


    /*
     * function converts a node that is read only into a verion that can be
     * edited
     * 
     */
    this.editNode = function (id) {
//        alert(id);
        uxui.log("BeController: this.editNode id is: " + id);
        var str = id.split("_");
        dataAccess.getNode(this, globalSession, str[1], str[0], true);
    };



    /*
     * editing the node finished
     * 
     */
    this.editNodeFinished = function (id) {
//        alert(id);
        uxui.log("BeController: this.editNode id is: " + id);
        var str = id.split("_");
        dataAccess.getNode(this, globalSession, str[1], str[0], false);
    };


    //   moveNode 

    this.forkxxxxxNode = function (id) {
        alert(id);
//        uxui.log("forkNode " + id);
//        this.idMove = id;
//        this.idRelationship = id;
        return;
    };

    this.forkNode = function (targetId) {
        uxui.log("moveNode  " + targetId + " to " + targetId);
        uxui.log("forkNode " + targetId);
        var str = targetId.split("_");
        this.idMove = str[1];
        this.idRelationship = str[1];
//        dataAccess.moveOrLinkNode(this, globalSession, str[1], str[0], str[2], obj.value);

    };



    this.moveNode = function (targetId) {

        var str = targetId.split("_");

        if (this.idMove === str[1]) {
            alert("error: nodes are equal");
        } else {
            uxui.log("moveNode  " + this.idMove + " to " + str[1]);
            // function (con, session, action, childId, newConnectedId)
            dataAccess.moveOrLinkNode(this, globalSession, "move", this.idMove, str[1], targetId);
        }
    };

    this.linkPaste = function (targetId) {
        var str = targetId.split("_");

        uxui.log("linkPaste between  " + this.idRelationship + " and " + targetId);

        if (this.idRelationship === str[1]) {
            alert("error: nodes are equal");
        } else {
            uxui.log("moveNode  " + this.idRelationship + " to " + str[1]);
            dataAccess.moveOrLinkNode(this, globalSession, "link", this.idRelationship, str[1], targetId);
        }
    };

    /*
     * method that ends editing the node
     * it is called whenever something in the node changes
     */
    this.saveNodeFragment = function (obj) {
        uxui.log("BeController: this.saveNodeFragment id is: " + obj.id);
        uxui.log("BeController: this.saveNodeFragment value is: " + obj.value);
        var s = obj.id;

        var str = s.split("_");
        dataAccess.updateNode(this, globalSession, str[1], str[0], str[2], obj.value);
    };

    /*
     * 
     * function is being called from UXUI object
     * obj is the html object 
     * id is the id of the node plus _file
     */

    this.uploadNodeFile = function (obj, id) {

        uxui.log("BeController: uploadNodeFile " + id);
        //     uploadNodeFile(this, "' + id + '_files") 

        // see http://blog.teamtreehouse.com/uploading-files-ajax

        var fileSelect = document.getElementById(id);
        var files = fileSelect.files;


        var util = new BeUtility();

        util.init(this, dataAccess, files, globalSession, id);
    }

    this.modifyFont = function (s) {
        uxui.modifyFont(s);
    }



    /**
     * show or hide the log
     * @returns {undefined}
     */
    this.showLog = function () {
        uxui.showHideLog();
    }
    /*
     * ======================================================================
     * ======================================================================
     * ======================================================================
     * ======================================================================
     * ======================================================================
     * ======================================================================
     */

    this.naviOff = function () {
        keyNavi = false;
    };


    this.naviOn = function () {
        keyNavi = true;
    };

    this.catchKey = function (e) {

        var c;

        if (navigator.userAgent.match("Gecko")) {
            c = e.which;
            //navigator.
        } else {
            c = e.keyCode;
        }

        // ESC key
        if (c == 27) {
            util.log("ESC pressed");
            this.naviOn();
        }

        if (keyNavi) {

            // T key
            if (c == 84) {
                util.log("T pressed");
            }

            // S key
            if (c == 83) {
                util.log("S pressed");
            }

            // R key
            if (c == 82) {
                util.log("R pressed");
            }
        }
        ;
        //alert(c);
    };




    this.catchFormKey = function (e) {

        var c;

        if (navigator.userAgent.match("Gecko")) {
            c = e.which;
        } else {
            c = e.keyCode;
        }

        // ESC key
        if (c == 27) {
            document.getElementsByTagName("BODY").item(0).focus();
        }
    };



}