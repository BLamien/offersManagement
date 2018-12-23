/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.converter;


import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pfa.model.users.Profil;
import pfa.service.users.ServiceProfils;

/**
 *
 * @author karim
 */
@ManagedBean(value = "profilConverter")
@javax.faces.bean.ManagedBean(name =  "profilConverter")
@SessionScoped
public class ProfilConverter implements Converter{
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
                    return serviceProfils.getProfilFromList(Integer.parseInt(value));
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
            return String.valueOf(((Profil) o).getIdProfil());
        }
        else {
            return null;
        }
    }
    //**************************************************************************************//
    //**************************************************************************************//  
    

}
