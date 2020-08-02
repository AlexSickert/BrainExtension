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


    this.initCrm = function () {
        util.setHtmlInDiv("mainBody", "loading crm...");

        // here we load the basic structure of the page
        // crmHtml
        util.setHtmlInDiv("mainBody", util.crmHtml());

        return;
    };

    this.setCrmResult = function (response) {
        //util.setHtmlInDiv("mainBody", response);

        util.setHtmlInDiv("crmSearchResult", this.prepareCrmResult(response));
        return;
    };


    this.setCrmOneResult = function (response) {
        //util.setHtmlInDiv("mainBody", response);

        util.setHtmlInDiv("crmSingleContact", this.prepareOneCrmResult(response));
        return;
    };

    this.setNewDataResult = function (response) {
        util.setHtmlInDiv("newDataResult", JSON.stringify(response));

    }

    this.prepareCrmResult = function (response) {

        var s = "<ul>"
        for (var i = 0; i < response.length; i++) {
            s += '<li onclick="con.showOneCrmResult(' + response[i].ID + ')"><b>' + response[i].title + "</b> <i> " + response[i].short_info + "</i></li>";
        }

        s += "</ul>";
        return s;
    }


    this.prepareOneCrmResult = function (response) {

        var s = "";
//        s += '<br>Title:&nbsp;<input type="text" id="title" value="' + response[0].title + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>short_info:&nbsp;<input type="text" id="short_info" value="' + response[0].short_info + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>location:&nbsp;<input type="text" id="location" value="' + response[0].location + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>category_tag:&nbsp;<input type="text" id="category_tag" value="' + response[0].category_tag + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>topic_tags:&nbsp;<input type="text" id="topic_tags" value="' + response[0].topic_tags + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>closeness:&nbsp;<input type="text" id="closeness" value="' + response[0].closeness + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>company_tags:&nbsp;<input type="text" id="company_tags" value="' + response[0].company_tags + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>watch_list:&nbsp;<input type="text" id="watch_list" value="' + response[0].watch_list + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>action:&nbsp;<input type="text" id="action" value="' + response[0].action + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>action_time:&nbsp;<input type="text" id="action_time" value="' + this.numberToDate(response[0].action_time) + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>comment:&nbsp;<textarea id="comment" style="width: 100%; height: 250px;" value="' + response[0].comment + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">' + response[0].comment + '</textarea>';
//        s += '<br>email_1:&nbsp;<input type="text" id="email_1" value="' + response[0].email_1 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>email_2:&nbsp;<input type="text" id="email_2" value="' + response[0].email_2 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        //s += '<br>instagram:&nbsp;<input type="text" id="instagram" value="' + response[0].instagram + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].instagram + '\')">instagram</span>:&nbsp;<input type="text" id="instagram" value="' + response[0].instagram + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].facebook_messenger + '\')">facebook_messenger</span>:&nbsp;<input type="text" id="facebook_messenger" value="' + response[0].facebook_messenger + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].linkedin + '\')">linkedin</span>:&nbsp;<input type="text" id="linkedin" value="' + response[0].linkedin + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].xing + '\')">xing</span>:&nbsp;<input type="text" id="xing" value="' + response[0].xing + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].web_1 + '\')">web_1</span>:&nbsp;<input type="text" id="web_1" value="' + response[0].web_1 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].web_2 + '\')">web_2</span>:&nbsp;<input type="text" id="web_2" value="' + response[0].web_2 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>viber:&nbsp;<input type="text" id="viber" value="' + response[0].viber + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>whatsapp:&nbsp;<input type="text" id="whatsapp" value="' + response[0].whatsapp + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>telegram:&nbsp;<input type="text" id="telegram" value="' + response[0].telegram + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>phone_1:&nbsp;<input type="text" id="phone_1" value="' + response[0].phone_1 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>phone_2:&nbsp;<input type="text" id="phone_2" value="' + response[0].phone_2 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>du_sie:&nbsp;<input type="text" id="du_sie" value="' + response[0].du_sie + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>gender:&nbsp;<input type="text" id="gender" value="' + response[0].gender + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>salutation:&nbsp;<input type="text" id="salutation" value="' + response[0].salutation + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>postal_address:&nbsp;<input type="text" id="postal_address" value="' + response[0].postal_address + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
//        s += '<br>source:&nbsp;<input type="text" id="source" value="' + response[0].source + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';



        s += '<table class="crm">';
        s += '<tr>';
        s += '<td colspan=2>';
        s += '<br>Title:&nbsp;<input  class="crm" type="text" id="title" value="' + response[0].title + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>short_info:&nbsp;<input  class="crm" type="text" id="short_info" value="' + response[0].short_info + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '</td>';
        s += '</tr><tr>';
        s += '<td >';
        s += '<br>location:&nbsp;<input class="crm"  type="text" id="location" value="' + response[0].location + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>category_tag:&nbsp;<input class="crm"  type="text" id="category_tag" value="' + response[0].category_tag + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>topic_tags:&nbsp;<input  class="crm" type="text" id="topic_tags" value="' + response[0].topic_tags + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>closeness:&nbsp;<input  class="crm" type="text" id="closeness" value="' + response[0].closeness + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '</td><td >';
        s += '<br>company_tags:&nbsp;<input class="crm" type="text" id="company_tags" value="' + response[0].company_tags + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>watch_list:&nbsp;<input class="crm" type="text" id="watch_list" value="' + response[0].watch_list + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>action:&nbsp;<input class="crm"  type="text" id="action" value="' + response[0].action + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>action_time:&nbsp;<input class="crm"  type="text" id="action_time" value="' + this.numberToDate(response[0].action_time) + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '</td>';
        s += '</tr><tr>';
        s += '<td colspan=2>';
        s += '<br>comment:&nbsp;<textarea id="comment" style="width: 100%; height: 250px;" value="' + response[0].comment + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">' + response[0].comment + '</textarea>';
        s += '</td>';
        s += '</tr><tr>';
        s += '<td >';
        s += '<br>email_1:&nbsp;<input class="crm"  type="text" id="email_1" value="' + response[0].email_1 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>email_2:&nbsp;<input  class="crm" type="text" id="email_2" value="' + response[0].email_2 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        //s += '<br>instagram:&nbsp;<input type="text" id="instagram" value="' + response[0].instagram + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].instagram + '\')">instagram</span>:&nbsp;<input  class="crm" type="text" id="instagram" value="' + response[0].instagram + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].facebook + '\')">facebook</span>:&nbsp;<input  class="crm" type="text" id="facebook" value="' + response[0].facebook + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].facebook_messenger + '\')">facebook_messenger</span>:&nbsp;<input  class="crm" type="text" id="facebook_messenger" value="' + response[0].facebook_messenger + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].linkedin + '\')">linkedin</span>:&nbsp;<input class="crm"  type="text" id="linkedin" value="' + response[0].linkedin + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].xing + '\')">xing</span>:&nbsp;<input  class="crm" type="text" id="xing" value="' + response[0].xing + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '</td><td >';
        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].web_1 + '\')">web_1</span>:&nbsp;<input  class="crm" type="text" id="web_1" value="' + response[0].web_1 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br><span style="color: blue;" onclick="con.handleLink(\'' + response[0].web_2 + '\')">web_2</span>:&nbsp;<input class="crm"  type="text" id="web_2" value="' + response[0].web_2 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>viber:&nbsp;<input class="crm"  type="text" id="viber" value="' + response[0].viber + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>whatsapp:&nbsp;<input  class="crm" type="text" id="whatsapp" value="' + response[0].whatsapp + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>telegram:&nbsp;<input class="crm"  type="text" id="telegram" value="' + response[0].telegram + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>phone_1:&nbsp;<input  class="crm" type="text" id="phone_1" value="' + response[0].phone_1 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>phone_2:&nbsp;<input  class="crm" type="text" id="phone_2" value="' + response[0].phone_2 + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '</td>';
        s += '</tr><tr>';
        s += '<td colspan=2>';
        s += '<br>du_sie:&nbsp;<input class="crm"  type="text" id="du_sie" value="' + response[0].du_sie + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>gender:&nbsp;<input class="crm"  type="text" id="gender" value="' + response[0].gender + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>salutation:&nbsp;<input class="crm" type="text" id="salutation" value="' + response[0].salutation + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>postal_address:&nbsp;<input class="crm"  type="text" id="postal_address" value="' + response[0].postal_address + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '<br>source:&nbsp;<input class="crm"  type="text" id="source" value="' + response[0].source + '" onchange="con.updateOneContactField(this, ' + response[0].ID + ')" onkeyup="con.updateOneContactField(this, ' + response[0].ID + ')">';
        s += '</td>';
        s += '</tr>';
        s += '</table>';


        return s;
    };

    this.numberToDate = function (x) {
        //alert(x);
        return new Date(parseInt(x));
    };

    this.dateToNumber = function (x) {
        return new Date(x).getTime();
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

    //  

    this.initCrmNavigation = function () {
        util.setHtmlInDiv("mainNavi", nav.getCrmNavigation());
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