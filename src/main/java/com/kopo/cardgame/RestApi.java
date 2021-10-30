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

	@RequestMapping("/createCard")
	public HashMap<String, String> createCard() {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String query = "CREATE TABLE card (idx INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, atk INTEGER, def INTEGER, atkRate INTEGER, defRate INTEGER)";
		String message = db.excuteCardQuery(query);

		result.put("message", message);
		return result;
	}

	@RequestMapping("/createMember")
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
		result.put("message", message);
		return result;
	}

	@RequestMapping("/logout_api")
	public static void logout(HttpServletRequest request) {
		request.getSession().invalidate();
		request.getSession(true);

	}

	@RequestMapping("/update_action")
	public HashMap<String, String> update_action(@RequestParam("id") String id,
			@RequestParam("password") String password, @RequestParam("name") String name) {
		HashMap<String, String> result = new HashMap<String, String>();

		DB db = new DB();
		String message = db.updateAction(new Member(id, password, name));
		result.put("message", message);
		return result;
	}

	@RequestMapping("/manage_member")
	public ArrayList<Member> api_select() {
		DB db = new DB();
		return db.manageMember();
	}

	@RequestMapping("/admin_update")
	public Member admin_update(@RequestParam("id") String id, HttpServletRequest request) {

		HttpSession session = request.getSession(); // 최초 사용자 로그인 성공 후 세션 값 생성
		Member loginMember = new Member(id);
		DB db = new DB();
		Member result = db.adminUpdate(loginMember);
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



}
