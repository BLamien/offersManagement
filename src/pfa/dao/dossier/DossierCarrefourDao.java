/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import java.util.List;
import pfa.model.dossier.DossierCarrefour;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
public interface DossierCarrefourDao {
    DossierCarrefour findById(int id);
    ArrayList<DossierCarrefour> findAll();
    List<DossierCarrefour> findAll(Utilisateur user);
    boolean add(DossierCarrefour dossier);
    boolean update(DossierCarrefour dossier); 
}
