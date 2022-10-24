package com.example.guestbook.entity;



import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass   //entity로 사용은 가능하지만 테이블을 생성하지는 않음
//jpa를 감시하고 있다가 데이터를 수정
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {  //인스턴스 생성 불가능 하도록 abstract
    @CreatedDate    //데이터 생성 날짜를 설정
    @Column(name = "regdate", updatable = false)    //저장 시 이름은 regdate, 수정 불가
    private LocalDateTime regDate;

    @LastModifiedDate   //데이터의 마지막 수정 날짜를 설정
    @Column(name ="moddate")
    private LocalDateTime modDate;
}
