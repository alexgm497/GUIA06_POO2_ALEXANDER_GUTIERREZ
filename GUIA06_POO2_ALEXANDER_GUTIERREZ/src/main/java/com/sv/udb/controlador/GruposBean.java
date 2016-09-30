/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.GruposFacadeLocal;
import com.sv.udb.modelo.Grupos;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alexander
 */
@Named(value = "gruposBean")
@RequestScoped
public class GruposBean implements Serializable {
    static Logger log = Logger.getLogger(GruposBean.class.getName());
    @EJB
    private GruposFacadeLocal FCDEGrupos;
    private List<Grupos> grupos;
    private Grupos objeGrupos;
    private boolean guardar;

    /**
     * Creates a new instance of GruposBean
     */
    public GruposBean() {
    }

    public List<Grupos> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupos> listGrup) {
        this.grupos = listGrup;
    }

    public Grupos getObjeGrupos() {
        return objeGrupos;
    }

    public void setObjeGrupos(Grupos objeGrupos) {
        this.objeGrupos = objeGrupos;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    @PostConstruct
    public void init() {
        this.objeGrupos = new Grupos();
        this.guardar = true;
        this.consTodo();
        this.limpForm();
    }

    public void limpForm() {
        this.objeGrupos = new Grupos();
        this.guardar = true;
    }

    public void consTodo() {
        try {
            this.grupos = FCDEGrupos.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página        
        try {
            FCDEGrupos.create(objeGrupos);
            this.guardar = true;
            this.grupos.add(objeGrupos);
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
            this.objeGrupos = FCDEGrupos.find(codiAlum);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void elim(int codiAlum) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try {
            this.objeGrupos = FCDEGrupos.find(codiAlum);
            FCDEGrupos.remove(objeGrupos);
            this.grupos.remove(objeGrupos);
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
            FCDEGrupos.edit(objeGrupos);
            this.grupos = FCDEGrupos.findAll();
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos actualizados');");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO actualizados');");
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }
}
