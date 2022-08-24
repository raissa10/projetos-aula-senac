package aula01082022;

public class atividade_2 {
    public static void main(String[] args) {
        int leitura = 5;
        int diasSemana = 6;

        int minutosLeitura = ((leitura * diasSemana)*4)*12;

        if(minutosLeitura > 60){
            int horasLeitura = minutosLeitura/60;
            System.out.println(horasLeitura+" Horas de leitura");
        }
        else{
            System.out.println();
        }


    }
}
