/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.GruposAlumnosFacadeLocal;
import com.sv.udb.modelo.GruposAlumnos;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Alexander
 */
@Named(value = "grupoAlumnosBean")
@RequestScoped
public class GrupoAlumnosBean implements Serializable{
    
    @EJB
    private GruposAlumnosFacadeLocal FCDEGrupos;
    private GruposAlumnos objeGrupAlum;
    private List<GruposAlumnos> alumGrup;
    private boolean guardar;

    
    public GruposAlumnos getObjeGrupAlum() {
        return objeGrupAlum;
    }

    public void setObjeGrupAlum(GruposAlumnos objeGrupAlum) {
        this.objeGrupAlum = objeGrupAlum;
    }

    public List<GruposAlumnos> getGrupos() {
        return alumGrup;
    }

    public boolean isGuardar() {
        return guardar;
    }
    
    /**
     * Creates a new instance of GrupoAlumnosBean
     */
    public GrupoAlumnosBean() {
    }
    
}
