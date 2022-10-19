package aula22082022;
import java.util.Arrays;

public class Exercicio05 {

    public static void main(String[] args) {

        int tamanhoVetor = 625;
        int divisor = 25;
        int vetor [] = new int[tamanhoVetor];
        int numeroMaximo = tamanhoVetor * 2;
        int contador = 0;
        for(int i = 0; i < numeroMaximo;i++){
            if(i % 2 == 0){
                vetor[contador] = i;
                contador++;
            }
        }

        int tamanhoMatriz = (tamanhoVetor / divisor);
        int matriz[][] = new int[tamanhoMatriz][tamanhoMatriz];
        contador = 0;
        for(int i = 0; i < tamanhoMatriz; i++){
            for(int j = 0; j < tamanhoMatriz; j++){
                matriz[i][j] = vetor[contador];
                contador++;
            }
        }

        System.out.println("Nova matriz formada:");
        for(int i = 0; i < matriz.length; i++){
            System.out.println( Arrays.toString(matriz[i]));
        }
    }
}
