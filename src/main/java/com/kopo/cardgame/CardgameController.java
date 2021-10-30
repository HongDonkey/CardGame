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
            }
            else {
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
            if (id == null) {
                return "signin";
            }
            else {
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
            }
            else {
                return "update"; 
            }

        } catch (Exception e) {
        }

        return "signin";
    
    }

    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String manageMb(Locale Locale, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            String id = (String) session.getAttribute("id");
            System.out.println(id);
            if (id.equals("admin")) {
                System.out.println(id);
                return "manageMb";
            }
            else if(id != null){
                return "my_card"; 
            }

        } catch (Exception e) {
        }

        return "signin";
    }




@RequestMapping(value="/admin_updatePage", method=RequestMethod.GET)
public String adminUpdate(Locale Locale, Model model, HttpServletRequest request) {
    HttpSession session = request.getSession();
    try {
        String id = (String) session.getAttribute("id");
        System.out.println(id);
        if (id.equals("admin")) {
            System.out.println(id);
            return "admin_update";
        }
        else if(id != null){
            return "my_card"; 
        }

    } catch (Exception e) {
    }

    return "signin";
}


}
