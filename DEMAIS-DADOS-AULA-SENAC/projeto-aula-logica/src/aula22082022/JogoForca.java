package aula22082022;

import java.util.Arrays;

public class JogoForca {

    public static void main(String[] args) {
        // int erros = 1;

//        enforcado(erros);
        palavrasForcaMatrizes();
    }


    static void palavrasForca(){

        String separator = "#";

        // Jogo da forca
        String listaPalavras =
            "bola#" +
            "abacate#" +
            "agua#" +
            "arara";

        String listaDicas =
                "E redonda#" +
                "e verde e uma fruta#" +
                "corpo humano e composto por 70%#" +
                "e uma ave e uma palavra palindroma!";

        String palavras [] = listaPalavras.split(separator);
        String dicas [] = listaDicas.split(separator);

        for(int i = 0; i < palavras.length;i++){
            // System.out.println(Arrays.toString(   palavras[i]   ));
            //      System.out.println(palavras[i]);

            String palavra = palavras[i];

            int tamanhoPalavra = palavra.length();

            System.out.println("Dica: " + dicas[i]);
            for(int j = 0; j < tamanhoPalavra;j++) {
                char letra = palavra.charAt(i);
                System.out.print("_ ");
            }
        }
    }

    static void palavrasForcaMatrizes(){

        // Jogo da forca - Matrizes
        String listaPalavrasEDicas [][]= {
                {"bola", "abacate", "agua", "arara"},
                {"E redonda", " e verde e uma fruta", "corpo humano e composto por 70%", "e uma ave e uma palavra palindroma!"}
        };

        for(int i = 0; i < listaPalavrasEDicas.length;i++){
            // System.out.println(Arrays.toString(   listaPalavrasEDicas[i]   ));

            // lista de palavras
            if(i == 0){
                System.out.println("Palavras");
                for(int j = 0; j < listaPalavrasEDicas[i].length; j++){
                    System.out.println(listaPalavrasEDicas[i][j]);
                }
            } else if(i == 1){
                System.out.println("Dicas");
                // lista de dicas
                for(int j = 0; j < listaPalavrasEDicas[i].length; j++){
                    System.out.println(listaPalavrasEDicas[i][j]);
                }
            }
        }
    }


    static void enforcado(int erros){

        switch (erros){
            case 1:
                // CABECA
                System.out.println("  _____");
                System.out.println(" / * * \\");
                System.out.println("|  ^   |");
                System.out.println("\\____/");
                break;
            case 2:
                // BRACO 1
                System.out.println("Braco 1");
                break;
            case 3:
                // BRACO 2
                System.out.println("Braco 2");
                break;
            case 4:
                // CORPO
                System.out.println("Corpo");
                break;
            case 5:
                // PERNA 1
                break;
            case 6:
            // PERNA 2
                break;
        }

        if (erros == 6){
            System.out.println("Perdeu...");
        } else {
            System.out.println("Continue tentando...");
        }



    }

}
