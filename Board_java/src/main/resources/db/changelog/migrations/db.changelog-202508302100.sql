--liquibase formatted sql
--changeset junior:202508301956
--comment: blocks table create

CREATE TABLE BLOCKS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    block_reason VARCHAR(255) NOT NULL,
    block_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unblock_at TIMESTAMP NULL,
    unblock_reason VARCHAR(255) NOT NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT cards_blocks_fk FOREIGN KEY (card_id) REFERENCES CARDS(id) ON DELETE CASCADE
) ENGINE=InnoDB

--rollback DROP TABLE BLOCKS