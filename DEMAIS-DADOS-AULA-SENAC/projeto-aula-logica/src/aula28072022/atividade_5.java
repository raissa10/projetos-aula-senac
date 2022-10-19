package aula28072022;

import java.util.Scanner;

public class atividade_5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe os valores");
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();

        if((a>b)&&(b>c) ){
            System.out.println("O número intermediario é: "+b);
        }
        else if((b>c)&&(c>a)){
            System.out.println("O número intermediario é: "+c);
        }
        else if((c>a)&&(a>b)){
            System.out.println("O número intermediario é: "+a);
        }
        else{
            System.out.println("Tem valores iguais");
        }

    }
}
