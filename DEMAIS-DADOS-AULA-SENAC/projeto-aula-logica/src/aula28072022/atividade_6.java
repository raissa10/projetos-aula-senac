package aula28072022;

import java.util.Scanner;

public class atividade_6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite os três números");
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();

        if((a >= b) && (b >=c)){
            System.out.println("Maior número é "+a);
            System.out.println("intermediario número é "+b);
            System.out.println("Menor número é "+c);

        }
        else if((b>=c)&&(c>=a)){
            System.out.println("Maior número é "+b);
            System.out.println("intermediario número é "+c);
            System.out.println("Menor número é "+a);
        }

        else if((c>=a)&&(a>=b)){
            System.out.println("Maior número é "+c);
            System.out.println("intermediario número é "+a);
            System.out.println("Menor número é "+b);
        }

        else if((b>=a)&&(a>=c)){
            System.out.println("Maior número é "+b);
            System.out.println("intermediario número é "+a);
            System.out.println("Menor número é "+c);
        }

        else if((c>=b)&&(b>=a)){
            System.out.println("Maior número é "+c);
            System.out.println("intermediario número é "+b);
            System.out.println("Menor número é "+a);
        }

        else if((a>=c)&&(c>=b)){
            System.out.println("Maior número é "+a);
            System.out.println("intermediario número é "+c);
            System.out.println("Menor número é "+b);
        }

    }
}
