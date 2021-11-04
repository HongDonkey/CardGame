package com.kopo.cardgame;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApi {

	@RequestMapping("/create_card_table")
	public HashMap<String, String> createCard() {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String query = "CREATE TABLE card (idx INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, atk INTEGER, def INTEGER, atk_rate INTEGER, def_rate INTEGER, tribe TEXT)";
		String message = db.excuteCardQuery(query);

		result.put("message", message);
		return result;
	}

	@RequestMapping("/create_member_table")
	public HashMap<String, String> createMember() {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String query = "CREATE TABLE member (idx INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, password TEXT, name TEXT, created TEXT, updated TEXT)";
		String message = db.excuteMemberQuery(query);

		result.put("message", message);
		return result;
	}

	@RequestMapping("/login_api")
	public Member login(HttpServletRequest request) {

		HttpSession session = request.getSession(); // 최초 사용자 로그인 성공 후 세션 값 생성
		String id = request.getParameter("user_id");
		String password = request.getParameter("user_password");

		Member loginMember = new Member(id, password);
		DB db = new DB();
		Member result = db.selectMember(loginMember);

		try {
			if (result != null) {
				session.setAttribute("is_login", true);
				session.setAttribute("idx", result.idx);
				session.setAttribute("id", result.id);
				session.setAttribute("name", result.name);
			} else {
				session.invalidate();
			}
		} catch (Exception e) {

		}

		return result;

	}

	@RequestMapping("/update_api")
	public Member update_api(HttpServletRequest request) {

		HttpSession session = request.getSession(); // 최초 사용자 로그인 성공 후 세션 값 생성
		int idx = (Integer) session.getAttribute("idx");
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("name");
		System.out.print(idx + id + name);
		Member loginMember = new Member(idx, id, name);
		DB db = new DB();
		Member result = db.updateMember(loginMember);
		try {
			if (result != null) {
				session.setAttribute("is_login", true);
				session.setAttribute("idx", result.idx);
				session.setAttribute("id", result.id);
				session.setAttribute("name", result.name);
			} else {
				session.invalidate();
			}
		} catch (Exception e) {

		}

		return result;

	}

	@RequestMapping("/signup_action")
	public HashMap<String, String> signup_action(@RequestParam("id") String id,
			@RequestParam("password") String password, @RequestParam("name") String name) {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String message = db.insertMember(new Member(id, password, name));
		String message2 = db.accountCreateDB(new Member(id)); 
		result.put("message", message);
		result.put("message2", message2);
		return result;
	}

	@RequestMapping("/logout_api")
	public static void logout(HttpServletRequest request) {
		request.getSession().invalidate();
		request.getSession(true);

	}

	@RequestMapping("/member_update_action")
	public HashMap<String, String> update_action(@RequestParam("id") String id,
			@RequestParam("password") String password, @RequestParam("name") String name) {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String message = db.memberUpdateAction(new Member(id, password, name));
		result.put("message", message);
		return result;
	}

	@RequestMapping("/manage_member")
	public ArrayList<Member> select_member() {
		DB db = new DB();
		return db.manageMember();
	}

	@RequestMapping("/admin_member_update_page") //admin 권한으로 업데이트 페이지 이동
	public Member admin_update_click(@RequestParam("id") String id, HttpServletRequest request) {

		HttpSession session = request.getSession(); // 최초 사용자 로그인 성공 후 세션 값 생성
		Member loginMember = new Member(id);
		DB db = new DB();
		Member result = db.adminMemberUpdate(loginMember);
		try {
			if (result != null) {
				// session.setAttribute("is_login", true);
				// session.setAttribute("mb_idx", result.idx);
				// session.setAttribute("mb_id", result.id);
				// session.setAttribute("mb_name", result.name);
			} else {
				session.invalidate();
			}
		} catch (Exception e) {

		}

		return result;

	}

	@RequestMapping("/delete_member") // DB 멤버 삭제
	public HashMap<String, String> delete_member(@RequestParam("id") String id, HttpServletRequest request) {
		HashMap<String, String> result = new HashMap<String, String>();

		HttpSession session = request.getSession();
		DB db = new DB();
		Member deleteMember = new Member(id);
		Member deleteDB = new Member(id);
		String message = db.deleteMember(deleteMember);
		String message2 = db.accountDropTable(deleteDB);

		result.put("message", message);
		result.put("message2", message2);
		return result;
	}

	@RequestMapping("/insert_card") // DB에 카드 정보 생성
	public HashMap<String, String> insert_card(@RequestParam("name") String name,
			@RequestParam("atk") int atk, 
			@RequestParam("def") int def,
			@RequestParam("atk_rate") int atk_rate,
			@RequestParam("def_rate") int def_rate,
			@RequestParam("tribe") String tribe) {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String message = db.insertCard(new Card(name, atk, def, atk_rate, def_rate, tribe));
		result.put("message", message);
		return result;
	}

	@RequestMapping("/card_manage_api")
	public ArrayList<Card> card_manage() {
		DB db = new DB();
		return db.manageCard();
		
	}

	@RequestMapping("/admin_card_update_page") // 카드 업데이트 페이지 호출
	public Card admin_card_update(@RequestParam("idx") int idx, HttpServletRequest request) {

		HttpSession session = request.getSession(); // 최초 사용자 로그인 성공 후 세션 값 생성
		Card editCard = new Card(idx);
		DB db = new DB();
		Card result = db.adminCardUpdate(editCard);
		try {
			if (result != null) {
				
			} else {
				session.invalidate();
			}
		} catch (Exception e) {

		}
		
		return result;

	}

	@RequestMapping("/card_update_action") // 카드 업데이트 동작
	public HashMap<String, String> card_update_action(@RequestParam("idx") int idx,
	@RequestParam("name") String name,
	@RequestParam("atk") int atk, 
	@RequestParam("def") int def,
	@RequestParam("atk_rate") int atk_rate,
	@RequestParam("def_rate") int def_rate,
	@RequestParam("tribe") String tribe) {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String message = db.cardUpdateAction(new Card(idx, name, atk, def, atk_rate, def_rate, tribe));
		result.put("message", message);
		return result;
	}

	@RequestMapping("/delete_card") // DB 카드 삭제
	public HashMap<String, String> delete_card(@RequestParam("idx") int idx, HttpServletRequest request) {
		HashMap<String, String> result = new HashMap<String, String>();

		HttpSession session = request.getSession();
		DB db = new DB();
		Card deleteCard = new Card(idx);
		String message = db.deleteCard(deleteCard);

		result.put("message", message);
		return result;
	}

	@RequestMapping("/makeCard")
	public ArrayList<Card> makeCard() { //카드뽑기(카드DB에서 하나의 행만 가져오기)
		DB db = new DB();
		return db.receiveCard();
	}

	@RequestMapping("/insertMyCard") // 개인 DB에 뽑은 카드 정보 입력
	public HashMap<String, String> insertMyCard(
			@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("atk") int atk, 
			@RequestParam("def") int def,
			@RequestParam("atk_rate") int atk_rate,
			@RequestParam("def_rate") int def_rate,
			@RequestParam("tribe") String tribe,
			HttpServletRequest reuest) {
		HashMap<String, String> result = new HashMap<String, String>();
	
		DB db = new DB();
		String message = db.insertMyCard(new Member(id), new Card(name, atk, def, atk_rate, def_rate, tribe));
		result.put("message", message);
		return result;
	}


}
