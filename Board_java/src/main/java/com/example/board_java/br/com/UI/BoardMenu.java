package com.example.board_java.br.com.UI;

import com.example.board_java.br.com.dto.BoardColumnInfoDTO;
import com.example.board_java.br.com.persistance.Config.ConnectionConfig;
import com.example.board_java.br.com.persistance.entity.BoardColumnEntity;
import com.example.board_java.br.com.persistance.entity.BoardColumnKindEnum;
import com.example.board_java.br.com.persistance.entity.BoardEntity;
import com.example.board_java.br.com.persistance.entity.CardEntity;
import com.example.board_java.br.com.service.BoardColumnQueryService;
import com.example.board_java.br.com.service.BoardQueryService;
import com.example.board_java.br.com.service.CardQueryService;
import com.example.board_java.br.com.service.CardService;
import lombok.AllArgsConstructor;

import javax.smartcardio.Card;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

@AllArgsConstructor
public class BoardMenu {
    public static Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    public BoardEntity entity;

    public void execute()  {
        try {
            System.out.println("Bem vindo ao board %s, selecione a operacao desejada");

            var option = -1;
            while (true) {
                System.out.println("1 - criar card");
                System.out.println("2 - mover card");
                System.out.println("3 - bloquear card");
                System.out.println("4 - desbloquear card");
                System.out.println("5 - cancelar card");
                System.out.println("6 - ver board");
                System.out.println("7 - ver coluna com cards");
                System.out.println("8 - ver card");
                System.out.println("9 - voltar para o menu anterior");
                System.out.println("10 - sair");

                option = scanner.nextInt();
                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                    case 9 -> System.out.println("Voltando para o menu anterior");
                    case 10 -> System.exit(4);
                    default -> System.out.println("Opção errada");
                }
            }
        }  catch (SQLException ex){
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private void showCard() throws  SQLException{
        System.out.println("infor o id do card que deseja ver");
        var selectedCardId = scanner.nextLong();
        try(var connection = ConnectionConfig.getConnection()) {

            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(c -> {
                        System.out.printf("Card %s - %s",c.id() ,c.title());
                        System.out.printf("Descricao %s", c.description());
                        System.out.println(c.blocked() ? "esta bloqueado Motivo: " + c.blockReason(): "nao esta bloqueado");
                        System.out.printf("ja foi bloqueado %s vezes", c.blocksAmount());
                        System.out.printf("esta na coluna %s - %s", c.columnId(), c.columnName());
                    }, () -> System.out.printf("Nao existe card com o id %s", selectedCardId));
        }
    }

    private void showColumn() throws SQLException {
        System.out.printf("Escolha uma coluna do board %s", entity.getName());
        var columnsIds = entity.getBoardColumns()
                .stream()
                .map(BoardColumnEntity::getId)
                .toList();
        var selectedColumn = -1L;
        while (!columnsIds.contains(selectedColumn)){
            entity.getBoardColumns()
                    .forEach(c ->
                            System.out.printf(" %s - %s [%s]", c.getId(), c.getName(), c.getKind() ));
            selectedColumn = scanner.nextLong();
        }
        try( var connection = ConnectionConfig.getConnection()) {
            var column = new BoardColumnQueryService(connection).findById(selectedColumn);
            column.ifPresent(co -> {
                System.out.printf("Coluna %s tipo %s", co.getKind(), co.getCards());
                co.getCards().forEach(ca -> System.out.printf("Card %s - %s Descricao: %s",
                        ca.getId(),
                        ca.getTitle(),
                        ca.getDescription()));

            });

        }

    }

    private void showBoard() throws SQLException{
        try(var connection = ConnectionConfig.getConnection()) {
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.printf("Board [%s,%s]", b.id(), b.name());
                b.columns().forEach(c -> {
                    System.out.printf("Coluna [%s] tipo: [%s] tem %s cards ", c.name()
                            , c.kind(), c.cardsAmount() );
                });
            });

        }
    }

    private void createCard() throws SQLException{
        var card = new CardEntity();
        System.out.println("informe o titulo do card");
        card.setTitle(scanner.next());
        System.out.println("informe a descricao do card");
        card.setDescription(scanner.next());
        card.setBoardColumn(entity.getInitialColumn());
        try(var connection = ConnectionConfig.getConnection()) {
            new CardService(connection).insert(card);
        }
    }
    private void moveCardToNextColumn() throws SQLException {
        System.out.println("informe o id do card para mover para proxima coluna");
        var cardId = scanner.nextLong();
        var boardColumnsInfo = entity.getBoardColumns().stream().map(bc ->
                new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind())).toList();
        try (var connection = ConnectionConfig.getConnection()){
            new CardService(connection).moveToNextColumn(cardId, boardColumnsInfo);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void cancelCard() throws SQLException {
        System.out.println("informe o id do card que ira ser cancelado");
        var cancelColumn = entity.getCancelColumn();
        var cardId = scanner.nextLong();
        var boardColumnsInfo = entity.getBoardColumns().stream().map(bc ->
                new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind())).toList();
        try (var connection = ConnectionConfig.getConnection()){
            new CardService(connection).cancel(cardId, cancelColumn.getId(), boardColumnsInfo);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void blockCard() throws SQLException {
        System.out.println("informe o id do card que sera bloqueado");
        var cardId = scanner.nextLong();
        System.out.println("informe o motivo do bloqueio do card");
        var reason = scanner.next();
        var boardColumnsInfo = entity.getBoardColumns().stream().map(bc ->
                new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind())).toList();
        try (var connection = ConnectionConfig.getConnection()){
            new CardService(connection).block(cardId, reason, boardColumnsInfo);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void unblockCard() throws SQLException {
        System.out.println("informe o id do card que sera desbloqueado");
        var cardId = scanner.nextLong();
        System.out.println("informe o motivo do desbloqueio do card");
        var reason = scanner.next();
        var boardColumnsInfo = entity.getBoardColumns().stream().map(bc ->
                new BoardColumnInfoDTO(bc.getId(), bc.getOrder(), bc.getKind())).toList();
        try (var connection = ConnectionConfig.getConnection()){
            new CardService(connection).block(cardId, reason, boardColumnsInfo);
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }









}
