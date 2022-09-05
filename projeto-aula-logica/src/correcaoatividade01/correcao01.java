package correcaoatividade01;

import java.text.DecimalFormat;
import java.util.Scanner;

public class correcao01 {

    public static void main(String[] args) {

        testaPerfeitoExercicio35ok();

        // testaPerfeitoExercicio52();
        // testaPerfeitoExercicio65();
        // testaPerfeitoExercicio66();
        // testaPerfeitoExercicio61();
        // testaPerfeitoExercicio21();

        // testaPerfeitoExercicio27();
        // testaPerfeitoExercicio38(1000,30);
        // testaPerfeitoExercicio38(2000,20);


        //testaAlgoritmo(9);
        // algoritmo38Salario();
        // System.out.println("Exercicio Yasmim...\n\n\n");
        // exercicioYasmin66();
        // exercicio66grupo2();
    }

    static void testaPerfeitoExercicio35ok() {
        Scanner ler = new Scanner(System.in);

        // 14130 para 15 - Douglas

        double raio = 0, vol = 0, pi = 3.14;

        // Faça um algoritmo que receba do usuario o raio em cm e calcule o volume de
        // uma esfera.
        // Formula:
        // VOLUME = 4 * PI *  R ao cubo.

        System.out.println("Informe o raio em cm");
        raio = ler.nextDouble();

        vol = (4 * pi) * raio * raio * raio /3;

        double vol2 = (4 * pi)      * Math.pow(raio, 3);

        double vol3 = Math.pow(2, 3);

//        2 * 2 * 2

        System.out.println("O volume da esfera e: " + vol);

        System.out.println("O volume da esfera testado e: " + vol2);

        System.out.println("Teste elevado: " + vol3);

    }

    static void testaPerfeitoExercicio38(float salario, float horas_extras) {
        Scanner teclado = new Scanner(System.in);

        float valor_das_horasextras,valor_INSS,salario_liquido;

//        salario = 1000;
//        horas_extras = 30;

        valor_das_horasextras = (salario/176f)*horas_extras*1.5f;
        valor_INSS = (salario+valor_das_horasextras)*0.1f;

        if (valor_INSS>150){
            valor_INSS=150;
        }

        salario_liquido= (salario + valor_das_horasextras) - valor_INSS;

        System.out.println("Valor das horas extras:");
        System.out.format("%.2f",valor_das_horasextras);
        System.out.println(" ");


        System.out.println("Valor do INSS:");
        System.out.format("%.2f",valor_INSS);
        System.out.println(" ");

        System.out.println("Salario liquido:");
        System.out.format("%.2f",salario_liquido);
    }

    static void testaPerfeitoExercicio35() {
        Scanner ler = new Scanner(System.in);

        double raio = 0, vol = 0, pi = 3.14;

        // Faça um algoritmo que receba do usuario o raio em cm e calcule o volume de
        // uma esfera.
        // Formula:
        // VOLUME = 4 * PI * R ao cubo.

        System.out.println("Informe o raio em cm");
        raio = 15;//ler.nextDouble();

        vol = (4 * pi) * raio * raio * raio / 3;

        double vol2 = ((4 * pi) * Math.pow(raio, 3)) / 3;

        System.out.println("O volume da esfera e: " + vol);
        System.out.println("O volume 2 da esfera e: " + vol);

    }

    static void testaPerfeitoExercicio27() {
        //Um estudante muito metódico estava matriculado em 6 disciplinas,
        // e dispunha de 1 hora e 40 minutos para estudar.
        // Sua intenção era dividir o tempo disponível igualmente para as 6 disciplinas,
        // e descansar livremente o tempo restante.
        // Faça um programa que calcule o tempo que ele deve dedicar para cada disciplina e o tempo livre.

        double tempoEstudo, tempoLivre;
        double tempo;
        double disciplinas = 6;
        double tempo_Estudo = 100;

        int newtempo = (int) (tempo_Estudo / disciplinas);

        int tempoTotal = newtempo * 6;

        int tempoDisponivel = 100 - tempoTotal;

        tempoLivre = (tempo_Estudo % disciplinas);

        System.out.println("O Tempo empregado por disciplina será de " + tempoDisponivel + " Minutos!");
        System.out.println("O aluno vai ter " + tempoLivre + " minutos de tempo livre");
    }

