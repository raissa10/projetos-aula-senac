package aula01082022;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class atividade_6 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LocalDate dataAtual = LocalDate.now();
        //System.out.println(dataAtual);

        int ano = dataAtual.getYear();
        //System.out.println(ano);
        //
        System.out.println("Informe seu ano de nacimento");
        int anoNacimento = input.nextInt();

        int idade = ano - anoNacimento;

        if(idade > 18){
            System.out.println("Maior de idade");
        }
        else{
            System.out.println("Menor de idade");
        }


    }
}
