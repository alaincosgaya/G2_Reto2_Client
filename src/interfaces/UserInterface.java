/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import factoria.*;
import clases.UserEntity;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface UserInterface {
    public void crearUsuario(UserEntity user);
    public UserEntity getUsuarioPorLogin(String username, String password);
    public UserEntity findClient(UserEntity user);
    public Collection<UserEntity> findAllUsers();
    public UserEntity findClientValidatePasswd(UserEntity user);
    public void actualizarCon(UserEntity user);
    public void resetPasswd(UserEntity user);
}
