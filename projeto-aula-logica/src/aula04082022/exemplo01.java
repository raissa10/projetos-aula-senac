package aula04082022;

public class exemplo01 {

    public static void main(String[] args) {
        int cont = 1;
//      passa na condicao apenas quando atender ao criterio
        while(cont <= 10){
            //System.out.println("Contador 1: " + cont);
            System.out.println("Minha frase na posicao:" + cont);
            cont++;
        }
        // exemplo 02
        cont = 7;
        // pelo menos uma vez ele passa na condicao
        do {
            System.out.println("Contador 2:" + cont);
            cont++;
        } while (cont <= 5);

        // exemplo for
        for (int i = 0; i <= 10; i++){
            System.out.println("Contador FOR:" + i);
        }
    }

    // comandos git
    // adicionar arquivos
    // git add *
    // git commit -m "mensagem"
    // git push



}
