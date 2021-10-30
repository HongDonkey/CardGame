package com.kopo.cardgame;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.common.hash.Hashing;

import org.sqlite.SQLiteConfig;

public class DB {

	private Connection conn = null;

	private void openCardGame() {
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();
			this.conn = DriverManager.getConnection("jdbc:sqlite:/c:/tomcat/cardgame.db", config.toProperties());
		} catch (Exception e) {
			e.printStackTrace();
			// 오류가 났을 때 해당 부분을 알려줌
		}
	}

	private void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String excuteCardQuery(String query) {
		this.openCardGame();
		String message = "success";
		try {
			Statement statement = this.conn.createStatement();
			int r = statement.executeUpdate(query);
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";

		}

		this.close();
		return message;
	}

	public String excuteMemberQuery(String query) {
		this.openCardGame();
		String message = "success";
		try {
			Statement statement = this.conn.createStatement();
			int r = statement.executeUpdate(query);
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";

		}

		this.close();
		return message;
	}

	public String insertMember(Member mb) { //회원가입
		this.openCardGame();
		String message = "success";

		try {
			String query2 = "SELECT * FROM member WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query2);
			preparedStatement.setString(1, mb.id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// resultSet에 SELECT 쿼리로 요청한 ID값을 받아왔다는 건
				// 기존에 등록된 ID가 있다는 뜻이므로 중복체크에 걸림
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
			mb.password = Hashing.sha256().hashString(mb.password, StandardCharsets.UTF_8).toString();
			statement.setString(2, mb.password);
			statement.setString(3, mb.name);
			statement.setString(4, nowString);
			statement.setString(5, nowString);

			int r = statement.executeUpdate();
			statement.close();
			message = "success";

		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";
			return message;

		}

		this.close();
		return message;
	}

	public String insertCard(Card card) {
		this.openCardGame();
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

		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";

		}

		this.close();
		return message;
	}

	public Member selectMember(Member mb) { // 로그인
		this.openCardGame();
		boolean is_login = false;
		Member result = null;

		try {
			String query = "SELECT * FROM member WHERE id=? and password=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, mb.id);
			mb.password = Hashing.sha256().hashString(mb.password, StandardCharsets.UTF_8).toString();
			preparedStatement.setString(2, mb.password);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// resultSet.next()가 있다는 것은 중복을 의미
				// 아이디와 패스워드가 DB에 있는 값과 중복되어야만 함

				is_login = true;
				result = new Member();
				result.idx = resultSet.getInt("idx");
				result.name = resultSet.getString("name");
				result.id = resultSet.getString("id");
				preparedStatement.close();
				conn.close();
				return result;

			}

		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

		this.close();
		return result;

	}

	public Member updateMember(Member mb) { //정보수정 페이지 불러오기
		this.openCardGame();
		boolean is_login = false;
		Member result = null;
		try {
			String query = "SELECT * FROM member WHERE idx=? and id=? and name=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, mb.idx);
			preparedStatement.setString(2, mb.id);
			preparedStatement.setString(3, mb.name);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// resultSet.next()가 있다는 것은 중복을 의미
				// 아이디와 패스워드가 DB에 있는 값과 중복되어야만 함

				is_login = true;
				result = new Member();
				result.idx = resultSet.getInt("idx");
				result.name = resultSet.getString("name");
				result.id = resultSet.getString("id");
				preparedStatement.close();
				conn.close();
				return result;

			}

		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}


		this.close();
		return result;

	}
	public String updateAction(Member mb) { //정보 수정 쿼리
		this.openCardGame();
		String result = "success";

		try {
			String query = "UPDATE member SET password=?, name=?, updated=? WHERE id=?";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String nowString = sdf.format(now);

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			mb.password = Hashing.sha256().hashString(mb.password, StandardCharsets.UTF_8).toString();
			preparedStatement.setString(1, mb.password);
			preparedStatement.setString(2, mb.name);
			preparedStatement.setString(3, nowString);
			preparedStatement.setString(4, mb.id);
			
			int r = preparedStatement.executeUpdate();
			preparedStatement.close();
			result = "success";
			



		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

		this.close();
		return result;

	}
	public ArrayList<Member> manageMember() {
		this.openCardGame();

		ArrayList<Member> results = new ArrayList<Member>();
		String query = "SELECT idx, id, name, created, updated FROM member";

		try {
			PreparedStatement statement = this.conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				String created = resultSet.getString("created");
				String updated = resultSet.getString("updated");
				results.add(new Member(idx, id, name, created, updated));
			}
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		this.close();
		return results;
	}

	public Member adminUpdate(Member mb) { //정보수정 페이지 불러오기
		this.openCardGame();
		boolean is_login = false;
		Member result = null;
		try {
			String query = "SELECT * FROM member WHERE id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, mb.id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// resultSet.next()가 있다는 것은 중복을 의미
				// 아이디와 패스워드가 DB에 있는 값과 중복되어야만 함

				is_login = true;
				result = new Member();
				result.idx = resultSet.getInt("idx");
				result.name = resultSet.getString("name");
				result.id = resultSet.getString("id");
				preparedStatement.close();
				conn.close();
				return result;

			}

		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}


		this.close();
		return result;

	}

	
}
