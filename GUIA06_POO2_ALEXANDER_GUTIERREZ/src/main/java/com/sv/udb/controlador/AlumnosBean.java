/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.AlumnosFacadeLocal;
import com.sv.udb.modelo.Alumnos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author REGISTRO
 */
@Named(value = "alumnosBean")
@ViewScoped
public class AlumnosBean implements Serializable {
    static Logger log = Logger.getLogger(AlumnosBean.class.getName());
    @EJB
    private AlumnosFacadeLocal FCDEAlum;
    private List<Alumnos> alumnos = new ArrayList();
    private Alumnos objeAlum;
    private boolean guardar;

    public Alumnos getObjeAlum() {
        return objeAlum;
    }

    public void setObjeAlum(Alumnos objeAlum) {
        this.objeAlum = objeAlum;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Alumnos> getAlumnos() {
        return alumnos;
    }

    /**
     * Creates a new instance of AlumnosBean
     */
    public AlumnosBean() {

    }

    @PostConstruct
    public void init() {
        this.objeAlum = new Alumnos();
        this.guardar = true;
        this.consTodo();
        this.limpForm();
    }

    public void limpForm() {
        this.objeAlum = new Alumnos();
        this.guardar = true;
    }

    public void consTodo() {
        try {
            this.alumnos = FCDEAlum.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página        
        try {
            objeAlum.setNombAlum(null);
            FCDEAlum.create(objeAlum);
            this.guardar = true;
            this.alumnos.add(objeAlum);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos guardados');");
        } catch (Exception ex) {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_ERRO','Mensaje', 'Datos NO guardados');");
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void cons(int codiAlum) {
        try {
            this.objeAlum = FCDEAlum.find(codiAlum);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void elim(int codiAlum) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try {
            this.objeAlum = FCDEAlum.find(codiAlum);
            FCDEAlum.remove(objeAlum);
            this.alumnos.remove(objeAlum);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos eliminados');");
        } catch (Exception ex) {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO eliminados');");
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void actu() {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try {
            FCDEAlum.edit(objeAlum);
            this.alumnos = FCDEAlum.findAll();
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos actualizados');");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO actualizados');");
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }
}
