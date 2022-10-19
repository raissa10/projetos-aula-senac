package aula11082022;

public class exercicio52 {
    // Os números perfeitos são iguais à soma de seus divisores:
    // 6 pode ser dividido por 1, 2 e 3 e, quando você soma esses números, o resultado é 6.
    // Faça um algoritmo que conte quantos numeros perfeitos existem entre 1 e 5000;

//
    public boolean isPerfeito(int numero){
        //int numero = 6;
        int soma = 0;

//        for i = 1 < = 5000 {
//            //  for (int i = numero; i > 0; i--){

        // numero = i
            for (int i = 1; i <= numero; i++) {
                // System.out.println("Numero: " + i);

                // verificar se o resto da divisao e zero
                // se for zero, ele e divisivel, senao nao

                if (numero % i == 0 && (i != numero)) {
                    soma = soma + i;

                    System.out.println("somando...." + i);
                }

                System.out.println("Soma na posicao:" + i + " e de:" + soma);
            }

            if (soma == numero) {
                System.out.println("Perfeito!");
                return true;
            }
//
//        }

        return false;
    }

    public static void main(String[] args) {
        int n, i, soma = 0, numpft = 0;
        for ( i = 1; i <= 10000; i++) {

            soma = 0;
            for (n = 1; n < i; n++) {

                if (i % n == 0) {
                    soma = soma + n;
                }
            }

            if (soma == i) {
                System.out.println("Perfeito: " + i);
                numpft++;
            }


        }
        System.out.println("Existem " + numpft + " números perfeitos entre 1 e 5000!");


    }
}
