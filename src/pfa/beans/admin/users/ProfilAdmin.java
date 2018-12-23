/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.beans.admin.users;


import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import pfa.model.users.DroitAcces;
import pfa.model.users.Profil;
import pfa.service.users.ServiceProfils;

/**
 *
 * @author karim
 */
@ManagedBean("adminProfil")
@javax.faces.bean.ManagedBean(name = "adminProfil")
@RequestScoped
public class ProfilAdmin {
    //**************************************************************************************//
    //**************************************************************************************//
    // service des profils
    @Autowired(required = true)
    private ServiceProfils serviceProfils;
    // liste des profils
    private List<Profil> allProfils;
    // liste de recherche
    private List<Profil> filtredProfils;
    private List<DroitAcces> allDroitAcceses;
    private List<DroitAcces> selectedDroitAcceses;
    //profil d'ajout
    private Profil profil;
    private Profil updateProfil;
    private Profil addProfilObject;
    private FacesMessage message;
    private String test;
    private final String UPDATE_VIEW = "/admin/profil/update";
    //**************************************************************************************//
    //**************************************************************************************//    
    public ProfilAdmin() {

    } 
    //**************************************************************************************//
    //**************************************************************************************//
    @PostConstruct
    private void init(){
        this.addProfilObject = new Profil();
        this.profil = null;
        // remplir la liste apres l'injection
        this.refreshProfils();
        this.allDroitAcceses = this.serviceProfils.getAllDroitAcces();
    }
    //**************************************************************************************//
    //**************************************************************************************//
    public void add(){
      String result  = profilValide(addProfilObject);
      if(result == null && serviceInjected() && serviceProfils.addProfil(addProfilObject) ){
          result = "Le profil "+addProfilObject.getLibele()+" est enregistré correctement";
          this.addProfilObject = new Profil();
          this.refreshProfils();
      }
       sendResponseMessage(result);
    }
    //**************************************************************************************//
    public void update(){
          String result  = profilValide(this.updateProfil);
          if(result == null && serviceInjected() && serviceProfils.updateProfil(this.updateProfil) ){        
              result = "Le profil "+ updateProfil.getLibele() +" est enregistré correctement";    
          }
          sendResponseMessage(result);  
    }
    //**************************************************************************************//
    public String toViewUpdate(){
        return UPDATE_VIEW;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //**************************************************************************************//
    //*******************************  Private METHODES  *********************************//
    //**************************************************************************************//
    private void sendResponseMessage(String response){
        this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, response,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    //**************************************************************************************//
    private boolean serviceInjected(){
        return (this.serviceProfils != null);
    }
    //**************************************************************************************//
    private void refreshProfils(){
        if(serviceInjected()){
           this.allProfils = this.serviceProfils.getAllProfils();
        }
    }
    //**************************************************************************************//
    private String profilValide(Profil profil){
        if(profil == null)
            return "Erreur";
        if(profil.getLibele() == null || profil.getLibele().length() < 3)
            return "Le nom d'un profil doit contenir au minimum 3 caractéres";
        if(profil.getDroitAcceses() == null || profil.getDroitAcceses().size() < 1)
            return "Un profil doit avoir au minimun un droit d'acces";
        return null;
    }
    //**************************************************************************************//
    
    


    //**************************************************************************************//
    //*******************************  GETTERS && SETTERS  *********************************//
    //**************************************************************************************//
    public List<Profil> getAllProfils() {
        return allProfils;
    }

    public void setAllProfils(List<Profil> allProfils) {
        this.allProfils = allProfils;
    }

    public List<Profil> getFiltredProfils() {
        return filtredProfils;
    }

    public void setFiltredProfils(List<Profil> filtredProfils) {
        this.filtredProfils = filtredProfils;
    }

    public Profil getAddProfilObject() {
        return addProfilObject;
    }

    public void setAddProfilObject(Profil addProfilObject) {
        this.addProfilObject = addProfilObject;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public List<DroitAcces> getAllDroitAcceses() {
        return allDroitAcceses;
    }

    public void setAllDroitAcceses(List<DroitAcces> allDroitAcceses) {
        this.allDroitAcceses = allDroitAcceses;
    }

    public List<DroitAcces> getSelectedDroitAcceses() {
        return this.selectedDroitAcceses;
    }

    public void setSelectedDroitAcceses(List<DroitAcces> selectedDroitAcceses) {
        this.selectedDroitAcceses = selectedDroitAcceses;
    }

    public Profil getUpdateProfil() {
        return updateProfil;
    }

    public void setUpdateProfil(Profil updateProfil) {
        this.updateProfil = updateProfil;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }


    
    
    
}
