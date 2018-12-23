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
import pfa.model.dossier.Phase;
 
@FacesConverter("phaseConverter")
public class PhaseConverter implements Converter {
    private final List<Phase> phases = AjouterDossierBean.ListPhases();
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
       if(value != null && value.trim().length() > 0) {
            try {     
                return getPhaseById(Integer.valueOf(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid phase."));
            }
        }
        else {
            return null;
        }   
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o != null) {
            return String.valueOf(((Phase) o).getIdPhase());
        }
        else {
            return null;
        }
    }
    private Phase getPhaseById(int id)
    {
    for(Phase n : phases)
    {
    if(n.getIdPhase()==id)
        return n;
    }
    return null;
    }
}


