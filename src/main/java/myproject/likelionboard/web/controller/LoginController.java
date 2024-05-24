/*
package myproject.likelionboard.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.service.LoginService;
import myproject.likelionboard.web.form.DeleteForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

*/
/*    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") DeleteForm loginForm){
        return "login/loginForm";
    }*//*

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("deleteForm") DeleteForm form, @RequestParam("id") Long id,
                        BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        if(loginService.login(id, form.getPassword()).isPresent()){
            Board loginBoard = loginService.login(id, form.getPassword()).get();
            log.info("loginBoard={}",loginBoard);

            HttpSession session = request.getSession();
            session.setAttribute("loginBoard", loginBoard);

            redirectAttributes.addAttribute("id", id);
            return "redirect:/boards/{id}";
        }else{
            bindingResult.reject("loginFail", "비밀번호 오류");
            return "login/loginForm";
        }
    }
}
*/
