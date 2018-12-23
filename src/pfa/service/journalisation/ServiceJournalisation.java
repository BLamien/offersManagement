/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.journalisation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
@Service("serviceJournalisaton")
public class ServiceJournalisation {
// journalisation type
public final int TYPE_ADMIn = 1;
public final int TYPE_AUTHENTIFICATION = 2;
public final int TYPE_DOSSIER = 3;
// operation type
public final int OP_ADD = 4;
public final int OP_DEL = 5;
public final int OP_UPDATE = 6;
public final int OP_APPROUVATION = 7;
public final int OP_LOGIN = 8;
public final int OP_LOGOUT = 9;
//
private final String OP_ADD_S = "Ajout";
private final String OP_UPDATE_S = "Modification";
private final String OP_DEL_S = "Suppression";
private final String OP_APPROUVATION_S = "Approuvaton";
private final String OP_LOGIN_S = "Authentification";
private final String OP_LOGOUT_S = "Deconnexion";
// folder journalisation
private final String folder = "D:\\ENSA\\4eme\\GI4\\S2\\PFA\\Projet\\pfa\\pfa_log\\"; //"C:\\Users\\karim\\Documents\\NetBeansProjects\\pfa\\src\\"; //



public void journaliser(Utilisateur user,int typeOpeation,int typeJournalisaton,String description){
    File f = getFile(typeJournalisaton);
    Document doc = getDocument(f);
    if(f != null && doc != null){
        String last_id;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // la racine du fichier
        Element racine = doc.getDocumentElement();
         last_id = "0";
        if(racine.getAttribute("last_id") != null)
         last_id = racine.getAttribute("last_id");
        int id;
        try {
            id = Integer.parseInt(last_id);
            id++;
            last_id = String.valueOf(id);
            racine.getAttributes().getNamedItem("last_id").setNodeValue(last_id);
        } catch (NumberFormatException e) {
        }
        catch (DOMException e) {
        }
        // creer l'element
        Element journalisation = doc.createElement("journalisation");
        journalisation.setAttribute("id",last_id);
        journalisation.setAttribute("date-heure",sdf.format(new Date()));
        // user
        Element userNode = doc.createElement("user");
        if(user != null){
            userNode.setAttribute("matricule",user.getMatricule());
            userNode.setAttribute("nom",user.getNom());
            userNode.setAttribute("prenom",user.getPrenom());
        }
        journalisation.appendChild(userNode);
        // operation
        Element operation = doc.createElement("operation");
        switch (typeOpeation){
            case OP_ADD : operation.setAttribute("type",OP_ADD_S); break;
            case OP_UPDATE : operation.setAttribute("type",OP_UPDATE_S); break;
            case OP_DEL : operation.setAttribute("type",OP_DEL_S); break;
            case OP_APPROUVATION : operation.setAttribute("type",OP_APPROUVATION_S); break;
            case OP_LOGIN : operation.setAttribute("type",OP_LOGIN_S); break;
            case OP_LOGOUT : operation.setAttribute("type",OP_LOGOUT_S); break; 
        }
        journalisation.appendChild(operation);
        // description
        Element desc = doc.createElement("description");
        desc.setTextContent(description);
        journalisation.appendChild(desc);
        // ajouter la journalisation
        racine.appendChild(journalisation);
        // transformer
        saveDocInXmlFile(doc,f);
    }
    
}


private File getFile(int typeJournalisation){
    File f;
    String docName="";
    try {
        switch(typeJournalisation){
            case TYPE_ADMIn : docName=folder+"admin.xml"; break;
            case TYPE_AUTHENTIFICATION: docName=folder+"login.xml"; break;
            case TYPE_DOSSIER: docName=folder+"dossier.xml"; break; 
        }
    f =  new File(docName);
    if(!f.exists()){
        return null;
    }
    } catch (Exception ex) {
        f = null;
        Logger.getLogger(ServiceJournalisation.class.getName()).log(Level.SEVERE, null, ex);
    }
    return f;
}

private Document getDocument(File f){
    try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder bd = dbf.newDocumentBuilder();
        return  bd.parse(f);
        
        
    } catch (SAXException ex) {
        Logger.getLogger(ServiceJournalisation.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(ServiceJournalisation.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParserConfigurationException ex) {
        Logger.getLogger(ServiceJournalisation.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}


private void saveDocInXmlFile(Document doc,File f){
    try {
        TransformerFactory tsf = TransformerFactory.newInstance();
        Transformer transformer = tsf.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(f);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        
        transformer.transform(source, streamResult);       
    } catch (TransformerException ex) {
        Logger.getLogger(ServiceJournalisation.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}
