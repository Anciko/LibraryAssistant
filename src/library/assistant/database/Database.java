/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.assistant.util.DatabaseConfigLoader;
import library.assistant.util.DatabaseProperty;

/**
 *
 * @author DELL
 */
public class Database {
    private static Database db;
    private  Connection conn;
    
    private Database () throws SQLException{
        connect();
        createDatabase();
        createTables();
    }
    
    public static Database getInstance() throws SQLException{
        if(db == null){
            db=new Database();
        }
        return db;
    }
    
    public void connect() throws SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DatabaseConfigLoader loader = new DatabaseConfigLoader();
        DatabaseProperty dbProp = loader.getDatabaseProperties();
        String host = dbProp.getHost();
        String port = dbProp.getPort();  
        String user = dbProp.getUser();
        String password =   dbProp.getPassword();  
        
        
        conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/",user,password);
    }
    
    public void createDatabase() throws SQLException{
        Statement stmt1 = conn.createStatement();
        stmt1.execute("create database if not exists lbdb");
    }
    
    public void createTables() throws SQLException{
        Statement stmt1 = conn.createStatement();
        stmt1.execute("create table if not exists lbdb.books (id varchar(45) primary key,title varchar(40),author varchar(40),publisher varchar(40),available boolean default true)" );
    
        Statement stmt2 = conn.createStatement();
        stmt2.execute("create table if not exists lbdb.members (id varchar(45) primary key,name varchar(40),email varchar(40),mobile varchar(40), address varchar(40))" );
        Statement stmt3 = conn.createStatement();
        stmt3.execute("create table if not exists lbdb.issue (member_id varchar(45),book_id varchar(45),issue_date date,renew_count int,foreign key(member_id)references members(id),foreign key(book_id) references books(id)) ");
    
    }
    
    public Connection getConnection(){
        return conn;
    }
}
