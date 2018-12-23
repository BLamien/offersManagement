/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.beans.admin.users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import pfa.model.users.Profil;
import pfa.model.users.Sexe;
import pfa.model.users.SituationFamiliale;
import pfa.model.users.Utilisateur;
import pfa.service.users.ServiceProfils;
import pfa.service.users.ServiceUtilisateur;

/**
 *
 * @author karim
 */
@ManagedBean("adminUsers")
@javax.faces.bean.ManagedBean(name = "adminUsers")
@RequestScoped
public class UtilisateurAdmin {
    //**************************************************************************************//
    //**************************************************************************************//<Uti
    // service des utilisateur
    @Autowired(required = true)
    private ServiceUtilisateur serviceUsers;
    // service des profils 
    @Autowired(required = true)
    private ServiceProfils serviceProfils;
    // list des utilisateurs
    private List<Utilisateur> allUtilisateurs;
    private List<Utilisateur> filtredUtilisateurs;
    private Utilisateur utilisateur = new Utilisateur();
    private Utilisateur userAdd = new Utilisateur();
    // list des profils
    private List<Profil> allProfils;
    // selected profil
    private Profil profil;
    // la situation familiale
    private SituationFamiliale celibataire = SituationFamiliale.CELIBATAIRE,mariee=SituationFamiliale.MARIEE;
    // le sexe :
    private Sexe homme = Sexe.HOMME,femme = Sexe.FEMME;
    // facesmessage
    private FacesMessage message;
    // url view update
    private final String UPDATE_VIEW = "modifierutilisateur";
    //**************************************************************************************//
    //**************************************************************************************//
    public UtilisateurAdmin(){
        this.init();
    }
    @PostConstruct
    private void init(){
           this.refreshUsers();
           this.refreshProfils();
    }
    //**************************************************************************************//
    //*******************************       METHODES       *********************************//
    //**************************************************************************************// 
    public void toUpdateView() throws IOException{
             FacesContext.getCurrentInstance().getExternalContext().redirect(UPDATE_VIEW);
    }
    //**************************************************************************************//     
    public void updateUser(){
       if(this.utilisateur != null && serviceUsersInjected() && serviceUsers.updateUtilisateur(this.utilisateur)){
           this.refreshUsers();
           sendResponseMessage("L'utilisateur " + utilisateur.getNom() +" est modifié(e) correctement");
           this.setUtilisateur(new Utilisateur());
       }
   }
    //**************************************************************************************//     
    public void addUser(){
        System.err.println("--------------------add user---------------");
    if(this.userAdd != null && serviceUsersInjected() && serviceUsers.addUtilisateur(userAdd)){
            this.refreshUsers();
            sendResponseMessage("L'utilisateur " + userAdd.getNom() +" est ajouté(e) correctement");
            this.setUserAdd(new Utilisateur());
        }
    }
    //**************************************************************************************//     

    
    //**************************************************************************************//
    //*******************************  Private METHODES  *********************************//
    //**************************************************************************************//
    private void sendResponseMessage(String response){
        this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, response,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
   }
    //**************************************************************************************//    
    private boolean serviceUsersInjected(){
        return (this.serviceUsers != null);
    }
    //**************************************************************************************//
    private boolean serviceProfilsInjected(){
        return (this.serviceProfils != null);
    }
    //**************************************************************************************//  
    private void refreshUsers(){
        if(serviceUsersInjected()){
            this.allUtilisateurs = serviceUsers.getAllUtilisateurs();
        }
    }
    //**************************************************************************************//  
    private void refreshProfils(){
        if(serviceProfilsInjected()){
            this.allProfils = serviceProfils.getAllProfils();
        }
    }
    //**************************************************************************************//  
    
    
    //**************************************************************************************//
    //*******************************  GETTERS && SETTERS  *********************************//
    //**************************************************************************************//
    public List<Utilisateur> getAllUtilisateurs() {
        return allUtilisateurs;
    }

    public void setAllUtilisateurs(List<Utilisateur> allUtilisateurs) {
        this.allUtilisateurs = allUtilisateurs;
    }

    public Sexe getHomme() {
        return homme;
    }

    public void setHomme(Sexe homme) {
        this.homme = homme;
    }

    public Sexe getFemme() {
        return femme;
    }

    public void setFemme(Sexe femme) {
        this.femme = femme;
    }

    public List<Profil> getAllProfils() {
        return allProfils;
    }

    public void setAllProfils() {
        if(serviceProfilsInjected())
        this.allProfils = serviceProfils.getAllProfils();
    }

    public SituationFamiliale getCelibataire() {
        return celibataire;
    }

    public void setCelibataire(SituationFamiliale celibataire) {
        this.celibataire = celibataire;
    }

    public SituationFamiliale getMariee() {
        return mariee;
    }

    public void setMariee(SituationFamiliale mariee) {
        this.mariee = mariee;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Utilisateur getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(Utilisateur userAdd) {
        this.userAdd = userAdd;
    }
  

    public List<Utilisateur> getFiltredUtilisateurs() {
        return filtredUtilisateurs;
    }

    public void setFiltredUtilisateurs(List<Utilisateur> filtredUtilisateurs) {
        this.filtredUtilisateurs = filtredUtilisateurs;
    }
    //**************************************************************************************//
    //**************************************************************************************//
}
