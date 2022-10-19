package aula22082022;

import java.util.Scanner;

public class Exemplo01 {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        // Faca um programa que, a partir de uma string digitada pelo usuario, imprima
        // a - o numero de caracteres da string;
        // b - a string com todas suas letras em maiusculo
        // c - o numero de vogais da string
        // d - se a string comeca com "UNI" (ignorando maiusculas/minusculas)
        // e - se a string digitada termina com "RIO"(ignorando maiusculas e minusculas)
        // f - O numero de digitos(0 a 9) da string
        // g - se a string e um palindromo ou nao

//        System.out.println("Informe uma palavra:");
        // 1 palavra apenas
//        String palavra = "54788Joao31434";//teclado.next();
//        String palavra = "ARARA";//teclado.next();
        String palavra = "OVO";//teclado.next();

        System.out.println("Letra A - o numero de caracteres da string");
        System.out.println(palavra.length());

        System.out.println("Letra B - a string com todas suas letras em maiusculo");
        System.out.println(palavra.toUpperCase());

        System.out.println("Letra C - o numero de vogais da string");
        int tamanhoPalavra = palavra.length();
        String partes [] = new String[tamanhoPalavra];

        int numeroVogais = 0;
        for(int i = 0; i < tamanhoPalavra;i++){
            char letra = palavra.charAt(i);
            System.out.println("Letra: " + letra);


            if((letra == 'a') || (letra == 'A')) {
                numeroVogais++;
            }

            if((letra == 'e') || (letra == 'E')) {
                numeroVogais++;
            }

            if((letra == 'i') || (letra == 'I')) {
                numeroVogais++;
            }

            if((letra == 'o') || (letra == 'O')) {
                numeroVogais++;
            }

            if((letra == 'u') || (letra == 'U')) {
                numeroVogais++;
            }
        }
        System.out.println("Quantidade de vogais da palavra:" + palavra + " e:" + numeroVogais);

        System.out.println("Letra D - se a string comeca com \"UNI\" (ignorando maiusculas/minusculas)");
        // Forma Um
        if(palavra.startsWith("UNI")){
            System.out.println("A string comeca com \"UNI\"");
        } else {
            System.out.println("A string não comeca com \"UNI\"");
        }

        System.out.println("Letra E - se a string termina com \"RIO\" (ignorando maiusculas/minusculas)");
        // Forma Um
        if(palavra.endsWith("RIO")){
            System.out.println("A string termina com \"RIO\"");
        } else {
            System.out.println("A string não termina com \"RIO\"");
        }

        // Forma 2
        int tamanhoPalavra2 = palavra.length();
        String partePalavra = "UNI";
        String novaLetra = "";
        for(int i = 0; i < tamanhoPalavra2;i++){
            if(i <= 2){
                char letra = palavra.charAt(i);
                novaLetra = novaLetra + letra;
            }
        }

        if(novaLetra.equalsIgnoreCase(partePalavra)){
            System.out.println("Forma 2 - A string comeca com \"UNI\"");
        } else {
            System.out.println("Forma 2 - A string nao comeca com \"UNI\"");
        }

        // Forma 3
        System.out.println("Usando substring:");
        String buscaNaPalavra = palavra.substring(0, 3);

        System.out.println("nova parte de palavra:" + buscaNaPalavra);

        if(buscaNaPalavra.equalsIgnoreCase(partePalavra)){
            System.out.println("Forma 3 - A string comeca com \"UNI\"");
        } else {
            System.out.println("Forma 3 - A string nao comeca com \"UNI\"");
        }

        System.out.println("Letra F - O numero de digitos(0 a 9) da string, se contem digitos numericos na palavra");
        int numeroDigitados= 0;
        for(int i = 0; i < tamanhoPalavra;i++){
            char letra = palavra.charAt(i);
            System.out.println("Letra: " + letra);


            if((letra == '0')
                || (letra == '1')
                || (letra == '2')
                || (letra == '3')
                || (letra == '4')
                || (letra == '5')
                || (letra == '6')
                || (letra == '7')
                || (letra == '8')
                || (letra == '9')
            ) {
                System.out.println("Numero na palavra: " + letra + " na posicao:" + i);

                numeroDigitados++;
            }

            // final da palavra
            if(i == tamanhoPalavra - 1){
                System.out.println("Quantidade de numeros digitados:" + numeroDigitados);
            }
        }

        System.out.println("LETRA G - g - se a string e um palindromo ou nao \n\n\n\n");
        String novaPalavra = "";
        for(int i = tamanhoPalavra - 1; i >= 0;i--) {
            char letra = palavra.charAt(i);
            System.out.println("Letra: " + letra);

            novaPalavra = novaPalavra + letra;
        }

        if(novaPalavra.equalsIgnoreCase(palavra)){
            System.out.println("A palavra: " + palavra + " é um Palindromo!");
        } else {
            System.out.println("A palavra: " + palavra + " não é um Palindromo!");
        }
    }
}
