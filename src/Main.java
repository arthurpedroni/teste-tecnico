import model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    public static void exibirTodosFuncionarios(List<Funcionario> funcionarios){
        funcionarios.stream().forEach(System.out::println);
    }

    public static void receberAumento(List<Funcionario> funcionarios, BigDecimal taxaAumento) {
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(taxaAumento)));
    }

    public static Map<String, List<Funcionario>> agruparFuncionarios(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {

            //Busca funcao do funcionario
            String funcao = funcionario.getFuncao();

            //Verifica se a função ja é uma chave no map
            if (!funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.put(funcao, new ArrayList<>());
            }

            //Procura no map a chave a adiciona o funcionario na lista da função
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }

        // Retorna o map gerado
        return funcionariosPorFuncao;
    }

    public static void exibirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for(String chave: funcionariosPorFuncao.keySet()) {
            System.out.println("FUNÇÃO: " + chave);
            for(Funcionario funcionario: funcionariosPorFuncao.get(chave)) {
                System.out.println(funcionario);
            }
            System.out.println("---------------------------------");
        }
    }

    public static void exibirFuncionariosAniversarianteMes(List<Funcionario> funcionarios, int mes) {
        List<Funcionario> aniversariantesMes = funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == mes)
                .toList();

        exibirTodosFuncionarios(aniversariantesMes);
    }
    public static void exibirFuncionarioMaisVelho(List<Funcionario> funcionarios){
        Funcionario funcionarioMaisVelho = funcionarios.get(0);
        for(Funcionario funcionario:funcionarios) {
            if (funcionario.getDataNascimento().isBefore(funcionarioMaisVelho.getDataNascimento())) {
                funcionarioMaisVelho = funcionario;
            }
        }
        System.out.println(funcionarioMaisVelho);
    }

    public static void exibirFuncionariosOrdemAlfabetica(List<Funcionario> funcionarios) {
        // Ordenando em ordem alfabética pelo nome
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));

        exibirTodosFuncionarios(funcionarios);
    }

    public static void exibirTotalSalarios(List<Funcionario> funcionarios){

        NumberFormat FORMATADOR_NUMERO = NumberFormat.getNumberInstance(new Locale("pt", "BR"));

        BigDecimal totalSalario = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO,BigDecimal::add);

        System.out.println("Total dos salários: " + FORMATADOR_NUMERO.format(totalSalario.setScale(2, RoundingMode.HALF_UP)));
    }

    public static void exibirQuantosSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {

        NumberFormat FORMATADOR_NUMERO = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
        BigDecimal qtdSalariosMinimos;
        for(Funcionario funcionario: funcionarios) {
            qtdSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("Nome: " + funcionario.getNome() + ", Salário: " + FORMATADOR_NUMERO.format(funcionario.getSalario().setScale(2, RoundingMode.HALF_UP)) + ", Recebe " + FORMATADOR_NUMERO.format(qtdSalariosMinimos) + " salários mínimos");
        }
    }

    public static void main(String[] args) {


        // formatar data para dd/mm/yyyy
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        List<Funcionario> funcionarios = new ArrayList<>(List.of(
                new Funcionario("Maria", LocalDate.parse("18/10/2000", formatador), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.parse("12/05/1990", formatador), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.parse("02/05/1961", formatador), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatador), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.parse("05/01/1995", formatador), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatador), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatador), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.parse("08/07/1994", formatador), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatador), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.parse("02/09/1996", formatador), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 – Remover o funcionário “João” da lista.
        removerFuncionario(funcionarios,"João");

        /* 3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
        • informação de data deve ser exibido no formato dd/mm/aaaa;
        • informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula. */
        exibirTodosFuncionarios(funcionarios);

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        BigDecimal taxa = new BigDecimal(1.1);
        receberAumento(funcionarios, taxa);

        //3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparFuncionarios(funcionarios);

        // 3.6 – Imprimir os funcionários, agrupados por função.
        exibirFuncionariosPorFuncao(funcionariosPorFuncao);

        //3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        int mes = 10;
        exibirFuncionariosAniversarianteMes(funcionarios, mes);
        mes = 12;
        exibirFuncionariosAniversarianteMes(funcionarios, mes);

        //3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        exibirFuncionarioMaisVelho(funcionarios);

        //3.10 – Imprimir a lista de funcionários por ordem alfabética.
        exibirFuncionariosOrdemAlfabetica(funcionarios);

        //3.11 – Imprimir o total dos salários dos funcionários.
        exibirTotalSalarios(funcionarios);

        //3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        BigDecimal salarioMinimo = new BigDecimal(1212.0);
        exibirQuantosSalariosMinimos(funcionarios,salarioMinimo);
    }
}