/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.GruposFacadeLocal;
import com.sv.udb.modelo.Grupos;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Alexander
 */
@Named(value = "gruposBean")
@RequestScoped
public class GruposBean implements Serializable{

    @EJB
    private GruposFacadeLocal FCDEGrupos;
    private List<Grupos> grupos;

    /**
     * Creates a new instance of GruposBean
     */
    public GruposBean() {
    }
    
    @PostConstruct
    public void init() {
        this.grupos = FCDEGrupos.findAll();
    }
}
