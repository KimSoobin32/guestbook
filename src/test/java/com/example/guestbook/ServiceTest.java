package com.example.guestbook;

import com.example.guestbook.domain.GuestBookDTO;
import com.example.guestbook.domain.PageRequestDTO;
import com.example.guestbook.domain.PageResponseDTO;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private GuestBookService guestBookService;

    //@Test
    public void insertTest(){
        GuestBookDTO dto = GuestBookDTO.builder()
                .title("삽입 테스트")
                .content("content insert")
                .writer("adam")
                .build();
        Long gno = guestBookService.register(dto);
        System.out.println("삽입된 번호: "+ gno);
    }

    //목록보기 테스트
    @Test
    public void listTest(){
        //페이지 번호 와 대이터 개수 설정
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        //메서드 호출
        PageResponseDTO<GuestBookDTO, GuestBook> resultDTO = guestBookService.getList(pageRequestDTO);

        //확인
        for (GuestBookDTO dto: resultDTO.getDtoList()){
            System.out.println(dto);
        }
        
        //이전 페이지번호와 다음페이지 존재 여부
        System.out.println("이전 여부 : "+resultDTO.isPrev());
        System.out.println("다음 여부 : "+resultDTO.isNext());
        System.out.println("전체 페이지 개수 : "+resultDTO.getTotalPage());  //전체 페이지 개수
        resultDTO.getPageList().forEach(i -> System.out.println(i));
        
    }
}
