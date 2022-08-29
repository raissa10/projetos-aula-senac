package aula29082022;

public class Exercicio01 {

    // site:http://www.universidadejava.com.br/java/
    // Exercícios sobre métodos
    //Crie uma classe chamada Calculadora. Esta classe deve possuir os seguintes métodos:
    //public double soma(double num1, double num2); Retorna a soma dos dois números.
    //
    //public double subtracao(int num1, double num2); Retorna a subtração dos dois números.
    //
    //public double produto(double num1, int num2); Retorna o produto dos dois números.
    //
    //public double divisao(int num1, int num2); Retorna o resultado da divisão dos dois números.
    //
    //public double restodivisao(int num1, short num2); Retorna o resto da divisão dos dois números.
    //
    //Elabore um roteiro de teste para a sua calculadora e observe os resultados.

    public static void main(String[] args) {
        calculaSoma(10,15);

        int resultadoRetorno = calculaSomaRetorno(15,15);

//        System.out.println("mostrando resultado retornado do metodo: " + resultadoRetorno);
    }

    // metodo sem retorno
    public static void calculaSoma(int n1, int n2){
        int resultado = n1 + n2;
        System.out.println("Resultado da soma e:" + resultado);
    }

    // metodo com retorno, retorna o tipo do metodo
    public static int calculaSomaRetorno(int n1, int n2){
        int resultado = n1 + n2;

        System.out.println("Resultado da soma e:" + resultado);

        return resultado;
    }


}
