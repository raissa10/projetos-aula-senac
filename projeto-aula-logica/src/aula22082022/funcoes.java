package aula22082022;

public class funcoes {

    // Constantes em JAVA
    // Conexao do banco de dados
    private static final String HOST   = "localhost";
    private static final String DBNAME = "meubancodados";
    private static final String USER   = "user";
    private static final String PASS   = "pass";

    public static void main(String[] args) {

        // constantes
        System.out.println("host: " + HOST);

        // metodo estatico
        // Classe Pessoa, como metodo estatico calculaIR
        // Chamar assim:Pessoa.calculaIR()
        float salario = Pessoa.calculaIR(2500);
        System.out.println("Salario calculado:" + salario);

        // metodo nao estatico
        Pessoa pessoa = new Pessoa();
        int idade = pessoa.getIdade();
        System.out.println("Idade:" + idade);

        // funcao sem retorno
//        soma(10,15);
//        soma(11,15);
//        soma(14,15);
//        soma(16,15);

        // funcao com retorno
//        int resultado = somaComRetorno(10, 15);
//        System.out.println("A soma e: " + resultado);
    }

    // Funcao sem retorno - JAVA
    static void soma(int numero1, int numero2){
        int resultado = numero1 + numero2;

        System.out.println("A soma e: " + resultado);
    }

    // Passar o tipo para ser retornado
    static int somaComRetorno(int numero1, int numero2) {
        // valor fixo
        int resultado = numero1 + numero2;

        return resultado;
    }

}
