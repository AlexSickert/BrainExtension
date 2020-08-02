

function BeNavigation() {

    this.getNavigation = function() {
        
        var s = "";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"+\"  onclick=\"con.modifyFont('bigger')\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"-\"  onclick=\"con.modifyFont('smaller')\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"TREE\"  onclick=\"con.navigate('tree')\"  />";
        s += "<input type=\"text\" id=\"searchField\" name=\"searchField\" class=\"searchField\"  value=\"\"  onkeyup=\"con.navigate('searchLimited')\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"TREE SEARCH\"  onclick=\"con.navigate('search')\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"RECENT\"  onclick=\"con.navigate('recent')\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"Log\"  onclick=\"con.showLog()\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"CRM\"  onclick=\"con.showCrm()\"  />";

        return s;
    };
    
    
    this.getCrmNavigation = function() {
        
        var s = "";
        
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"+\"  onclick=\"con.modifyFont('bigger')\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"-\"  onclick=\"con.modifyFont('smaller')\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"BE\"  onclick=\"con.setBeNavigation()\"  />";
        //s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"CRM\"  onclick=\"con.showCrm()\"  />";
        s += "&nbsp;Search:&nbsp;";
        s += "<input type=\"text\" id=\"searchField\" name=\"searchField\" class=\"searchField\"  value=\"\"  onkeyup=\"con.crmSearch()\"  />";
        s += "&nbsp;category_tag:&nbsp;";
        s += "<input type=\"text\" id=\"crmCategory\" name=\"searchField\" class=\"searchField\"  value=\"\"  onkeyup=\"con.crmSearch()\"  />";
        s += "&nbsp;closeness:&nbsp;";
        s += "<input type=\"text\" id=\"crmCloseness\" name=\"searchField\" class=\"searchField\"  value=\"\"  onkeyup=\"con.crmSearch()\"  />";
        s += "&nbsp;watch_list:&nbsp;";
        s += "<input type=\"text\" id=\"crmWatchlist\" name=\"searchField\" class=\"searchField\"  value=\"\"  onkeyup=\"con.crmSearch()\"  />";
        s += "&nbsp;Sort by:&nbsp;";
        s += "<input type=\"text\" id=\"crmSort\" name=\"searchField\" class=\"searchField\"  value=\"\"  onkeyup=\"con.crmSearch()\"  />";
        s += "<input type=\"button\" id=\"ORG\" name=\"ORG\" class=\"naviButton\"  value=\"CRM\"  onclick=\"con.crmSearch()\"  />";

        return s;
    };

}