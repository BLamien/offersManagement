/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.service.dossier;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfa.daoImp.dossier.AvisDaoImp;
import pfa.model.dossier.Avis;

/**
 *
 * @author karim
 */
@Service("serviceAvis") 
public class ServiceAvis {
    //**************************************************************************************//       
    //**************************************************************************************// 
    private static final int APPROUVE= 2;
    private static final int NON_APPROUVE= 3;  
    private static final int EN_COURS= 1; 
    //**************************************************************************************//       
    //**************************************************************************************//  
    @Autowired(required = true)
    private AvisDaoImp daoAvis;

    private Avis avisApprouve,avisNonApprouve,avisEnCours;
    private List<Avis> allAvis;
    //**************************************************************************************//       
    //**************************************************************************************// 
    @PostConstruct
    private void init(){
        if(daoInjected()){
            this.avisApprouve = daoAvis.findById(APPROUVE);
            this.avisNonApprouve = daoAvis.findById(NON_APPROUVE);
            this.avisEnCours = daoAvis.findById(EN_COURS);
            refrechListeAvis();
        }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public List<Avis> allAvis(){
        return this.allAvis;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public Avis getAvisApprouve() {
        return avisApprouve;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public Avis getAvisNonApprouve() {
        return avisNonApprouve;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    public Avis getAvisEnCours() {
        return avisEnCours;
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private void refrechListeAvis() {
       if(daoInjected()){
        this.allAvis = daoAvis.findAll();
       }
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
    private boolean daoInjected(){
        return (this.daoAvis != null);
    }
    //**************************************************************************************//       
    //**************************************************************************************// 
}
