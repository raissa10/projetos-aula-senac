package aula28072022;

import java.util.Scanner;

public class atividade_7 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe um valor");
        int numero = input.nextInt();

        int antecessor = numero + 1;
        int sucessor = numero - 1;

        System.out.println("O antecessor do número "+numero+" é "+antecessor);
        System.out.println("O sucessor do número "+numero+" é "+sucessor);

    }
}
