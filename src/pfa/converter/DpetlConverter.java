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
import pfa.model.dossier.Dpetl;
 
 
@FacesConverter("dpetlConverter")
public class DpetlConverter implements Converter {
    private final List<Dpetl> dpetls = AjouterDossierBean.ListDpetls();
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       if(value != null && value.trim().length() > 0) {
            try {     
                return getDpetlById(Integer.valueOf(value));
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
            return String.valueOf(((Dpetl) o).getIdDpetl());
        }
        else {
            return null;
        }
    }
    private Dpetl getDpetlById(int id)
    {
    for(Dpetl d : dpetls)
    {
    if(d.getIdDpetl()==id)
        return d;
    }
    return null;
    }
}
