package aula08082022;

import java.util.Scanner;

public class Exercicio01 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Suponha que para cada aluno de uma sala exista
        // uma ficha contendo o nome e a idade de 05 alunos.

        // Faça algoritmo que determine quantos alunos tem idade maior que 30.

        int idade = 0;
        String nome = "";

        // teste => 35,25,39,24,38
        // => total => 3
        int quantidadeAlunoMaiorTrintaAnos = 0;

        for(int i = 0; i < 5;i++ ){
            System.out.println("Nome Aluno: ");
            nome = teclado.next();

            System.out.println("Idade:");
            idade = teclado.nextInt();

            if(idade >= 30){
                // conta com idade maior ou igual a 30
                quantidadeAlunoMaiorTrintaAnos++;
            }
        }

        System.out.println("Quandidade de alunos com mais de 30 anos:" + quantidadeAlunoMaiorTrintaAnos);

    }
}
