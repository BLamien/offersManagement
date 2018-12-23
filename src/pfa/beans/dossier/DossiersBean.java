
package pfa.beans.dossier;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pfa.model.archivage.ColonneArchivage;
import pfa.model.dossier.*;
import pfa.service.archivage.ServiceArchivage;
import pfa.service.dossier.ServiceDossier;

@javax.annotation.ManagedBean("dossiersbean")
@RequestScoped
@ManagedBean(name = "dossiersbean")
public class DossiersBean implements Serializable{
    /*-------type dossiers------*/
    public static final int CPS_ETUDE_TYPE = 1;
    public static final int CPS_TRAVAUX_TYPE = 2;
    public static final int CPS_CONTROLE_TYPE = 3;
    public static final int CARREFOUR_TYPE = 4;
    public static final int LIAISON_TYPE = 5;
    public static final int OA_TYPE = 6;
    /*---------recherche avancee------------*/
    public final int KEY_DOSSIER = 1;
    public final int KEY_DATE_CREATION = 2;
    public final int KEY_DATE_SORTIE = 3;
    public final int KEY_FIN_DATE_SORTIE = 3;
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
    public static int dossierType; 
    
    private String[] selectedParam;
    private static final HashMap<Integer,String> typeDossier=new HashMap<>();
    static
        {
            typeDossier.put(1,"CPS ETUDE");
            typeDossier.put(2,"CPS TRAVAUX");
            typeDossier.put(3,"CPS CONTROLE");
            typeDossier.put(4,"CARREFOUR");
            typeDossier.put(5,"LIAISON");
            typeDossier.put(6,"OA");
            
        };
    
    private HashMap<Integer,String> cles_valeur;
    private String cles[]=new String[14];
    private String valeurs[]=new String[14];
    private Date dateentreedebut,dateentreefin;
    private Date datesortiedebut,datesortiefin;
    
    private String approbation;
    
    @Autowired(required = true)
    @Qualifier("serviceDossier")
    private ServiceDossier serviceDossier;
    
    @Autowired(required = true)
    @Qualifier("serviceArchivage")
    private ServiceArchivage serviceArchivage;
    private Avis avis;
    private Dpetl dpetl;
    private Phase phase;
    private Nature nature;
    
    private List<DossierLiaison> liaison;
    private List<DossierLiaison> liaisontmp;
    private List<DossierLiaison> liaisonSh=new ArrayList<DossierLiaison>() ;
    private List<DossierCarrefour> carrefour,carrefourtmp;
    private List<DossierOa> oa,oatmp;
    private List<DossierCpsEtude> cpse,cpsetmp;
    private List<DossierCpsTravaux> cpst,cpsctmp;
    private List<DossierCpsControle> cpsc,cpscmp;

   // private DossierLiaison selectedliaison;
    private DossierCarrefour selectedcarrefour;
    private DossierOa selectedoa;
    private DossierCpsEtude selectedcpse;
    private DossierCpsTravaux selectedcpst;
    private DossierCpsControle selectedcpsc;
    
    private Dossier modDossier;
    private Dossier detailDossier;
    
    private Dossier uploadDossier;
    private UploadedFile note;
    
    private Dossier archivDoss;
    private String descriptArchiv;
    private String colonneArchiv;
    
    @PostConstruct
    public void init()
    {
       liaison=serviceDossier.allDossierLiaisons();
       dossierType=5;
    }
    public void listDossiers(int type) throws IOException
    {
      
        switch(type)
        {
            case LIAISON_TYPE:
                liaison=serviceDossier.allDossierLiaisons();
                dossierType=5;
                break;
            case OA_TYPE:
                oa=serviceDossier.allDossierOas();
                dossierType=6;
                break;
            case CARREFOUR_TYPE:
                carrefour=serviceDossier.allDossierCarrefours();
                dossierType=4;
                break;
            case CPS_ETUDE_TYPE:
                cpse=serviceDossier.allDossierCpsEtudes();
                dossierType=1;
                break;
            case CPS_TRAVAUX_TYPE:
                cpst=serviceDossier.allDossierCpsTravauxs();
                dossierType=2;
                break;
            case CPS_CONTROLE_TYPE:
                cpsc=serviceDossier.allDossierCpsControles();
                dossierType=3;
                break;
        }
     FacesContext.getCurrentInstance().getExternalContext().redirect("dossiers");
    }
  
