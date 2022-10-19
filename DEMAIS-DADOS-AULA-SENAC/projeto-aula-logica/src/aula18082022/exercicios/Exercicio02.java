package aula18082022.exercicios;

import java.util.Arrays;

public class Exercicio02 {

    public static void main(String[] args) {
        // Exercicio 02
        //Dadas duas matrizes A e B de ordem 3 X 3.
        //Matriz A =
        //[
        //  [2, 59, 617],
        //  [8, 5, 95],
        //  [1, 59, 67]
        //]
        //Matriz B =
        //[
        //  [22, 559, 678],
        //  [13, 59 , 7],
        //  [27, 159, 627]
        //]
        //Faca um algoritmo que some as duas e gere a matriz C que é a Soma dos respectivos elementos de A e B.
        //Imprima os valores das tres matrizes.

        int Mat_A[][] = {
                {2, 59, 617},
                {8, 5, 95},
                {1, 59, 67}
        };
        int Mat_B [][] = {
                {22, 559,678},
                {13, 59, 7},
                {27, 159, 627},
        } ;
        int Mat_C [][]= new int [3][3];

        for (int l = 0; l < Mat_C.length; l++){
            for (int c = 0; c < Mat_C[l].length; c++) {
                Mat_C[l][c] = (Mat_A[l][c] + Mat_B[l][c] );
            }
        }
        System.out.println("\nMatriz A: ");
        for (int l = 0; l < Mat_B.length; l++) {
            System.out.println(Arrays.toString(Mat_A[l]));
        }

        System.out.println("\nMatriz B: ");
        for (int l = 0; l < Mat_B.length; l++) {
            System.out.println( Arrays.toString(Mat_B[l]));
        }

        System.out.println("\nMatriz A + Matriz B: ");
        for (int l = 0; l < Mat_B.length; l++) {
            System.out.println( Arrays.toString(Mat_C[l]));
        }
    }
}
