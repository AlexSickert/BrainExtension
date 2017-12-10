function BeMdParser() {

    var bulletFlag = false;
    var thisLineHasBullet = false;

    this.parse = function (s, id) {

        var content = s;
        var contentArr = content.split("\n");
        var arrayLength = contentArr.length;
        var tmpString;
        var tstString;

        var enumFlag = false;


        for (var i = 0; i < arrayLength; i++) {

            this.thisLineHasBullet = false;

            contentArr[i] = contentArr[i].trim();

            //now check for first character
            tmpString = contentArr[i];



            if (tmpString.length > 0) {
                tstString = tmpString.substring(0, 1);

                // if the first chatacter is not a ' then we need to convert links
                if (tstString !== "'") {
                    tmpString = this.convertUrl(tmpString, id);
                }


                switch (tstString) {
                    case "!":
                        //code block
                        tmpString = this.exclamationMark(tmpString);
                        break;
                    case "#":
                        //code block
                        // if the flag for enumeration was off  then add the opening tag
                        break;
                    case "-":
                        // if the flag for bullet  was off  then add the opening tag
                        //code block
                        if (contentArr[i].trim().substring(0, 3) == "---") {
                            // ignore this
                        } else {
                            this.thisLineHasBullet = true;
                            tmpString = this.bulletPoint(tmpString);
                        }
                        break;
                    case "'":
                        // this is the case if we completely need to ignore any processing  - it is the "  '  "   sign
                        // if the flag for bullet or enumeration  or table etc. was on then add the closing tag
                        //code block
                        break;
                    case "|":
                        // this is the case if we have a table. this is the start of a table. then we first split alongside the | and then later within the strings we split by whitespace
                        //code block
                        break;
                    default:
                    // if the flag for bullet or enumeration was on then add the closing tag
                    // then set flags off
                    //default code block
                }

                // now detect HREFs in line
                // we split the sring along the whitespaces. then we check if there is .com etc. and then we test if it starts with www o http and depending on this
                // we add the missing piece and wrap it in a href or a javascript function that makes it opening in new window using the <span> tag


                // now detect Tables in line with | sign
                // we split 



                contentArr[i] = tmpString;
            }
            // if the flags are still switched on, then we need to close now. 
            // if we are in certain modes then we need to leave these mode poentially

            if (this.thisLineHasBullet === false && this.bulletFlag === true) {
                contentArr[i] = contentArr[i] + "</ul";
            }

            if (this.thisLineHasBullet === false) {
                contentArr[i] = contentArr[i] + "</br>";
            }


        }

        content = "";

        for (var i = 0; i < arrayLength; i++) {
//            content += contentArr[i] + "<br>";
            content += contentArr[i];
        }

        return content;

    };


    /*
     * this is for bullet point processing                * 
     */
    this.bulletPoint = function (s) {
        var ret = "";
        if (this.bulletFlag === false) {
            ret += "<ul>";
            this.bulletFlag = true;
        }
        ret = "<li>" + s.substring(1, s.length) + "</li>";
        return ret;
    };

    this.convertUrl = function (text, id) {
        text = text.trim();
        textArr = text.split(" ");
        ret = "";
        for (i = 0; i < textArr.length; i++) {
            ret += this.urlify(textArr[i], id) + " ";
        }
        return ret;
    };

    /**
     * takes one string with no whitepsace and converts to link if necessary
     * @param {type} text
     * @returns {String} */
    this.urlify = function (text, id) {
        //var urlRegex = /[a-zA-Z0-9_]+\.[a-zA-Z0-9_]+/g;
        //var urlRegex = /(https?:\/\/[^\s]+)/g;


        if (text.match(/[a-zA-Z0-9_]+\.[a-zA-Z0-9_]+/g)) {
            if (text.match(/^(http|https):\/\//g)) {
                return "<a href='" + text + "' target='_blank'>" + text + "</a>";
            } else if (text.match(/^www\./g)) {
                return "<a href='http:\/\/" + text + "' target='_blank'>" + text + "</a>";
            } else {
                // check if it is an image
                if (text.toLowerCase().endsWith(".jpg") || text.toLowerCase().endsWith(".png") || text.toLowerCase().endsWith(".gif")) {
                    
                    // http://localhost:9000/fileDownload?s=uxBuD1CYZBNp5IS4TI0BzmXAk&c=tree&id=9499&name=management-alternative-structure.html
                    var url = globalBaseUrl + "fileDownload?s=" + globalSession + "&c=tree&id=" + id + "&name=" + text;
                    return "<a href='" + url.trim() + "' ><img src='" + url.trim() + "' alt='" + text + "'  width='200'></a>";

                } else {
                    return "<a href='http:\/\/www." + text + "' target='_blank'>" + text + "</a>";
                }
            }
        } else {
            return text;
        }
    };



    /*
     * This is for parsing exclamation marks at the beginning of the line
     */

    this.exclamationMark = function (s) {

        var tstString;
        var ret = "";

        // maybe better to user the <span> tag ???? 
        if (s.length > 2) {
            tstString = s.substring(0, 3);
            if (tstString === "!!!") {
                ret = "<b><u>" + s.substring(3, s.length) + "</u></b>";
                return ret;
            }
        }

        if (s.length > 1) {
            tstString = s.substring(0, 2);
            if (tstString === "!!") {
                ret = "<b>" + s.substring(2, s.length) + "</b>";
                return ret;
            }
        }

        if (s.length > 0) {
            tstString = s.substring(0, 1);
            if (tstString === "!") {
                ret = "<u>" + s.substring(1, s.length) + "</u>";
                return ret;
            }
        }



        return ret;

    };
}


