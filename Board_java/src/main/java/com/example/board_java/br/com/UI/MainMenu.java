package com.example.board_java.br.com.UI;

import com.example.board_java.br.com.persistance.Config.ConnectionConfig;
import com.example.board_java.br.com.persistance.entity.BoardColumnEntity;
import com.example.board_java.br.com.persistance.entity.BoardColumnKindEnum;
import com.example.board_java.br.com.persistance.entity.BoardEntity;
import com.example.board_java.br.com.service.BoardQueryService;
import com.example.board_java.br.com.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu  {
    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException{
        System.out.println("Bem vindo ao sistema de board");
        System.out.println("Escolha a opção");

        var option = -1;
        while (true){
            System.out.println("1 - criar");
            System.out.println("2 - selecioanr board");
            System.out.println("3 - excluir board");
            System.out.println("4 - sair");
            option = scanner.nextInt();
            switch (option){
                case 1-> createBoard();
                case 2-> selectBoard();
                case 3-> deleteBoard();
                case 4 -> System.exit(4);
                default -> System.out.println("Opção errada");
            }

        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("informe o nome do seu board");
        entity.setName(scanner.next());
        System.out.println("Seu board tera colunas maior que 3, informe quantas se sim, senão digite 0");
        var addiotionalColumns = scanner.nextInt();
        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        var initialColumnName = scanner.next();
        var initialColumn = createColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < addiotionalColumns; i++) {
            System.out.printf("Informe o nome da coluna pendente %s do board", i);
            var pendingColumnName = scanner.next();
            var pendingColumn = createColumn(pendingColumnName, BoardColumnKindEnum.PENDING, i+1);
            columns.add(pendingColumn);
        }
        System.out.println("Informe o nome da coluna final do board");
        var finalColumnName = scanner.next();
        var finalColumn = createColumn(finalColumnName, BoardColumnKindEnum.FINAL, addiotionalColumns +  1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        var cancelColumnName = scanner.next();
        var cancelColumn = createColumn(cancelColumnName, BoardColumnKindEnum.CANCEL, addiotionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        try(var connection = ConnectionConfig.getConnection()) {
            var service = new BoardService(connection);
            service.insert(entity);
        }
    }

    private void selectBoard() throws SQLException {
        System.out.println("informe o id do board que quer selecionar");
        var id = scanner.nextLong();
        try(var connection = ConnectionConfig.getConnection()) {
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findbBID(id);
            optional.ifPresentOrElse(
                    b ->  new BoardMenu(b).execute(), () -> System.out.printf("não foi encontrado o id %s", id));
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("informe o id do board");
        var id = scanner.nextLong();
        try(var connection = ConnectionConfig.getConnection()) {
            var service = new BoardService(connection);
            service.delete(id);
            if(service.delete(id)){
                System.out.printf("O board %s foi excluido ", id);
            } else {
                System.out.printf("não foi encontra o board com id %s", id);

            }
        }
    }
    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
