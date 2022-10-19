package aula28072022;

import java.util.Scanner;

public class atividade_3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe os valores");
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();

        if((a>b)&&(b>c) ){
            System.out.println("O menor  número é: "+c);
        }
        else if((b>c)&&(c>a)){
            System.out.println("O menor número é: "+a);

        }
        else if((c>a)&&(a>b)){
            System.out.println("O menor número é: "+b);
        }
        else{
            System.out.println("Tem valores iguais");
        }
    }
}
