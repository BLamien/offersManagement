/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.users;

import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfa.daoImp.users.UtilisateurDaoImp;
import pfa.model.users.Utilisateur;
import pfa.service.journalisation.ServiceJournalisation;
import pfa.service.util.UserSession;

/**
 *
 * @author karim
 */
@Service("serviceUser")
public class ServiceUtilisateur {
    //**************************************************************************************//       
    //**************************************************************************************//
    @Autowired(required = true)
    private UtilisateurDaoImp utilisateurDao;
    //**************************************************************************************//       
    //**************************************************************************************//
    private List<Utilisateur> allUtilisateurs;
    
    @Autowired(required = true)
    private ServiceJournalisation serviceLog;
    
    @Autowired(required = true)
    private UserSession serviceSession;
    //**************************************************************************************//       
    //**************************************************************************************//
    @PostConstruct
    private void init(){
     refreshListeUtilisateurs();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public Utilisateur findByMatricule(String matricule){
        if(matricule != null && matricule.length() > 0 && daoInjected())
            return this.utilisateurDao.findByMatricule(matricule);
        else
            return null;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public void refreshListeUtilisateurs(){
        if(daoInjected())
        this.allUtilisateurs = utilisateurDao.findAll();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    //**************************************************************************************//
    public boolean addUtilisateur(Utilisateur utilisateur){
        if(utilisateur != null && daoInjected() && utilisateur.getProfil() != null && utilisateurDao.add(utilisateur)){
        refreshListeUtilisateurs();
        String desc = "Ajout de l'utilisateur numéro " + utilisateur.getMatricule();
        this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_ADMIn,desc);
            return true;
        }
        else{
            return false;
        }
     
    }
    //**************************************************************************************//
    //**************************************************************************************//
    public boolean updateUtilisateur(Utilisateur utilisateur){
        if(utilisateur != null && daoInjected() && utilisateurDao.update(utilisateur)){
            refreshListeUtilisateurs();
        String desc = "Modification de l'utilisateur numéro " + utilisateur.getMatricule();
        this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_ADMIn,desc);
            return true;
        }
        else{
            return false;
        }
     
    }
    //**************************************************************************************//    
    //**************************************************************************************//       
    //**************************************************************************************//
    public List<Utilisateur> getAllUtilisateurs() {
        return allUtilisateurs;
    }
    //**************************************************************************************//       
    //**************************************************************************************//    
    private boolean daoInjected(){
        return (this.utilisateurDao != null);
    }
    //**************************************************************************************//       
    public String changePasswordValidator(Utilisateur utilisateur,String password,String newPassword,String repeatNewPassword){
        if(utilisateur == null)
            return "Une erreur s'est produite";
        if(!password.equals(utilisateur.getPassword()))
            return "Veuillez vérifier votre mot de passe actuel";
        if(newPassword.length() < 8 || repeatNewPassword.length() <8)
            return "Votre mot de passe doit contenir au minimum 8 caractéres";
        if(!newPassword.equals(repeatNewPassword))
            return "Vérifier le nouveau mot de passe";
        return null;
    }
    //**************************************************************************************//
    public String valideUser(Utilisateur utilisateur){
        if(utilisateur == null)
            return "Une erreur s'est produite";
        if(utilisateur.getProfil() == null)
            return "Veuillez choisir un profil";
        return null;
    }
    //**************************************************************************************//
    public Utilisateur loginUtilisateur(String login,String password){
     if(login.length() <= 0 || password.length() <= 0)
         return null;
     if(daoInjected()){
         System.out.println("Dao kayn");
         return utilisateurDao.findByLoginPassword(login, password);
     }
     return null;
    }
    //**************************************************************************************//
    
}
