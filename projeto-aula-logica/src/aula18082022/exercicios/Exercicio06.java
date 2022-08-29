package aula18082022.exercicios;

import java.util.Scanner;

public class Exercicio06 {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        /*Dada uma matriz de ordem 3X3.
        Matriz A =
        [
          [2, 5, 11],
          [5, 4, 17],
          [11, 17, 6]
        ]
        Faca um algoritmo que verifique se esta Matriz é simetrica.
        Para isso sabemos que a Matriz é simetrica quando: Matriz A = Matriz A(Transposta).
        Desse modo temos de gerar a Matriz transposta de A e ver se Aij = Aji.*/
        int linhas = 3, colunas = 3;
        int matriz_B[][] = new int[linhas][colunas];
        int matriz_A[][] = {{ 2,  5, 11},
                { 5,  4, 17},
                {11, 17,  6}};
        for (int i=0;i< matriz_B.length;i=i+1){
            for (int j=0;j< matriz_B.length;j=j+1){
                matriz_B[j][i] = matriz_A[i][j];
            }
        }

        System.out.println("Dados da matriz Transposta");
        boolean transposta = true;
        for (int i=0;i< matriz_B.length;i=i+1){
            for (int j=0;j< matriz_B.length;j=j+1){
                if(matriz_A[i][j] != matriz_B[i][j]){
                    transposta = false;
                }
            }
        }

        if(transposta){
            System.out.println("A matriz e transposta!");
        } else {
            System.out.println("A matriz nao e transposta!");
        }
    }
}
