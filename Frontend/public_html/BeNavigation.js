

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

        return s;
    };

}