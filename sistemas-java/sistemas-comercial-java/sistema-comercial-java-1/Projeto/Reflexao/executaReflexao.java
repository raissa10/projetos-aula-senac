
package Reflexao;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Gelvazio Camargo
 */
public class executaReflexao {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {

        Contato contato = new Contato("Gelvazio Camargo", 32, "gelvazio@gmail.com");
        contato.enviarEmail("Assunto de Testes", "Mensagem de testes");
        
        //Reflexao reflexao = new Reflexao();
        //Class<Contato> classe = new Class<?>;//new Contato("Gelvazio Camargo", 32, "gelvazio@gmail.com");
        //reflexao.infoObjeto(contato.enviarEmail("Assunto de Testes", "Mensagem de testes"));
        
    }
}
