package com.mycompany.estoque.service;

import com.mycompany.estoque.DAO.ProductDAO;
import com.mycompany.estoque.db.DbConfig;
import com.mycompany.estoque.exception.DbException;
import com.mycompany.estoque.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductDAO {

    private Connection connection;

    public ProductServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Product> getAll() {
        String query = "select * from estoque order by id";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<Product>();
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4)));
            }
            return products;
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
    }
    
    public Product getById(Integer id) {
        String query = "select * from estoque where id = ?";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;       
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
            }            
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
        return null;
    }
    

    public Product createProduct(Product product) {
        String query = "insert into estoque (name, price, quantity) values(?, ?, ?)";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
        return null;
    }

    public Product updateProduct(Product product) {
        String query = "update estoque set name = ?, price = ?, quantity = ? where id = ?";
        connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setInt(4, product.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
                }
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
            DbConfig.closeResultSet(resultSet);
        }
        return null;
    }

    public String deleteProduct(Integer id) {
        String query = "delete from estoque where id = ?";
        connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConfig.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return "Product was delected.";
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e.getCause());
        } finally {
            DbConfig.closeConnection(connection);
            DbConfig.closePreparedStatement(preparedStatement);
        }
        return "E!";
    }
}
