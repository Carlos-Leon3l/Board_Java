package com.example.board_java.br.com.persistance.entity;

import lombok.Data;

@Data
public class BoardColumnEntity {
    private Long id;
    private String name;
    private int order;
    private BoardColumnKindEnum kind;

}
