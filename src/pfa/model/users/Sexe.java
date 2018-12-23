/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfa.model.users;

/**
 *
 * @author karim
 */
public enum Sexe {
    HOMME(1,"Homme"),FEMME(2,"Femme");
    private final int idSexe;
    private final String value;
    
    
    private Sexe(int idSexe,String value) {
        this.idSexe = idSexe;
        this.value = value;
    }


    public int getIdSexe() {
        return this.idSexe;
    }

    public String getValue() {
        return value;
    }
    
   
}
