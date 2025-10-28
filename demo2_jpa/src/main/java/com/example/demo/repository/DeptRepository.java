package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Dept;

/*
	Mybatis 설정
	UserMapper 인터페이스 : List<User> selectAll();	
	Mapper.xml 인터페이스와 동기화 : select * from User >> 객체화

	JpaRepository<Dept,Integger> 기반으로 CRUD 함
	메서드	        설명
	save(entity)	INSERT 또는 UPDATE 수행
	findById(id)	PK로 단건 조회 (Optional 반환)
	findAll()	    전체 조회
	count()	        데이터 개수 조회
	existsById(id)	해당 ID 존재 여부 확인
	delete(entity)	엔티티 삭제
	deleteById(id)	ID로 삭제
	deleteAll()	    전체 삭제

	// 저장 (INSERT)
	memberRepository.save(new Member("홍길동", 20));
	
	// 조회 (SELECT)
	Optional<Member> member = memberRepository.findById(1L);
	
	// 전체 조회
	List<Member> members = memberRepository.findAll();
	
	// 수정 (UPDATE: JPA는 save로 동작)
	Member m = memberRepository.findById(1L).orElseThrow();
	m.setAge(30);
	memberRepository.save(m);
	
	// 삭제
	memberRepository.deleteById(1L);
*/
public interface DeptRepository extends JpaRepository<Dept, Integer> { //<Dept, Integer> DTO 함수 제공
	//end 자동으로 함수 생성
}
