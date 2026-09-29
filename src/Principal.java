import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat FORMATO_NUMERO =
            new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public static void main(String[] args) {

        // inserir todos os funcionários, na mesma ordem da tabela
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // remover o funcionário "João"
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // imprimir todos os funcionários
        System.out.println("===== Todos os funcionários =====");
        funcionarios.forEach(Principal::imprimirFuncionario);

        // aumento de 10% no salário
        BigDecimal fatorAumento = new BigDecimal("1.10");
        funcionarios.forEach(f ->
                f.setSalario(f.getSalario().multiply(fatorAumento).setScale(2, RoundingMode.HALF_UP)));

        System.out.println("\n===== Após aumento de 10% =====");
        funcionarios.forEach(Principal::imprimirFuncionario);

        // agrupar por função em um Map
        Map<String, List<Funcionario>> porFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // imprimir agrupados por função
        System.out.println("\n===== Funcionários por função =====");
        porFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(f -> {
                System.out.print("   ");
                imprimirFuncionario(f);
            });
        });

        // aniversariantes dos meses 10 e 12
        System.out.println("\n===== Aniversariantes em outubro e dezembro =====");
        funcionarios.stream()
                .filter(f -> f.getData_de_nascimento().getMonthValue() == 10
                          || f.getData_de_nascimento().getMonthValue() == 12)
                .forEach(Principal::imprimirFuncionario);

        // funcionário com maior idade
        System.out.println("\n===== Funcionário com maior idade =====");
        funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getData_de_nascimento))
                .ifPresent(f -> System.out.println(
                        "Nome: " + f.getNome() + " | Idade: " + calcularIdade(f.getData_de_nascimento()) + " anos"));

        // ordem alfabética
        System.out.println("\n===== Funcionários em ordem alfabética =====");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(Principal::imprimirFuncionario);

        // total dos salários
        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n===== Total dos salários =====");
        System.out.println("R$ " + FORMATO_NUMERO.format(total));

        // quantidade de salários mínimos por funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n===== Salários mínimos por funcionário =====");
        funcionarios.forEach(f -> {
            BigDecimal qtd = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + FORMATO_NUMERO.format(qtd) + " salários mínimos");
        });
    }

    private static void imprimirFuncionario(Funcionario f) {
        System.out.println("Nome: " + f.getNome()
                + " | Nascimento: " + f.getData_de_nascimento().format(FORMATO_DATA)
                + " | Salário: " + FORMATO_NUMERO.format(f.getSalario())
                + " | Função: " + f.getFuncao());
    }

    private static int calcularIdade(LocalDate data_de_nascimento) {
        return Period.between(data_de_nascimento, LocalDate.now()).getYears();
    }
}