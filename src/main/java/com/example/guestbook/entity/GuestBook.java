package com.example.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuestBook extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //maria db같은 경우 적용 안될 때.. GenerationType.IDENTITY 써도 됨
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 1500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    //getter만 만들었기 때문에..
    //수정을 위한 메서드 생성 (수정 가능한 것들..)
    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }

}
