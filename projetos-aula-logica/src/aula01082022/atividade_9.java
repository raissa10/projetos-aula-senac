package aula01082022;

public class atividade_9 {
    public static void main(String[] args) {
        int horas = 140;

        int minutos = horas % 100;
        System.out.println(minutos);
        horas = horas - minutos;
        if(horas == 100){
            horas = (horas - 40)/6;
            int segundos = (minutos % 6)*10;

            minutos = minutos/6;
            System.out.println("Tempo de estudo "+(horas+minutos)+" minutos e "+segundos);
        }
        else if(horas == 200){
            horas = (horas-80)/6;
            int segundos = (minutos % 6)*10;

            minutos = minutos/6;
            System.out.println("Tempo de estudo "+(horas+minutos)+" minutos e "+segundos);
        }


    }
}
