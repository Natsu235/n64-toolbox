/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.dao;

import com.phantomnat.n64toolbox.java.sql.DatabaseConnection;

/**
 *
 * @author Dorian Pilorge
 */
public abstract class DAO<T> {

    protected DatabaseConnection connect = null;

    public DAO(DatabaseConnection conn) {
        this.connect = conn;
    }

    /**
    * Méthode de création
    * @param obj
    * @return boolean 
    */
    public abstract boolean create(T obj);

    /**
    * Méthode pour effacer
    * @param obj
    * @return boolean 
    */
    public abstract boolean delete(T obj);

    /**
    * Méthode de mise à jour
    * @param obj
    * @return boolean
    */
    public abstract boolean update(T obj);

    /**
    * Méthode de recherche des informations
    * @param id
    * @return T
    */
    public abstract T find(int id);

}
