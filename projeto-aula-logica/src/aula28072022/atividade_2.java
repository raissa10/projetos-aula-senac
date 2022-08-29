package aula28072022;

import java.util.Scanner;

public class atividade_2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite os três números");
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();

        if((a >= b) && (b >=c)){
            System.out.println(c+","+b+","+a);

        }
        else if((b>=c)&&(c>=a)){
            System.out.println(a+","+c+","+b);
        }

        else if((c>=a)&&(a>=b)){
            System.out.println(b+","+a+","+c);
        }

        else if((b>=a)&&(a>=c)){
            System.out.println(c+","+a+","+b);
        }

        else if((c>=b)&&(b>=a)){
            System.out.println(a+","+b+","+c);
        }

        else if((a>=c)&&(c>=b)){
            System.out.println(b+","+c+","+a);
        }


    }
}
