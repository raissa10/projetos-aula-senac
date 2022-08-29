package aula28072022;

import java.util.Scanner;

public class atividade_1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        float vetor[] = new float[3];

        float media = 0;

        System.out.println("Informe o nome do aluno");
        String nome = input.next();

        for(int i=1; i<=vetor.length; i++){
            System.out.println("Informe a nota "+i);
            media+= input.nextFloat();
        }

        media = media/vetor.length;

        if(media >= 8){
            System.out.println("Aprovado");
            System.out.println("Aluno "+nome+" com media "+media);
        }
        else{
            System.out.println("Reprovado");
            System.out.printf("Aluno "+nome+" com media: %.1f",media);
            //utilizar printf é %.1f para saber a quantidade de numero depois da virgula
        }


    }
}
