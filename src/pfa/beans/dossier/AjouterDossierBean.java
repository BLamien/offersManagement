
package pfa.beans.dossier;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pfa.model.dossier.Avis;
import pfa.model.dossier.Dossier;
import pfa.model.dossier.DossierCarrefour;
import pfa.model.dossier.DossierCpsEtude;
import pfa.model.dossier.DossierLiaison;
import pfa.model.dossier.DossierOa;
import pfa.model.dossier.Dpetl;
import pfa.model.dossier.Nature;
import pfa.model.dossier.Phase;
import pfa.model.users.Utilisateur;
import pfa.service.dossier.*;
import pfa.service.util.UserSession;





@javax.annotation.ManagedBean("ajouterdossierbean")
@ManagedBean(name="ajouterdossierbean", eager = true)
@SessionScoped
public class AjouterDossierBean implements Serializable{
    
    private String typedossier;
    private String typecps;
    private String dossierdescription;
    private Dpetl dpetl;
    private String route;
    private double longeur;
    private double pkd;
    private double pkf;
    private Nature nature;
    private String bet;
    private Phase phase;
    private Date dateentree;
    private Date datesortie;
    
    private FacesMessage message;

    @Autowired(required = true)
    @Qualifier("serviceDpetl")
    private ServiceDpetl serviceDpetl;
    
    @Autowired(required = true)
    @Qualifier("serviceAvis")
    private ServiceAvis serviceAvis;
    
    @Autowired(required = true)
    @Qualifier("serviceDossier")
    private ServiceDossier serviceDossier;
    
    @Autowired(required = true)
    @Qualifier("serviceSession")
    private UserSession usersess;

    private static List<Phase>phases;
    private static List<Dpetl> dpetls;
    private static List<Nature> natures;
    private static List<Avis> aviss;

    @PostConstruct
    public void init()
    {
        phases=serviceDossier.allPhases();
        dpetls=serviceDpetl.allDpetls();
        natures=serviceDossier.allNatures();
        aviss=serviceAvis.allAvis();
    }
    /*dpetls*/
    public static List<Dpetl> ListDpetls()
    {
    return dpetls;
    }
    /*dpetls*/
    public static List<Nature> ListNatures()
    {
    return natures;
    }
    /*dpetls*/
    public static List<Phase> ListPhases()
    {
    return phases;
    }
     /*dpetls*/
    public static List<Avis> ListAvis()
    {
    return aviss;
    }
    /*ajouter dossier*/
    public void ajouterdossier() throws IOException
    {
        boolean b=choixDossier();
        if(!b)
        {
            sendResponseMessage("une erreur lors de l'ajout du dossier");
        }
        else{
            resetAll();
            sendResponseMessage("Le dossier a ete ajouté correctement");
        }
    }
    private boolean choixDossier()
    {
        Dossier d=null;
        switch(typedossier)
        {
            case "liaison":
                DossierLiaison dl=new DossierLiaison();
                dl.setPhase(phase);
                dl.setRoute(route);
                dl.setLongueur(longeur);
                dl.setPkdebut(pkd);
                dl.setPkfin(pkf);
                dl.setBet(bet);
                dl.setDossier(dossierdescription);
                dl.setDateEntree(dateentree);
                dl.setDpetl(dpetl);
                dl.setNature(nature);
                List<Utilisateur> resp=new ArrayList<Utilisateur>();
                                   resp.add(usersess.getUser());
                dl.setResponsables(resp);
                d=dl;
                break;
            case "crf":
                DossierCarrefour dc=new DossierCarrefour();
                dc.setDossier(dossierdescription);
                dc.setDateEntree(dateentree);
                dc.setDpetl(dpetl);
                dc.setNature(nature);
                d=dc;
                break;
            case "oa":
                DossierOa doa=new DossierOa();
                doa.setDossier(dossierdescription);
                doa.setDateEntree(dateentree);
                doa.setDpetl(dpetl);
                doa.setNature(nature);
                d=doa;
                break;
            case "cps":
                switch(typecps)
                {
                    case "cpse":
                        DossierCpsEtude cpse=new DossierCpsEtude();
                        cpse.setDossier(dossierdescription);
                        cpse.setDateEntree(dateentree);
                        cpse.setDpetl(dpetl);
                        cpse.setNature(nature);
                        d=cpse;
                        break;
                    case "cpst":
                        break;
                    case "cpsc":
                        break;
                }
                break;
        }
      return serviceDossier.addDossier(d);
    }
    private void resetAll()
    {
    typedossier="";
    typecps="";
    dossierdescription="";
    route="";
    longeur=0;
    pkd=0;
    pkf=0;
    bet="";
    dateentree=null;
    datesortie=null;
    }
    public String getTypedossier() {
        return typedossier;
    }

    public String getTypecps() {
        return typecps;
    }

    public Dpetl getDpetl() {
        return dpetl;
    }

    public String getRoute() {
        return route;
    }

    public double getLongeur() {
        return longeur;
    }

    public double getPkd() {
        return pkd;
    }

    public double getPkf() {
        return pkf;
    }

    public Nature getNature() {
        return nature;
    }


    public String getBet() {
        return bet;
    }

    public Phase getPhase() {
        return phase;
    }

    public Date getDateentree() {
        return dateentree;
    }

    public Date getDatesortie() {
        return datesortie;
    }

    public void setTypedossier(String typedossier) {
        this.typedossier = typedossier;
    }

    public void setTypecps(String typecps) {
        this.typecps = typecps;
    }

    public void setDpetl(Dpetl dpetl) {
        this.dpetl = dpetl;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setLongeur(double longeur) {
        this.longeur = longeur;
    }

    public void setPkd(double pkd) {
        this.pkd = pkd;
    }

    public void setPkf(double pkf) {
        this.pkf = pkf;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    } 

    public void setBet(String bet) {
        this.bet = bet;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void setDateentree(Date dateentree) {
        this.dateentree = dateentree;
    }

    public void setDatesortie(Date datesortie) {
        this.datesortie = datesortie;
    }

    public ServiceDossier getServiceDossier() {
        return serviceDossier;
    }

    public void setServiceDossier(ServiceDossier serviceDossier) {
        this.serviceDossier = serviceDossier;
    }

    public String getDossierdescription() {
        return dossierdescription;
    }

    public void setDossierdescription(String dossierdescription) {
        this.dossierdescription = dossierdescription;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    public List<Dpetl> getDpetls() {
        return dpetls;
    }

    public void setDpetls(List<Dpetl> dpetls) {
        this.dpetls = dpetls;
    }

    public List<Nature> getNatures() {
        return natures;
    }

    public void setNatures(List<Nature> natures) {
        this.natures = natures;
    }

    public  List<Avis> getAviss() {
        return aviss;
    }

    public  void setAviss(List<Avis> aviss) {
       this.aviss = aviss;
    }
    private void sendResponseMessage(String response){
        this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, response,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
   }
    
}
