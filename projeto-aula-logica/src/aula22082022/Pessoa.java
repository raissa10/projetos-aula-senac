package aula22082022;

public class Pessoa {

    private static final float TAXA_IMPOSTO_INSS = 10;

    public static float calculaIR(int salario){
        float valorImpostoINSS = salario * (TAXA_IMPOSTO_INSS / 100);

        return salario - valorImpostoINSS;
    }

    public int getIdade(){
        return 20;
    }
}
