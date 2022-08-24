package aula16082022;

import javax.swing.*;

public class exercicio08 {

    public static void main(String[] args) {

        // Armazena a palavra numa
        String palavra = JOptionPane.showInputDialog(null, "Digite uma palavra");

        //tamanho recebe o qtd de letras q possui a palavra
        int tamanho = palavra.length();

        String palavraInvertida = "";

        //pega a ultima letra da variavel palavra,
        // e inseri na primeira de palavraInvertida,
        // depois pega penultima e inseri na 2ª,
        // e assim em diante, até pegar a primeira,
        // e colocar na ultima.
        for (int i = tamanho - 1; i >= 0; i--) {
            palavraInvertida += palavra.charAt(i);
        }

        //Compara as Duas Strings e Exibe o Resultado
        if (palavra.equalsIgnoreCase(palavraInvertida)) {
            JOptionPane.showMessageDialog(null, palavra + " É uma palavra palindroma");
        } else {
            JOptionPane.showMessageDialog(null, palavra + " Não é uma palavra palindroma");
        }
    }
}
