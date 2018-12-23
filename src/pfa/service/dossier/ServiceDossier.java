/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.dossier;

import java.text.SimpleDateFormat;
import pfa.model.dossier.Phase;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pfa.dao.dossier.DossierLiaisonDao;
import pfa.daoImp.dossier.AvisDaoImp;
import pfa.daoImp.dossier.DossierCarrefourDaoImp;
import pfa.daoImp.dossier.DossierCpsControleDaoImp;
import pfa.daoImp.dossier.DossierCpsEtudeDaoImp;
import pfa.daoImp.dossier.DossierCpsTravauxDaoImp;
import pfa.daoImp.dossier.DossierLiaisonDaoImp;
import pfa.daoImp.dossier.DossierOaDaoImp;
import pfa.daoImp.dossier.DpetlDaoImp;
import pfa.daoImp.dossier.NatureDaoImp;
import pfa.daoImp.dossier.PhaseDaoImp;
import pfa.model.dossier.Avis;
import pfa.model.dossier.Dossier;
import pfa.model.dossier.DossierCarrefour;
import pfa.model.dossier.DossierCpsControle;
import pfa.model.dossier.DossierCpsEtude;
import pfa.model.dossier.DossierCpsTravaux;
import pfa.model.dossier.DossierLiaison;
import pfa.model.dossier.DossierOa;
import pfa.model.dossier.Dpetl;
import pfa.model.dossier.LettreAppro;
import pfa.model.dossier.Nature;
import pfa.model.users.Utilisateur;
import pfa.service.journalisation.ServiceJournalisation;
import pfa.service.users.ServiceProfils;
import pfa.service.util.UserSession;

/**
 *
 * @author karim
 */
