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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Gelvazio Camargo
 */
public class Menu extends JFrame {

    public JFrame frame;

    public Menu() {
        super("Aplicação JAVA");
        setSize(1000, 700);
        setLocation(180, 20);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);

        JMenu menuVenda = new JMenu("Venda");
        barra.add(menuVenda);

        JMenu menuCadastro = new JMenu("Cadastro");
        barra.add(menuCadastro);

        JMenu menuCompra = new JMenu("Compra");
        barra.add(menuCompra);

        JMenuItem menuItemNovaEntrada = new JMenuItem("Nova Entrada de Venda");
        menuVenda.add(menuItemNovaEntrada);
        menuItemNovaEntrada.addActionListener(new MyListener(menuItemNovaEntrada.getText()));

        JMenuItem menuItemRelatorio = new JMenuItem("Relatório de Venda");
        menuVenda.add(menuItemRelatorio);
        menuItemRelatorio.addActionListener(new MyListener(menuItemRelatorio.getText()));

        JMenuItem menuItemCadastroDeProdutos = new JMenuItem("Cadastro de Produtos");
        menuCadastro.add(menuItemCadastroDeProdutos);
        menuItemCadastroDeProdutos.addActionListener(new MyListener(menuItemCadastroDeProdutos.getText()));

        // JMenuItem menuItemCadastroDePessoa = new JMenuItem("Cadastro de Pessoas");
        //menuCadastro.add(CadPessoa1);
        // menuItemCadastroDePessoa.addActionListener(CadPessoa1);
        JMenuItem menuItemCadastroDeFornecedores = new JMenuItem("Cadastro de Fornecedores");
        menuCadastro.add(menuItemCadastroDeFornecedores);
        menuItemCadastroDeFornecedores.addActionListener(new MyListener(menuItemCadastroDeFornecedores.getText()));

        JMenuItem menuItemPedidoDeCompra = new JMenuItem("Pedido de Compra");
        menuCompra.add(menuItemPedidoDeCompra);
        menuItemPedidoDeCompra.addActionListener(new MyListener(menuItemPedidoDeCompra.getText()));

        JMenuItem menuItemRelatorioPedidoDeCompra = new JMenuItem("Relatório Pedido de Compra");
        menuCompra.add(menuItemRelatorioPedidoDeCompra);
        menuItemRelatorioPedidoDeCompra.addActionListener(new MyListener(menuItemRelatorioPedidoDeCompra.getText()));
    }

    public static void main(String[] args) {
        Menu teste = new Menu();
        teste.setVisible(true);
    }
}

class MyListener implements ActionListener {

    private String titulo;

    public MyListener(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new JFrame(titulo);
        frame.setSize(400, 400);
        frame.setLocation(300, 100);

        frame.setVisible(true);
    }

}
