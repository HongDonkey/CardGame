package com.kopo.cardgame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.sqlite.SQLiteConfig;

public class DB {


    private Connection conn = null;
    private void openCard() {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            this.conn = DriverManager.getConnection("jdbc:sqlite:/c:/tomcat/card.db", config.toProperties());
        } catch (Exception e){
            e.printStackTrace();
            //오류가 났을 때 해당 부분을 알려줌
        }
    }

    private void openMember() {
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            this.conn = DriverManager.getConnection("jdbc:sqlite:/c:/tomcat/member.db", config.toProperties());
        } catch (Exception e){
            e.printStackTrace();
            //오류가 났을 때 해당 부분을 알려줌
        }
    }

    private void close() {
        if(this.conn != null) {
            try{
                this.conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String excuteCardQuery(String query) {
        this.openCard();
        String message = "success";
        try { 
            Statement statement = this.conn.createStatement();
            int r = statement.executeUpdate(query);
            statement.close();

        } catch (Exception e){
            e.printStackTrace();
            message = "fail";

        }
        
	this.close();
    return message;
	}

    public String excuteMemberQuery(String query) {
        this.openMember();
        String message = "success";
        try { 
            Statement statement = this.conn.createStatement();
            int r = statement.executeUpdate(query);
            statement.close();

        } catch (Exception e){
            e.printStackTrace();
            message = "fail";

        }
        
	this.close();
    return message;
	}

    

    public String insertMember(Member mb) {
        this.openMember();
        String message = "success";

        try { 
        String query2 = "SELECT * FROM member WHERE id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query2);
			preparedStatement.setString(1, mb.id);
			ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) { 
                //resultSet에 SELECT 쿼리로 요청한 ID값을 받아왔다는 건
                //기존에 등록된 ID가 있다는 뜻이므로 중복체크에 걸림
                preparedStatement.close();
                conn.close();
                message = "distinct";
                return message;

            }
 
        
        String query = "INSERT INTO member (id, password, name, created, updated) VALUES(?, ?, ?, ?, ?)";
        

        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String nowString = sdf.format(now);
        
        
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, mb.id);
            statement.setString(2, mb.password);
            statement.setString(3, mb.name);
            statement.setString(4, nowString);
            statement.setString(5, nowString);

            int r = statement.executeUpdate();
            statement.close();
            message = "success";

        } catch (Exception e){
            e.printStackTrace();
            message = "fail";
            return message;

        }
        
	this.close();
    return message;
	}
    

    public String insertCard(Card card) {
        this.openCard();
        String message = "success";
        String query = "INSERT INTO card (name, atk, def, atkRate, defRate) VALUES(?, ?, ?, ?, ?)";
        
        try { 
            PreparedStatement statement = this.conn.prepareStatement(query);
            statement.setString(1, card.name);
            statement.setInt(2, card.atk);
            statement.setInt(3, card.def);
            statement.setInt(4, card.atkRate);
            statement.setInt(5, card.defRate);
 
            int r = statement.executeUpdate();
            statement.close();

        } catch (Exception e){
            e.printStackTrace();
            message = "fail";

        }
        
	this.close();
    return message;
	}

    public String login_api() {
        this.openMember();
        String message = "fail";
    
        ArrayList<Member> results = new ArrayList<Member>();
        String query = "SELECT * FROM member WHERE id=? and password=?";

        try { 
            PreparedStatement statement = this.conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String id = resultSet.getString("id");
                String password = resultSet.getString("password");
                results.add(new Member(id, password));
                message = "success";
            }
            statement.close();

        } catch (Exception e){
            e.printStackTrace();

        } 
        
        this.close();
        return message;

    }
    
    
}
