package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.EmpMapper;
import com.example.demo.model.EmpDeptDto;

@Service
//@RequiredArgsConstructor  //lombok 통해서 주입   ..요게 편함
public class EmpService {
	
	private final EmpMapper empMapper;
	
	@Autowired
	public EmpService(EmpMapper empMapper) {
		this.empMapper= empMapper;
	}
	
	public List<EmpDeptDto> getAllEmpWithDept(){
		return empMapper.findAllWithDept();
	}
	
}