//@Component("dossierS")
@Service("serviceDossier")
public class ServiceDossier {
    // dossier type
    public final int CPS_ETUDE_TYPE = 1;
    public final int CPS_TRAVAUX_TYPE = 2;
    public final int CPS_CONTROLE_TYPE = 3;
    public final int CARREFOUR_TYPE = 4;
    public final int LIAISON_TYPE = 5;
    public final int OA_TYPE = 6;
    // key de recherche
    //**************************************************************************************//       
    //**************************************************************************************//
    public final int KEY_DOSSIER = 1;
    public final int KEY_DATE_CREATION = 2;
    public final int KEY_DATE_SORTIE = 3;
    public final int KEY_FIN_DATE_SORTIE = 16;
    public final int KEY_REF_APPRO = 4;
    public final int KEY_PROGRAMMATION = 5;
    public final int KEY_DATE_ENTREE = 6;
    public final int KEY_FIN_DATE_ENTREE = 13;
    public final int KEY_AVIS = 7;
    public final int KEY_NATURE = 8;
    public final int KEY_DPETL = 9;
    public final int KEY_PHASE = 10;
    public final int KEY_ROUTE = 11;
    public final int KEY_BET=12;
    public final int KEY_COMMUNE = 14;
    public final int KEY_LONGUEUR = 15;
    //**************************************************************************************//       
    //**************************************************************************************//
    // les daos des dossiers
    @Qualifier("cpsEtudeDao")
    @Autowired(required = true)
    private DossierCpsEtudeDaoImp cpsEtudeDaoImp;
    @Autowired(required = true)
    private DossierCpsTravauxDaoImp cpsTravauxDaoImp;
    @Autowired(required = true)
    private DossierCpsControleDaoImp cpsControleDaoImp;
    @Autowired(required = true)
    private DossierCarrefourDaoImp carrefourDaoImp;
    @Autowired(required = true)
    private DossierLiaisonDaoImp liaisonDaoImp;
    @Autowired(required = true)
    private DossierOaDaoImp oaDaoImp;
    // dpetl dao
    @Autowired(required = true)
    private DpetlDaoImp dpetlDao;
    // Nature dao
    @Autowired(required = true)
    private NatureDaoImp natureDao;
    // Phase dao
    @Autowired(required = true)
    private PhaseDaoImp phaseDao;
    // Avis Sevice
    @Autowired(required=true)
    @Qualifier("serviceAvis")
    private ServiceAvis serviceAvis;
    // log service
    @Autowired(required = true)
    private ServiceJournalisation serviceLog; 
    @Autowired(required = true)
    @Qualifier("serviceSession")
    private UserSession serviceSession;
    @Autowired(required = true)
    private ServiceProfils serviceProfils;
    //**************************************************************************************//       
    //**************************************************************************************//  
    private ArrayList<DossierCarrefour> allDossierCarrefours;
    private ArrayList<DossierCpsControle> allDossierCpsControles;
    private ArrayList<DossierLiaison> allDossierLiaisons;
    private ArrayList<DossierOa> allDossierOas;
    private ArrayList<DossierCpsEtude> allDossierCpsEtudes;
    private ArrayList<DossierCpsTravaux> allDossierCpsTravauxs;
    private ArrayList<DossierCpsControle> alDossierCpsControles;
    //**************************************************************************************//       
    //**************************************************************************************//
    @PostConstruct
    public void init(){
    this.refreshAll();
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public Dossier getDossier(int id,int typeDossier){
      Dossier d = null;
      switch (typeDossier){
          case CARREFOUR_TYPE : d = carrefourDaoImp.findById(id);break;
          case OA_TYPE : d = oaDaoImp.findById(id);break;
          case LIAISON_TYPE : d = liaisonDaoImp.findById(id);break;
          case CPS_CONTROLE_TYPE : d = cpsControleDaoImp.findById(id);break;
          case CPS_ETUDE_TYPE : d=cpsEtudeDaoImp.findById(id);break;
          case CPS_TRAVAUX_TYPE : d = cpsTravauxDaoImp.findById(id);break;
      }
      return d;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public boolean addDossier(Dossier dossier){
        

        boolean b = false;
        if(dossier != null){
            // avis en cours
            dossier.setAvis(serviceAvis.getAvisEnCours());
            // date création
            dossier.setDateCreation(new Date());
            // ajouter le dossier<
            if(dossier instanceof DossierCpsEtude){
             b = this.cpsEtudeDaoImp.add((DossierCpsEtude)dossier);
            }
            else if(dossier instanceof DossierCpsTravaux){
             b = this.cpsTravauxDaoImp.add((DossierCpsTravaux)dossier);  
            }
            else if(dossier instanceof DossierCpsControle){
             b = this.cpsControleDaoImp.add((DossierCpsControle)dossier);  
            }
            else if(dossier instanceof DossierCarrefour){
             b = this.carrefourDaoImp.add((DossierCarrefour)dossier);
            }
            else if(dossier instanceof DossierLiaison){
             b = this.liaisonDaoImp.add((DossierLiaison)dossier);     
            }
            else if(dossier instanceof DossierOa){
             b = this.oaDaoImp.add((DossierOa)dossier); 
            } 
            // actualiser la liste apres l'operation
            if(b){
            this.refrechAfterOperation(dossier);
            // journaliser
            String desc = "Ajout du dossier numéro " + dossier.getIdDossier();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_DOSSIER,desc);
            }
        }
        return b;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public boolean updateDossier(Dossier dossier){
        boolean b = false;
        if(dossier != null){
            if(dossier instanceof DossierCpsEtude){
             b = this.cpsEtudeDaoImp.update((DossierCpsEtude)dossier);
            }
            else if(dossier instanceof DossierCpsTravaux){
             b = this.cpsTravauxDaoImp.update((DossierCpsTravaux)dossier);   
            }
            else if(dossier instanceof DossierCpsControle){
             b = this.cpsControleDaoImp.update((DossierCpsControle)dossier);      
            }
            else if(dossier instanceof DossierCarrefour){
             b = this.carrefourDaoImp.update((DossierCarrefour)dossier);     
            }
            else if(dossier instanceof DossierLiaison){
             b = this.liaisonDaoImp.update((DossierLiaison)dossier);       
            }
            else if(dossier instanceof DossierOa){
             b = this.oaDaoImp.update((DossierOa)dossier);    
            }
            if(b){
            refrechAfterOperation(dossier);
            // journaliser
            String desc = "Modification du dossier numéro " + dossier.getIdDossier();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_UPDATE,serviceLog.TYPE_DOSSIER,desc);
            }
        }
        return b;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public boolean addNote(Dossier dossier){
        if(this.updateDossier(dossier)){
            // journaliser
            String desc = "Ajout d'une Note pour le dossier numéro " + dossier.getIdDossier();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_ADD,serviceLog.TYPE_DOSSIER,desc);
            return true;}
        else{ return false;}
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public ArrayList<Dpetl> allDpetls(){
     ArrayList<Dpetl> all = null;
     if(this.dpetlDao != null){
         all = this.dpetlDao.findAll();
     }
     return all;
    }
    //**************************************************************************************//       
    //**************************************************************************************//   
    public ArrayList<Nature> allNatures(){
     ArrayList<Nature> all = null;
     if(natureDao != null){
         all = this.natureDao.findAll();
     }
     return all;
    }
    //**************************************************************************************//       
    //**************************************************************************************//  
    public ArrayList<Phase> allPhases(){
     ArrayList<Phase> all = null;
     if(phaseDao != null){
         all = this.phaseDao.findAll();
     }
     return all;
    }
    //**************************************************************************************//       
    //**************************************************************************************//   
    public List<Dossier> getDossiersOfType(int typeDossier){
    if(typeDossier == LIAISON_TYPE){
        return (List<Dossier>)(List<?>)allDossierLiaisons();
    }
    else if(typeDossier == CARREFOUR_TYPE){
        return (List<Dossier>)(List<?>)allDossierCarrefours();
    }
    else if(typeDossier == OA_TYPE){
        return (List<Dossier>)(List<?>)allDossierOas();
    }
    else if(typeDossier == CPS_ETUDE_TYPE){
        return (List<Dossier>)(List<?>)allDossierCpsEtudes();
    }
    else if(typeDossier == CPS_CONTROLE_TYPE){
         return (List<Dossier>)(List<?>)allDossierCpsControles();
    }
    else if(typeDossier == CPS_TRAVAUX_TYPE){
          return (List<Dossier>)(List<?>)allDossierCpsTravauxs();
    } 
    return null;
    }
    
    //**************************************************************************************//       
    //**************************************************************************************//  
    //*****************************   TOUS LES DOSSIERS ************************************//       
    //**************************************************************************************//  
    public ArrayList<DossierCarrefour> allDossierCarrefours(){
        return  this.allDossierCarrefours;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private void refreshListeDossierCarrefours(){
        if(carrefourDaoImp != null&& serviceSession.getUser() != null 
           && serviceSession.getUser().getProfil().getIdProfil().equals(serviceProfils.getAdminProfil().getIdProfil())){
            this.allDossierCarrefours =  carrefourDaoImp.findAll();
        }
        else if(carrefourDaoImp != null && serviceSession.getUser() != null  ){
             this.allDossierCarrefours =  carrefourDaoImp.findAll(serviceSession.getUser());
        }
 
    } 
    //**************************************************************************************//       
    //**************************************************************************************//
    public ArrayList<DossierLiaison> allDossierLiaisons(){
        return this.allDossierLiaisons;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private void refreshListeDossierLiaisons(){
        if(liaisonDaoImp != null ){
            this.allDossierLiaisons = liaisonDaoImp.findAll();
        }
        else if(liaisonDaoImp != null && serviceSession.getUser() != null ){
            this.allDossierLiaisons = liaisonDaoImp.findAll(serviceSession.getUser());
        }
    }/*
    private void refreshListeDossierLiaisons(){
     this.allDossierLiaisons = liaisonDaoImp.findAll(serviceSession.getUser());
    }*/
    /*private void refreshListeDossierLiaisons(){
    if(liaisonDaoImp != null && serviceSession.getUser() != null 
           && serviceSession.getUser().getProfil().getIdProfil().equals(serviceProfils.getAdminProfil().getIdProfil())){
            this.allDossierLiaisons = liaisonDaoImp.findAll();
        }
        else if(liaisonDaoImp != null && serviceSession.getUser() != null ){
            this.allDossierLiaisons = liaisonDaoImp.findAll(serviceSession.getUser());
        }
    }*/
    //**************************************************************************************//       
    //**************************************************************************************//  
    public ArrayList<DossierOa> allDossierOas(){
        return this.allDossierOas;
    }
    //**************************************************************************************//       
    //**************************************************************************************//   
    public void refreshListeDossierOas(){
        if(oaDaoImp != null && serviceSession.getUser() != null 
           && serviceSession.getUser().getProfil().getIdProfil().equals(serviceProfils.getAdminProfil().getIdProfil())){
            this.allDossierOas =  oaDaoImp.findAll();
        }
        else if(oaDaoImp != null && serviceSession.getUser() != null  ){
           this.allDossierOas =  oaDaoImp.findAll(serviceSession.getUser()); 
        }

    }
    //**************************************************************************************//       
    //**************************************************************************************//    
    public ArrayList<DossierCpsEtude> allDossierCpsEtudes(){
        return this.allDossierCpsEtudes;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public void refreshListeDossierCpsEtudes(){
        if(cpsEtudeDaoImp != null&& serviceSession.getUser() != null 
           && serviceSession.getUser().getProfil().getIdProfil().equals(serviceProfils.getAdminProfil().getIdProfil())){
            this.allDossierCpsEtudes =  cpsEtudeDaoImp.findAll();
        }
        else if(cpsEtudeDaoImp != null && serviceSession.getUser() != null){
            this.allDossierCpsEtudes =  cpsEtudeDaoImp.findAll(serviceSession.getUser());
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public ArrayList<DossierCpsTravaux> allDossierCpsTravauxs(){
        return this.allDossierCpsTravauxs;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public void refreshListeDossierCpsTravauxs(){
       if(cpsTravauxDaoImp != null && serviceSession.getUser() != null 
           && serviceSession.getUser().getProfil().getIdProfil().equals(serviceProfils.getAdminProfil().getIdProfil())){
            this.allDossierCpsTravauxs = cpsTravauxDaoImp.findAll();
        }
        else if( cpsTravauxDaoImp != null && serviceSession.getUser() != null ){
            this.allDossierCpsTravauxs = cpsTravauxDaoImp.findAll(serviceSession.getUser()); 
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public ArrayList<DossierCpsControle> allDossierCpsControles(){
        return this.alDossierCpsControles;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public void refreshListeDossierCpsControles(){
        if(cpsControleDaoImp != null && serviceSession.getUser() != null 
           && serviceSession.getUser().getProfil().getIdProfil().equals(serviceProfils.getAdminProfil().getIdProfil())){
            this.alDossierCpsControles =  cpsControleDaoImp.findAll();
        }
        else if(  cpsControleDaoImp != null && serviceSession.getUser() != null ){
           this.alDossierCpsControles =  cpsControleDaoImp.findAll(serviceSession.getUser()); 
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private void refrechAfterOperation(Dossier dossier){
        if(dossier != null){
            if(dossier instanceof DossierCpsEtude){
             this.refreshListeDossierCpsEtudes();
            }
            else if(dossier instanceof DossierCpsTravaux){
             this.refreshListeDossierCpsTravauxs();
            }
            else if(dossier instanceof DossierCpsControle){
             this.refreshListeDossierCpsControles();
            }
            else if(dossier instanceof DossierCarrefour){
             this.refreshListeDossierCarrefours();
            }
            else if(dossier instanceof DossierLiaison){    
             this.refreshListeDossierLiaisons();
            }
            else if(dossier instanceof DossierOa){
             this.refreshListeDossierOas();
            }  
        }    
    }
    //**************************************************************************************//  
    private void refreshAll(){
    this.refreshListeDossierCarrefours();
    this.refreshListeDossierCpsControles();
    this.refreshListeDossierCpsEtudes();
    this.refreshListeDossierCpsTravauxs();
    this.refreshListeDossierLiaisons();
    this.refreshListeDossierOas();
    }
    
    
    
    //**************************************************************************************//  
    //*****************************   Approuve un dossier            ***********************//    
    //**************************************************************************************//     
    public boolean approuve(Dossier dossier,LettreAppro lettreAppro,String refAppro){
       boolean b = false;
       if( dossier != null && lettreAppro != null && serviceAvis.getAvisApprouve() != null){
           lettreAppro.setDossier(dossier);
           dossier.setLettreAppro(lettreAppro);
           dossier.setAvis(serviceAvis.getAvisApprouve());
           if(refAppro != null && refAppro.length() > 0)
               dossier.setRefAppro(refAppro);
           b = this.updateDossier(dossier);
           if(b){
           refrechAfterOperation(dossier);
            // journaliser
            String desc = "Approuvation du dossier numéro " + dossier.getIdDossier();
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_APPROUVATION,serviceLog.TYPE_DOSSIER,desc);
           }
       }
       return b;
    }
    //**************************************************************************************//       
    //**************************************************************************************//     
    
    
    

    //**************************************************************************************//       
    //**************************************************************************************//  
    //*****************************   Rejeter un dossier            ***********************//    
    //**************************************************************************************//     
    public boolean rejeter(Dossier dossier){
       boolean b = false;
       if( dossier != null && serviceAvis.getAvisNonApprouve() != null ){
           dossier.setAvis(serviceAvis.getAvisNonApprouve());
           b = this.updateDossier(dossier);
           if(b){
           refrechAfterOperation(dossier);
            // journaliser
            String desc = "Le dossier numéro " + dossier.getIdDossier()+" est rejeté";
            this.serviceLog.journaliser(serviceSession.getUser(),serviceLog.OP_APPROUVATION,serviceLog.TYPE_DOSSIER,desc);
           }
       }
       return b;
    }
    //**************************************************************************************//       
    //**************************************************************************************//     
    
    
    
    //**************************************************************************************//       
    //**************************************************************************************//  
    //*****************************   RECHERCHE AVANCEE DES DOSSIERS ***********************//    
    //**************************************************************************************// 
    private boolean keyExit(HashMap<Integer,String> hm,int keyType){
        return hm.containsKey(keyType) ;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private boolean searchByDossier(Dossier dossier,HashMap<Integer,String>  hm){
            if((keyExit(hm,KEY_DOSSIER) || keyExit(hm,KEY_COMMUNE)) && dossier.getDossier() != null){
                String data = dossier.getDossier().toLowerCase();
                return containsKeyword(data, hm,KEY_DOSSIER);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    //**************************************************************************************//       
    //**************************************************************************************// 
    private boolean searchByRefAppro(Dossier dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_REF_APPRO)  && dossier.getRefAppro() != null){
                String data = dossier.getRefAppro().toLowerCase();
                return containsKeyword(data, hm,KEY_REF_APPRO);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private boolean searchByProgrammation(Dossier dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_PROGRAMMATION) && dossier.getProgrammation() != null){
                String data = dossier.getProgrammation().toLowerCase();
                return containsKeyword(data, hm, KEY_PROGRAMMATION);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public boolean searchByDateEntree(Dossier dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_DATE_ENTREE)){
                if(dossier.getDateEntree() == null)
                    return false;
                String date = dateToString(dossier.getDateEntree());
                String dateD = hm.get(KEY_DATE_ENTREE);
                String dateF = null;
                // si la date de fin existe 
                if(keyExit(hm,KEY_FIN_DATE_ENTREE)){
                    dateF = hm.get(KEY_FIN_DATE_ENTREE);
                }
                return  dateBetween(date, dateD, dateF);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private boolean searchByDateSortie(Dossier dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_DATE_SORTIE) && dossier.getDateEntree() != null){
                String date = dateToString(dossier.getDateEntree());
                String dateD = hm.get(KEY_DATE_SORTIE);
                String dateF = null;
                // si la date de fin existe 
                if(keyExit(hm,KEY_FIN_DATE_SORTIE)){
                    dateF = hm.get(KEY_FIN_DATE_SORTIE);
                }
                return dateBetween(date, dateD, dateF);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private boolean searchByAvis(Dossier dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_AVIS) && dossier.getAvis() != null && dossier.getAvis().getLibele() != null){
                String data= dossier.getAvis().getLibele().toLowerCase();
                return containsKeyword(data, hm,KEY_AVIS);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private boolean searchByNature(Dossier dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_NATURE) && dossier.getNature() != null && dossier.getNature().getLibele() != null){
                 String data = dossier.getNature().getLibele().toLowerCase();
                 return containsKeyword(data, hm,KEY_NATURE);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private boolean searchByDpetl(Dossier dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_DPETL) && dossier.getDpetl()!= null && dossier.getDpetl().getLibele() != null){
                String data = dossier.getDpetl().getLibele().toLowerCase();
                return containsKeyword(data, hm,KEY_DPETL);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    public List<Dossier> searchDossier(List<Dossier> all,HashMap<Integer,String> hm){
        // bool pour parcourir
        boolean b;
        // liste de resultats
        List<Dossier> allFoundeds = new ArrayList<Dossier>();
        // parcourir la liste
        for(Dossier dossier:all){
            // verifier le dossier et Hashmap
            if(dossier != null && hm != null){
               b = true;
            //pour chaque dossier chercher dans tous les champs
            
            //chercher par dossier
            if(b)
                b = searchByDossier(dossier, hm);
            else
                continue;
            // chercher par ref_appro
            if(b)
               b = searchByRefAppro(dossier, hm);
            else
                continue;
            //chercher par programmation
            if(b)
              b = searchByProgrammation(dossier, hm);
            else
                continue;
            // chercher par date entree
            if(b)
              b = searchByDateEntree(dossier, hm);
            else
                continue; 
            // chercher par date sortie
            if(b)
              b = searchByDateSortie(dossier, hm);
            else
                continue;        
            // chercher par Avis
            if(b)
              b = searchByAvis(dossier, hm);
            else
                continue; 
            //chercher par Dpetl
            if(b)
              b = searchByDpetl(dossier, hm);
            else
                continue; 
            // chercher par NATURE
            if(b)
              b = searchByNature(dossier, hm);
            else
                continue; 
            // si tous les elements sont trouvés ==> ajouté l'onjet dans la liste
            if(b)
              allFoundeds.add(dossier); 
            }     
        }
        // retourner le resultat
        if(allFoundeds.size() > 0){
            return allFoundeds;
        }
        else{
            return null;
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public String dateToString(Date date){
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(date);
        }else{
            throw new IllegalArgumentException("Date incorrecte");
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private Date stringToDate(String dateString){
        try {
            if(dateString != null && dateString.length() >= 10){  
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                return  df.parse(dateString);
            }
            else{
                return null;
            } 
        } catch (Exception e) {
        }
       return null;
    }
    
    private boolean dateBetween(String date,String dateDebut,String dateFin){
        Date dateD,dateF,dateBetween;
        dateD = stringToDate(dateDebut);
        dateF = stringToDate(dateFin);
        dateBetween =  stringToDate(date);
        System.out.println(dateToString(dateD));
        System.out.println(dateToString(dateF));
        System.out.println(dateToString(dateBetween));
        if(dateBetween != null && dateD != null && dateF != null)
        return (dateBetween.compareTo(dateD) == 0 || dateBetween.compareTo(dateD) >  0) && 
                (dateBetween.compareTo(dateF) == 0 || dateBetween.compareTo(dateF) <  0);
        else if(dateBetween != null && dateD != null && dateF == null)
        return (dateBetween.compareTo(dateD) == 0 || dateBetween.compareTo(dateD) >  0);
        return false;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private boolean containsKeyword(String data,HashMap<Integer,String> hm,int key){
        boolean b =false;
        if(data.length() > 0){
                String keyword = hm.get(key).toLowerCase();
                if(data.contains(keyword)){
                    b = true; 
                }   
        }
        return b;
    }
    
    
    
    
    
    
    
    
    
    
    //**************************************************************************************//       
    //**************************************************************************************//  
    //******************  RECHERCHE AVANCEE DES DOSSIERS LIAISON            ****************//    
    //**************************************************************************************// 
    public List<Dossier> searchDossiersLiaison(List<Dossier> all,HashMap<Integer,String> hm){
      // premier filtrage
      all = searchDossier(all, hm);
      boolean b;
      List<Dossier> allFounded = new ArrayList<Dossier>();
      if(all != null && all.size() > 0)
      for(Dossier dossier:all){
          // verifier le type de l'instance
          if(dossier != null && hm != null && dossier instanceof DossierLiaison){
              b= true;
              // chercher la phase
              if(b)
              b = searchByPhase((DossierLiaison)dossier, hm);
              else
                  continue;
              // chhercher pa route
              if(b)
              b = searchByRoute((DossierLiaison)dossier, hm);
              else
                  continue;
              // chercher par BET
              if(b)
              b = searchByBet((DossierLiaison)dossier, hm);
              else
                  continue;
              // chercher par longueur
              if(b)
              b = searchByLongueur((DossierLiaison)dossier, hm);
              else
                  continue;
              // ajouter dans la nouvelle liste
              if(b)
              allFounded.add(dossier);
          }
      }
      if(allFounded.size() > 0)
      return allFounded;
      else
      return null;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private boolean searchByPhase(DossierLiaison dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_PHASE) && dossier.getPhase()!= null && dossier.getPhase().getLibele() != null){
                String data = dossier.getPhase().getLibele().toLowerCase();
                return containsKeyword(data, hm,KEY_PHASE);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private boolean searchByRoute(DossierLiaison dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_ROUTE) && dossier.getRoute()!= null && dossier.getRoute().length() > 0){
                String data = dossier.getRoute().toLowerCase();
                return containsKeyword(data, hm,KEY_ROUTE);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private boolean searchByBet(DossierLiaison dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_BET) && dossier.getBet()!= null && dossier.getBet().length() > 0){
                String data = dossier.getBet().toLowerCase();
                return containsKeyword(data, hm,KEY_BET);
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
    private boolean searchByLongueur(DossierLiaison dossier,HashMap<Integer,String>  hm){
            if(keyExit(hm,KEY_LONGUEUR) && dossier.getLongueur() != null){
                try {
                    double lg = Double.parseDouble(hm.get(KEY_LONGUEUR));
                    if(lg == dossier.getLongueur())
                      return true;
                    else
                      return false;
                } catch (Exception e) {
                }
            }
        return true;
    }
    //**************************************************************************************//       
    //**************************************************************************************//
}
