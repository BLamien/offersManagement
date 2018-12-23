package pfa.converter;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import pfa.beans.dossier.AjouterDossierBean;
import pfa.model.dossier.Dpetl;
import pfa.model.dossier.Nature;
import pfa.service.dossier.ServiceDossier;
 
 
@FacesConverter("natureConverter")
public class NatureConverter implements Converter {
    private final List<Nature> natures = AjouterDossierBean.ListNatures();
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       if(value != null && value.trim().length() > 0) {
            try {     
                return getNatureById(Integer.valueOf(value));
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
            return String.valueOf(((Nature) o).getIdNature());
        }
        else {
            return null;
        }
    }
    private Nature getNatureById(int id)
    {
    for(Nature n : natures)
    {
    if(n.getIdNature()==id)
        return n;
    }
    return null;
    }
}

