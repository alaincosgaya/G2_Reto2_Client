/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoria;

import clases.ContratoEntity;
import interfaces.ContratoInterface;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;
import restful.ContratoClient;

/**
 *
 * @author 2dam
 */
public class ContratoManagerImplementation implements ContratoInterface {

    private static ContratoClient webClient;

    public ContratoManagerImplementation() {
        this.webClient = new ContratoClient();
    }

    @Override
    public Collection<ContratoEntity> getAllContratos() {
        List<ContratoEntity> contratos = null;
        contratos = webClient.findAll(new GenericType<List<ContratoEntity>>() {
        });
        return contratos;
    }

    @Override
    public Collection<ContratoEntity> getContratosGranjero(String idGranjero) {
        List<ContratoEntity> contratos = null;
        contratos = webClient.contratosGranjero(new GenericType<List<ContratoEntity>>() {
        }, idGranjero);
        return contratos;
    }

    @Override
    public ContratoEntity getContrato(String idContrato) {
        ContratoEntity contrato = null;
        contrato = webClient.find(new GenericType<ContratoEntity>() {
        }, idContrato);
        return contrato;
    }

    @Override
    public Collection<ContratoEntity> getContratosTrabajador(String idTrabajador) {
        List<ContratoEntity> contratos = null;
        contratos = webClient.contratosTrabajador(new GenericType<List<ContratoEntity>>() {
        }, idTrabajador);
        return contratos;
    }

    @Override
    public Collection<ContratoEntity> getContratosGranja(String idGranja) {
        List<ContratoEntity> contratos = null;
        contratos = webClient.contratosGranja(new GenericType<List<ContratoEntity>>() {
        }, idGranja);
        return contratos;
    }

    @Override
    public void despedirTrabajador(String idTrabajador, String idGranja) {
        webClient.despedirTrabajador(idTrabajador, idGranja);
    }

    @Override
    public ContratoEntity cambiarSueldo(String idTrabajador, String idGranja, String salario) {
        ContratoEntity contrato = null;
        contrato = webClient.cambiarSueldo(new GenericType<ContratoEntity>() {
        }, idTrabajador, idGranja, salario);
        return contrato;
    }

    @Override
    public void contratarTrabajador(ContratoEntity contrato) {
        webClient.create_XML(contrato);

    }
    
}
