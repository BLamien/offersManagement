/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.beans.admin.archivage;

import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import pfa.model.archivage.ColonneArchivage;
import pfa.model.archivage.LigneArchivage;
import pfa.model.archivage.RangeArchivage;
import pfa.service.archivage.ServiceArchivage;

/**
 *
 * @author karim
 */
@ManagedBean("archivageBean")
@javax.faces.bean.ManagedBean(name = "archivageBean")
@RequestScoped
public class ArchivageAdmin {
//**************************************************************************************//
//**************************************************************************************//
// les services
@Autowired(required = true)
private ServiceArchivage serviceRange;
// les objets
private RangeArchivage rangeArchivage,addRangeArchivage = new RangeArchivage();
private ColonneArchivage colonneArchivage,addColonneArchivage = new ColonneArchivage();
private LigneArchivage ligneArchivage,addLigneArchivage = new LigneArchivage();
// les listes
private List<RangeArchivage> allRanges,filtredRanges;
private List<LigneArchivage> allLignes,filtredLignes;
private List<ColonneArchivage> allColonnes,filtredColonnes;
// les objets
private FacesMessage message;
//**************************************************************************************//
//**************************************************************************************//
@PostConstruct()
private void init(){
  this.refreshListRanges();
  // remplir la listes
  this.setListRange();
 
}

public ArchivageAdmin(){
    super();
    this.init();
}
//**************************************************************************************//
//**************************************************************************************//


    
    
    
//**************************************************************************************//       
//**************************************************************************************//
//****************************  RANGE METHODES  *************************************//       
//**************************************************************************************//
//**************************************************************************************//    
public void onRowRangeEdit(RowEditEvent event) {
    RangeArchivage editedRanger = (RangeArchivage)event.getObject();
    String result  = serviceRange.rangeValide(editedRanger);
    if(editedRanger != null && this.serviceRange.update(editedRanger)){
      result = "Range "+editedRanger.getNumRange()+" modifié";
    }
    sendResponseMessage(result);
}
//**************************************************************************************//    
public void onRowRangeSelect(SelectEvent event){
    System.out.println("La rangé "+((RangeArchivage)event.getObject()).getIdRange()+" est selectionné");
    this.rangeArchivage = (RangeArchivage)event.getObject();
    this.setListLignes();
}
//**************************************************************************************//
public void itemRangeSelected(){
    if(this.rangeArchivage != null){
      System.out.println("La rangé "+this.rangeArchivage.getIdRange()+" est selectionné");
      this.setListLignes();      
    }
}
//**************************************************************************************//
private void setListRange(){
    if(this.allRanges != null && this.allRanges.size() > 0){
        this.rangeArchivage = this.allRanges.get(0);
        this.setListLignes();
    }
}
//**************************************************************************************//
private boolean serviceRangeOn(){
    return (this.serviceRange != null);
}
//**************************************************************************************//
private void refreshListRanges(){
    if(serviceRangeOn()){
       this.setAllRanges(serviceRange.getAllRanges());
    }
}
//**************************************************************************************//
public void addRanger(){
    String result = null;
     System.out.println("Dkhelna");
    if(result == null && serviceRange.add(this.getAddRangeArchivage())){
        result = "La rangé est enregistrée correctement";
        this.setAddRangeArchivage(new RangeArchivage());
        this.refreshListRanges();
    }
    sendResponseMessage(result);
}


//**************************************************************************************//
//**************************************************************************************//       
//**************************************************************************************//
//****************************  LIGNE METHODES    *************************************//       
//**************************************************************************************//
//**************************************************************************************//       
public void itemLigneSelected(){
    if(ligneArchivage != null){
      this.setListColonne();
    }
}
//**************************************************************************************//
public void onRowLigneEdit(RowEditEvent event){
   LigneArchivage ligneEdited = (LigneArchivage)event.getObject();
   String result = serviceRange.ligneArchivageValide(ligneEdited);
       if(ligneEdited != null && serviceRangeOn() && serviceRange.updateLigneArchivage(ligneEdited)){
           this.refreshListRanges();
           result = "La ligne "+ ligneEdited.getIdLigne()+" est modifiée correctement";
       }
   sendResponseMessage(result);
}
//**************************************************************************************//
public void onRowLigneSelect(SelectEvent event){
    System.out.println("La ligne "+((LigneArchivage)event.getObject()).getIdLigne()+" est selectionné");
    this.setListColonne();
}
//**************************************************************************************//
private void setListLignes(){
    if(this.rangeArchivage != null && this.rangeArchivage.getLigneArchivages() != null){
        this.allLignes = this.rangeArchivage.getLigneArchivages();
        if(this.allLignes.size() > 0){
            this.ligneArchivage = this.allLignes.get(0);
            this.setListColonne();
        }
        else{
            this.ligneArchivage = null;
        }

    }    
}
//**************************************************************************************//
public void addLigne(){
    System.out.println("dkhlna");
    String result = null;
    this.addLigneArchivage.setRangeArchivage(this.addRangeArchivage);
    if(result == null && this.addRangeArchivage != null && this.serviceRange.addLigneArchivage(this.addLigneArchivage)){
        result = "La ligne est enregistrée correctement";
        this.allLignes.add(addLigneArchivage);
        this.setAddLigneArchivage(new LigneArchivage());
        //this.refreshListRanges();
    }
    sendResponseMessage(result);
}

//**************************************************************************************//       
//**************************************************************************************//
//****************************  Colonnes METHODES    *************************************//       
//**************************************************************************************//     
//**************************************************************************************//
public void onRowColonneSelect(SelectEvent event){
    System.out.println("La ligne "+((LigneArchivage)event.getObject()).getIdLigne()+" est selectionné");
    //this.setListColonne();
}
//**************************************************************************************//
private void setListColonne(){
 if(this.ligneArchivage != null && this.ligneArchivage.getColonneArchivages() != null){
     this.allColonnes = this.ligneArchivage.getColonneArchivages();
     if(this.ligneArchivage.getColonneArchivages().size() > 0)
         this.colonneArchivage = this.ligneArchivage.getColonneArchivages().get(0);
 }   
}
//**************************************************************************************//
public void onRowColonneEdit(RowEditEvent event){
    String result = null;
    ColonneArchivage colonneEdited = (ColonneArchivage)event.getObject();
    if(colonneEdited != null && serviceRangeOn() && serviceRange.updateColonneArchivage(colonneEdited)){
        result = "La colonne "+ colonneEdited.getIdColonne()+" est modifiée correctement";
    }
    sendResponseMessage(result);
}
//**************************************************************************************//
public void addColonne(){
    String result = null;
    if( addColonneArchivage != null && this.serviceRange.addColonneArchivage(this.addColonneArchivage)){
        result = "La cellule est enregistrée correctement";
        this.getAllColonnes().add(addColonneArchivage);
        this.setAddColonneArchivage(new ColonneArchivage());
    }
    sendResponseMessage(result);
}



//**************************************************************************************//
//**************************************************************************************//
//********************************P   PRIVATE METHODE***********************************//
//**************************************************************************************//
private void sendResponseMessage(String response){
    this.message = new FacesMessage(FacesMessage.SEVERITY_INFO, response,  null);
    FacesContext.getCurrentInstance().addMessage(null, message);
}

