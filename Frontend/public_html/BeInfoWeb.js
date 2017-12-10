// JavaScript File

function BeInfoWeb() {

    this.getTreeRootNodes = function (response) {

        var s = "";
        var node = null;

        // response is an array and each element of the array is a hashmap
        var arrayLength = response.length;
        for (var i = 0; i < arrayLength; i++) {
            node = response[i];
            s += this.getNodeSmall(node['id'], node['title']);
        }
        return s;
    };

    // getTreeParentNodes
    this.getTreeParentNodes = function (response) {

        var s = "";
        var node = null;

        // response is an array and each element of the array is a hashmap
        var arrayLength = response.length;
        for (var i = 0; i < arrayLength; i++) {
            node = response[i];
            s = this.getBreadCrumbElement(node['id'], node['title']) + s;
        }
        return s;
    };


    this.getBreadCrumbElement = function (id, text) {

        var s = "";
        var n = Math.random().toString(36).substr(2, 5);

//        s += '<div id="' + n + "_" + id + '"  data-expanded="no" >';
//        s += '<div  onclick=con.expandNode("' + n + "_" + id + '") data-expanded="no" class="smallNodeTitle">';
//        s += "&#x203A;&nbsp;" + text;
//        s += '</div>';
//        s += '</div>';

        s += '<span id="' + n + "_" + id + '_parent" onclick=con.jumpToNode("' + id + '")  >';
        s += "&nbsp;&gt;&nbsp;" + text;
        s += '</span>';

        return s;
    };


    this.getNodeSmall = function (id, text) {

        var s = "";
        var n = Math.random().toString(36).substr(2, 5);

        s += '<div id="' + n + "_" + id + '"  data-expanded="no" >';
        s += '<div  onclick=con.expandNode("' + n + "_" + id + '") data-expanded="no" class="smallNodeTitle">';
        s += "&#x203A;&nbsp;" + text;
        s += '</div>';
        s += '</div>';
        return s;
    };


    this.getCollapsedNodeSmall = function (id, text) {

        var s = "";

        s += '<div  onclick=con.expandNode("' + id + '") data-expanded="no" class="smallNodeTitle">';
        s += "&#x203A;&nbsp;" + text;
        s += '</div>';

        return s;
    };

    // the big node will get inserted into the small node !!!
    this.getNodeBig = function (id, title, content, files, tags, children) {

        var util = new BeUtility();

        var ids = id.split("_");

        var s = "";
        s += "<table>";
        s += "<tr>";
        //s += "<td class='tdNarrow' >Parent</td>";
        s += "<td class='tdTitle' colspan='1' onclick=\"con.collapseNode('" + id + "', this)\" >" + title + "</td><td class='tdTitle' colspan=1 ><span class='breadcrumb' id='" + id + "_parents'>... </span></td>";
//        s += "<td onclick=con.editNode('" + id + "') >Edit, Move, Learn, Relation, Action</td>";
        s += "<td class='tdTitleButtons'>";
        s += this.getButton("EDIT", "onclick=con.editNode('" + id + "')");
        s += this.getButton("FORK", "onclick=con.forkNode('" + id + "')");
        s += this.getButton("MOVE", "onclick=con.moveNode('" + id + "')");
        s += this.getButton("LINK", "onclick=con.linkPaste('" + id + "')");

        s += "</td>";
        s += "</tr>";

        if (content !== null) {
            s += "<tr>";
            s += "<td colspan=3>" + util.parseMdToHtml(content, ids[1]) + "</td>";
            s += "</tr>";
        }
        // if (files !== null) {
        s += "<tr>";

//            s += '<td colspan=2><div id="' + id + '_filelist">' + files + '</div></td>';
        s += '<td colspan=2><div id="' + id + '_filelist">...</div></td>';
        s += "<td ><div id='" + id + "_relationships'>... </div></td>";
        s += "</tr>";
        // }

        s += "<tr>";
        s += "<td class='dividerSoft' colspan=3>&nbsp;</td>";
        s += "</tr>";


        // here needs to be javascript action for the children
        s += "<tr>";
//        s += "<td class='tdNarrow' onclick=con.makeChildren('" + id + "')>+</td>";
        s += "<td class='tdNarrow'>" + this.getButton("+", "onclick=con.makeChildren('" + id + "')") + "</td>";

        s += "<td colspan=2><div id='" + id + "_children'>children loading... </div></td>";

        s += "</tr>";
        s += "<tr>";
        s += "<td class='dividerBold' colspan=3>&nbsp;</td>";
        s += "</tr>";
        s += "</table>";

        return s;
    };

    this.getNodeFileList = function (fileArray, linkMap) {

        var name;
        var link;
        var html = "";

        for (var i = 0; i < fileArray.length; i++) {
            name = fileArray[i];
            link = linkMap[name];
            html += "<a href='" + link + "'>" + name + "</a></br>";

        }

        return html;

    }

    // the big node will get inserted into the small node !!!
    this.getNodeBigEdit = function (id, title, content, files, tags, children) {

        var s = "";
        s += "<table>";
        s += "<tr>";
        s += "<td><span class='breadcrumb' id='" + id + "_parents'>... </span></td>";
        s += "<td><textarea id='" + id + "_title" + "' onchange=con.saveNodeFragment(this) onkeyup=con.saveNodeFragment(this)  >" + title + "</textarea></td>";
        s += "<td>"
        s += this.getButton("OK", "onclick=con.editNodeFinished('" + id + "')");
        s += "</td>";
        s += "</tr>";

        if (content !== null) {
            s += "<tr>";
            s += "<td colspan=3><textarea class='contentEdit' id='" + id + "_content" + "'  onchange=con.saveNodeFragment(this)  onkeyup=con.saveNodeFragment(this) >" + content + "</textarea></td>";
            s += "</tr>";
        }

        /*
         * the file upload stuff
         */

        s += "<tr>";
        s += '<td colspan=3> <input type="file" id="' + id + '_files" name="photos[]" multiple/><button type="submit" onclick="con.uploadNodeFile(this, \'' + id + '_files\')" id="upload-button">Upload</button>  </td>';
        s += "</tr>";

        //  if (files !== null) {
        s += "<tr>";
//            s += '<td colspan=2><div id="' + id + '_filelist">' + files + '</div></td>';
        s += '<td colspan=2><div id="' + id + '_filelist">...</div></td>';
        s += "<td ><div id='" + id + "_relationships'>... </div></td>";
        s += "</tr>";
        //  }

//        if (tags !== null) {
//            s += "<tr>";
//            s += "<td colspan=3>" + tags + "</td>";
//            s += "</tr>";
//        }

        // here needs to be javascript action for the children
        s += "<tr>";
//        s += "<td>+</td>";
        s += "<td onclick=con.makeChildren('" + id + "')>+</td>";
        s += "<td colspan=2><div id='" + id + "_children'>children loading... </div></td>";
        s += "</tr>";

        s += "</table>";

        return s;
    };


    this.getButton = function (text, code) {

        var s;
        s = "<input type=\"button\" id=\"" + text + "\" name=\"" + text + "\" class=\"naviButton\"  value=\"" + text + "\"  onclick=\"" + code + "\"  />";

        return s;
    };


}



