package aula18082022.exercicios;

public class Exercicio03 {

    public static void main(String[] args) {
        // Dado uma matriz A de ordem 3x3.
        //Matriz A =
        //[
        //  [2, 5, 7],
        //  [8, 9, 3],
        //  [1, 6, 10]
        //]
        //Faca um algoritmo que:
        //a. Calcule a soma dos elementos da primeira coluna
        //b. calcule o produto dos elementos da primeira linha
        //c. calcule a soma de todos os elementos da matriz;
        //d. Calcule a soma da diagonal principal

        int mat_a [][] = {
                {2, 5, 7},
                {8, 9, 3},
                {1, 6, 10},
        };
        int soma = 0;
        int prod = 1;
        int somtod = 0;
        int diagonal = 0;
        int l, c;

        for (l = 0; l < mat_a.length; l++) {
            for (c = 0; c < mat_a[l].length; c++) {
                somtod = somtod + mat_a[l][c];

                if (l==0){
                    prod = prod * mat_a[l][c];
                }
                if (c == 0) {
                    soma = soma + mat_a[l][c];
                }
                if (l == c) {
                    diagonal = diagonal + mat_a[l][c];
                }
            }
        }
        System.out.println("A soma dos elementos da primeira coluna é: " + soma);
        System.out.println("O produto dos elementos da primeira linha é: " + prod);
        System.out.println("A soma de todos os elementos da matriz é: " + somtod);
        System.out.println("A soma da diagonal principal é: " + diagonal);
    }
}
