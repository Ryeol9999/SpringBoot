package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
	select e.empno,
		e.enape,
		e.job,
		e.sal,
		e.deptno,
		d.name
		from emp e join dept d
		on e.deptno = d.deptno;
	Join 되ㅐ는 쿼리 문도 별도의 DTO 생성


*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpDeptDto {
 //완성
	private int empno;
	private String ename;
	private String job;
	private int sal;
	private int deptno;
	private String dname; //부서명
}
