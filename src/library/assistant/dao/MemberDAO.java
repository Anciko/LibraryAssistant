/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.assistant.database.Database;
import library.assistant.model.Book;
import library.assistant.model.Member;

/**
 *
 * @author DELL
 */
public class MemberDAO {

    public void saveMember(Member member) throws SQLException {

        Database db = Database.getInstance();
        Connection conn = db.getConnection();
        db.connect();
        String sql = "insert into lbdb.members (id,name,email,mobile,address) value (?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, member.getId());
        stmt.setString(2, member.getName());
        stmt.setString(3, member.getEmail());
        stmt.setString(4, member.getMobile());
        stmt.setString(5, member.getAddress());

        stmt.execute();

    }

    public ObservableList<Member> getMembers() throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.members";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        ObservableList<Member> list = FXCollections.observableArrayList();
        while (result.next()) {
            String id = result.getString("id");
            String name = result.getString("name");
            String email = result.getString("email");
            String mobile = result.getString("mobile");
            String address = result.getString("address");
            Member member = new Member(id, name, email, mobile, address);
            list.add(member);
        }

        return list;
    }

    public Member getMember(String id) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.members where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        ResultSet result = stmt.executeQuery();
        Member member = null;
        while (result.next()) {

            String name = result.getString("name");
            String email = result.getString("email");
            String mobile = result.getString("mobile");
            String address = result.getString("address");

            member = new Member(id, name, email, mobile, address);

        }

        return member;
    }
}
