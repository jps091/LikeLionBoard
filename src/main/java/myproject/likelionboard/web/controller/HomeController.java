package myproject.likelionboard.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String homeLogin(HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if(session == null){
            return "home";
        }

        Member loginMember = (Member) session.getAttribute("loginKey");
        if(loginMember == null){
            return "home";
        }
        log.info("login Home Member={}",loginMember);
        return "redirect:/boards";
    }
}
