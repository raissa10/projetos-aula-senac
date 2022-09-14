package Testes.XML_NFE;

import javax.swing.JOptionPane;

public class Executar {

    public static void mensagemErro(String titulo, String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        /*
         Mensagem msg = new Mensagem();
         Criptografia ctf = new Criptografia();
         ListMensagem lmsg = new ListMensagem();
         ListCriptografia lctf = new ListCriptografia();
        
        
         lctf = ManipXML.lerXMLCriptografia();
         lmsg = ManipXML.lerXMLMensagem();
        
         msg.setTitulo("titulo");
         msg.setTexto("Ola, estou testando a criptografia");
         msg.setChave("3244");
         lmsg.incluirBuscandoCodigo(msg);
         ctf.setMensagem(msg);
         ctf.criptografarMensagem(msg);
         lctf.incluirBuscandoCodigo(ctf);

         ctf = new Criptografia();
         msg = new Mensagem();
        
         msg.setTitulo("titulo1");
         msg.setTexto("texto2");
         msg.setChave("3244");
         lmsg.incluirBuscandoCodigo(msg);

         ctf.setMensagem(msg);
         ctf.criptografarMensagem(msg);
         lctf.incluirBuscandoCodigo(ctf);
              
        
         System.out.println(ctf.getCriptografia());
         System.out.println(ctf.decriptografarMensagem(ctf));
         System.out.println(lmsg.listarMensagem());
         System.out.println(lctf.listarCriptografia());
         ManipXML.gravarXMLCriptografia(lctf.getListCriptografia());
        
         */

        mensagemErro("Titulo para a mensagem", "Atencao texto para mensagem");
    }

}

// <Criptografia codigo="2"><textoCriptografado>4r8v&gt;x|#5Mgw#zn</textoCriptografado><Mensagem codigo="2"><tituloDaCriptografia>titulo1</tituloDaCriptografia><texto>texto2</texto><chave>3244</chave></Mensagem></Criptografia><Criptografia codigo="4"><textoCriptografado>4r0$&gt;x|%:+gw^TA</textoCriptografado><Mensagem codigo="4"><tituloDaCriptografia>titulo1</tituloDaCriptografia><texto>texto2</texto><chave>3244</chave></Mensagem></Criptografia><Criptografia codigo="6"><textoCriptografado>4rVwkx|5{.gw"f/</textoCriptografado><Mensagem codigo="6"><tituloDaCriptografia>titulo1</tituloDaCriptografia><texto>texto2</texto><chave>3244</chave></Mensagem></Criptografia>
