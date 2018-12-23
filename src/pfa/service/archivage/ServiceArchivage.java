/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.archivage;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfa.dao.archivage.ArchivageDao;
import pfa.daoImp.archivage.ArchivageDaoImp;
import pfa.daoImp.archivage.ColonneArchivageDaoImp;
import pfa.daoImp.archivage.LigneArchivageDaoImp;
import pfa.daoImp.archivage.RangeArchivageDaoImp;
import pfa.model.archivage.Archive;
import pfa.model.archivage.ColonneArchivage;
import pfa.model.archivage.LigneArchivage;
import pfa.model.archivage.RangeArchivage;
import pfa.model.dossier.Dossier;
import pfa.model.users.Utilisateur;
import pfa.service.dossier.ServiceAvis;
import pfa.service.journalisation.ServiceJournalisation;
import pfa.service.util.UserSession;

/**
 *
 * @author karim
 */
@Service("serviceArchivage")
public class ServiceArchivage {
    //**************************************************************************************//       
    //**************************************************************************************//
    @Autowired(required = false)
    private RangeArchivageDaoImp daoRange;
    
    @Autowired(required = true)
    private LigneArchivageDaoImp daoLigneArchivage;
    
    @Autowired(required = true)
    private ColonneArchivageDaoImp daoColonneArchivage;
    
    @Autowired(required = true)
    private ServiceJournalisation serviceLog;
    
    @Autowired(required = true)
    private UserSession serviceSession;
    
    @Autowired(required = true)
    private ArchivageDaoImp archivageDao;
    @Autowired(required = true)
    private ServiceAvis serviceAvis;
    //**************************************************************************************//       
    //**************************************************************************************//
    private List<RangeArchivage> allRanges = null;
    //**************************************************************************************//       
    @PostConstruct
    private void init(){
        this.refreshListeRanges();
    }
    //**************************************************************************************//     
    public List<RangeArchivage> getAllRanges() {    
        // a supprimer 
        //this.refreshListeRanges();
        return allRanges;
    }
    //**************************************************************************************//   
    
    
    
    
    
    
    
    
    
