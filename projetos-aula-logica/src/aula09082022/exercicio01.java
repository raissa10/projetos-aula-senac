package aula09082022;

public class exercicio01 {
    public static void main(String[] args) {
        // Escreva um algoritmo que exiba 20 vezes a mensagem “Eu gosto de estudar Algoritmos!”.
        String mensagem = "Eu gosto de estudar Algoritmos!";

        int contador = 0;
        int total = 20;
        // forma 1 WHILE
        // ENQUANTO contador < total, continua REPETINDO
        while(contador < total){
            // System.out.println("posicao:" + contador);

            System.out.println(mensagem);

            contador = contador + 1;
        }

        // forma 2 - FOR
        int numeroDescrescente = 20;
        int numeroCrescente = 0;
        for (contador = 0; contador < total; contador++){
            System.out.println("posicao usando FOR: " + contador);

            System.out.println(mensagem);

            numeroDescrescente = numeroDescrescente - 1;
            numeroCrescente = numeroCrescente + 1;

            System.out.println("Descrescente: " + numeroDescrescente);
            System.out.println("Crescente: " + numeroCrescente);
        }

        // 3 forma descrescente
        int descescente2 = 1;
        for (contador = 20; contador >= 0; contador--){
            System.out.println("Descrescente 2:" + contador);
        }
    }
}
