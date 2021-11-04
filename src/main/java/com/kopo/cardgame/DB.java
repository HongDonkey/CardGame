package com.kopo.cardgame;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	public String accountCreateDB(Member mb) { // 계정별 테이블 생성
		this.openCardGame();
		String result = "fail";
		

		try {
			String query = "CREATE TABLE {0} (idx INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, atk INTEGER, def INTEGER, atk_rate INTEGER, def_rate INTEGER, tribe TEXT)";
			
			Statement statement = conn.createStatement();
			statement.execute(MessageFormat.format(query, mb.id));

			statement.close();
			result = "success";

		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

		this.close();
		return result;

	}

	public String accountDropTable(Member mb) { // 회원삭제 시 계정의 테이블 삭제
		this.openCardGame();
		String result = "fail";
		

		try {
			// String query = "DROP TABLE ? CASCADE CONSTRAINTS";

			
			// PreparedStatement prestatement = conn.prepareStatement(query);
			// prestatement.setString(1, mb.id);

			PreparedStatement prestatement = conn.prepareStatement(
			String.format("DROP TABLE IF EXISTS %s", mb.id));
			prestatement.execute();

			prestatement.close();
			result = "success";

		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

		this.close();
		return result;

	}

	public String insertMember(Member mb) { // 회원가입
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

	public Member updateMember(Member mb) { // 정보수정 페이지 불러오기
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

	public String memberUpdateAction(Member mb) { // 정보 수정 쿼리
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

	public Member adminMemberUpdate(Member mb) { // 정보수정 페이지 불러오기
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

	public String deleteMember(Member mb) {
		this.openCardGame();
		String message = "success";
		Member result = null;

		try {
			String query = "DELETE FROM member WHERE id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, mb.id);

			int r = preparedStatement.executeUpdate();
			// DELTE 쿼리는 excuteUpdate 메서드 사용
			preparedStatement.close();

		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";
			return message;
		}
		this.close();
		return message;
	}

	public String insertCard(Card card) { // 회원가입
		this.openCardGame();
		String message = "success";

		try {
			String query2 = "SELECT * FROM card WHERE name = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query2);
			preparedStatement.setString(1, card.name);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// resultSet에 SELECT 쿼리로 요청한 카드이름을 받아왔다는 건
				// 등록된 카드이름이 있다는 뜻이므로 중복체크에 걸림
				preparedStatement.close();
				conn.close();
				message = "distinct";
				return message;

			}

			String query = "INSERT INTO card (name, atk, def, atk_rate, def_rate, tribe) VALUES(?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = this.conn.prepareStatement(query);
			statement.setString(1, card.name);
			statement.setInt(2, card.atk);
			statement.setInt(3, card.def);
			statement.setInt(4, card.atk_rate);
			statement.setInt(5, card.def_rate);
			statement.setString(6, card.tribe);

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

	public ArrayList<Card> manageCard() {
		this.openCardGame();

		ArrayList<Card> results = new ArrayList<Card>();
		String query = "SELECT * FROM card";

		try {
			PreparedStatement statement = this.conn.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				int atk = resultSet.getInt("atk");
				int def = resultSet.getInt("def");
				int atk_rate = resultSet.getInt("atk_rate");
				int def_rate = resultSet.getInt("def_rate");
				String tribe = resultSet.getString("tribe");
				results.add(new Card(idx, name, atk, def, atk_rate, def_rate, tribe));
			}
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		this.close();
		return results;
	}

	public Card adminCardUpdate(Card card) { // 정보수정 페이지 불러오기
		this.openCardGame();
		
		Card result = null;
		try {
			String query = "SELECT * FROM card WHERE idx=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, card.idx);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// resultSet.next()가 있다는 것은 중복을 의미
				// 아이디와 패스워드가 DB에 있는 값과 중복되어야만 함

				result = new Card();
				result.idx = resultSet.getInt("idx");
				result.name = resultSet.getString("name");
				result.atk = resultSet.getInt("atk");
				result.def = resultSet.getInt("def");
				result.atk_rate = resultSet.getInt("atk_rate");
				result.def_rate = resultSet.getInt("def_rate");
				result.tribe = resultSet.getString("tribe");
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

	public String cardUpdateAction(Card card) { // 정보 수정 쿼리
		this.openCardGame();
		String result = "fail";

		try {
			String query = "UPDATE card SET name=?, atk=?, def=?, atk_rate=?, def_rate=?, tribe=? WHERE idx=?";

			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, card.name);
			preparedStatement.setInt(2, card.atk);
			preparedStatement.setInt(3, card.def);
			preparedStatement.setInt(4, card.atk_rate);
			preparedStatement.setInt(5, card.def_rate);
			preparedStatement.setString(6, card.tribe);
			preparedStatement.setInt(7, card.idx);

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

	public String deleteCard(Card card) {
		this.openCardGame();
		String message = "success";
		Card result = null;

		try {
			String query = "DELETE FROM card WHERE idx=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, card.idx);

			int r = preparedStatement.executeUpdate();
			// DELTE 쿼리는 excuteUpdate 메서드 사용
			preparedStatement.close();

		} catch (Exception e) {
			e.printStackTrace();
			message = "fail";
			return message;
		}
		this.close();
		return message;
	}

	public ArrayList<Card> receiveCard() {
		this.openCardGame();

		ArrayList<Card> results = new ArrayList<Card>();
		String query = "SELECT * FROM card WHERE idx = ?";

		try {
			PreparedStatement statement = this.conn.prepareStatement(query);
			Random random = new Random();
			int rdIdx = random.nextInt(18);
			statement.setInt(1, rdIdx);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idx = resultSet.getInt("idx");
				String name = resultSet.getString("name");
				int atk = resultSet.getInt("atk");
				int def = resultSet.getInt("def");
				int atk_rate = resultSet.getInt("atk_rate");
				int def_rate = resultSet.getInt("def_rate");
				String tribe = resultSet.getString("tribe");

				results.add(new Card(idx, name, atk, def, atk_rate, def_rate, tribe));
			}
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		this.close();
		return results;
	}

	public String insertMyCard(Member mb, Card card) { // 회원가입
		this.openCardGame();
		String message = "success";
			try{

			String query = "INSERT INTO ? (name, atk, def, atk_rate, def_rate, tribe) VALUES(?, ?, ?, ?, ?, ?)";

			PreparedStatement statement = this.conn.prepareStatement(query);
			statement.setString(1, mb.id);
			statement.setString(2, card.name);
			statement.setInt(3, card.atk);
			statement.setInt(4, card.def);
			statement.setInt(5, card.atk_rate);
			statement.setInt(6, card.def_rate);
			statement.setString(7, card.tribe);

			int r = statement.executeUpdate();
			statement.close();
			message = "success";
			} catch(Exception e){

			}

		this.close();
		return message;
	}

}
