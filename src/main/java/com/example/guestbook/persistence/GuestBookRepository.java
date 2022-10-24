package com.example.guestbook.persistence;

import com.example.guestbook.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//GuestBook 테이블에 crud 작업을 수행하기 위한 Repository 인터페이스
public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, QuerydslPredicateExecutor<GuestBook> {
}