/*

This simulates the server response

*/

function DummyServer() {

    //   var obj = JSON.parse(text);  
    // JSON.stringify({"key":"value"});
    /*
    var text = '{ "employees" : [' +
 '{ "firstName":"John" , "lastName":"Doe" },' +
 '{ "firstName":"Anna" , "lastName":"Smith" },' +
 '{ "firstName":"Peter" , "lastName":"Jones" } ]}';
    */

    var dataAccess;
    var requestObj;

    this.request = function(access, jsonString) {
        dataAccess = access;
        requestObj = JSON.parse(jsonString);

        switch (requestObj.m) {
            case '':
                this.checkLogin(requestObj);
                break;


            default:
                document.write("???????????????")
        }




    }

    this.checkLogin = function(jsonString) {

    }


    /* ------------------------------- */

    this.login = function(obj) {
        
        var result = false;
        if(obj.u == 'aaa'){
            if(obj.p == 'bbb'){
                if(obj.v == 'ccc'){
                    result = true;
                }
                
            }
        }
        
        if(result){
            // send login to clinet
        }else{
            //refuse login
        }
    
    }


}