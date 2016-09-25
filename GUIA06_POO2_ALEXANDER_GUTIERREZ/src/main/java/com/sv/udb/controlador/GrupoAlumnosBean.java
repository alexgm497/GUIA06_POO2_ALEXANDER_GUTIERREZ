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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alexander
 */
@Named(value = "grupoAlumnosBean")
@ViewScoped
public class GrupoAlumnosBean implements Serializable {

    @EJB
    private GruposAlumnosFacadeLocal FCDEGruposAlumnos;
    private GruposAlumnos objeGrupAlum;
    private List<GruposAlumnos> alumGrup;
    private boolean guardar;

    public GruposAlumnos getObjeGrupAlum() {
        return objeGrupAlum;
    }

    public void setObjeGrupAlum(GruposAlumnos objeGrupAlum) {
        this.objeGrupAlum = objeGrupAlum;
    }

    public List<GruposAlumnos> getAlumGrup() {
        return alumGrup;
    }

    public void setAlumGrup(List<GruposAlumnos> alumGrup) {
        this.alumGrup = alumGrup;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    /**
     * Creates a new instance of GrupoAlumnosBean
     */
    public GrupoAlumnosBean() {

    }

    @PostConstruct
    public void init() {
        this.limpForm();
        this.consTodo();
    }

    public void consTodo() {
        try {
            this.alumGrup = FCDEGruposAlumnos.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    public void limpForm() {
        this.objeGrupAlum = new GruposAlumnos();
        this.guardar = true;
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página        
        try {
            FCDEGruposAlumnos.create(objeGrupAlum);
            this.guardar = true;
            this.alumGrup.add(objeGrupAlum);
            limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos guardados');");
        } catch (Exception ex) {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_ERRO','Mensaje', 'Datos NO guardados');");
        }
    }

    public void cons(int codiAlumGrup) {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try {
            this.objeGrupAlum = FCDEGruposAlumnos.find(codiAlumGrup);
            this.guardar = false;
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
    }

    public void elim(int codiGrupAlum) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try {
            this.objeGrupAlum = FCDEGruposAlumnos.find(codiGrupAlum);
            FCDEGruposAlumnos.remove(objeGrupAlum);
            this.alumGrup.remove(objeGrupAlum);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos eliminados');");
        } catch (Exception ex) {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO eliminados');");
        }
    }

    public void actu() {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try {
            FCDEGruposAlumnos.edit(objeGrupAlum);
            this.alumGrup = FCDEGruposAlumnos.findAll();
            this.objeGrupAlum = new GruposAlumnos();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos actualizados');");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO actualizados');");
            ex.printStackTrace();
        }
    }
}
