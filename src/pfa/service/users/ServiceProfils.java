/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.users;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pfa.daoImp.users.DroitAccesDaoImp;
import pfa.daoImp.users.ProfilDaoImp;
import pfa.model.users.DroitAcces;
import pfa.model.users.Profil;
import pfa.model.users.Utilisateur;
import pfa.service.journalisation.ServiceJournalisation;
import pfa.service.util.UserSession;

/**
 *
 * @author karim
 */
@Service("serviceProfil")
public class ServiceProfils {
    private final int ADMIN_PROFIL_ID = 2;
    //**************************************************************************************//       
    //**************************************************************************************//
    private List<Profil> allProfils;
    private List<DroitAcces> allDroitAcces = null;
    
    @Autowired(required = true)
    private ServiceJournalisation serviceLog;
    
    @Autowired(required = true)
    private UserSession serviceSession;
    //**************************************************************************************//       
    //**************************************************************************************//
    @PostConstruct
    private void init(){
        // initialiser la liste des profils
        this.refreshListeProfils();
        // remplir la liste des droitAcces
        if(daoDroitAcces != null)
        this.allDroitAcces = this.daoDroitAcces.findAll();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    @Autowired(required = true)
    private ProfilDaoImp daoProfil;
    
    @Autowired(required = true)
    private DroitAccesDaoImp daoDroitAcces;
    //**************************************************************************************//       
    //**************************************************************************************//
    public List<Profil> getAllProfils() {
        return daoProfil.findAll();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public boolean addProfil(Profil profil){
        if(profil != null && this.daoProfil.add(profil)){
           // actualiser la liste des profils
           this.refreshListeProfils();
           String desc = "Ajout du Profil numéro " + profil.getIdProfil();
           this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_ADMIn,desc);
           return true;
        }
        else{
           return false;
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public boolean updateProfil(Profil profil){
        if(profil != null && this.daoProfil.update(profil)){
           // actualiser la liste des profils
           this.refreshListeProfils();
            String desc = "Modification du Profil numéro " + profil.getIdProfil();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_ADMIn,desc);
           return true;
        }
        else{
           return false;
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public Profil getProfil(int id){
        return (this.daoProfil != null) ? this.daoProfil.findById(id) : null;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private void refreshListeProfils(){
        if(daoProfil != null)
        this.allProfils = daoProfil.findAll();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public Profil getProfilFromList(int id){
        Profil p = null;
        for(Profil profil : this.allProfils){
            if(profil.getIdProfil() == id){
                p = profil;
                break;
            }
        }
        return p;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public List<DroitAcces> getAllDroitAcces() {
        return allDroitAcces;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public DroitAcces getDroitAccesFromList(List<DroitAcces> all,int id){
        DroitAcces d = null;
        for(DroitAcces droit : all){
            if(droit.getIdDroit() == id){
                d = droit;
                break;
            }
        }
        return d;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public Profil getAdminProfil(){
        return getProfil(ADMIN_PROFIL_ID);
    }
    //**************************************************************************************//       
    //**************************************************************************************//
}
