package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public Map<String, Object> getBoardList(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        List<BoardDTO> boards = boardService.getBoards(page, size);
        int total = boardService.getTotalCount();
        Map<String, Object> result = new HashMap<>();
        result.put("data", boards);
        result.put("total", total);
        return result;
    }
}