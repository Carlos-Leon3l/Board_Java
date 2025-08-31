package com.example.board_java.br.com.UI;

import com.example.board_java.br.com.persistance.Config.ConnectionConfig;
import com.example.board_java.br.com.persistance.entity.BoardEntity;
import com.example.board_java.br.com.service.BoardQueryService;
import lombok.AllArgsConstructor;

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

    private void showCard() {
    }

    private void showColumn() {
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

    private void cancelCard() {
    }

    private void unblockCard() {
    }

    private void blockCard() {
    }

    private void moveCardToNextColumn() {
    }

    private void createCard() {

    }
}
