package aula02082022;

import java.util.Scanner;

public class atividade_9999 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String nomes[] = new String[2];
        int idades[] = new int[2];

        int idadeMaiorDe30 = 0;
        int idadeMenorDe30 = 0;

        for(int i=0;i<nomes.length;i++){
            int numAluno = i + 1;
            System.out.println("Informe o nome do aluno: "+numAluno);
            nomes[i] = input.next();

            System.out.println("Informe a idade do aluno "+nomes[i]);
            idades[i] = input.nextInt();

            if(idades[i] > 30){
                idadeMaiorDe30++;
            }
            else{
                idadeMenorDe30++;
            }
        }
        //
        if(idadeMaiorDe30 == 1){
            System.out.println(idadeMaiorDe30+" aluno maior de 30 anos");
        }
        else if(idadeMaiorDe30 > 1){
            System.out.println(idadeMaiorDe30+" alunos maior de 30 anos");
        }
        else{
            System.out.println("Nenhum aluno maior de 30 anos");
        }


        if(idadeMenorDe30 == 1){
            System.out.println(idadeMenorDe30+" aluno menor de 30 anos");
        }
        else if(idadeMenorDe30 > 1){
            System.out.println(idadeMenorDe30+" alunos menor de 30 anos");
        }
        else{
            System.out.println("Nenhum aluno menor de 30 anos");
        }
    }
}
