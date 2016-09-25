/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.ProfesoresFacadeLocal;
import com.sv.udb.modelo.Profesores;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alexander
 */
@Named(value = "profesoresBean")
@RequestScoped
public class ProfesoresBean {

    @EJB
    private ProfesoresFacadeLocal FCDEProfesores;
    private List<Profesores> profesores;
    private Profesores objeProfesores;
    private boolean guardar;

    /**
     * Creates a new instance of ProfesoresBean
     */
    public ProfesoresBean() {
    }

    public List<Profesores> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesores> profesores) {
        this.profesores = profesores;
    }

    public Profesores getObjeProfesores() {
        return objeProfesores;
    }

    public void setObjeProfesores(Profesores objeProfesores) {
        this.objeProfesores = objeProfesores;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    @PostConstruct
    public void init() {
        this.objeProfesores = new Profesores();
        this.guardar = true;
        this.consTodo();
        this.limpForm();
    }

    public void limpForm() {
        this.objeProfesores = new Profesores();
        this.guardar = true;
    }

    public void consTodo() {
        try {
            this.profesores = FCDEProfesores.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página        
        try {
            FCDEProfesores.create(objeProfesores);
            this.guardar = true;
            this.profesores.add(objeProfesores);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos guardados');");
        } catch (Exception ex) {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_ERRO','Mensaje', 'Datos NO guardados');");
        }
    }

    public void cons(int codiAlum) {
        try {
            this.objeProfesores = FCDEProfesores.find(codiAlum);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void elim(int codiAlum) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try {
            this.objeProfesores = FCDEProfesores.find(codiAlum);
            FCDEProfesores.remove(objeProfesores);
            this.profesores.remove(objeProfesores);
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
            FCDEProfesores.edit(objeProfesores);
            this.profesores = FCDEProfesores.findAll();
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos actualizados');");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO actualizados');");
            ex.printStackTrace();
        }
    }
}
