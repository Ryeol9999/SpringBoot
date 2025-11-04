package com.example.board.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDTO {
    private int id;
    private String title;
    private String writer;
    private String content;
    private Date createdAt;
}