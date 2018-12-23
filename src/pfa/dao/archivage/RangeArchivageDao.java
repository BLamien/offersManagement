/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.dao.archivage;

import java.util.ArrayList;
import pfa.model.archivage.RangeArchivage;

/**
 *
 * @author karim
 */
public interface RangeArchivageDao {
    boolean add(RangeArchivage rangeArchivage);
    ArrayList<RangeArchivage> findAll();
    RangeArchivage findById(int id);
}
