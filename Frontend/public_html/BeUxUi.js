/*
 
 
 This object is responsible for making the whole application responsive from design perspective. 
 
 It does not add content etc. It only decides layout questions for user experience using data it received from Controller object. 
 
 It decides which css classes to use and if certain columns in table should not be displayed for us reasons. 
 
 */


function BeUxUi() {

    var util = new BeUtility();
    var nav = new BeNavigation();
    var content = new BeInfoWeb();
    var login = new BeLogIn();




    this.initTree = function (response) {
        util.setHtmlInDiv("mainBody", content.getTreeRootNodes(response));
        return;
    };

    this.modifyFont = function (s) {

        if (s === "bigger") {
            //document.body.style.fontSize = "30px";
            globalFontSize += 2;
        } else {
            //document.body.style.fontSize = "5px";
            globalFontSize -= 2;
        }

        util.updateFontSize();

        return;
    };

    this.expandTreeNode = function (responseArr, idNode, idHtml, editVersion) {
        var s;
        var response;
        response = responseArr[0];
        var id = idHtml + "_" + idNode;
        if (editVersion === true) {
            s = content.getNodeBigEdit(id, response.title, response.content, response.files, response.tags, response.children);
        } else {
            s = content.getNodeBig(id, response.title, response.content, response.files, response.tags, response.children);
        }
        util.setHtmlInDiv(id, s);

        return;
    };


    this.collapseTreeNode = function (idNode, obj) {
        var s, t, ids;
        t = obj.innerHTML;        

        // his.getCollapsedNodeSmall = function (id, text) {
        s = content.getCollapsedNodeSmall(idNode, t);
        util.setHtmlInDiv(idNode, s);
        return;
    };

    this.setChildNodes = function (response) {
        var id;
        var html;
        id = response.idHtml + "_" + response.idNode + "_children";

//        id = response.idNode + "_children";
        this.log("setting content for id in DOM: " + id);
        html = content.getTreeRootNodes(response.r);
        util.setHtmlInDiv(id, html);
        return;
    };

    // setParentNodes

    this.setParentNodes = function (response) {
        var id;
        var html;
        id = response.idHtml + "_" + response.idNode + "_parents";

//        id = response.idNode + "_children";
        this.log("setting content for id in DOM: " + id);
        html = content.getTreeParentNodes(response.r);
        util.setHtmlInDiv(id, html);
        return;
    };


    this.updateFileListOfNode = function (fileArray, linkMap, idNode, idHtml) {
        var id;
        var html;
        id = idHtml + "_" + idNode + "_filelist";
        html = content.getNodeFileList(fileArray, linkMap);
        util.setHtmlInDiv(id, html);
        return;
    };

    this.initNavigation = function () {
        util.setHtmlInDiv("mainNavi", nav.getNavigation());
//        util.setHtmlInDiv("content", util.getLogIn());
        return;
    };

    this.setDefaultMain = function () {
        util.setHtmlInDiv("mainBody", "OK, Logged in.");
        return;
    };

    this.initLogIn = function () {
        util.setHtmlInDiv("mainBody", login.getLogIn());
        return;
    };

    this.initFooter = function () {
        util.setHtmlInDiv("footer", util.getFooter());
        return;
    };

    this.initLog = function () {
        util.setHtmlInDiv("mainLog", util.getLog());
        return;
    };

    this.log = function (s) {
        util.log(s);
        return;
    };

    this.showHideLog = function () {
        util.log(document.getElementById("mainLog").style.display);

        var s = document.getElementById("mainLog").style.display;

        if (s === 'none') {
            document.getElementById("mainLog").style.display = "inline";
        } else {
            document.getElementById("mainLog").style.display = 'none';
        }
        return;
    };



}