    static void testaPerfeitoExercicio21() {
        // CERTO E 360 KM AO FINAL DE 1 ANO
        //      DIA     SEMANA    TOTSEMANA ANO
        // KM = (1600 *  5 *      45) / 1000
        int dist1 = 800, dist2 = 0, dia = 5, semana = 45, km = 0;

        // Vou e volto diariamente a pé para o trabalho, que dista aproximadamente 800 m
        // de minha casa.
        // Supondo que trabalho 5 dias por semana, 45 semanas por ano,
        // "bole" a operação matemática que deve ser efetuada para calcular quantos
        // quilômetros,
        // aproximadamente, terei andado ao final de um ano.
        // Elabore um programa que faça as contas e mostre o resultado

        dist1 *= dia;
        dist2 = dist1 * semana;
        km = dist2 / 1000;

        System.out.println("A distancia semanal e 800m * (5) semanal: " + dist1 + "m");
        System.out.println("A distancia anual e 4000m * (45) anual: " + dist2 + "m");
        System.out.println("A distancia anual e anual / 1000: " + km + "km");

    }

    static void testaPerfeitoExercicio61() {
        String nome = "";
        int cont;
        int Pnomejoao = 0;

        for (cont = 0; cont < 10; cont++) {
            System.out.println("escreva seu nome: " + cont);
//            nome = teclado.next();
//
//            if (nome.equals("João"))
//                Pnomejoao++;
        }
    }

    static void testaPerfeitoExercicio66() {
        Scanner teclado = new Scanner(System.in);
        DecimalFormat df=new DecimalFormat("0");

        double limite = 500;

        double pares = 0;
        double impares = 0;
        double divisores3 = 0;
        double somapares = 0;
        double fatorialImpares = 0;
        double perfeitos = 0;
        double imperfeitos = 0;

        for (int i = 1; i <= limite; i++) {

//        pares
            if (i % 2 == 0) {
                pares++;

//            impares
            } else {
                impares++;
            }
//            divisiveis 3
            if (i % 3 == 0) {
                divisores3++;
            }
//          soma dos numeros pares
            if (i % 2 == 0) {
                somapares+=i;
            }
//        quantidade de numero perfeitos
            double n = 0;
            double calculo = 0;

            for (n = 1; n < i; n++) {
                if (i % n == 0) {
                    calculo = calculo + n;
                }
            }
            if (calculo == i) {
                perfeitos++;
            } else {
                imperfeitos++;
            }
        }
        //            fatorial dos numeros impares
        double contador = 1;
        while (contador <= 500){
            double numero = contador;

            boolean impar = true;
            // verifica se o numero e impar para calcular o fatorial
            if(numero % 2 == 0){
                impar = false;
            }

            // quando nao for impar, prossegue para op proximo numero
            if(!impar){
                contador++;
                continue;
            }

            double y = 1;
            for (double x = 1; x <= numero; x++) {
                y *= x;
            }
            System.out.println("Fatorial do numero impar " + numero + " e: " + y);

            contador++;


        }
        System.out.println("o resultado é:");
        System.out.println(df.format(pares));
        System.out.println(df.format(impares));
        System.out.println(df.format(divisores3));
        System.out.println(df.format(somapares));
        System.out.println(df.format(perfeitos));
        System.out.println(df.format(imperfeitos));
    }

    static void testaPerfeitoExercicio65() {
        Scanner teclado = new Scanner(System.in);
        int numero, razao;
        System.out.println("Numero: ");

        numero = 1;//teclado.nextInt();

        System.out.println("Razao: ");
        razao = 2;//teclado.nextInt();

        System.out.println("\nnumero 1: "+numero);

        for (int cont = 1; cont <= 9; cont++) {
            numero+=razao;
            System.out.println("numero "+cont+": "+numero);
        }

    }

    static void testaAlgoritmo(int numeroExercicio) {
        switch (numeroExercicio) {
            case 9:
                testaAlgoritmos(1, 2, 3);
                testaAlgoritmos(3,2,1);
                testaAlgoritmos(2,1,3);
                break;
        }
    }

    static void testaPerfeitoExercicio52() {
        for(int x=1; x<=5000; x++){
            int soma = 0;
            for(int i=1; i<x; i++){
                if((x% i)==0){
                    soma+=i;
                }
            }

            if(soma == x){
                System.out.println("Perfeito" + soma);
            }
        }
    }

