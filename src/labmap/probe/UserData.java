/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package labmap.probe;

/**
 *
 * @author Pratyush
 */
public class UserData {
    String username, system, systemType;
    Boolean loggedIn;
    public UserData(String username, String system, String systemType, Boolean loggedIn) {
        this.username = username;
        this.system = system;
        this.systemType = systemType;
        this.loggedIn = loggedIn;
    }
}
