package myproject.likelionboard.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.dto.SearchBoard;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.entity.Member;
import myproject.likelionboard.domain.dto.UpdateDto;
import myproject.likelionboard.domain.service.BoardService;
import myproject.likelionboard.web.form.DeleteForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/view")
    public String retrieveBoard(@RequestParam("id") Long id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "/boards/view";
    }

    @GetMapping
    public String retrieveBoards(@SessionAttribute(value = "loginKey", required = false) Member loginMember,
                                 @ModelAttribute("search") SearchBoard searchBoard, Model model){

        model.addAttribute("loginMember", loginMember);

        List<Board> boards = boardService.findAllBoards(searchBoard);
        model.addAttribute("boards", boards);
        return "/boards/boards";
    }

    private static Member getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginKey");
        return loginMember;
    }

    @GetMapping("/write")
    public String writeForm(Model model, HttpServletRequest request){
        Member loginMember = getLoginMember(request);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("board", new Board());
        return "/boards/writeForm";
    }

    @PostMapping("/write")
    public String writeBoard(@Validated @ModelAttribute("board") Board param, BindingResult bindingResult,
                             HttpServletRequest request){
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/boards/writeForm";
        }

        Member loginMember = getLoginMember(request);
        Board board = settingBoard(param, loginMember);
        boardService.create(board);
        return "redirect:/boards";
    }
    private static Board settingBoard(Board param, Member loginMember) {
        Board board = new Board();
        board.setName(loginMember.getLoginId());
        board.setTitle(param.getTitle());
        board.setContent(param.getContent());
        board.setPassword(param.getPassword());
        board.setCreateDate(param.getCreateDate());
        return board;
    }

    @GetMapping("/update")
    public String updateForm(Model model, @RequestParam("id") Long id){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "/boards/updateForm";
    }

    @PostMapping("/update")
    public String updateBoard(@Validated @ModelAttribute("board") UpdateDto param, BindingResult bindingResult,
                              @RequestParam("id") Long id, HttpServletRequest request,
                              RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/boards/updateForm";
        }

        Board board = boardService.findById(id);
        Member loginMember = getLoginMember(request);

        // 로그인 회원이랑 게시판작성자가 같을경우
        if(board.getName().equals(loginMember.getLoginId())){
            boardService.updateBoard(id, param);
            return redirectToBoard(id, redirectAttributes);
        } else if(!board.getPassword().equals(param.getPassword())){// 게시판 비밀번호가 틀릴경우
            bindingResult.reject("loginFail", "비밀번호를 다시 입력하세요.");
            return "/boards/updateForm";
        }else{ // 로그인 회원이랑 게시판작성자가 다르지만 게시판 비밀번호를 올바르게 입력했을 경우
            boardService.updateBoard(id, param);
            return redirectToBoard(id, redirectAttributes);
        }
    }

    private static String redirectToBoard(Long id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("id", id);
        return "redirect:/boards?id={id}";
    }

    @GetMapping("/delete")
    public String deleteForm(@RequestParam("id") Long id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "/boards/deleteForm";
    }

    @PostMapping("/delete")
    public String  deleteBoard(@RequestParam("id") Long id,
                               @Validated @ModelAttribute("board") DeleteForm form,
                               BindingResult bindingResult, HttpServletRequest request){
        Board deleteBoard = boardService.findById(id);
        Member loginMember = getLoginMember(request);

        if (isOwner(loginMember, deleteBoard)) {
            boardService.deleteBoard(id);
            return "redirect:/boards";
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/boards/deleteForm";
        }

        if(!form.getPassword().equals(deleteBoard.getPassword())){
            bindingResult.reject("loginFail", "비밀번호가 틀렸습니다.");
            return "/boards/deleteForm";
        }
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }
    private boolean isOwner(Member loginMember, Board board) {
        return loginMember != null && board.getName().equals(loginMember.getLoginId());
    }
}
