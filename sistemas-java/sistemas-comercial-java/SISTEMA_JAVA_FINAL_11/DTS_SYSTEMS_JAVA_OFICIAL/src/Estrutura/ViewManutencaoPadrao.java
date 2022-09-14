/**
 * Este software tem reproducao proibida e protegida por Lei!
 * Sua copia esta proibida!!
 * @author Gelvazio Camargo
 */
package Estrutura;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class ViewManutencaoPadrao extends JInternalFrame {
        
    /**
     * Centraliza o formulario na tela
     */
    protected void centralizaFormulario() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        // variavel largura com a largura da tela
        int screenWidth = screenSize.width - this.getWidth();
        // variavel largura com a altura da tela
        int screenHeigth = screenSize.height - this.getHeight();
        //Divide o tamanho da tela para ficar no centro
        setLocation(screenWidth / 2, screenHeigth / 2);
    }

    public void PegaCodigoUsuarioLogado() {
        JOptionPane.showMessageDialog(null, "IMplementar o Metodo de pegar dados do Usuario Logado!!");
    }

    public void PegaCodigoFilialUsuarioLogado(int cd_usuariologado) {
        //JOptionPane.showMessageDialog(null, "IMplementar o Metodo de pegar dados da Filial do  Usuario Logado!!");
        cd_usuariologado = 1;
    }

    protected void adicionaConsulta(ViewConsultaPadrao form){        
        this.getDesktopPane().add(form);
        form.setVisible(true);        
    }
    
    /*
     public void validaCPF()
     {
     /*
     public static boolean validaCPF(String CPF) {
        
     if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") 
     || CPF.equals("44444444444") || CPF.equals("55555555555") || CPF.equals("66666666666") || 
     CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") || 
     (CPF.length() != 11)) 
     return(false); 
        
     char dig10, dig11; int sm, i, r, num, peso;
        
     }
                
    
    
     private static String calcDigVerif(String num) {  
     Integer primDig, segDig;  
     int soma = 0, peso = 10;  
     for (int i = 0; i < num.length(); i++)  
     soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;  
  
     if (soma % 11 == 0 | soma % 11 == 1)  
     primDig = new Integer(0);  
     else  
     primDig = new Integer(11 - (soma % 11));  
  
     soma = 0;  
     peso = 11;  
     for (int i = 0; i < num.length(); i++)  
     soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;  
          
     soma += primDig.intValue() * 2;  
     if (soma % 11 == 0 | soma % 11 == 1)  
     segDig = new Integer(0);  
     else  
     segDig = new Integer(11 - (soma % 11));  
  
     return primDig.toString() + segDig.toString();  
     }  
  
     private static int calcSegDig(String cpf, int primDig) {  
     int soma = 0, peso = 11;  
     for (int i = 0; i < cpf.length(); i++)  
     soma += Integer.parseInt(cpf.substring(i, i + 1)) * peso--;  
          
     soma += primDig * 2;  
     if (soma % 11 == 0 | soma % 11 == 1)  
     return 0;  
     else  
     return 11 - (soma % 11);  
     }  
  
     public static String geraCPF() {  
     String iniciais = "";  
     Integer numero;  
     for (int i = 0; i < 9; i++) {  
     numero = new Integer((int) (Math.random() * 10));  
     iniciais += numero.toString();  
     }  
     return iniciais + calcDigVerif(iniciais);  
     }  
  
     public static boolean validaCPF(String cpf) {  
     if (cpf.length() != 11)  
     return false;  
  
     String numDig = cpf.substring(0, 9);  
     return calcDigVerif(numDig).equals(cpf.substring(9, 11));  
     }
    
     public static String imprimeCPF(String CPF) { 
     return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-" + CPF.substring(9, 11)); 
     }
    
     }
    
     */
}

