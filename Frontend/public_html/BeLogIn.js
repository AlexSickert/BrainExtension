/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function BeLogIn() {

    this.getLogIn = function () {

        var s = "";
        s += 'Login<br>';
        
        s += '<form action="" method="post"> <br>';
        
        s += '<table style="width: 300px;">';
        
        s += '<tr>';
        
        s += '<td>User:';
        s += '</td>';
        s += '<td><input type="text" name="user" id="user" value="">';
        s += '</td>';
        
        s += '</tr>';        
        s += '<tr>';
        
        s += '<td>Password:';
        s += '</td>';
        s += '<td><input type="password" name="password" id="password" value="">';
        s += '</td>';
        
        s += '</tr>';
        s += '<tr>';
        
        s += '<td>';
        s += '</td>';
        s += '<td><input type="button" onClick="con.submitLogin()" value="submit">';
        s += '</td>';
        
        s += '</tr>';
        s += '</table>';
        
        s += "</form>";
        return s;
    };
}