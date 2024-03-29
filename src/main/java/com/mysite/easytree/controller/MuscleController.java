package com.mysite.easytree.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.easytree.service.MuscleService;
import com.mysite.easytree.tool.GeneAnalyticsTool;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MuscleController {

	private final MuscleService muscleService;
	
	@PostMapping(value = "/muscle/calc")
	public String muscleCalc(@RequestParam("ncbiCodes") String ncbiCodes,
			@RequestParam("dnaSequence") String dnaSequence,
			@RequestParam("kindOfTree") String kindOfTree,
			Model model) throws IOException {
		// => 이 함수에서는 최종적으로 newick format 내용과 정렬결과 html파일만 반환받으면 된다.
		//ncbiCodes와 dnaSequence 둘 다 공백인 경우만 예외처리
		if(ncbiCodes.equals("") && dnaSequence.equals("")) {
			String message = "유전자 항목을 선택하시거나 유전자 데이터를 직접 입력하셔야 합니다.";
			model.addAttribute("message", message);
			return "error/404";
		}
		
		
		GeneAnalyticsTool tool = muscleService.executeTool(ncbiCodes, dnaSequence, kindOfTree);
		model.addAttribute("tool", tool);
		return "result";
	}
}
