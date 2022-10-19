package aula18082022.exercicios;

import java.util.Arrays;

public class Exercicio05 {

    public static void main(String[] args) {
        // Exercicio 05
        //Dada uma matriz de ordem 3X3.
        //Matriz A =
        //[
        //  [1, 5,],
        //  [7, 3],
        //  [8, 2]
        //]
        //Faca um algoritmo que:
        //a. Gere a Matriz B transposta de A.
        //a. A Matriz gerada sera a matriz abaixo:
        //Matriz B - Transposta =
        //[
        //  [1, 7, 8],
        //  [5, 3, 2]
        //]

        int mat_a[][] = {
                {1, 5},
                {7, 3},
                {8, 2},
        };
        int mat_b[][] = new int [2][3];

        for (int l = 0; l< 2; l++){
            for (int c = 0; c < 3; c++ ) {
                mat_b[l][c] = mat_a[c][l];
            }
            System.out.println(Arrays.toString(mat_b[l]));
        }
    }
}
