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
            if (id != null) {
                return "redirect:/my_card";
            }
            else {
                return "signin"; 
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
    public String my_card(Locale locale, Model model) {
        return "my_card";
    }

}
