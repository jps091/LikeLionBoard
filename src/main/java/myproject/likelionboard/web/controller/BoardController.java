package myproject.likelionboard.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.entity.UpdateDto;
import myproject.likelionboard.domain.service.BoardService;
import myproject.likelionboard.web.form.DeleteForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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
    public String retrieveBoards(Model model){
        List<Board> boards = boardService.findAllBoards();
        log.info("boards size={}",boards.size());
        model.addAttribute("boards", boards);
        return "/boards/boards";
    }

    @GetMapping("/write")
    public String writeForm(Model model){
        model.addAttribute("board", new Board());
        return "/boards/writeForm";
    }

    @PostMapping("/write")
    public String writeBoard(@Validated @ModelAttribute("board") Board param, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/boards/writeForm";
        }
        Board board = settingBoard(param);
        boardService.create(board);
        return "redirect:/boards";
    }
    private static Board settingBoard(Board param) {
        Board board = new Board();
        board.setName(param.getName());
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
                              @RequestParam("id") Long id, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "/boards/updateForm";
        }

        Board board = boardService.findById(id);
        if(!param.getPassword().equals(board.getPassword())){
            bindingResult.reject("loginFail", "비밀번호가 틀렸습니다.");
            return "/boards/updateForm";
        }

        boardService.updateBoard(id, param);
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
    public String  deleteBoard(@RequestParam("id") Long id, @Validated @ModelAttribute("board") DeleteForm form, BindingResult bindingResult){
        Board deleteBoard = boardService.findById(id);

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
}
