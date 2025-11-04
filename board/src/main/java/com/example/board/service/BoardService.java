package com.example.board.service;

import com.example.board.dto.BoardDTO;

import java.util.List;

public interface BoardService {

    // 게시글 목록 조회
    List<BoardDTO> getBoards(int page, int size);

    // 전체 게시글 수 조회
    int getTotalCount();
}
