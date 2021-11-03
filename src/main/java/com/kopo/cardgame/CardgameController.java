package com.kopo.cardgame;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CardgameController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String signin(Locale locale, Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id == null) {
                return "signin";
            } else {
                return "my_card";
            }

        } catch (Exception e) {
        }

        return "signin";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Locale locale, Model model) {
        return "signup";
    }

    @RequestMapping(value = "/my_card", method = RequestMethod.GET)
    public String my_card(Locale locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id == null) { //세션에 ID정보가 없으면 로그인화면으로
                return "signin";
            } else {
                return "my_card";
            }

        } catch (Exception e) {
        }

        return "signin";

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(Locale locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id == null) {
                return "signin";
            } else {
                return "update";
            }

        } catch (Exception e) {
        }

        return "signin";

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String manageMb(Locale Locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id.equals("admin")) { //admin 계정으로 로그인했다면
                System.out.println(id);
                return "manageMb";
            } else if (id != null) { //세션에 id값은 있으나 admin이 아닐 경우(일반 회원)
                return "my_card";
            }

        } catch (Exception e) {
        }

        return "signin";
    }

    @RequestMapping(value = "/admin_member_update", method = RequestMethod.GET)
    public String adminUpdate(Locale Locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id.equals("admin")) {
                System.out.println(id);
                return "admin_member_update";
            } else if (id != null) {
                return "my_card";
            }

        } catch (Exception e) {
        }

        return "signin";
    }

    @RequestMapping(value = "/card_create", method = RequestMethod.GET)
    public String card_create(Locale Locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id.equals("admin")) {
                System.out.println(id);
                return "card_create";
            } else if (id != null) {
                return "my_card";
            }

        } catch (Exception e) {
        }

        return "signin";
    }

    @RequestMapping(value = "/card_manage", method = RequestMethod.GET)
    public String manageCard(Locale Locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id.equals("admin")) { //admin 계정으로 로그인했다면
                System.out.println(id);
                return "manageCard";
            } else if (id != null) { //세션에 id값은 있으나 admin이 아닐 경우(일반 회원)
                return "my_card";
            }

        } catch (Exception e) {
        }

        return "signin";
    }

    @RequestMapping(value = "/admin_card_update", method = RequestMethod.GET)
    public String adminUpdateCard(Locale Locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id.equals("admin")) {
                System.out.println(id);
                return "admin_card_update";
            } else if (id != null) {
                return "my_card";
            }

        } catch (Exception e) {
        }

        return "signin";
    }

}
