/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.converter;

import javax.annotation.ManagedBean;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pfa.beans.admin.archivage.ArchivageAdmin;
import pfa.beans.admin.users.ProfilAdmin;
import pfa.model.users.DroitAcces;
import pfa.model.users.Profil;
import pfa.service.users.ServiceProfils;

/**
 *
 * @author karim
 */
@ManagedBean(value = "droitConverter")
@javax.faces.bean.ManagedBean(name =  "droitConverter")
@SessionScoped
public class DroitAccesConverter implements Converter{

    
    //**************************************************************************************//
    //**************************************************************************************//
    @Autowired(required = true)
    
    @Qualifier("serviceProfil")
    private ServiceProfils serviceProfils;
    //**************************************************************************************//
    //**************************************************************************************//
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       if(value != null && value.trim().length() > 0) {
            try {   
                if(serviceProfils != null){
                   return serviceProfils.getDroitAccesFromList(serviceProfils.getAllDroitAcces(),Integer.parseInt(value));
                }else{
                    return null;
                }
                    
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Profil"));
            }
        }
        else {
            return null;
        } 
    }
    //**************************************************************************************//
    //**************************************************************************************//
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
      if(o != null) {
            return String.valueOf(((DroitAcces) o).getIdDroit());
        }
        else {
            return null;
        }
    }
    //**************************************************************************************//
    //**************************************************************************************//
}
