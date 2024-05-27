package myproject.likelionboard.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Member;
import myproject.likelionboard.domain.exception.LoginIdDuplication;
import myproject.likelionboard.domain.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public String retrieveMember(@PathVariable("id") Long id, Model model){
        Member member = memberService.findMemberById(id);
        model.addAttribute("member", member);
        return "/members/member";
    }

    @GetMapping("/join")
    public String joinForm(@ModelAttribute("member") Member member){
        return "/members/joinForm";
    }

    @PostMapping("/join")
    public String joinMember(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            log.info("form errors={}", bindingResult);
            return "/members/joinForm";
        }

        try{
            Member saveMember = memberService.save(member);

            HttpSession session = request.getSession();
            session.setAttribute("loginKey", saveMember);

            Long id = saveMember.getId();
            redirectAttributes.addAttribute("id", id);
            return "redirect:/members/{id}";
        }catch (LoginIdDuplication e){
            bindingResult.reject("idDuplication", "입력하신 ID는 이미 존재합니다. 다시 입력해주세요.");
            return "/members/joinForm";
        }

    }
}