    /*recherche avancee*/
    public void init_recherche_avancee() throws ParseException
    {
        cles[6]="DPETL";
        cles[7]="Nature";
        cles[8]="Phase";
        cles[9]="Avis";
        
        cles[10]="dateentreedebut";
        cles[11]="dateentreefin";
        cles[12]="datesortiedebut";
        cles[13]="datesortiefin";
        
        valeurs[10]=generatedate(dateentreedebut);
        valeurs[11]=generatedate(dateentreefin);
        valeurs[12]=generatedate(datesortiedebut);
        valeurs[13]=generatedate(datesortiefin);
    }
    int i=0;
    public String rechercheAvancee() throws ParseException
    {
            init_recherche_avancee();
            generateHash();
            if(i==0)
            {
                liaisonSh.clear();
                for(DossierLiaison ld : liaison)
                liaisonSh.add(ld);
                i++;
            }
            if(cles_valeur.size()>0)
            {
                System.err.println("---------Hash---------");
                for(Entry<Integer, String> entry : cles_valeur.entrySet()) {
                    int key = entry.getKey();
                    String value = entry.getValue();
                    System.err.println("cle :"+key+"---valeur :"+value);
                }
                List dossier=new ArrayList<Dossier>();
                for(DossierLiaison dl : liaisonSh )
                    dossier.add(dl);
                List<DossierLiaison>l=new ArrayList<DossierLiaison>();
                l= serviceDossier.searchDossiersLiaison(dossier, cles_valeur);
                liaison.clear();
                if(l!=null)
                for(DossierLiaison ld : l)
                    liaison.add(ld);
            }else
            {
                System.err.println("---------noHash---------");
                liaison.clear();
                for(DossierLiaison dl:liaisonSh)
                liaison.add(dl);
                
                liaisonSh.clear();
                i=0;

            }
            return "";
    }
        /*------------Generate Forma Date-----------------*/
    private String generatedate(Date dt) throws ParseException
    {
        
        String formatedDate="";
        if(dt!=null ){
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
             formatedDate =sdf.format(dt);
        }
         return formatedDate; 
    }
    /*-------------Generate Hash----------------*/
    public void generateHash() throws ParseException
    {  
    //cles_valeur
        cles_valeur=new HashMap<>();
        for(int i=0;i<valeurs.length;i++){
                if(!valeurs[i].equals(""))
                {
                    switch(cles[i])
                    {
                        case "dossier":
                            cles_valeur.put(KEY_DOSSIER, valeurs[i]);
                            break;
                        case "route":
                            cles_valeur.put(KEY_ROUTE, valeurs[i]);
                            break;
                        case "long":
                            cles_valeur.put(KEY_LONGUEUR, valeurs[i]);
                            break;
                        case "bet":
                            cles_valeur.put(KEY_BET, valeurs[i]);
                            break;                    
                        case "refappr":
                            cles_valeur.put(KEY_REF_APPRO, valeurs[i]);
                            break;
                        case "commun":
                            cles_valeur.put(KEY_COMMUNE, valeurs[i]);
                            break;                    
                        case "DPETL":
                            cles_valeur.put(KEY_DPETL, valeurs[i]);
                            break;
                        case "Nature":
                            cles_valeur.put(KEY_NATURE, valeurs[i]);
                            break;
                        case "Phase":
                            cles_valeur.put(KEY_PHASE, valeurs[i]);
                            break;
                        case "Avis":
                            cles_valeur.put(KEY_AVIS, valeurs[i]);
                            break; 
                        case "dateentreedebut":
                            cles_valeur.put(KEY_DATE_ENTREE, valeurs[i]);
                            break;
                        case "dateentreefin":
                            cles_valeur.put(KEY_FIN_DATE_ENTREE, valeurs[i]);
                            break;
                        case "datesortiedebut":
                            cles_valeur.put(KEY_DATE_SORTIE, valeurs[i]);
                            break;
                        case "datesortiefin":
                            cles_valeur.put(KEY_FIN_DATE_SORTIE, valeurs[i]);
                            break;
                    }
                
                }
        }
    }
    /*-------modifier view------*/
    public void modifierDossier(Dossier modd) throws IOException
    {
                        switch(dossierType)
                        {
                            case LIAISON_TYPE:
                                modDossier=new DossierLiaison();
                                modDossier=modd;
                                break;
                            case OA_TYPE:
                                modDossier=new DossierOa();
                                modDossier=modd;
                                break;
                            case CARREFOUR_TYPE:

                                break;
                            case CPS_ETUDE_TYPE:

                                break;
                            case CPS_TRAVAUX_TYPE:

                                break;
                            case CPS_CONTROLE_TYPE:

                                break;
                        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("modifier");
    }
    
    public void enregistrerModification() throws IOException
    {
         FacesContext context = FacesContext.getCurrentInstance();
         if(serviceDossier.updateDossier(modDossier))
         context.addMessage(null, new FacesMessage("Successful",  "Vos modifications ont été effectuées avec succès!") );
         else
         context.addMessage(null, new FacesMessage("Error",  "Erreur lors de la modification.") );
    
    }
    /*---------------Selectionner dossier-------------------------*/
     public void detailsDossier(SelectEvent event) throws IOException {
          switch(dossierType)
                        {
                            case LIAISON_TYPE:
                                detailDossier=new DossierLiaison();
                                detailDossier= (DossierLiaison) event.getObject();
                                break;
                            case OA_TYPE:
                                detailDossier=new DossierOa();
                                detailDossier= (DossierOa) event.getObject();
                                break;
                            case CARREFOUR_TYPE:

                                break;
                            case CPS_ETUDE_TYPE:

                                break;
                            case CPS_TRAVAUX_TYPE:

                                break;
                            case CPS_CONTROLE_TYPE:

                                break;
                        }       
                FacesContext.getCurrentInstance().getExternalContext().redirect("details"); 

    }
 /*Ajouter note*/
    public void upload(){
        System.err.println("-*---------------sssssssss--"+note);
        if(note != null) {
            System.err.println("-*-----------------"+note.getFileName());
            FacesMessage message = new FacesMessage("Succesful", note.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    /*archiver*/
    public void archiver()
    {
    //serviceArchivage.archiver(modDossier, null, approbation);
    }
    /*approbation*/
    public void approver(Dossier doss) throws IOException
    {
        switch(approbation)
        {
        case "aprv":
            serviceDossier.approuve(null,null,null);
            break;
        case "rejt":
            serviceDossier.rejeter(doss);
            break;
        }
         FacesContext.getCurrentInstance().getExternalContext().redirect("dossiers");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public List<DossierLiaison> getLiaison() {
        return liaison;
    }

    public void setLiaison(List<DossierLiaison> laison) {
        this.liaison = laison;
    }

    public List<DossierCarrefour> getCarrefour() {
        return carrefour;
    }

    public void setCarrefour(List<DossierCarrefour> carrefour) {
        this.carrefour = carrefour;
    }

    public List<DossierOa> getOa() {
        return oa;
    }

    public void setOa(List<DossierOa> oa) {
        this.oa = oa;
    }

    public List<DossierCpsEtude> getCpse() {
        return cpse;
    }

    public void setCpse(List<DossierCpsEtude> cpse) {
        this.cpse = cpse;
    }

    public List<DossierCpsTravaux> getCpst() {
        return cpst;
    }

    public void setCpst(List<DossierCpsTravaux> cpst) {
        this.cpst = cpst;
    }

    public List<DossierCpsControle> getCpsc() {
        return cpsc;
    }

    public void setCpsc(List<DossierCpsControle> cpsc) {
        this.cpsc = cpsc;
    }    

    public ServiceDossier getServiceDossier() {
        return serviceDossier;
    }

    public void setServiceDossier(ServiceDossier serviceDossier) {
        this.serviceDossier = serviceDossier;
    }

    public Avis getAvis() {
        return avis;
    }

    public void setAvis(Avis avis) {
        this.avis = avis;
    }

    public Dpetl getDpetl() {
        return dpetl;
    }

    public void setDpetl(Dpetl dpetl) {
        this.dpetl = dpetl;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    } 
    
    public int getDossierType() {
        return dossierType;
    }

    public void setDossierType(char dossierType) {
        DossiersBean.dossierType = dossierType;
    }

    public String[] getSelectedParam() {
        return selectedParam;
    }

    public void setSelectedParam(String[] selectedParam) {
        this.selectedParam = selectedParam;
    }

    public List<DossierLiaison> getLiaisontmp() {
        return liaisontmp;
    }

    public void setLiaisontmp(List<DossierLiaison> liaisontmp) {
        this.liaisontmp = liaisontmp;
    }

    public List<DossierCarrefour> getCarrefourtmp() {
        return carrefourtmp;
    }

    public void setCarrefourtmp(List<DossierCarrefour> carrefourtmp) {
        this.carrefourtmp = carrefourtmp;
    }

    public List<DossierOa> getOatmp() {
        return oatmp;
    }

    public void setOatmp(List<DossierOa> oatmp) {
        this.oatmp = oatmp;
    }

    public List<DossierCpsEtude> getCpsetmp() {
        return cpsetmp;
    }

    public void setCpsetmp(List<DossierCpsEtude> cpsetmp) {
        this.cpsetmp = cpsetmp;
    }

    public List<DossierCpsTravaux> getCpsctmp() {
        return cpsctmp;
    }

    public void setCpsctmp(List<DossierCpsTravaux> cpsctmp) {
        this.cpsctmp = cpsctmp;
    }

    public List<DossierCpsControle> getCpscmp() {
        return cpscmp;
    }

    public void setCpscmp(List<DossierCpsControle> cpscmp) {
        this.cpscmp = cpscmp;
    }

    public  HashMap<Integer, String> getTypeDossier() {
        return typeDossier;
    }

    public String[] getCles() {
        return cles;
    }

    public void setCles(String cles[]) {
        this.cles = cles;
    }
    
    public String [] getValeurs() {
        return valeurs;
    }

    public void setValeurs(String [] valeurs) {
        this.valeurs = valeurs;
    }

    public Date getDateentreedebut() {
        return dateentreedebut;
    }

    public void setDateentreedebut(Date dateentreedebut) {
        this.dateentreedebut = dateentreedebut;
    }

    public Date getDateentreefin() {
        return dateentreefin;
    }

    public void setDateentreefin(Date dateentreefin) {
        this.dateentreefin = dateentreefin;
    }

    public Date getDatesortiedebut() {
        return datesortiedebut;
    }

    public void setDatesortiedebut(Date datesortiedebut) {
        this.datesortiedebut = datesortiedebut;
    }

    public Date getDatesortiefin() {
        return datesortiefin;
    }

    public void setDatesortiefin(Date datesortiefin) {
        this.datesortiefin = datesortiefin;
    }
    
    public DossierCarrefour getSelectedcarrefour() {
        return selectedcarrefour;
    }

    public void setSelectedcarrefour(DossierCarrefour selectedcarrefour) {
        this.selectedcarrefour = selectedcarrefour;
    }

    public DossierOa getSelectedoa() {
        return selectedoa;
    }

    public void setSelectedoa(DossierOa selectedoa) {
        this.selectedoa = selectedoa;
    }

    public DossierCpsEtude getSelectedcpse() {
        return selectedcpse;
    }

    public void setSelectedcpse(DossierCpsEtude selectedcpse) {
        this.selectedcpse = selectedcpse;
    }

    public DossierCpsTravaux getSelectedcpst() {
        return selectedcpst;
    }

    public void setSelectedcpst(DossierCpsTravaux selectedcpst) {
        this.selectedcpst = selectedcpst;
    }

    public DossierCpsControle getSelectedcpsc() {
        return selectedcpsc;
    }

    public void setSelectedcpsc(DossierCpsControle selectedcpsc) {
        this.selectedcpsc = selectedcpsc;
    }

    public Dossier getModDossier() {
        return modDossier;
    }

    public void setModDossier(Dossier modDossier) {
        this.modDossier = modDossier;
    }

    public Dossier getDetailDossier() {
        return detailDossier;
    }

    public void setDetailDossier(Dossier detailDossier) {
        this.detailDossier = detailDossier;
    }

    public UploadedFile getNote() {
        return note;
    }

    public void setNote(UploadedFile note) {
        this.note = note;
    }

    public String getApprobation() {
        return approbation;
    }

    public void setApprobation(String approbation) {
        this.approbation = approbation;
    }

    public Dossier getArchivDoss() {
        return archivDoss;
    }

    public void setArchivDoss(Dossier archivDoss) {
        this.archivDoss = archivDoss;
    }

    public String getDescriptArchiv() {
        return descriptArchiv;
    }

    public void setDescriptArchiv(String descriptArchiv) {
        this.descriptArchiv = descriptArchiv;
    }

    public String getColonneArchiv() {
        return colonneArchiv;
    }

    public void setColonneArchiv(String colonneArchiv) {
        this.colonneArchiv = colonneArchiv;
    }
    
    
    
}
