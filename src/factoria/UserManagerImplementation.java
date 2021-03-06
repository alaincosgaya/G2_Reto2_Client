/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import cifrado.CifradoClient;
import clases.UserEntity;
import interfaces.UserInterface;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import restful.UserClient;

/**
 *
 * @author 2dam
 */
public class UserManagerImplementation implements UserInterface {
    private static UserClient webClient;

    public UserManagerImplementation() {
        this.webClient = new UserClient();
    }

    @Override
    public void crearUsuario(UserEntity user) {
        System.out.println("Creando usuario(SERVER)");
        user.setPassword(CifradoClient.encrypt(user.getPassword()));
        webClient.create(user);
    
    }
    
    @Override
    public UserEntity getUsuarioPorLogin(String username, String password) {
        List<UserEntity> user = null;
        user = webClient.validatePassword(new GenericType<List<UserEntity>>(){}, username, password);
        if(!user.isEmpty()){
            return user.get(0);
        }
        return null;
    }
    
    @Override
    public UserEntity findClient(UserEntity user) {
        UserEntity users = null;
        webClient.find(new GenericType<UserEntity>() {
        }, user.getId().toString());
        return users;
    }
    
    @Override
    public Collection<UserEntity> findAllUsers() {
        List<UserEntity> users = null;
        users = webClient.findAll(new GenericType<List<UserEntity>>() {
        });
        return users;
    }

    @Override
    public UserEntity findClientValidatePasswd(UserEntity user) {
       List<UserEntity> users = null;
       String username = user.getUsername();
       user.setPassword(CifradoClient.encrypt(user.getPassword()));
       String passwd = user.getPassword();
       users = webClient.validatePassword(new GenericType<List<UserEntity>>() {
        }, username, passwd);
       return users.get(0);
    }

    @Override
    public void actualizarCon(UserEntity user) {
        String con = CifradoClient.encrypt(user.getPassword());
        System.out.println(con);
        user.setPassword(con);
        //String id = user.getId().toString();
        webClient.edit(user, user.getId().toString());
    }
    
    @Override
    public void editPasswd(UserEntity user, String password){
        String con = CifradoClient.encrypt(password);
        System.out.println(con);
       
      
        webClient.editPasswd(user, password);
    }
    
    @Override
    public void resetPasswd(UserEntity user) {
        
        webClient.resetContra(new GenericType<UserEntity>() {
        }, user.getEmail());
    
    }
}