    public void onTabChange(TabChangeEvent event) {
        System.out.println("Rangé actuelle :" +  this.rangeArchivage.getIdRange());
    }







//**************************************************************************************//       
//**************************************************************************************//
//****************************  Getters && SETTERS *************************************//       
//**************************************************************************************//     
//**************************************************************************************//

    public RangeArchivage getRangeArchivage() {
        return rangeArchivage;
    }

    public void setRangeArchivage(RangeArchivage rangeArchivage) {
        this.rangeArchivage = rangeArchivage;
    }

    public List<RangeArchivage> getAllRanges() {
        if(serviceRangeOn()){
            return serviceRange.getAllRanges();
        }
        else{
            return null;
        }
    }

    public void setAllRanges(List<RangeArchivage> allRanges) {
        this.allRanges = allRanges;
    }

    public List<RangeArchivage> getFiltredRanges() {
        return filtredRanges;
    }

    public void setFiltredRanges(List<RangeArchivage> filtredRanges) {
        this.filtredRanges = filtredRanges;
    }

    public LigneArchivage getLigneArchivage() {
        return ligneArchivage;
    }

    public void setLigneArchivage(LigneArchivage ligneArchivage) {
        this.ligneArchivage = ligneArchivage;
    }

    public List<LigneArchivage> getAllLignes() {
        return allLignes;
    }

    public void setAllLignes(List<LigneArchivage> allLignes) {
        this.allLignes = allLignes;
    }

    public List<LigneArchivage> getFiltredLignes() {
        return filtredLignes;
    }

    public void setFiltredLignes(List<LigneArchivage> filtredLignes) {
        this.filtredLignes = filtredLignes;
    }

    public ColonneArchivage getColonneArchivage() {
        return colonneArchivage;
    }

    public void setColonneArchivage(ColonneArchivage colonneArchivage) {
        this.colonneArchivage = colonneArchivage;
    }

    public List<ColonneArchivage> getAllColonnes() {
        return allColonnes;
    }

    public void setAllColonnes(List<ColonneArchivage> allColonnes) {
        this.allColonnes = allColonnes;
    }

    public List<ColonneArchivage> getFiltredColonnes() {
        return filtredColonnes;
    }

    public void setFiltredColonnes(List<ColonneArchivage> filtredColonnes) {
        this.filtredColonnes = filtredColonnes;
    }

    public RangeArchivage getAddRangeArchivage() {
        return addRangeArchivage;
    }

    public void setAddRangeArchivage(RangeArchivage addRangeArchivage) {
        this.addRangeArchivage = addRangeArchivage;
    }

    public LigneArchivage getAddLigneArchivage() {
        return addLigneArchivage;
    }

    public void setAddLigneArchivage(LigneArchivage addLigneArchivage) {
        this.addLigneArchivage = addLigneArchivage;
    }

    public ColonneArchivage getAddColonneArchivage() {
        return addColonneArchivage;
    }

    public void setAddColonneArchivage(ColonneArchivage addColonneArchivage) {
        this.addColonneArchivage = addColonneArchivage;
    }


    
}

