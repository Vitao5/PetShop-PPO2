/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.petshop.DAO;

import com.mycompany.petshop.Objetos.Pet;
import com.mycompany.petshop.Util.Conexao;
import com.mycompany.petshop.Util.ManipulaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vitin
 */

public class PetDAO {
    Connection conn;
    ManipulaData md;
    PessoaDAO pDAO;

    public PetDAO() {
        conn = new Conexao().conectar();
        md = new ManipulaData();
        pDAO = new PessoaDAO();
    }

    public Pet salvar(Pet pet) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO pet(nome, raca, data_nascimento, cor, porte, idpessoa) VALUES(?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getRaca());
            stmt.setDate(3, md.string2Date(pet.getData_nascimento()));
            stmt.setString(4, pet.getCor());
            stmt.setString(5, pet.getPorte());
            stmt.setInt(6, pet.getPessoa().getIdPessoa());

            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pet.setIdpet(rs.getInt("idpet"));
            } else {
                pet.setIdpet(-1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return pet;
    }
    
    public int excluir(Pet pet) {
        int verif = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM pet WHERE idpet = ?");
            stmt.setInt(1, pet.getIdpet());
            verif = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return verif;
    }

    public void editar(Pet p) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE pet SET nome = ?, data_nascimento = ?, raca = ?, cor = ?, porte = ?, idpessoa = ? WHERE idpet = ?"
            );

            stmt.setString(1, p.getNome());
            stmt.setDate(2, md.string2Date(p.getData_nascimento()));
            stmt.setString(3, p.getRaca());
            stmt.setString(4, p.getCor());
            stmt.setString(5, p.getPorte());
            stmt.setInt(6, p.getPessoa().getIdPessoa());
            stmt.setInt(7, p.getIdpet());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Pet> getPets() {
        List<Pet> lstPets = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement("SELECT *, pes.nome AS nome_pessoa, pes.data_nascimento AS data_pessoa "
                       + "FROM pet p INNER JOIN pessoa pes ON p.idpessoa = pes.idpessoa");
            rs = ppStmt.executeQuery();

            while (rs.next()) {
                lstPets.add(getPet(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstPets;
    }

    public List<Pet> getPets(String nome) {
        List<Pet> lstPets = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM pet WHERE nome ILIKE ?");
            ppStmt.setString(1, nome + "%");
            rs = ppStmt.executeQuery();

            while (rs.next()) {
                lstPets.add(getPet(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstPets;
    }

    public List<Pet> getPets(String nome, String dataInicio, String dataFim) {
        List<Pet> lstPets = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM pet "
                    + "WHERE nome ILIKE ? AND data_nascimento BETWEEN ? AND ?");
            ppStmt.setString(1, nome + "%");
            ppStmt.setDate(2, md.string2Date(dataInicio));
            ppStmt.setDate(3, md.string2Date(dataFim));
            rs = ppStmt.executeQuery();

            while (rs.next()) {
                lstPets.add(getPet(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstPets;
    }
    
    public List<Pet> getPets(String dataInicio, String dataFim) {
        List<Pet> lstPets = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement ppStmt = conn.prepareStatement("SELECT * FROM pet "
                    + "WHERE data_nascimento BETWEEN ? AND ?");
            ppStmt.setDate(1, md.string2Date(dataInicio));
            ppStmt.setDate(2, md.string2Date(dataFim));
            rs = ppStmt.executeQuery();

            while (rs.next()) {
                lstPets.add(getPet(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lstPets;
    }

    private Pet getPet(ResultSet rs) throws SQLException {
        Pet pet = new Pet();
        pet.setIdpet(rs.getInt("idpet"));
        pet.setNome(rs.getString("nome"));
        pet.setRaca(rs.getString("raca"));
        pet.setData_nascimento(md.date2String(rs.getString("data_nascimento")));
        pet.setCor(rs.getString("cor"));
        pet.setPorte(rs.getString("porte"));

        pet.setPessoa(pDAO.getPessoa(rs.getInt("idPessoa")));
        return pet;
    }
}