
package Reflexao;

/**
 *
 * @author Gelvazio Camargo
 * site:https://javasimples.wordpress.com/2010/01/23/java-e-seus-espelhos-reflexo-computacional/
 */
public class Contato {

    private String nome;
    private int idade;
    private String email;

    public Contato() {
    }

    public Contato(String nome, int idade, String email) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
    }

    public void emString() {
        System.out.println(nome + " - " + idade + " - " + email);
    }

    public int idadeEmDias() {
        return idade * 365;
    }

    public void enviarEmail(String assunto, String mensagem) {
        System.out.println("Email");
        System.out.println("Para: " + email);
        System.out.println("Assunto: " + assunto);
        System.out.println("Mensagem: " + mensagem);
        System.out.print("Enviando:");
        System.out.println("concluido");
    }
}
