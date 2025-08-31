package com.example.board_java.br.com.dto;

import com.example.board_java.br.com.persistance.entity.BoardColumnKindEnum;


public record BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {
}
