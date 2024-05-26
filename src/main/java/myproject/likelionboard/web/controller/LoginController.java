package myproject.likelionboard.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Member;
import myproject.likelionboard.domain.service.LoginService;
import myproject.likelionboard.web.form.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    public static final String LOGIN_KEY = "loginKey";
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String Login(@Validated @ModelAttribute("loginForm")LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request){

        if(bindingResult.hasErrors()){
            log.info("login errors={}",bindingResult);
            return "login/loginForm";
        }

        if(loginService.login(loginForm.getLoginId(), loginForm.getPassword()).isPresent()){
            Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword()).get();
            HttpSession session = request.getSession();
            session.setAttribute(LOGIN_KEY, loginMember);
            //return "redirect:/boards";
            return "redirect:/";
        }else{
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return "login/loginForm";
        }
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }
}