    //**************************************************************************************//       
    //**************************************************************************************//
    //****************************  RANGE METHODES  *************************************//       
    //**************************************************************************************//
    //**************************************************************************************//       
    private void refreshListeRanges(){
     if(daoInjected()){
       this.allRanges = daoRange.findAll();
     }
    }
    //**************************************************************************************//       
    private boolean daoInjected(){
        return (this.daoRange != null);
    }
    //**************************************************************************************//       
    public boolean add(RangeArchivage rangeArchivage){
        if(daoInjected()  && rangeArchivage != null && daoRange.add(rangeArchivage)){
        this.refreshListeRanges();
        String desc = "Ajout range d'archivage numéro  " + rangeArchivage.getIdRange();
        this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_ADMIn,desc);
        return true;
        }
        else{
            return false;
        }
    }
    //**************************************************************************************//       
    public boolean update(RangeArchivage rangeArchivage){
        if(daoInjected()  && rangeArchivage != null && daoRange.update(rangeArchivage)){
            this.refreshListeRanges();
            String desc = "Modification range d'archivage numéro  " + rangeArchivage.getIdRange();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_ADMIn,desc);
            return true;
        }
        else{
            return false;
        }
    }
    //**************************************************************************************//       
    public RangeArchivage getRangeFromList(List<RangeArchivage> allRanges,int idRange){
      RangeArchivage rangeArchivage = null;
      for(RangeArchivage ra : allRanges){
          if(ra.getIdRange() == idRange){
              rangeArchivage = ra;
              break;
          }
      }
     return rangeArchivage;
    }
    //**************************************************************************************//    
    public String rangeValide(RangeArchivage rangeArchivage){
        if(rangeArchivage == null)
            return "Données incorrectes";
        return null;
    }
    //**************************************************************************************//      
    
    
    
    
    //**************************************************************************************//       
    //**************************************************************************************//
    //****************************  LIGNE METHODES    *************************************//       
    //**************************************************************************************//
    //**************************************************************************************//       
    public LigneArchivage getLigneArchivageById(int id){
        if(daoLigneArchivage != null){
            return daoLigneArchivage.findById(id);
        }
        else{
            return null;
        }
    }
    //**************************************************************************************//       
    public boolean updateLigneArchivage(LigneArchivage ligneArchivage){
        if(ligneArchivage != null && daoLigneArchivage != null && daoLigneArchivage.update(ligneArchivage)){
            this.refreshListeRanges();
            String desc = "Modification de la ligne d'archivage numéro  " + ligneArchivage.getIdLigne();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_ADMIn,desc);
            return true;
        }
            return false;
    }
    //**************************************************************************************//   
    public boolean addLigneArchivage(LigneArchivage ligneArchivage){
        if(ligneArchivage != null && daoLigneArchivage != null && daoLigneArchivage.add(ligneArchivage)){
            this.refreshListeRanges();
            String desc = "Ajout de la ligne d'archivage numéro  " + ligneArchivage.getIdLigne();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_ADMIn,desc);
            return true;
        }
            return false;
    }
    //**************************************************************************************//   
    public String ligneArchivageValide(LigneArchivage ligne){
     if(ligne == null)
         return  "Erreur données incorrectes";
     if(ligne.getRangeArchivage() == null)
         return  "Erreur, veuillez choisir une rangé"; 
     if(String.valueOf(ligne.getNumLigne()).length()  == 0)
         return "Veuillez saisir le numéro de la rangé";
     return null;
    }
    //**************************************************************************************//   
    public LigneArchivage getLigneArchivageFromList(List<LigneArchivage> allLigneArchivages,int id){
     LigneArchivage ligne = null;
        System.out.println("DKHLNA N9LBO 3LA LIGNE");
     if(allLigneArchivages != null && allLigneArchivages.size() > 0){
         for(LigneArchivage l : allLigneArchivages){
             if(l.getIdLigne() == id){
                 ligne = l;
                 break;
             }
         }
     }   
     return ligne;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    //****************************  Colonnes METHODES    *************************************//       
    //**************************************************************************************//
    //**************************************************************************************//       
    public boolean updateColonneArchivage(ColonneArchivage colonneArchivage){
        if((colonneArchivage != null && daoColonneArchivage != null && daoColonneArchivage.update(colonneArchivage))){
            this.refreshListeRanges();
            String desc = "Modification de la cellule d'archivage numéro  " + colonneArchivage.getIdColonne();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_ADMIn,desc);
            return true;
        }
        else{
            return false;
        }
       
    }
    public boolean addColonneArchivage(ColonneArchivage colonneArchivage){
        if(colonneArchivage != null && daoColonneArchivage != null && daoColonneArchivage.add(colonneArchivage)){
            this.refreshListeRanges();
            String desc = "Ajout de la cellule d'archivage numéro  " + colonneArchivage.getIdColonne();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_ADMIn,desc);
            return true;
        }
            return false;
    }
    public ColonneArchivage getColonneArchivage(int id){
        return (daoColonneArchivage != null) ? daoColonneArchivage.findById(id) : null;
    }
    //**************************************************************************************//   
    
    
    
    
    //**************************************************************************************//       
    //**************************************************************************************//
    public boolean archiver(Dossier dossier,ColonneArchivage colonne,String description){
        if(dossier == null || colonne == null || archivageDao == null)
            return false;
        if(dossier.getAvis().getLibele().equals(serviceAvis.getAvisEnCours().getLibele()))
            return false;   
        Archive archive = new Archive(colonne, dossier);
        archive.setDateArchivage(new Date());
        archive.setHeureCreation(new Date());
        archive.setDescription(description);
        if(archivageDao.add(archive)){
            String desc = "Archivage du dossier  " + dossier.getIdDossier();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_DOSSIER,desc);
            return true;
        }
            return false;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
}
