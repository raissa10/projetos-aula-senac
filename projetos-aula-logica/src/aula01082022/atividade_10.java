package aula01082022;

public class atividade_10 {
    public static void main(String[] args) {

        // tempo que pais um 1 será maior que pais 2
        int pais1 = 5000000;
        int mortalidadePais1 = (int) (pais1 * 0.04);
        //Mortes de pessoas no pais 1
        System.out.println(mortalidadePais1);

        int natalidadePais1 = (int) (pais1 * 0.011);
        //Nacimento de pessoas no pais 1
        System.out.println(natalidadePais1);

        int pais2 = 7000000;
        int mortalidadePais2 = (int) (pais2 * 0.02);
        //Mortes de pessoas no pais 1
        System.out.println(mortalidadePais2);

        int natalidadePais2 = (int) (pais2 * 0.008);
        //Nacimento de pessoas no pais 2
        System.out.println(natalidadePais2);

        int taxaNatalidade1 = 0;
        int taxaNatalidade2 = 0;

        for(int tempo=1; tempo<=100;tempo++){
            taxaNatalidade1 = (int)  pais1 - ((natalidadePais1 - mortalidadePais1)*tempo);
            taxaNatalidade2 = (int)  pais2 - ((natalidadePais2 - mortalidadePais2)*tempo);

            if(taxaNatalidade1 > taxaNatalidade2){
                System.out.println("deu certo: "+tempo);
                break;
            }

        }

        
        //while(taxaNatalidade1 > taxaNatalidade2){
        //    taxaNatalidade1+= (int)  pais1 - ((natalidadePais1 - mortalidadePais1)*tempo);
        //    taxaNatalidade2+= (int)  pais1 - ((natalidadePais1 - mortalidadePais1)*tempo);
        //}
        //  int taxaNatalidade1 = (int)  pais1 - ((natalidadePais1 - mortalidadePais1)*tempo);
        //  int taxaNatalidade2 = (int)  pais2 - ((natalidadePais2 - mortalidadePais2)*tempo);

        // System.out.println(taxaNatalidade1+" "+taxaNatalidade2);
        // if(taxaNatalidade1 > taxaNatalidade2){
        //     System.out.println("deu certo");
        // }

        // int tempo = 1;
        /*int tempo = 1;
        while(tempo <33){


            tempo++;

        }
        System.out.println(taxaNatalidade1+"/"+taxaNatalidade2);
        // uso switch

        int salario = 1500;
        switch (salario){
            case 1211:
               int taxa_inss = (int) 7.5;
                break;

        }*/
        /*
        int tempo = 1;
        while (){

                tempo++;
        }*/


    }
}
