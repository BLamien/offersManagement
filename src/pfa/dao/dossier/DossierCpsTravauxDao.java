/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import java.util.List;
import pfa.model.dossier.DossierCpsTravaux;
import pfa.model.users.Utilisateur;

/**
 *
 * @author karim
 */
public interface DossierCpsTravauxDao {
    DossierCpsTravaux findById(int id);
    ArrayList<DossierCpsTravaux> findAll();
    List<DossierCpsTravaux> findAll(Utilisateur user);
    boolean add(DossierCpsTravaux dossier);
    boolean update(DossierCpsTravaux dossier);   
}
