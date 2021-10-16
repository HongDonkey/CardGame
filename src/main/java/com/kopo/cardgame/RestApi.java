package com.kopo.cardgame;

import java.util.HashMap;

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

    @RequestMapping(value="/signup_action")
    public HashMap<String, String> signup_action(@RequestParam("id") String id, @RequestParam("password") String password, @RequestParam("name") String name) {
		HashMap<String, String> result = new HashMap<String, String>();

        DB db = new DB();
        String message = db.insertMember(new Member(id, password, name));        
		result.put("message", message);
		return result;
	}

	@RequestMapping("/login_api")
	public String login_api() {
        DB db = new DB();       
		return db.login_api();
	}

}