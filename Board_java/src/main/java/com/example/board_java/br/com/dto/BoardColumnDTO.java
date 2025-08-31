package com.example.board_java.br.com.dto;

import com.example.board_java.br.com.persistance.entity.BoardColumnKindEnum;

public record BoardColumnDTO(Long id, String name,
                             BoardColumnKindEnum kind, int cardsAmount) {


}
