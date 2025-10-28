package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


//Entity 설계
//이 클래스를 가지고 create 할거다 (create table Dept ..... 컬럼명(PK) 타입, 컬럼명 타입
@Entity
@Data
@Table(name="DEPT")
public class Dept {
	
	@Id
	private int deptno;
	private String dname;
	private String loc;
}
