package aula28072022;

import java.util.Scanner;

public class atividade_4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe os valores");
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();

        if((a>b)&&(b>c) ){
            System.out.println("O maior  número é: "+a);
        }
        else if((b>c)&&(c>a)){
            System.out.println("O maior número é: "+b);
        }
        else if((c>a)&&(a>b)){
            System.out.println("O maior número é: "+c);
        }
        else{
            System.out.println("Tem valores iguais");
        }

    }
}
