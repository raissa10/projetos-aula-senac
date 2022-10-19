package aula09082022;

public class exercicio02 {
    public static void main(String[] args) {
        // Escreva um algoritmo que calcule a soma dos números de 1 a 15.

        int contador = 1;
        // lista de repeticao
        int total = 15;



        // operacao => soma dos números de 1 a 15.  111
        int soma = 0;
        for(contador = 1; contador <= total;contador++){
            System.out.println("Posicao:" + contador);

            // Acumular as somas
            // soma atual = soma anterior + numero atual
               soma       = soma          + contador;

            System.out.println("Soma calculada na posicao:" + contador + " e de: " + soma);

            // Exemplo
            // ver a idade maior de uma lista de pessoas
            //int idade = teclado = idade;
            // int idadeMaior = 0
//            if(idade > idadeMaior){
//                idadeMaior = idade;
//            }
        }

        System.out.println("Soma:" + soma);

    }
}
