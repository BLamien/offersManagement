/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.beans.admin.dpetl;

import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import pfa.model.dossier.Dpetl;
import pfa.service.dossier.ServiceDpetl;

/**
 *
 * @author karim
 */
@ManagedBean("adminDpetl")
@javax.faces.bean.ManagedBean(name = "adminDpetl")
@RequestScoped
public class DpetlAdmin {
//**************************************************************************************//
//**************************************************************************************//
@Autowired(required = true)
private ServiceDpetl serviceDpetl;
private List<Dpetl> allDpetls,filtredDpetls;
private Dpetl dpetlAdd,dpetlUpdate;
private final String UPDATE_VIEW = "/admin/dpetl/update";
private FacesMessage message;
//**************************************************************************************//
//**************************************************************************************// 
//**************************************************************************************//
//**************************************************************************************// 
@PostConstruct
private void init(){
    this.refreshAllDpetls();
    this.dpetlAdd = new Dpetl();
    this.dpetlUpdate = new Dpetl();
}
//**************************************************************************************//
//**************************************************************************************//
public void update(){
    System.out.println("OKok");
    String resultat = dpetlValide(this.dpetlUpdate);
    if(resultat == null && serviceDpetlInjected() && serviceDpetl.updateDpetl(dpetlUpdate)){
     this.refreshAllDpetls();
     resultat = "Modifications enregistrées !";   
    }
    sendResponseMessage(resultat);
}
//**************************************************************************************//
//**************************************************************************************//
public void add(){
    String resultat = dpetlValide(this.dpetlAdd);
    if(resultat == null && serviceDpetlInjected() && serviceDpetl.addDpetl(dpetlAdd)){
     this.refreshAllDpetls();
     resultat = "Dpetl enregistré!";   
    }
    sendResponseMessage(resultat);   
}
//**************************************************************************************//
//**************************************************************************************//
public String toUpdateView(){
    return  UPDATE_VIEW;
}
//**************************************************************************************//
//**************************************************************************************//

















//**************************************************************************************//
//********************************    PRIVATE METHODES    ******************************//
//**************************************************************************************//
private boolean serviceDpetlInjected(){
    return (this.serviceDpetl != null);
}
//**************************************************************************************//
private void refreshAllDpetls(){
    if(serviceDpetlInjected()){
        this.allDpetls = this.serviceDpetl.alllDpetls();
    }
}
//**************************************************************************************//
private void sendResponseMessage(String response){
        this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, response,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
}
//**************************************************************************************//
private String dpetlValide(Dpetl dpetl){

    if(dpetl == null)
      return  "Erreur données incorrectes";
    if(dpetl.getLibele().length() < 5)
      return  "La description comporte au minimum 5 caractéres !";      
    return null;
}
//**************************************************************************************//
//********************************    GETTERS setters     ******************************//
//**************************************************************************************//
public List<Dpetl> getAllDpetls() {
    return allDpetls;
}

public void setAllDpetls(List<Dpetl> allDpetls) {
    this.allDpetls = allDpetls;
}

public List<Dpetl> getFiltredDpetls() {
    return filtredDpetls;
}

public void setFiltredDpetls(List<Dpetl> filtredDpetls) {
    this.filtredDpetls = filtredDpetls;
}

public Dpetl getDpetlAdd() {
    return dpetlAdd;
}

public void setDpetlAdd(Dpetl dpetlAdd) {
    this.dpetlAdd = dpetlAdd;
}

public Dpetl getDpetlUpdate() {
    return dpetlUpdate;
}

public void setDpetlUpdate(Dpetl dpetlUpdate) {
    this.dpetlUpdate = dpetlUpdate;
}
//**************************************************************************************//
//**************************************************************************************//
//**************************************************************************************//
}
