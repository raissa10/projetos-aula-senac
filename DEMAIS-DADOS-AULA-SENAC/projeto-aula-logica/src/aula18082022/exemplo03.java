package aula18082022;

public class exemplo03 {

    public static void main(String[] args) {

        int [] primos = {2,3,5,7,9,11,13,17,19};
        char [] diaSemana = {'d', 's', 't', 'q', 'q', 's', 's'};
        String [] meses = {"janeiro", "fevereiro", "marco", "abril"};
        float [] salarios = {1890.89f, 2578.65f, 4564.54f, 12587.54f};

        System.out.println("Percorrendo vetor de numeros primos:");
        for(int i = 0; i < primos.length; i++){
            System.out.println(primos[i]);
        }

        System.out.println("Percorrendo vetor de dias da semana:");
        for(int i = 0; i < diaSemana.length; i++){
            System.out.println(diaSemana[i]);
        }

        System.out.println("Percorrendo vetor de meses:");
        for(int i = 0; i < meses.length; i++){
            System.out.println(meses[i]);
        }

        System.out.println("Percorrendo vetor de salarios:");
        for(int i = 0; i < salarios.length; i++){
            System.out.println(salarios[i]);
        }
    }
}