    static void algoritmo38Salario() {
        Scanner entrada = new Scanner (System.in);

        //Um salário tem os seguintes componentes: - valor nominal - adicional devido a horas extras - valor descontado
        //para o INSS (10% do valor a receber, limitado a 150 reais). O valor adicional devido às horas extras é
        //calculado dividindo-se o valor nominal
        //por 176 (22 dias de 8 horas), multiplicando-se pela quantidade de horas e ainda com um acréscimo de 50%.
        //Escrever um programa que lê os valores necessários, calcula e mostra na tela os componentes do salário e
        //o salário líquido
        //resultante para o empregado.
        //Não é preciso prever arredondamentos, mas os valores devem ser mostrados na tela com duas casas decimais.
        //Exemplos: para um salário de R$ 1.000,00, com 30 horas extras, teremos R$ 255,68 de horas extras
        //[(1.000/176)*30*1,5], R$ 125,57 de INSS e um salário líquido de R$ 1.130,11.
        //Para um salário de R$ 2.000,00 e 20 horas extras, seriam R$ 340,91 de horas extras,
        //R$ 150,00 de INSS (e não os 10%), com um salário líquido de R$ 2.190,91.



        System.out.println("Informe o salário:");
        float salarioNominal = 1000f;//entrada.nextFloat();

        System.out.println("Informe o tanto de horas extras:");
        int horasExtras = 30;//entrada.nextInt();

        float calculoExtras = (salarioNominal / 176) * horasExtras;
        float salarioExtras = (float) (calculoExtras + (calculoExtras * 0.5));
        float salarioMaior = salarioNominal + salarioExtras;
        float calculoINSS =  (float) (salarioMaior * 0.10);
        double salarioFinal = 0;

        System.out.println("O valor que você recebeu de horas extras é:" + salarioExtras);

        if (calculoINSS > 150) {
            calculoINSS = 150;

            salarioFinal = (float) (salarioMaior - calculoINSS);
            System.out.println("Seu INSS deu: " + calculoINSS);

            System.out.println("Seu salário final com o desconto do INSS e o acréscimo das horas extras é: " + salarioFinal);

        } else {
            salarioFinal = (double) (salarioMaior - calculoINSS);
            System.out.println("Seu INSS deu: " + calculoINSS);

            System.out.println("Seu salário final com o desconto do INSS e o acréscimo das horas extras é: %.2f"  + salarioFinal);
        }

    }

    static void testaAlgoritmos(int num1, int num2, int num3) {
        Scanner teclado = new Scanner(System.in);

        int exercicio = 9;
        if (exercicio == 9) {
            System.out.println("Testando.....");
            System.out.println("Testando.....");
            System.out.println("Testando.....");

            int A = num1, B = num2, C = num3;



            if (A > B) {
                if (A > C) {
                    if (B > C) {
                        System.out.println("O menor termo digitado foi: " + C + " C");
                    }else{
                        System.out.println("O menor termo digitado foi: " + B + " B");
                    }
                }
            }

            if (A < B) {
                if (B > C) {
                    if (A > C) {
                        System.out.println("O menor termo digitado foi: " + C + " C");
                    }else{
                        System.out.println("O menor termo digitado foi: " + A + " A");
                    }
                }
            }

            if (A < B) {
                if (A < C) {
                    System.out.println("O menor termo digitado foi: " + A + " A");
                }
            }

            if (A > B) {
                if (A < C) {
                    System.out.println("O menor termo digitado foi: " + B + "B");
                }
            }

        }
    }

