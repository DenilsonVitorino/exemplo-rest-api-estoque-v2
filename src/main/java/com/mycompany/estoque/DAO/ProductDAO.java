/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.estoque.DAO;

import com.mycompany.estoque.model.Product;
import java.util.List;

/**
 *
 * @author dv22p
 */
public interface ProductDAO {
    
    List<Product> getAll();
    Product getById(Integer id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    String deleteProduct(Integer id);
}
