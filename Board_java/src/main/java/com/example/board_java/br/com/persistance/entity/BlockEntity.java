package com.example.board_java.br.com.persistance.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class BlockEntity {
    private Long id;
    private String block_reason;
    private OffsetDateTime block_at;
    private OffsetDateTime unblock_at;
    private String unblock_reason;


}
