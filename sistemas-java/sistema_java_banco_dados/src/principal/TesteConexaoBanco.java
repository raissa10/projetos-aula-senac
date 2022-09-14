
package principal;

import principal.Conexao;

/**
 *
 * @author Gelvazio Camargo
 */
public class TesteConexaoBanco {


    public static void main(String[] args) {
        String dados = testBancoPostgres();

        System.out.println("Dados...");

        System.out.println(dados);
    }

    public static String testBancoPostgres() {

        String dados = Conexao.conectaBanco();

        // retorna os usuarios para nao retornar em branco...
        return "[" + dados + "]";
    }

}
