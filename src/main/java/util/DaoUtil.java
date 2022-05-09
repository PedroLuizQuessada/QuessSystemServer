package util;

import exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoUtil {
    private final SenhaUtil senhaUtil = new SenhaUtil();
    private final ParametrosUtil parametrosUtil = new ParametrosUtil();

    private static Connection con;
    private Statement stmt;

    public void conectarBd() throws ClassNotFoundException, SQLException, DaoException {
        Class.forName("org.hsql.jdbcDriver");
        con = DriverManager.getConnection("jdbc:HypersonicSQL:hsql://" + parametrosUtil.getServidor(), parametrosUtil.getServidorUsuario(), parametrosUtil.getServidorSenha());

        inicializaTabelas();
    }

    private void inicializaTabelas() throws SQLException, DaoException {
        stmt = con.createStatement();

        try {
            stmt.executeUpdate("CREATE TABLE USUARIOS " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nlogin VARCHAR(30) NOT NULL, " +
                    "\nsenha VARCHAR(30) NOT NULL, " +
                    "\nemail VARCHAR(30) NOT NULL, " +
                    "\ntentativasAcesso INTEGER NOT NULL, " +
                    "\nadm BIT NOT NULL, " +
                    "\ngerente BIT NOT NULL, " +
                    "\ndepartamento INTEGER NOT NULL, " +
                    "\nnativo BIT, " +
                    "\nPRIMARY KEY(id))");

            String senhaAdm = senhaUtil.criptografar(parametrosUtil.getAdmSenha());
            insert(String.format("INSERT INTO USUARIOS (login, senha, email, tentativasAcesso, adm, gerente, departamento, nativo) VALUES ('%s', '%s', '%s', 0, true, true, 0, true)", parametrosUtil.getAdmLogin(), senhaAdm, parametrosUtil.getAdmEmail()));
        } catch (SQLException exception) {
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE DEPARTAMENTOS " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nnome VARCHAR(30) NOT NULL, " +
                    "\nPRIMARY KEY(id))");

            insert("INSERT INTO DEPARTAMENTOS (nome) VALUES ('Arquitetos')");
        } catch (SQLException exception) {
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE GRUPOS " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nnome VARCHAR(30) NOT NULL, " +
                    "\nlider INTEGER, " +
                    "\nPRIMARY KEY(id))");

        } catch (SQLException exception) {
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE USUARIOSGRUPOS " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nidusuario INTEGER NOT NULL, " +
                    "\nidgrupo INTEGER NOT NULL, " +
                    "\nPRIMARY KEY(id))");

        } catch (SQLException exception) {
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CADASTROS " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nnome VARCHAR(30) NOT NULL, " +
                    "\ntabela VARCHAR(30) NOT NULL, " +
                    "\ntipopermissao VARCHAR(30) NOT NULL, " +
                    "\npermitidos INTEGER NOT NULL, " +
                    "\nPRIMARY KEY(id))");

        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CAMPOSCADASTROS " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nidcadastro INTEGER NOT NULL, " +
                    "\nordem INTEGER NOT NULL, " +
                    "\nlabel VARCHAR(30) NOT NULL, " +
                    "\ncoluna VARCHAR(30) NOT NULL, " +
                    "\ntipo VARCHAR(30) NOT NULL, " +
                    "\nvinculado BIT NOT NULL, " +
                    "\nbloqueado BIT NOT NULL, " +
                    "\nobrigatorio BIT NOT NULL, " +
                    "\npesquisavel BIT, " +
                    "\nquebralinha BIT, " +
                    "\nnativo BIT, " +
                    "\ninativo BIT, " +
                    "\nagrupador INTEGER, " +
                    "\nordemagrupador INTEGER, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSTEXTO " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nvalorpadrao VARCHAR(30), " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSNUMERICO " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nvalorpadrao INTEGER, " +
                    "\nlimite VARCHAR(30), " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSDATAHORA " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nvalorpadrao VARCHAR(30), " +
                    "\nlimite VARCHAR(30), " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSDATA " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nvalorpadrao VARCHAR(30), " +
                    "\nlimite VARCHAR(30), " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSCHECKBOX " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nestadopadrao BIT NOT NULL, " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSAREATEXTO " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nvalorpadrao VARCHAR(500), " +
                    "\nlimitecaracteres INTEGER, " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSCOMBOBOX " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nopcaopadrao VARCHAR(30), " +
                    "\nopcoes VARCHAR(500), " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSRADIO " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nopcaopadrao VARCHAR(30), " +
                    "\nopcoes VARCHAR(500), " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE CONFIGSCAMPOSAGRUPADOR " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nordenacaodesc BIT NOT NULL, " +
                    "\nordenacaocampo VARCHAR(30), " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE REGRASCONDICIONAIS " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\ncadastro BIT NOT NULL, " +
                    "\nidcampo INTEGER NOT NULL, " +
                    "\nidcampoinfo INTEGER NOT NULL, " +
                    "\noperador VARCHAR(10) NOT NULL, " +
                    "\nvalor VARCHAR(30) NOT NULL, " +
                    "\ntiporegra VARCHAR(30) NOT NULL, " +
                    "\ngrupo INTEGER NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }

        try{
            stmt.executeUpdate("CREATE TABLE AUTOMACOES " +
                    "(id INTEGER NOT NULL IDENTITY, " +
                    "\nnome VARCHAR(30) NOT NULL, " +
                    "\ndescricao VARCHAR(500) NOT NULL, " +
                    "\nhorariolimites BIT NOT NULL, " +
                    "\nhorariolimiteinicio VARCHAR(10) NOT NULL, " +
                    "\nhorariolimitefim VARCHAR(10) NOT NULL, " +
                    "\nmaquinaexterna BIT NOT NULL, " +
                    "\ndiasexecucao BIT NOT NULL, " +
                    "\nsegunda BIT NOT NULL, " +
                    "\nterca BIT NOT NULL, " +
                    "\nquarta BIT NOT NULL, " +
                    "\nquinta BIT NOT NULL, " +
                    "\nsexta BIT NOT NULL, " +
                    "\nsabado BIT NOT NULL, " +
                    "\ndomingo BIT NOT NULL, " +
                    "\nemailfalha VARCHAR(30) NOT NULL, " +
                    "\ntipopermissao VARCHAR(30) NOT NULL, " +
                    "\npermitidos INTEGER NOT NULL, " +
                    "\nativo BIT NOT NULL, " +
                    "\nPRIMARY KEY(id))");
        } catch (SQLException exception){
            //Tabela já criada
        }
    }

    public List<Map<String, Object>> select(String sql, List<String> colunas) throws DaoException {
        List<Map<String, Object>> listMapRetorno = new ArrayList<>();

        try {
            ResultSet resultSet = con.createStatement().executeQuery(sql);

            while (resultSet.next()) {
                Map<String, Object> mapLinha = new HashMap<>();
                for (String coluna : colunas) {
                    mapLinha.put(coluna, resultSet.getString(coluna));
                }
                listMapRetorno.add(mapLinha);
            }
            return listMapRetorno;
        }
        catch (Exception exception){
            exception.printStackTrace();
            throw new DaoException();
        }
    }

    public void insert(String sql) throws DaoException{
        try{
            con.createStatement().executeUpdate(sql);
        }
        catch (Exception exception){
            exception.printStackTrace();
            throw new DaoException();
        }
    }
}
