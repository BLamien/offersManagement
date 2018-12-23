/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.users;

import java.util.List;
import pfa.model.users.DroitAcces;
import pfa.model.users.Profil;

/**
 *
 * @author karim
 */
public interface DroitAccesDao {
    DroitAcces findById(int id);
    List<DroitAcces> findAll();
}
