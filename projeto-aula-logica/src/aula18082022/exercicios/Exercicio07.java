package aula18082022.exercicios;

import java.util.Arrays;
import java.util.Scanner;

public class Exercicio07 {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        /*Dada uma matriz de ordem 3X3.
        Matriz A =
        [
          [2, 5, 11],
          [5, 4, 17],
          [11, 17, 6]
        ]
        Faca um algoritmo que gera a Matriz B Oposta desta matriz A.*/
        int linhas = 3, colunas = 3;
        int matriz_B[][] = new int[linhas][colunas];
        int matriz_A[][] = {{ 2,  5, 11},
                { 5,  4, 17},
                {11, 17,  6}};
        System.out.println("{"+matriz_A[0][0]+","+matriz_A[0][1]+","+matriz_A[0][2]+"}");
        System.out.println("{"+matriz_A[1][0]+","+matriz_A[1][1]+","+matriz_A[1][2]+"}");
        System.out.println("{"+matriz_A[2][0]+","+matriz_A[2][1]+","+matriz_A[2][2]+"}");
        for (int i=0;i< matriz_B.length;i=i+1){
            for (int j=0;j< matriz_B.length;j=j+1){
                matriz_B[i][j] = matriz_A[i][j]*(-1);
            }
        }

        for (int i=0;i< matriz_B.length;i++){
            System.out.println(Arrays.toString(matriz_B[i]));
        }
    }
}
