package main;

import exception.*;
import util.DaoUtil;
import enums.ConfigsAutomacaoEnum;
import util.ExcelUtil;
import util.ParametrosUtil;
import util.TimerUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutomacaoConfigsController {
    private final DaoUtil daoUtil = new DaoUtil();
    private final TimerUtil timerUtil = new TimerUtil();
    private final ParametrosUtil parametrosUtil = new ParametrosUtil();
    private final ExcelUtil excelUtil = new ExcelUtil();

    void conferirConfigs() throws DaoException, TimerException, GerarPlanilhaException, ConversaoException, IOException, DiretorioException {
        while (true) {
            System.out.println("Conferindo configurações...");
            List<String> colunas = new ArrayList<>();
            for (ConfigsAutomacaoEnum config : ConfigsAutomacaoEnum.values()) {
                colunas.add(config.getDescricao());
            }

            List<Map<String, Object>> automacoes = daoUtil.select("SELECT * FROM AUTOMACOES WHERE ativo = true", colunas);

            for (Map<String, Object> automacao : automacoes) {
                int idAutomacao = Integer.parseInt(automacao.get("id").toString());
                String path = parametrosUtil.getGoogleDriveAutomacaoPath() + idAutomacao;
                if (!(new File(path)).exists()) {
                    if (!new File(path).mkdirs()) {
                        throw new DiretorioException("gerar", path);
                    }
                }
                else if (Files.list(Paths.get(path)).count() > 0) {
                    limparDiretorio(path);
                }

                List<List<String>> infos = new ArrayList<>();
                List<String> info = new ArrayList<>();
                for (String coluna: colunas) {
                    info.add(String.valueOf(automacao.get(coluna)));
                }
                infos.add(info);

                excelUtil.gerarPlanilha(colunas, infos, path);
            }

            timerUtil.aguardarMinutos(parametrosUtil.getIntervaloMinutos());
        }
    }

    private void limparDiretorio(String path) throws IOException, DiretorioException {
        while (true) {
            if (Files.list(Paths.get(path)).count() > 0) {
                String pathString = Files.list(Paths.get(path)).findFirst().get().toString();
                if (!new File(pathString).delete()) {
                    throw new DiretorioException("limpar", pathString);
                }
            }
            else {
                break;
            }
        }
    }
}
