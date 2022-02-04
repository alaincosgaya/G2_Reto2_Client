/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.GranjeroEntity;
import interfaces.GranjeroInterface;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;
import restful.GranjeroClient;

/**
 *
 * @author Alejandro
 */
public class GranjeroManagerImplementation implements GranjeroInterface {

    private static  GranjeroClient webClient;

    public GranjeroManagerImplementation() {
        this.webClient = new GranjeroClient();
    }

    @Override
    public Collection<GranjeroEntity> getAllGranjeros() {
        Collection<GranjeroEntity> granjeros = null;
        granjeros = webClient.findAll_XML(new GenericType<List<GranjeroEntity>>() {
        });
        return granjeros;
    }

}
