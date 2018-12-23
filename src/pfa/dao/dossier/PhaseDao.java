/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import pfa.model.dossier.Phase;

/**
 *
 * @author karim
 */
public interface PhaseDao {
    Phase findById(int id);
    ArrayList<Phase> findAll();
}
