/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.petshop.BO;

import com.mycompany.petshop.DAO.PetDAO;
import com.mycompany.petshop.Objetos.Pet;
import java.util.List;

/**
 *
 * @author vitin
 */

public class PetBO {
    PetDAO petDAO;
    
    public PetBO(){
        petDAO = new PetDAO();
    }
    
    public Pet salvar(Pet p){
        return petDAO.salvar(p);
    }
    
    public void editar(Pet p){
        petDAO.editar(p);
    }
    
    public int excluir(Pet p){
        return petDAO.excluir(p);
    }
    
    public List<Pet> getPets(){
        return petDAO.getPets();
    }
    
    public List<Pet> getPets(String nome){
        return petDAO.getPets(nome);
    }
    
    public List<Pet> getPets(String nome, String dataInicio, String dataFim){
        return petDAO.getPets(nome, dataInicio, dataFim);
    }
    
    public List<Pet> getPets(String dataInicio, String dataFim){
        return petDAO.getPets(dataInicio, dataFim);
    }
}
