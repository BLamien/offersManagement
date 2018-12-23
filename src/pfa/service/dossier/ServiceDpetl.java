/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.dossier;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfa.daoImp.dossier.DpetlDaoImp;
import pfa.model.dossier.Dpetl;
import pfa.model.users.Utilisateur;
import pfa.service.journalisation.ServiceJournalisation;
import pfa.service.users.ServiceUtilisateur;
import pfa.service.util.UserSession;

/**
 *
 * @author karim
 */
@Service("serviceDpetl")
public class ServiceDpetl {
//**************************************************************************************//       
//**************************************************************************************// 
private List<Dpetl> allDpetls;
//**************************************************************************************// 
@Autowired(required = true)
private DpetlDaoImp daoDpetl;
//**************************************************************************************// 
@Autowired(required = true)
private ServiceJournalisation serviceLog;

    
@Autowired(required = true)
private UserSession serviceSession;
//**************************************************************************************//       
//**************************************************************************************// 
@PostConstruct
private void init(){
this.refreshListeDpetls();
}
//**************************************************************************************//       
//**************************************************************************************// 
public boolean addDpetl(Dpetl dpetl){
    if(dpetl != null && daoDpetl != null && daoDpetl.add(dpetl)){
        this.refreshListeDpetls();
        String desc = "Ajout du DPETL numéro " + dpetl.getIdDpetl();
        this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_ADMIn,desc);
        return true;
    }
    else{
        return false;
    }
}
//**************************************************************************************// 
//**************************************************************************************// 
public boolean updateDpetl(Dpetl dpetl){
    if(dpetl != null && daoDpetl != null && daoDpetl.update(dpetl)){
        this.refreshListeDpetls();
        String desc = "Modification du DPETL numéro " + dpetl.getIdDpetl();
        this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_ADMIn,desc);
        return true;
    }
    else{
        return false;
    }
}
//**************************************************************************************// 
//**************************************************************************************// 
public boolean deleteDpetl(Dpetl dpetl){
    if(dpetl != null && daoDpetl != null && daoDpetl.delete(dpetl)){
        this.refreshListeDpetls();
        String desc = "Suppression du DPETL numéro " + dpetl.getIdDpetl();
        this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_DEL,serviceLog.TYPE_ADMIn,desc);
        return true;
    }
    else{
        return false;
    }  
}
//**************************************************************************************// 
//**************************************************************************************// 
public List<Dpetl> searchDpetl(List<Dpetl> all,String keyword){
    List<Dpetl> alFounded;
    if(all != null && all.size() > 0){
        alFounded = new ArrayList<Dpetl>();
        // parcourir la liste
        for(Dpetl dpetl:all){
         // si l'objet courant n'est pas null
         if(dpetl != null && dpetl.getLibele() != null && dpetl.getLibele().length() > 0 && dpetl.getLibele().toLowerCase().contains(keyword.toLowerCase())){
            alFounded.add(dpetl);
         }   
        }
        // verifier si un element est trouvé
        if(alFounded.size() > 0)
            return alFounded;
        else
            return null;
    }
    else{
        return null;
    }
}
//**************************************************************************************// 
//**************************************************************************************// 
public List<Dpetl> alllDpetls() {
    return this.allDpetls;
}
//**************************************************************************************// 
//**************************************************************************************// 
private void refreshListeDpetls(){
    if(daoDpetl != null)
    this.allDpetls = daoDpetl.findAll();
}
//**************************************************************************************// 
//**************************************************************************************// 
    public ArrayList<Dpetl> allDpetls(){
     ArrayList<Dpetl> all = null;
     if(daoDpetl != null){
         all = this.daoDpetl.findAll();
     }
     return all;
    }
}
