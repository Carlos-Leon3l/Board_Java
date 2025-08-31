package com.example.board_java.br.com.persistance.entity;

import lombok.Data;

@Data
public class CardEntity {
    private Long id;
    private String title;
    private String description;
    private  BoardColumnEntity boardColumn = new BoardColumnEntity();
}
