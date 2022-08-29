package aula01082022;

import java.util.Scanner;

public class atividade_12 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        System.out.println("Digite o dividendo:");
        int n1 = input.nextInt();

        System.out.println("Digite o divisor: ");
        int n2 = input.nextInt();

        int rest = 0;
        for(int i=1; i<n1; i++){
            if(n1 >= n2){
                n1 = n1 - n2;
                rest++;
            }

        }
        System.out.println(rest);
        System.out.println(rest*n2);

       /*int mod = n1/n2;

        int res = mod - n2;*/




    }
}
