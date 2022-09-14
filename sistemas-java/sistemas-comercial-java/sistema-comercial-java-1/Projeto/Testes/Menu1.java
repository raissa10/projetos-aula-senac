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

/**
 *
 * @author Gelvazio Camargo
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")

public class Menu1 extends JFrame {

    public JFrame frame;

    public Menu1() {

        //Configurações do Frame         
        super("Aplicação JAVA");
        setSize(700, 700);
        setLocation(180, 20);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);

        //Referênte aos Menus  
        JMenu menuVenda = new JMenu("Venda");
        barra.add(menuVenda);

        JMenu menuCadastro = new JMenu("Cadastro");
        barra.add(menuCadastro);

        JMenu menuCompra = new JMenu("Compra");
        barra.add(menuCompra);

        //MENU VENDA         
        //Referênte a NOVA ENTRADA do menu VENDA  
        JMenuItem menuItemNovaEntrada = new JMenuItem("Nova Entrada de Venda");
        menuVenda.add(menuItemNovaEntrada);

        //Chama NOVA ENTRADA DE VENDA  
        menuItemNovaEntrada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Nova Entrada de Venda");//Frame e abaixo suas configurações     
                frame.setSize(400, 400);
                frame.setLocation(300, 100);

                frame.setVisible(true);
            }
        });

        //Referênte ao RELATÓRIO do Menu1 VENDA     
        JMenuItem menuItemRelatorio = new JMenuItem("Relatório de Venda");
        menuVenda.add(menuItemRelatorio);

        //Chama RELATÓRIO DE VENDA  
        menuItemRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Relatório de Venda");//Frame e abaixo suas configurações     
                frame.setSize(400, 400);
                frame.setLocation(300, 100);

                frame.setVisible(true);
            }
        });

        //MENU CADASTRO  
        //Referênte ao Cadastro de Produtos  
        JMenuItem menuItemCadastroDeProdutos = new JMenuItem("Cadastro de Produtos");
        menuCadastro.add(menuItemCadastroDeProdutos);

        //Chama Cadastro de Produtos  
        menuItemCadastroDeProdutos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Cadastro de Produtos");//Frame e abaixo suas configurações     
                frame.setSize(400, 400);
                frame.setLocation(300, 100);
                frame.setVisible(true);
            }
        });

        //Referênte ao Cadastro de Fornecedores   
        JMenuItem menuItemCadastroDeFornecedores = new JMenuItem("Cadastro de Fornecedores");
        menuCadastro.add(menuItemCadastroDeFornecedores);

        //Chama Cadastro de Fornecedores  
        menuItemCadastroDeFornecedores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Cadastro de Pessoas");//Frame e abaixo suas configurações     
                frame.setSize(400, 400);
                frame.setLocation(300, 100);

                frame.setVisible(true);

            }
        });

        //MENU COMPRA        
        //Refêrente ao Pedido de Compra do MENU Compra  
        JMenuItem menuItemPedidoDeCompra = new JMenuItem("Pedido de Compra");
        menuCompra.add(menuItemPedidoDeCompra);

        //Chama Pedido de Compra  
        menuItemPedidoDeCompra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Pedido de Compra");//Frame e abaixo suas configurações    
                frame.setSize(400, 400);
                frame.setLocation(300, 100);

                frame.setVisible(true);
            }
        });
        //Referênte ao Relatorio de Compra do Menu1 Compra        
        JMenuItem menuItemRelatorioPedidoDeCompra = new JMenuItem("Relatório Pedido de Compra");
        menuCompra.add(menuItemRelatorioPedidoDeCompra);

        //Chama Relatório do Pedido de Compra do Menu1 Compra  
        menuItemRelatorioPedidoDeCompra.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame = new JFrame("Relatório de Pedido de Compra");//Frame e abaixo suas configurações    
                frame.setSize(400, 400);
                frame.setLocation(300, 100);

                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Menu1 teste = new Menu1();
        teste.
                setVisible(true);
    }
}
