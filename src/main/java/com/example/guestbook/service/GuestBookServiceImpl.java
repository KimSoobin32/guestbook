package com.example.guestbook.service;

import com.example.guestbook.domain.GuestBookDTO;
import com.example.guestbook.domain.PageRequestDTO;
import com.example.guestbook.domain.PageResponseDTO;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.example.guestbook.persistence.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Log4j2
public class GuestBookServiceImpl implements GuestBookService{
    private final GuestBookRepository guestBookRepository;
    //데이터 삽입을 위한 메서드
    @Override
    public Long register(GuestBookDTO dto) {
        log.info("데이터 삽입");
        log.info(dto);

        //Repository에서 사용하기 위한 dto를 entity로 변환
        GuestBook entity = dtoToEntity(dto);

        GuestBook result = guestBookRepository.save(entity);    //데이터 삽입

        return result.getGno(); //삽입한 후 리턴받은 데이터의 gno 리턴
    }

    @Override
    public PageResponseDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {

        //requestDTO가 넘겨받은 keyword 값의 좌우 공백을 제거
        String keyword = requestDTO.getKeyword();
        if(keyword != null){
            requestDTO.setKeyword(keyword.trim());
        }

        //페이지 단위 요청을 위한 Pageable 객체를 생성
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        //데이터베이스에서 조회
        //Page<GuestBook> result = guestBookRepository.findAll(pageable);

        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<GuestBook> result = guestBookRepository.findAll(booleanBuilder, pageable);


        //Entity를 DTO로 변환하기 위한 객체 생성
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));


        //데이터 목록 생성
        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> guestBook = guestBookRepository.findById(gno);

        return guestBook.isPresent() ? entityToDto(guestBook.get()) : null;
    }

    //검색 조건을 만들어주는 메서드
    private BooleanBuilder getSearch(PageRequestDTO requestDTO){

        String type = requestDTO.getType(); //검색 항목 가져오기
        String keyword = requestDTO.getKeyword(); //검색어 가져오기

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;   //querydsl은 GuestBook아니라 QGuestBook써야함..

        //검색 조건이 없는 경우
        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        //검색 조건이 있는 경우
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);   //앞에 만들었던 조건 합침..
        return booleanBuilder;

    }


}
