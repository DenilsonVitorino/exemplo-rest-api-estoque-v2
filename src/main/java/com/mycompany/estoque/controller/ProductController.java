package com.mycompany.estoque.controller;

import com.google.gson.Gson;
import com.mycompany.estoque.DAO.DAOFactory;
import com.mycompany.estoque.DAO.ProductDAO;
import com.mycompany.estoque.model.Product;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("products")
public class ProductController {

    Gson gson;
    ProductDAO productDAO;

    public ProductController() {
        gson = new Gson();
        productDAO = DAOFactory.createProductDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {                
        return productDAO.getAll();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Integer id) { 
        Product product = productDAO.getById(id);
        if (product != null) {
            return Response.status(Response.Status.OK).entity(product).build();            
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }   
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response createProduct(Product product) {
        Product productCreated = productDAO.createProduct(product);
        if (productCreated != null) {
            return Response.status(Response.Status.CREATED).entity(productCreated).build();            
        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        }        
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response updateProduct(Product product) {
        Product productUpdated = productDAO.updateProduct(product);
        if (productUpdated != null) {
            return Response.status(Response.Status.OK).entity(productUpdated).build();            
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }        
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)    
    @Path("/{id}")  
    public Response deleteProduct(@PathParam("id") Integer id) {
        String message = productDAO.deleteProduct(id);
        if (message != "E!") {
            return Response.status(Response.Status.NO_CONTENT).build();            
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }        
    }
}

