package aula02082022;

import java.util.Scanner;

public class atividade_29999 {
    public static void main(String[] args) {
        /*
        Dado o modelo(Fiat, Ford,GM, Wolskwagen,Toyota,Honda), ano fabricação, cor e placa de 5 carros.
         Faca um algoritmo que:
         a)Imprima quantos carros sao da cor verde e o percentual em relacao ao total.
         b)Imprima quantos foram fabricados antes de 1990 percentual em relacao ao total;
         c)Imprima quantos sao da Fiat e o percentual em relacao ao total;
        */

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

        double porcetagemCor = (verde*100)/quantidade;
        System.out.println( verde+" carros verdes vendidos \n"+
                porcetagemCor+"% de carros verdes vendidos"
        );

        double porcetagemMenor1990 = (menor1990*100)/quantidade ;
        System.out.println(menor1990+" carros fabricados antes de 1990 \n"+
                porcetagemMenor1990+"% vendido pela concessionária"
                );

        int porcetagemFiat = (fiat*100)/quantidade;
        System.out.println(fiat+" São da fiat \n"+
                porcetagemFiat+"% deste modelo vendido pela concessionária");

    }
}
