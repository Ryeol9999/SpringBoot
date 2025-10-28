package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Dept;
import com.example.demo.repository.DeptRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptService {
	private final DeptRepository deptRepository;
	
	public List<Dept> findAll(){
		return deptRepository.findAll(); // findall() JPA가 entity 기반으로 함수 생성
	}
	
	public Dept findById(int deptno) {
		return deptRepository.findById(deptno).orElse(null);
	}
	
	public void save(Dept dept) {  //insert
		deptRepository.save(dept);
	}
	
	public void delete(int deptno) {  //delete
		deptRepository.deleteById(deptno);
	 
	
	//update >> 기존데이터 delete 새로운데이터 insert
	}
}
