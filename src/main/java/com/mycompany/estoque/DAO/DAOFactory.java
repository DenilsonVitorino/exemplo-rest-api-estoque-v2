/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.estoque.DAO;

import com.mycompany.estoque.db.DbConfig;
import com.mycompany.estoque.service.ProductServiceImpl;

/**
 *
 * @author dv22p
 */
public class DAOFactory {

    public static ProductDAO createProductDAO() {
		return new ProductServiceImpl(DbConfig.connect());
    }
}
