package pfa.converter;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pfa.beans.dossier.AjouterDossierBean;
import pfa.model.dossier.Avis;
import pfa.model.dossier.Dpetl;
import pfa.service.dossier.ServiceDossier;

@FacesConverter("avisConverter")
public class AvisConverter implements Converter {
    private final List<Avis> aviss = AjouterDossierBean.ListAvis();
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       if(value != null && value.trim().length() > 0) {
            try {     
                return getAvisById(Integer.valueOf(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }   
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o != null) {
            return String.valueOf(((Avis) o).getIdAvis());
        }
        else {
            return null;
        }
    }
    private Avis getAvisById(int id)
    {
    for(Avis a : aviss)
    {
    if(a.getIdAvis()==id)
        return a;
    }
    return null;
    }
}
