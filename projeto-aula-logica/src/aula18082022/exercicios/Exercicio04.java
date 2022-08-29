package aula18082022.exercicios;

public class Exercicio04 {

    public static void main(String[] args) {
        // Dada uma matriz A 2x3 de valores reais(float ou Double)(salários).
        //Matriz A =
        //[
        //  [2500.98, 5573.45, 7980.32],
        //  [850.26, 987.23, 3536.24]
        //]
        //Faça um algoritmo que faca a leitura destes valores e imprima:
        //a. Soma dos dados de cada coluna e linha.
        //Ex: Soma coluna 01=> xxx
        //Ex: Soma coluna 02=> xxx
        //Ex: Soma coluna 03=> xxx
        //Ex: Soma linha 01=> xxx
        //Ex: Soma linha 02=> xxx
        //b. listar valores menores que a media em um novo vetor(se necessario for).

        float mat[][] = {
                {2500.98f , 5573.45f, 7980.32f},
                {850.26f, 987.23f, 3536.24f},
        };
        int i = 6;
        float menmedia[] = new float [i];
        int l,c;
        float sc1 = 0, sc2 = 0, sc3 = 0;
        float sl1 = 0, sl2 = 0, st = 0;
        float med;

        for (l = 0; l< mat.length; l++) {
            for (c = 0; c< mat[l].length; c++){
                st = st + mat[l][c];
                if (c == 0) {
                    sc1 = sc1 + mat[l][c];
                }
                if (c == 1) {
                    sc2 = sc2 + mat[l][c];
                }
                if (c == 2) {
                    sc3 = sc3 + mat[l][c];
                }

                if (l == 0) {
                    sl1 = sl1 + mat[l][c];
                }
                if (l == 1) {
                    sl2 = sl2 + mat[l][c];
                }
            }
        }

        med = st / 6;
        int cont = 0;
        for (l = 0; l < mat.length; l++) {
            for (c = 0; c < mat[l].length; c++) {

                if (mat[l][c] < med) {

                    menmedia[cont] = mat[l][c];
                    cont++;
                }
            }
        }
        //System.out.println("Contador: " + cont);
        System.out.println("Soma da Coluna 1: " + sc1);
        System.out.println("Soma da Coluna 2: " + sc2);
        System.out.println("Soma da Coluna 3: " + sc3);
        System.out.println("Soma da Linha 1: " + sl1);
        System.out.println("Soma da Linha 2: " + sl2);
        System.out.println("A média de salário é: " + med );
        System.out.println("Os salários abaixo da média são: ");
        for (i = 0; i < cont; i++) {
            System.out.println(menmedia[i]);
        }
    }
}
