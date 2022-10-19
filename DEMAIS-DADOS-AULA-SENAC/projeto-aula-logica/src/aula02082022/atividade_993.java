package aula02082022;

import java.util.Scanner;

public class atividade_993 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Quer cadastrar quantos carros?");
        int quantidade = input.nextInt();

        int carros[] = new int[quantidade];

        int ano[] = new int[quantidade];
        String cor[] = new String[quantidade];
        String placa[] = new String[quantidade];

        int fiat = 0;
        int wolskwagen = 0;
        int gm = 0;
        int toyota = 0;
        int honda =0;
        int ford =0;
        //

        int verde = 0;
        int menor1990 = 0;

        for(int quant = 0; quant < carros.length;quant++){
            System.out.println("Informe o modelo do carro: ");
            String modelo = input.next();

            if(modelo.equalsIgnoreCase("Fiat")){
                System.out.println("Informe ano de fabricação");
                ano[quant] = input.nextInt();

                if(ano[quant] < 1990){
                    menor1990++;
                }

                System.out.println("Informe a cor do carro");
                cor[quant] = input.next();

                if(cor[quant].equalsIgnoreCase("verde")){
                    verde++;
                }

                System.out.println("Informe a placa do carro");
                placa[quant] = input.next();
                fiat++;
            }
            //
            else if(modelo.equalsIgnoreCase("wolskwagen")){
                System.out.println("Informe ano de fabricação");
                ano[quant] = input.nextInt();
                if(ano[quant] < 1990){
                    menor1990++;
                }

                System.out.println("Informe a cor do carro");
                cor[quant] = input.next();
                if(cor[quant].equalsIgnoreCase("verde")){
                    verde++;
                }
                System.out.println("Informe a placa do carro");
                placa[quant] = input.next();
                wolskwagen++;
            }
            //
            else if(modelo.equalsIgnoreCase("gm")){
                System.out.println("Informe ano de fabricação");
                ano[quant] = input.nextInt();

                if(ano[quant] < 1990){
                    menor1990++;
                }

                System.out.println("Informe a cor do carro");
                cor[quant] = input.next();
                if(cor[quant].equalsIgnoreCase("verde")){
                    verde++;
                }
                System.out.println("Informe a placa do carro");
                placa[quant] = input.next();
                gm++;
            }
            //
            else if(modelo.equalsIgnoreCase("toyota")){
                System.out.println("Informe ano de fabricação");
                ano[quant] = input.nextInt();
                if(ano[quant] < 1990){
                    menor1990++;
                }
                System.out.println("Informe a cor do carro");
                cor[quant] = input.next();
                if(cor[quant].equalsIgnoreCase("verde")){
                    verde++;
                }
                System.out.println("Informe a placa do carro");
                placa[quant] = input.next();
                toyota++;
            }
            //
            else if(modelo.equalsIgnoreCase("honda")){
                System.out.println("Informe ano de fabricação");
                ano[quant] = input.nextInt();

                if(ano[quant] < 1990){
                    menor1990++;
                }

                System.out.println("Informe a cor do carro");
                cor[quant] = input.next();
                if(cor[quant].equalsIgnoreCase("verde")){
                    verde++;
                }
                System.out.println("Informe a placa do carro");
                placa[quant] = input.next();
                honda++;
            }
            //
            else if(modelo.equalsIgnoreCase("ford")){
                System.out.println("Informe ano de fabricação");
                ano[quant] = input.nextInt();
                if(ano[quant] < 1990){
                    menor1990++;
                }
                System.out.println("Informe a cor do carro");
                cor[quant] = input.next();
                if(cor[quant].equalsIgnoreCase("verde")){
                    verde++;
                }
                System.out.println("Informe a placa do carro");
                placa[quant] = input.next();
                ford++;
            }
            //
            else{
                System.out.println("Não temos esse modelo");
            }
            //
        }


    }
}
