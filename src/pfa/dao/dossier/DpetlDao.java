/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.dossier;

import java.util.ArrayList;
import java.util.List;
import pfa.model.dossier.Dpetl;

/**
 *
 * @author karim
 */
public interface DpetlDao {
    Dpetl findById(int id);
    List<Dpetl> findAll();
    boolean add(Dpetl dpetl);
    boolean update(Dpetl dpetl);
    boolean delete(Dpetl dpetl);
}
