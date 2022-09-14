/*
 * Copyright (C) 2014 Gelvazio Camargo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Testes;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Gelvazio Camargo
 */
public class Formulario_Padrao_Desenhado extends javax.swing.JFrame {

    public static class Botao extends JFrame {

        private final JButton ok, cancelar, excluir, adicionar, sair;

        public Botao() {
            super("Criando botões");
            setLayout(new FlowLayout());

            //Passa um nome para cada botão
            ok = new JButton("OK");
            cancelar = new JButton("Cancelar");
            excluir = new JButton("Excluir");
            adicionar = new JButton("Adicionar");
            sair = new JButton("Sair");

            //Coloca os tamanhos dos botoes
            ok.setSize(100, 100);
            cancelar.setSize(100, 100);
            excluir.setSize(100, 100);
            adicionar.setSize(100, 100);
            sair.setSize(100, 100);

            ok.setAlignmentY(RIGHT_ALIGNMENT);
            ok.setAlignmentX(LEFT_ALIGNMENT);
            //Adicion a ao Frame os botoes
            add(ok);
            add(cancelar);
            add(excluir);
            add(adicionar);
            add(sair);
        }
    }

    public static void main(String[] args) {
        Formulario_Padrao_Desenhado formulario = new Formulario_Padrao_Desenhado();
        //formulario
        formulario.setSize(800, 500);
        formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formulario.setResizable(true);
        formulario.setMaximumSize(null);
        formulario.setVisible(true);

        //Chamando botao
        Botao botao1 = new Botao();
        botao1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        botao1.setSize(800, 500);
        botao1.setVisible(true);
    }

}
