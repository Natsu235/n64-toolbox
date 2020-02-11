/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phantomnat.n64toolbox.java.dao;

import com.phantomnat.n64toolbox.java.model.Rom;
import com.phantomnat.n64toolbox.java.sql.DatabaseConnection;

/**
 *
 * @author Dorian Pilorge
 */
public class RomDAO extends DAO<Rom> {

    public RomDAO(DatabaseConnection conn) {
        super(conn);
    }

    @Override
    public boolean create(Rom obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(Rom obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Rom obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Rom find(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
