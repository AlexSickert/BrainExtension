package server;

import Log.Log;
import database.BusinessLayerData;

import java.util.ArrayList;

public class JsonRequestCrmHandler {


    BusinessLayerData bld = new BusinessLayerData();

    public JsonCrmRequest handle(JsonCrmRequest processObj) {

        // first check if session is valid
        if(Main.sessHan.checkSession(processObj.session)) {

            Log.log("info", "checking session - OK session is valid");

            // insert action
            if (processObj.action.equals("insert")) {
                //bld.insertCrmContact(processObj);

            }

            // load action
            if (processObj.action.equals("newData")) {
                ArrayList arl = bld.uploadCrmBulkContacts(processObj.newData);
                processObj.result = arl;
            }

            // load action
            if (processObj.action.equals("load")) {
                ArrayList arl = bld.loadCrmContacts(processObj);
                processObj.result = arl;
            }

            // loadOne
            if (processObj.action.equals("loadOne")) {
                ArrayList arl = bld.loadCrmOneContact(processObj.rowId);
                processObj.result = arl;
            }

            // update action
            if (processObj.action.equals("update")) {
                String errorStr = bld.updateCrmContact(processObj.rowId, processObj.fieldName, processObj.fieldValue);

                if(errorStr.length() > 0){
                    processObj.hasError = true;
                    processObj.errorDescription = errorStr;
                }else{
                    processObj.hasError = false;
                }


            }
        }else{
            // invalid session
            Log.log("info", "checking session - session not valid");
            Log.log("info", "session is: " + processObj.session);
        }

        return processObj;
    }

}
