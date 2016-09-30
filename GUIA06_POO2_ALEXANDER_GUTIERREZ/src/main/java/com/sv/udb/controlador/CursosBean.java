/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import static com.fasterxml.jackson.databind.util.ClassUtil.getRootCause;
import com.sv.udb.ejb.CursosFacadeLocal;
import com.sv.udb.modelo.Cursos;
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
@Named(value = "cursosBean")
@RequestScoped
public class CursosBean {
    static Logger log = Logger.getLogger(CursosBean.class.getName());
    @EJB
    private CursosFacadeLocal FCDECursos;
    private List<Cursos> cursos;
    private Cursos objeCursos;
    private boolean guardar;

    /**
     * Creates a new instance of CursosBean
     */
    public CursosBean() {
    }

    public List<Cursos> getCursos() {
        return cursos;
    }

    public void setCursos(List<Cursos> cursos) {
        this.cursos = cursos;
    }

    public Cursos getObjeCursos() {
        return objeCursos;
    }

    public void setObjeCursos(Cursos objeCursos) {
        this.objeCursos = objeCursos;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }

    @PostConstruct
    public void init() {
        this.limpForm();
        this.consTodo();
    }

    public void consTodo() {
        try {
            this.cursos = FCDECursos.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void limpForm() {
        this.objeCursos = new Cursos();
        this.guardar = true;
    }

    public void guar() {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página        
        try {
            FCDECursos.create(objeCursos);
            this.guardar = true;
            this.cursos.add(objeCursos);
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos guardados');");
        } catch (Exception ex) {
            ex.printStackTrace();
            ctx.execute("setMessage('MESS_ERRO','Mensaje', 'Datos NO guardados');");
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void cons(int codiCurs) {
        try {
            this.objeCursos = FCDECursos.find(codiCurs);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }

    public void elim(int codiCurs) {
        RequestContext ctx = RequestContext.getCurrentInstance();
        try {
            this.objeCursos = FCDECursos.find(codiCurs);
            FCDECursos.remove(objeCursos);
            this.cursos.remove(objeCursos);
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
            FCDECursos.edit(objeCursos);
            this.cursos = FCDECursos.findAll();
            this.limpForm();
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos actualizados');");
        } catch (Exception ex) {
            ctx.execute("setMessage('MESS_SUCC','Mensaje', 'Datos NO actualizados');");
            ex.printStackTrace();
            log.debug(getRootCause(ex).getMessage());
        }
    }
}
