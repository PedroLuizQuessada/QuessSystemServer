package enums;

public enum ConfigsAutomacaoEnum {
    ID("id"),
    HORARIOLIMITES("horariolimites"),
    HORARIOLIMITEINICIO("horariolimiteinicio"),
    HORARIOLIMITEFIM("horariolimitefim"),
    DIASEXECUCAO("diasexecucao"),
    SEGUNDA("segunda"),
    TERCA("terca"),
    QUARTA("quarta"),
    QUINTA("quinta"),
    SEXTA("sexta"),
    SABADO("sabado"),
    DOMINGO("domingo"),
    EMAILFALHA("emailfalha"),
    ATIVO("ativo");

    private final String descricao;

    ConfigsAutomacaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
