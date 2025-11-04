package com.example.board.mapper;

import com.example.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDTO> selectBoardList(@Param("start") int start, @Param("end") int end);
    int countBoard();
}
