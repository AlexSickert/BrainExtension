
/*
 * objects to create the JSON objects that get sent to the server
 * ToDo: make inherited code so that we have less code repetition
 */

/*
 * request for performing the login
 */
function BeRequest(user, password, value) {
    this.m = 'l';  // module
    this.u = user;
    this.p = password;
    this.v = value;
    this.e = ""; //error status
    this.s = ""; //session 
}

/*
 * get the initial tree and all other fragments of a tree
 */
function TreeRequest(session, parent, content) {
    this.m = 't';  // module
    this.u = "";
    this.v = "";
    this.s = session; //session 
    this.p = parent;
    this.c = content; // contetn we want to get
    this.r = null;
}

function TreeSearchRequest(session, parent, content, searchString) {
    this.m = 't';  // module
    this.u = "";
    this.v = "";
    this.s = session; //session 
    this.p = parent;
    this.search = searchString;
    this.c = content; // contetn we want to get
    this.r = null;
}

/*
 * object to get the list of child nodes of a specific node
 * @param {type} session
 * @param {type} idNode
 * @param {type} idHtml
 * @returns {ChildNodesRequest}
 */
function ChildNodesRequest(session, idNode, idHtml) {
    this.m = 't';  // module
    this.u = "";
    this.v = "";
    this.s = session; //session 
    this.idNode = idNode;
    this.idHtml = idHtml; // contetn we want to get    
//    this.p = parent;
    this.c = "childNodes"; // contetn we want to get
    this.r = null;
}

function AnchestorsRequest(session, idNode, idHtml) {
    this.m = 't';  // module
    this.u = "";
    this.v = "";
    this.s = session; //session 
    this.idNode = idNode;
    this.idHtml = idHtml; // contetn we want to get    
//    this.p = parent;
    this.c = "parentNodes"; // contetn we want to get
    this.r = null;
}

function MakeChildAndNodesRequest(session, idNode, idHtml) {
    this.m = 't';  // module
    this.u = "";
    this.v = "";
    this.s = session; //session 
    this.idNode = idNode;
    this.idHtml = idHtml; // contetn we want to get    
//    this.p = parent;
    this.c = "makeChildNodes"; // contetn we want to get
    this.r = null;
}

/*
 * get one specific node
 */
function NodeRequest(session, idNode, idHtml, version) {
    this.m = 't';  // module t is tree
    this.s = session; //session 
    this.c = "node"; // contetn we want to get
    this.e = ''; // error state
    this.v = version; // for edit - then true, else false BOOLEAN value! 
    this.idNode = idNode;
    this.idHtml = idHtml; // contetn we want to get
    this.r = null; // response we want to get back
}

/*
 * update one node
 */
function NodeUpdateRequest(session, idNode, idHtml, field, value) {
    this.m = 't';  // module t is tree
    this.s = session; //session 
    this.c = "update"; // contetn we want to get
    this.e = ''; // error state
    this.v = true; // for edit - then true, else false BOOLEAN value! 
    this.idNode = idNode;
    this.idHtml = idHtml; // contetn we want to get
    this.r = null; // response we want to get back
    this.f = field;
    this.value = value;
}

/*
 * get files of one node
 */
function NodeFilesRequest(session, idNode, idHtml) {
    this.m = 't';  // module t is tree
    this.s = session; //session 
    this.c = "file-list"; // contetn we want to get
    this.d = ""; // contetn we want to get
    this.i = ""; // info 
    this.e = ''; // error state
    this.value = ""; // for edit - then true, else false
    this.idNode = idNode;
    this.idHtml = idHtml; // contetn we want to get
    this.f = null; // field we update    
    this.r = null; // response we want to get back
    this.fileNames = null;
    this.filesHashMap = null;
}

// con, session, idNode, idHtml, fileNameArray, fileHashMap
function NodeFileUploadRequest(session, idNode, idHtml, fileNameArray, fileHashMap) {
    this.m = 't';  // module t is tree
    this.s = session; //session 
    this.c = "file-upload"; // contetn we want to get
    this.d = "field"; // contetn we want to get
    this.i = ""; // info 
    this.e = ''; // error state
//    this.value = value; // for edit - then true, else false
    this.idNode = idNode;
    this.idHtml = idHtml; // contetn we want to get
//    this.f = field; // field we update    
    this.r = null; // response we want to get back
    this.fileNames = fileNameArray;
    this.filesHashMap = fileHashMap;
}

function NodeMoveOrLinkRequest(session, idNode, idParent, action, idHtml) {
    this.m = 't';  // module t is tree
    this.s = session; //session 
    this.c = "move-or-link"; // contetn we want to get
    this.d = action; // contetn or action we want to get
    this.i = ""; // info 
    this.e = ''; // error state
    this.idNode = idNode;
    this.idParent = idParent;
    this.idHtml = idHtml; // the id of the html element and node we need to refresh
}



function CrmLoadRequest(session, action) {
    this.m = 'c';  // module t is tree
    this.session = session; //session 
    this.action = action; // contetn or action we want to get

    this.fieldName = "";
    this.fieldValue = "";

    this.rowId = "";

    this.filter_1_field = "";
    this.filter_1_value = "";
    this.filter_1_logic = "";

    this.filter_2_field = "";
    this.filter_2_value = "";
    this.filter_2_logic = "";

    this.filter_3_field = "";
    this.filter_3_value = "";
    this.filter_3_logic = "";

    this.filter_4_field = "";
    this.filter_4_value = "";
    this.filter_4_logic = "";

    this.result = [];

    this.newData = "";

    this.hasError = false;
    this.errorDescription = "";

}



