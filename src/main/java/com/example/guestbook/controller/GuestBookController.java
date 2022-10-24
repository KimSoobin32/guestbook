package com.example.guestbook.controller;

import com.example.guestbook.domain.GuestBookDTO;
import com.example.guestbook.domain.PageRequestDTO;
import com.example.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class GuestBookController {
    //서비스 객체 주입
    private final GuestBookService guestBookService;

    @GetMapping({"/"})
    public String main(){
        log.info("/");
        return "redirect:/guestbook/list";
    }

    //void를 리턴하면 요청 url이 view의 이름이 됨
    @GetMapping({"/guestbook/list"})
    public void list(PageRequestDTO dto, Model model){
        log.info("list.............");
        //리눅스 서버는 앞에 / 쓰면 못 찾을 수 있음 (guestbook/list로 써야 함..)

        //서비스 메서드 호출
        //result의 dtoList에 dto의 List가 있고
        //result의 pageList에 페이지 번호의 List가 존재
        model.addAttribute("result",guestBookService.getList(dto));

    }
    
    //등록 요청을 get방식으로 처리하는 메서드 - 등록 페이지로 이동
    @GetMapping("/guestbook/register")
    public void register(){
        log.info("register GET...");
       
    }
    
    //등록 요청을 POST방식으로 처리하는 메서드 - 등록 수행
    @PostMapping("/guestbook/register")
    public String register(GuestBookDTO dto, RedirectAttributes redirectAttributes){
        log.info("register GET...");
        
        //등록 요청 처리
        Long gno = guestBookService.register(dto);

        //데이터 저장
        redirectAttributes.addFlashAttribute("msg",gno+"등록");
        
        //목록보기로 리다이렉트 (리다이렉트로 이동 시 모델 쓸 수 없음... 세션이나 이와 비슷한 RedirectAttributes이용해야 함..)
        return "redirect:/guestbook/list";
    }

    //상세보기 요청 처리
    @GetMapping("/guestbook/read")
    //ModelAttribute : 매개변수를 결과 페이지에 넘겨줄 때 사용 (어떤 처리 안하고 바로 넘겨줄 때..
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        GuestBookDTO dto = guestBookService.read(gno);
        model.addAttribute("dto",dto);
    }




    
}
