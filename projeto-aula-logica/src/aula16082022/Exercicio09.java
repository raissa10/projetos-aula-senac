package aula16082022;

import java.util.Scanner;

public class Exercicio09 {

    public static void main(String[] args) {
        // Faça um algoritmo que leia Nome e notas (total de 4 notas) de 5 alunos de um colégio.
        //Após a leitura faça:
        //a. Imprima o Nome e a média dos alunos aprovados (Média >= 7.0).
        //b. Imprima o Nome e a média dos alunos em Recuperação (5.0 >= Média menor que 7.0)
        //c. Imprima o Nome e a média dos alunos reprovados (Média menor que 5.0).

        //d. Imprima o percentual de alunos aprovados.
        //e. Imprima o percentual de alunos reprovados.

        Scanner teclado = new Scanner(System.in);

        // 3 alunos ou 5
        int contadorAlunos = 3;

        // Criando os vetores de armazenamento
        String alunosAprovados [] = new String[contadorAlunos];
        String alunosRecuperacao [] = new String[contadorAlunos];
        String alunosReprovados [] = new String[contadorAlunos];

        // Vetores de Medias
        double mediaAlunosAprovados [] = new double[contadorAlunos];
        double mediaAlunosRecuperacao [] = new double[contadorAlunos];
        double mediaAlunosReprovados [] = new double[contadorAlunos];

        String alunos[] = new String[contadorAlunos];
        double notasaluno[] = new double[4];

        double percentualAlunosAprovados = 0;
        double percentualAlunosReprovados = 0;
        double percentualAlunosRecuperacao = 0;

        // contador de aprovados, recuperacao e reprovados
        int contadorAprovados = 0, contadorReprovados = 0, contadorRecuperacao = 0;

        for (int i = 0; i < alunos.length; i++){
            System.out.println("Informe o nome do aluno:");
            alunos[i] = teclado.next();

            int contaNota = 1;
            double somaNotaAluno = 0;
            for (int j = 0; j < notasaluno.length; j++){
                System.out.println("Informe a " + contaNota + "ª nota do aluno:");
                notasaluno[j] = teclado.nextDouble();

                somaNotaAluno = somaNotaAluno + notasaluno[j];

                contaNota++;
            }

            double mediaAluno = somaNotaAluno / 4;
            if(mediaAluno >= 7){
                // Armazena o nome do aluno aprovado
                alunosAprovados[contadorAprovados] = alunos[i];
                mediaAlunosAprovados[contadorAprovados] = mediaAluno;

                // incrementa o contador
                contadorAprovados++;
            } else if(mediaAluno >= 5 && mediaAluno < 7){
                // Armazena o nome do aluno em recuperacao
                alunosRecuperacao[contadorRecuperacao] = alunos[i];
                mediaAlunosRecuperacao[contadorRecuperacao] = mediaAluno;

                // incrementa contador
                contadorRecuperacao ++;
            } else {
                // Alunos reprovados
                alunosReprovados[contadorReprovados] = alunos[i];
                mediaAlunosReprovados[contadorReprovados] = mediaAluno;

                // incrementa alunos reprovados
                contadorReprovados++;
            }
        }

        // Precisa fazer cast do tipo int para double para nao ficar zerado
        percentualAlunosAprovados = ((double)contadorAprovados / (double)contadorAlunos) * 100;
        percentualAlunosReprovados = ((double)contadorReprovados / (double)contadorAlunos) * 100;
        percentualAlunosRecuperacao = ((double)contadorRecuperacao / (double)contadorAlunos) * 100;

        // Percorrendo vetores de dados
        System.out.println("Alunos aprovados:");
        for(int i = 0; i < contadorAprovados; i++){
            System.out.println("Aluno: " + alunosAprovados[i] + " foi aprovado com a media:" + mediaAlunosAprovados[i]);
        }

        System.out.println("Alunos recuperação:");
        for(int i = 0; i < contadorRecuperacao; i++){
            System.out.println("Aluno: " + alunosRecuperacao[i] + " ficou em recuperacao com a media:" + mediaAlunosRecuperacao[i]);
        }

        System.out.println("Alunos reprovados:");
        for(int i = 0; i < contadorReprovados; i++){
            System.out.println("Aluno: " + alunosReprovados[i] + " foi reprovado com a media:" + mediaAlunosReprovados[i]);
        }

        System.out.println();
        System.out.println("Percentual de alunos aprovados:" + percentualAlunosAprovados);
        System.out.println("Percentual de alunos recuperacao:" + percentualAlunosRecuperacao);
        System.out.println("Percentual de alunos reprovados:" + percentualAlunosReprovados);

        // Testes
        // aluno 1 - joao - nota 1=>3, nota 2=>4, nota3 => 3, nota4=> 2 => Reprovado media = 3
        // aluno 2 - maria - nota 1=>5, nota 2=>6, nota3 => 6, nota4=> 6 => Recuperacao media = 5.75
        // aluno 3 - pedro - nota 1=>7, nota 2=>7, nota3 => 9, nota4=> 8 => Aprovado media = 7,75

        // percentual - 33.33% de cada tipo
    }
}
