package aula22082022;

public class assuntoAula {

    public static void main(String[] args) {
        /***
         * Constantes em JAVA com atributo `final`
         * Comandos `break` e `continue` em lacos de repeticao
         *
         * Passar funcoes apos matrizes
         * tipos de funcoes(com retornos(Tipadas) e sem retorno(void))
         * funcoes com parametro por valor(passado o valor no parametro)
         * funcoes com parametro por referencia(passado apenas a referencia)
         *
         * Passar assunto sobre  `enums`
         * Iniciar Classes e atributos apos finalizar as pastas da aula.
         *
         * */

        String pessoas [] = {"joao", "maria", "pedro", "Ana", "taila"};

        // Algoritmo 1 - Mostre os nomes na tela enquanto nenhuma pessoa se chamar Ana
//        for (int i = 0; i < pessoas.length; i++){
//
//            // comando break
//            String nome = pessoas[i];
//            if(nome.equalsIgnoreCase("Ana")){
//                // para a execucao
//                break;
//            }
//
//            System.out.println(nome);
//
//        }

        // Algoritmo 2 - Mostre os nomes na tela diferentes de Ana
        for (int i = 0; i < pessoas.length; i++){

            // comando continue
            String nome = pessoas[i];
            if(nome.equalsIgnoreCase("Ana")){
                // continua a execucao
                continue;
            }

            if(nome.equalsIgnoreCase("taila")){
                // continua a execucao
                continue;
            }

            System.out.println(nome);

        }

        // debugger comando F8  pula uma linha por vez
        // comando F7 entra numa funcao ou classe
        // comando f9 vai para o proximo breakpoint
    }
}
