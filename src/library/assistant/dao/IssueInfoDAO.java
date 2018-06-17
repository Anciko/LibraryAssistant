/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import library.assistant.database.Database;
import library.assistant.model.IssueInfo;


public class IssueInfoDAO {
    public void saveIssueInfo(IssueInfo issueInfo) throws SQLException{
          Database db = Database.getInstance();
        Connection conn = db.getConnection();
        db.connect();
        String sql = "insert into lbdb.issue (member_id,book_id,issue_date,renew_count) value (?,?,now(),0)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, issueInfo.getMemberId());
        stmt.setString(2, issueInfo.getBookId());
    
        stmt.execute();

    } 
    
    public IssueInfo getIssueInfo(String bookId) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        String sql = "select * from lbdb.issue where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,bookId);
        ResultSet result = stmt.executeQuery();
        IssueInfo issueInfo = null;
        
        if(result.next()){
            String memberId = result.getString("member_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");
            issueInfo = new IssueInfo(memberId, bookId, issueDate, renewCount);
        }
        return issueInfo;
    }

    public void deleteIssueInfo(String bookId) throws SQLException {
       Connection conn = Database.getInstance().getConnection();
       String sql = "delete from lbdb.issue where book_id=?";
       PreparedStatement stmt = conn.prepareStatement(sql);
       stmt.setString(1,bookId);
       stmt.execute();
    }

    public void updateRenewCount(String bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
       String sql = "update lbdb.issue set renew_count=renew_count+1 where book_id=?";
       PreparedStatement stmt = conn.prepareStatement(sql);
       stmt.setString(1,bookId);
       stmt.execute();
    }
}
