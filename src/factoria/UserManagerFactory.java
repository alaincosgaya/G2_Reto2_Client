/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import interfaces.UserInterface;

/**
 *
 * @author Idoia Ormaetxea
 */
public class UserManagerFactory {

    public static UserInterface getUserManagerImplementation() {
        UserInterface userManager = new UserManagerImplementation();
        return userManager;

    }
}