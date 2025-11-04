package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<BoardDTO> getBoards(int page, int size) {
        int start = (page - 1) * size;
        int end = start + size;
        return boardMapper.selectBoardList(start, end);
    }

    @Override
    public int getTotalCount() {
        return boardMapper.countBoard();
    }
}
