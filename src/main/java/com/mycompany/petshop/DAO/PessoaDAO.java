/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.petshop.DAO;

import com.mycompany.petshop.Objetos.Pessoa;
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
public class PessoaDAO {
    Connection conn;
    ManipulaData md;
    
    public PessoaDAO()
        {
            conn = new Conexao().conectar();
            md = new ManipulaData();
        }
    
    public Pessoa salvar(Pessoa p){
        try{
            PreparedStatement stmt = conn.prepareStatement
            ("INSERT INTO pessoa(nome, data_nascimento) values(?,?)",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getNome());
            stmt.setDate(2, md.string2Date(p.getData()));
            stmt.execute(); // Executa o SQL no banco
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next())
            {
                p.setIdPessoa(rs.getInt("idpessoa"));
            }else{
                p.setIdPessoa(-1);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return p;
    }

    public void editar(Pessoa p)
    {
        try
        {
            PreparedStatement stmt = conn.prepareStatement
            ("UPDATE pessoa SET nome = ?, data_nascimento = ? "
                    + "WHERE idpessoa = ?");
            stmt.setString(1, p.getNome());
            stmt.setDate(2, md.string2Date(p.getData()));
            stmt.setInt(3, p.getIdPessoa());
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    
    public int excluir(Pessoa p){
        int verif = 0;
        try
        {
            PreparedStatement stmt = conn.prepareStatement
            ("DELETE FROM pessoa WHERE idpessoa = ?");
            stmt.setInt(1, p.getIdPessoa());
            verif = stmt.executeUpdate();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();

        }
        
        return verif;
    }
    
    public Pessoa getPessoa(ResultSet rs) throws SQLException{
        Pessoa p = new Pessoa();
        
        p.setIdPessoa(rs.getInt("idPessoa"));
        p.setNome(rs.getString("nome"));
        p.setData(md.date2String(rs.getString("data_nascimento")));
        
        return p;
    }

    public Pessoa getPessoa(int idPessoa){
        try{
            PreparedStatement ppStmt = conn.prepareStatement
            ("SELECT * FROM pessoa WHERE idpessoa = ?");
            ppStmt.setInt(1, idPessoa);

            ResultSet rs = ppStmt.executeQuery();
            if(rs.next()){
                return getPessoa(rs);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }
    
    public List<Pessoa> getPessoas(){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;
        
        try{
                PreparedStatement ppStmt = conn.prepareStatement
        ("SELECT * FROM pessoa");


        rs = ppStmt.executeQuery();


        while(rs.next()){
            lstP.add(getPessoa(rs));
        }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return lstP;
    }
    
     public List<Pessoa> getPessoas(Pessoa p){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;
        
        try{
                PreparedStatement ppStmt = conn.prepareStatement
        ("SELECT * FROM pessoa WHERE nome ILIKE ?");
        ppStmt.setString(1, p.getNome() + "%");
                
        rs = ppStmt.executeQuery();
        
        while(rs.next()){
            lstP.add(getPessoa(rs));
        }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return lstP;
    }

    public List<Pessoa> getPessoas(String nome){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;

        try{
            PreparedStatement ppStmt = conn.prepareStatement
            ("SELECT * FROM pessoa WHERE nome ILIKE ?");
            ppStmt.setString(1, nome + "%");

            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPessoa(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return lstP;
    }

    public List<Pessoa> getPessoas(String nome, String dataInicio, String dataFim){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;

        try{
            PreparedStatement ppStmt = conn.prepareStatement
            ("SELECT * FROM pessoa WHERE nome ILIKE ? AND data_nascimento BETWEEN ? AND ?");
            ppStmt.setString(1, nome + "%");
            ppStmt.setDate(2, md.string2Date(dataInicio));
            ppStmt.setDate(3, md.string2Date(dataFim));

            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPessoa(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return lstP;
    }

    public List<Pessoa> getPessoas(String dataInicio, String dataFim){
        List<Pessoa> lstP = new ArrayList<>();
        ResultSet rs;

        try{
            PreparedStatement ppStmt = conn.prepareStatement
            ("SELECT * FROM pessoa WHERE data_nascimento BETWEEN ? AND ?");
            ppStmt.setDate(1, md.string2Date(dataInicio));
            ppStmt.setDate(2, md.string2Date(dataFim));

            rs = ppStmt.executeQuery();
            while(rs.next()){
                lstP.add(getPessoa(rs));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return lstP;
    }
    
}
