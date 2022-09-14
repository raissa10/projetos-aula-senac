package ViewConsultasCadastro;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JInternalFrame;

/**
 *
 * @author gelvazio
 */

public class ViewConsultaPadrao extends JInternalFrame {
    
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
}
