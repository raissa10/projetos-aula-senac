package sistema;

import javax.swing.*;

public class principal {

    public static void main(String[] args) {
        InterfaceJogo Forca = new InterfaceJogo();
        Forca.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // centraliza a tela
        Forca.setLocationRelativeTo(null);
        Forca.setVisible(true);
    }
}
