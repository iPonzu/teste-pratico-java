import java.time.LocalDate;

public class Pessoa {
    private String nome;
    private LocalDate data_de_nascimento;

    public Pessoa
    (
        String nome,
        LocalDate data_de_nascimento
    ){
        this.nome = nome;
        this.data_de_nascimento = data_de_nascimento;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public LocalDate getData_de_nascimento(){
        return data_de_nascimento;
    }
    public void setData_de_nascimento(LocalDate data_de_nascimento){
        this.data_de_nascimento = data_de_nascimento;
    }
}
