package main;

import exception.*;
import util.DaoUtil;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private final DaoUtil daoUtil = new DaoUtil();

    private final AutomacaoConfigsController automacaoConfigsController = new AutomacaoConfigsController();

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        try {
            daoUtil.conectarBd();
            automacaoConfigsController.conferirConfigs();
        }
        catch (ClassNotFoundException exception){
            JOptionPane.showMessageDialog(null, "O driver do banco de dados não foi encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (SQLException exception){
            JOptionPane.showMessageDialog(null, "Erro na iniciação do acesso ao banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (DaoException | TimerException | GerarPlanilhaException | ConversaoException | DiretorioException | IOException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
