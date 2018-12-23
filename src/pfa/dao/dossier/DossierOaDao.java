/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import java.util.List;
import pfa.model.dossier.DossierOa;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
public interface DossierOaDao {
    DossierOa findById(int id);
    ArrayList<DossierOa> findAll();
    List<DossierOa> findAll(Utilisateur user);
    boolean add(DossierOa dossier);
    boolean update(DossierOa dossier);   
}