    static void exercicio66grupo2() {
        Scanner teclado = new Scanner(System.in);
        //Escreva um algoritmo que leia os numeros de 1 ate 500.
        //Neste intervalo armazene os seguintes dados sobre os numeros:
        int numero = 5;
        //quantidade de numeros pares
        int par = 0;
        int impar = 0;
        int dividido = 0;
        int parsoma = 0;
        int soma = 0;

        int fatorial = 0;
        int totalperfeito = 0;
        int imperfeito = 0;
        for (int i = 0; i < 5; i++) {
            fatorial = 1;
            totalperfeito = 0;
            imperfeito = 0;
            int J = 0;

            if (i % 2 == 0) {
                par = par + 1;
            }
            //quantidade de numeros impares
            else {
                impar = impar + 1;
                //quantidade de numeros divisiveis por 3
            }
            if (i % 3 == 0) {
                dividido = dividido + 1;
            }
            //soma dos numeros pares
            if (i % 2 == 0) {
                parsoma = parsoma + parsoma;
            }
            //fatorial dos numeros impares
            for (int j = i; j > 5; j--) {
                fatorial = fatorial * j;
            }
            for (int num = 1; i < numero; i++) {
                if (num % i == 0) {
                    soma = soma + i;
                }
            }
            if (soma == numero) {
                System.out.println("Ele e perfeito " + numero);
                totalperfeito++;
            } else {
                imperfeito++;
            }
        }
        System.out.println("Numeros pares = " + par);
        System.out.println("Numeros impares = " + impar);
        System.out.println("Numeros divisiveis = " + dividido);
        System.out.println("Numeros soma dos pares = " + parsoma);
        System.out.println("Numeros fatoriais impar = " + fatorial);
        System.out.println("Numeros perfeitos = " + totalperfeito);
        System.out.println("Numeros imperfeitos = " + imperfeito);
    }

    static void exercicio66grupo3() {

    }

    static void exercicio66grupo4() {

    }

    static void exercicio66grupo5() {

    }

    static void exercicio66grupo6() {

    }

    static void exercicio66grupo7() {

    }

    static void exercicioYasmin66() {

        // Escreva um algoritmo que leia os numeros de 1 ate 500.
        //Neste intervalo armazene os seguintes dados sobre os numeros:
        //1. quantidade de numeros pares
        //2. quantidade de numeros impares
        //3. quantidade de numeros divisiveis por 3
        //4. soma dos numeros pares
        //5. fatorial dos numeros impares
        //6. quantidade de numeros perfeitos
        //7. quantidade de numeros imperfeitos

        // variáveis

        // controles:
        int pares = 0, impares = 0, divtres = 0, pft = 0, impft = 0;

        // soma
        int somapar = 0;
        int somapft;

        // fatorial
        int fatimpar = 1;

        // controlefor
        int controle;
        int controlador = 5000;

        for (controle = 1; controle <= controlador; controle++) {

            fatimpar = 1;

            if (controle % 2 == 0) {
                pares++;
                somapar = somapar + controle;
            } else {
                impares++;

                int n = controle;

                for (int i = n; i >= 1; i--) {
                    fatimpar = fatimpar * i;
                }

                if(controle<=65){
                    //System.out.println("O fatorial de " + controle + " e " + fatimpar);
                }

            }
            if (controle % 3 == 0) {
                divtres++;
            }
            somapft = 0;
            for (int x = 1; x < controle; x++) {
                if (controle % x == 0) {
                    somapft = somapft + x;
                }
            }
            if (somapft == controle) {
                pft++;
            } else {
                impft++;
            }

        }

        //    //1. quantidade de numeros pares
        //        //2. quantidade de numeros impares
        //        //3. quantidade de numeros divisiveis por 3
        //        //4. soma dos numeros pares
        //        //5. fatorial dos numeros impares
        //        //6. quantidade de numeros perfeitos
        //        //7. quantidade de numeros imperfeitos

        System.out.println("A quantidade de numeros pares e:" + pares);
        System.out.println("A quantidade de numeros impares e:" + impares);
        System.out.println("A quantidade de numeros divisiveis por 3:" + divtres);
        System.out.println("A soma dos numeros pares e:" + somapar);
        System.out.println("A quantidade de numeros perfeitos e:" + pft);
        System.out.println("A quantidade de numeros imperfeitos e:" + impft);


        // resposta exercicio yasmim
//        O fatorial de 1 e 1
//        O fatorial de 3 e 6
//        O fatorial de 5 e 120
//        O fatorial de 7 e 5040
//        O fatorial de 9 e 362880
//        A quantidade de numeros pares e:5
//        A quantidade de numeros impares e:5
//        A quantidade de numeros divisiveis por 3:3
//        A soma dos numeros pares e:30
//        A quantidade de numeros perfeitos e:1
//        A quantidade de numeros imperfeitos e:9


    }
}
