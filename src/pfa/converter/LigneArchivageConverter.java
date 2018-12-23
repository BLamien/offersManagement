/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.converter;


import javax.annotation.ManagedBean;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.springframework.beans.factory.annotation.Autowired;
import pfa.beans.admin.archivage.ArchivageAdmin;
import pfa.model.archivage.LigneArchivage;
import pfa.service.archivage.ServiceArchivage;

/**
 *
 * @author karim
 */
@ManagedBean(value = "ligneConverter")
@javax.faces.bean.ManagedBean(name =  "ligneConverter")
@SessionScoped
public class LigneArchivageConverter implements Converter{


    @Autowired(required = true)
    private ServiceArchivage serviceArchivage;
    
    private ArchivageAdmin archivageBean;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {  
                // context JSF
                ELContext elContext = FacesContext.getCurrentInstance().getELContext();
                // recupérer le bean
                archivageBean = (ArchivageAdmin)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "archivageBean");
                if(serviceArchivage != null && archivageBean != null ){
                    LigneArchivage l = serviceArchivage.getLigneArchivageFromList(archivageBean.getAllLignes(),Integer.parseInt(value));
                    return l;  
                }
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Profil"));
            }
        }
            
        return null;    
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
      if(o != null) {
            return String.valueOf(((LigneArchivage) o).getIdLigne());
        }
        else {
            return null;
        }
    }
    
}
