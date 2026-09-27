package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private String funcao;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat FORMATADOR_NUMERO = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        return String.format(
                "Funcionario:%nNome: %s | Data de nascimento: %s%nSalario: %s | Função: %s%n",
                getNome(),
                getDataNascimento().format(FORMATADOR),
                FORMATADOR_NUMERO.format(salario.setScale(2, RoundingMode.HALF_UP)),
                funcao
        );
    }
}