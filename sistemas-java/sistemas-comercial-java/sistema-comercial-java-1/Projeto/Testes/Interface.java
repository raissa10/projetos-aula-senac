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
import VisaoCadastros.CadFornecedores;
import VisaoCadastros.CadPessoa;
import VisaoCadastros.CadProduto;
import VisaoCadastros.CadVendedores;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

public class Interface extends javax.swing.JFrame implements ActionListener, ItemListener {

    private JMenuBar barraMenu; //barra de menu
    private JMenu arquivo, nivel, opcoes; //itens principais da barra de menu
    private JMenuItem sobre, sair, solucao, proximo; //subitens dos menus arquivo e op��es

    //Menu Testes Geo
    private JMenu cadastro, faturamento, financeiro, relatorio, config;
    private JMenuItem pessoa, produto, regiao, vendedor, cliente, fornecedor;

    private JRadioButtonMenuItem niveis[] = new JRadioButtonMenuItem[4]; //itens do menu n�veis
    private ButtonGroup radioGroup; //grupo dos botoes de escolha
    private JButton vetbotao[] = new JButton[26]; //vetor dos botoes do alfabeto
    private JTextField segredo[]; //vetor das letras que correspondem ao segredo
    private ImageIcon vetletras[] = new ImageIcon[26]; //vetor das figuras dos botoes do alfabeto
    private ImageIcon enforcado; //imagem do enforcado
    private ImageIcon jogoDaForca; //imagem "Jogo da Forca"
    private ImageIcon imgSegredo; //imagem do segredo
    private JPanel painelInicio; //painel que ser� mostrado na tela de abertura do jogo
    private JPanel painelLetras; //painel onde ficar�o os bot�es com as letras;
    private JPanel painelFigura; //painel onde vai ficar a figura do segredo
    private JPanel painelSegredo; //painel onde fica o vetor do segredo
    private JPanel painelCentral; //painel do vetor do segredo completo (com label)
    private JLabel titulo; //label que cont�m a imagem do titulo
    private JLabel msg1, msg2, msg3, msg4, msg5; // labels que cont�m as mensagens do painel inicial
    private JLabel labelEnforcado; //label que cont�m a figura do enforcado
    private JLabel labelSegredo; //label que cont�m a palavra "Descubra do segredo!"
    private JLabel atnivel; //label uqe vai mostar qual nivel est� sendo jogado
    private JLabel labelfigura; //label que cont�m a figura do segredo

//private Jogo jogo; //referente ao jogo em si
    private int nivelAtual; //�ltimo n�vel escolhido pelo jogador

    /**
     * M�todo construtor para inicializar e montar os pain�is Monta a tela de
     * entrada (at� que o usus�rio realize uma a��o)
     */
    public Interface() {
        super("Jogo da Forca");
        configuraJFrame();
        criaMenu();
        fazTitulo();
        fazPainelBotoes();
        this.painelLetras.setVisible(false);
        fazPainelEnforcado();
        fazPainelInicial();
        fazPainelCentral();
        fazPainelFigura("");
        this.painelCentral.setVisible(false);
        this.painelSegredo.setVisible(false);
        this.nivelAtual = 0; //inicializado para compara��o caso o menu das op��es seja ativado
    }

    //m�todo que configura o JFrame
    private void configuraJFrame() {
        setSize(1000, 700);
        setResizable(false);
        setLocation(10, 10);
        getContentPane().setLayout(null);
    }

    //m�todo que cria o menu
    private void criaMenu() {
        this.sobre = new JMenuItem("Sobre");
        this.sobre.addActionListener(this);
        this.sobre.setMnemonic('o');

        this.sair = new JMenuItem("Sair");
        this.sair.addActionListener(this);
        this.sair.setMnemonic('r');

        //itens do menu OP��ES
        this.solucao = new JMenuItem("Solução");
        this.solucao.addActionListener(this);
        this.solucao.setMnemonic('S');

        this.proximo = new JMenuItem("Próximo");
        this.proximo.addActionListener(this);
        this.proximo.setMnemonic('P');

        //itens do Menu N�VEis
        for (int i = 0; i <= 3; i++) {
            this.niveis[i] = new JRadioButtonMenuItem("Nível" + (i + 1));
            this.niveis[i].addItemListener(this);
            this.niveis[i].setFocusable(false);
        }

        //grupo dos botoes dos menu n�veis
        this.radioGroup = new ButtonGroup();
        this.radioGroup.add(this.niveis[0]);
        this.radioGroup.add(this.niveis[1]);
        this.radioGroup.add(this.niveis[2]);
        this.radioGroup.add(this.niveis[3]);

        //Item de SubMenu       
        this.cliente = new JMenuItem("Cliente");
        this.cliente.addActionListener(this);
        this.cliente.setMnemonic('C');

        this.vendedor = new JMenuItem("Vendedor");
        this.vendedor.addActionListener(this);
        this.vendedor.setMnemonic('V');

        this.produto = new JMenuItem("Produto");
        this.produto.addActionListener(this);
        this.produto.setMnemonic('P');

        //Itens da barra de Menu
        this.cadastro = new JMenu("Cadastro");
        this.cadastro.setMnemonic('C');
        this.cadastro.addActionListener(this);

        //Adicionando SubMenus ao Menu Cadastro
        this.cadastro.add(this.cliente);
        this.cadastro.add(this.vendedor);
        this.cadastro.add(this.produto);

        this.arquivo = new JMenu("Arquivo");
        this.arquivo.setMnemonic('A');
        this.arquivo.addActionListener(this);
        this.arquivo.add(this.sobre);
        this.arquivo.add(this.sair);

        this.nivel = new JMenu("Níveis");
        this.nivel.setMnemonic('N');
        this.nivel.addActionListener(this);
        for (int i = 0; i <= 3; i++) {
            this.nivel.add(this.niveis[i]);
        }

        this.opcoes = new JMenu("Opções");
        this.opcoes.setMnemonic('O');
        this.opcoes.addActionListener(this);
        this.opcoes.add(this.solucao);
        this.opcoes.add(this.proximo);

        //Barra de Menu
        this.barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);
        this.barraMenu.add(this.cadastro);
        this.barraMenu.add(this.arquivo);
        this.barraMenu.add(this.nivel);
        this.barraMenu.add(this.opcoes);
    }

    //m�todo que faz o t�tulo
    private void fazTitulo() {
        this.jogoDaForca = new ImageIcon("img\\jogodaforca.gif");
        this.titulo = new JLabel(jogoDaForca);
        this.titulo.setLocation(220, 0);
        this.titulo.setSize(563, 114);
        getContentPane().add(this.titulo);
    }

    //m�todo que faz o painel do bot�es
    private void fazPainelBotoes() {
        this.painelLetras = new JPanel();
        this.painelLetras.setSize(1000, 200);
        this.painelLetras.setLocation(0, 500);
        //this.painelLetras.setBackground(new Color (100,50,230));//teste
        this.painelLetras.setLayout(null);
        getContentPane().add(this.painelLetras);
        char letter = 'a';
        for (byte i = 0; i < 26; i++) {
            letter = (char) (i + 'a');
            this.vetletras[i] = new ImageIcon("img\\" + letter + ".gif");
            this.vetbotao[i] = new JButton(vetletras[i]);
            //this.vetbotao[i].setBackground(Color.WHITE); //teste
            this.vetbotao[i].setFocusable(false);
            this.vetbotao[i].setRolloverEnabled(false);
            this.vetbotao[i].setSize(60, 52);
            this.vetbotao[i].addActionListener(this);
            switch (i) {
                case 0:
                    this.vetbotao[0].setLocation(45, 10);
                    break;
                case 1:
                    this.vetbotao[1].setLocation(115, 10);
                    break;
                case 2:
                    this.vetbotao[2].setLocation(185, 10);
                    break;
                case 3:
                    this.vetbotao[3].setLocation(255, 10);
                    break;
                case 4:
                    this.vetbotao[4].setLocation(325, 10);
                    break;
                case 5:
                    this.vetbotao[5].setLocation(395, 10);
                    break;
                case 6:
                    this.vetbotao[6].setLocation(465, 10);
                    break;
                case 7:
                    this.vetbotao[7].setLocation(535, 10);
                    break;
                case 8:
                    this.vetbotao[8].setLocation(605, 10);
                    break;
                case 9:
                    this.vetbotao[9].setLocation(675, 10);
                    break;
                case 10:
                    this.vetbotao[10].setLocation(745, 10);
                    break;
                case 11:
                    this.vetbotao[11].setLocation(815, 10);
                    break;
                case 12:
                    this.vetbotao[12].setLocation(885, 10);
                    break;
                case 13:
                    this.vetbotao[13].setLocation(45, 70);
                    break;
                case 14:
                    this.vetbotao[14].setLocation(115, 70);
                    break;
                case 15:
                    this.vetbotao[15].setLocation(185, 70);
                    break;
                case 16:
                    this.vetbotao[16].setLocation(255, 70);
                    break;
                case 17:
                    this.vetbotao[17].setLocation(325, 70);
                    break;
                case 18:
                    this.vetbotao[18].setLocation(395, 70);
                    break;
                case 19:
                    this.vetbotao[19].setLocation(465, 70);
                    break;
                case 20:
                    this.vetbotao[20].setLocation(535, 70);
                    break;
                case 21:
                    this.vetbotao[21].setLocation(605, 70);
                    break;
                case 22:
                    this.vetbotao[22].setLocation(675, 70);
                    break;
                case 23:
                    this.vetbotao[23].setLocation(745, 70);
                    break;
                case 24:
                    this.vetbotao[24].setLocation(815, 70);
                    break;
                case 25:
                    this.vetbotao[25].setLocation(885, 70);
                    break;
            }
            this.painelLetras.add(this.vetbotao[i]);
        }
    }

    //m�todo que habilita os painel dos bot�es para que um nvo nivel se inicie
    private void habilitaBotoes() {
        for (int i = 0; i < 26; i++) {
            this.vetbotao[i].setEnabled(true);
        }
    }

    //m�todo que desabilita os bot�es caso a solu��o seja pedida pelo usu�rio
    private void desabilitaBotoes() {
        for (int i = 0; i < 26; i++) {
            this.vetbotao[i].setEnabled(false);
        }
    }

    //m�todo que faz o Painel Inicial
    private void fazPainelInicial() {
        this.msg1 = new JLabel("Olá!!!", JLabel.CENTER);
        this.msg1.setFont(new Font("Comic Sans", Font.BOLD, 40));
        this.msg2 = new JLabel("Bem-vindo(a)!", JLabel.CENTER);
        this.msg2.setFont(new Font("Comic Sans", Font.BOLD, 40));
        this.msg3 = new JLabel("Escolha no", JLabel.CENTER);
        this.msg3.setFont(new Font("Comic Sans", Font.BOLD, 40));
        this.msg4 = new JLabel("menu um nível", JLabel.CENTER);
        this.msg4.setFont(new Font("Comic Sans", Font.BOLD, 40));
        this.msg5 = new JLabel("para jogar!", JLabel.CENTER);
        this.msg5.setFont(new Font("Comic Sans", Font.BOLD, 40));
        this.painelInicio = new JPanel();
        this.painelInicio.setSize(700, 300);
        this.painelInicio.setLocation(280, 200);
        //this.painelInicio.setBackground (new Color (150,0,250));//teste
        this.painelInicio.setLayout(new GridLayout(5, 1));
        this.painelInicio.add(this.msg1);
        this.painelInicio.add(this.msg2);
        this.painelInicio.add(this.msg3);
        this.painelInicio.add(this.msg4);
        this.painelInicio.add(this.msg5);
        getContentPane().add(this.painelInicio);
    }

    //m�todo que faz o painel da figura do enforcado
    private void fazPainelEnforcado() {
        this.enforcado = new ImageIcon("img\\hmg5.gif");
        this.labelEnforcado = new JLabel(this.enforcado);
        this.labelEnforcado.setSize(206, 248);
        this.labelEnforcado.setLocation(10, 200);
        getContentPane().add(this.labelEnforcado);
    }

    //m�todo que atualiza a figura do enforcado
    //int num = numero de erros para mostrar a imagem correta
    private void atualizaEnforcado(int num) {
        ImageIcon image = new ImageIcon("img\\hmg" + num + ".gif");
        this.labelEnforcado.setIcon(image);
    }

    //m�todo que faz o painel das caixinhas das letras do segredo
    private void fazPainelSegredo() {
        int i;
        this.segredo = new JTextField[20];
        this.painelSegredo = new JPanel();
        this.painelSegredo.setSize(480, 50);
        this.painelSegredo.setLocation(260, 260); //teste
        this.painelSegredo.setLayout(new FlowLayout(1));
        for (i = 0; i < 20; i++) {
            this.segredo[i] = new JTextField(1);
            this.segredo[i].setFont(new Font("Comic Sans", Font.BOLD, 16));
            this.segredo[i].setEditable(false);
            this.segredo[i].setBackground(Color.WHITE);
            this.segredo[i].setVisible(false);
            this.painelSegredo.add(this.segredo[i]);
        }
        getContentPane().add(this.painelSegredo);
    }

    //m�todo que faz o painel Central completo
    private void fazPainelCentral() {
        this.painelCentral = new JPanel();
        this.painelCentral.setSize(480, 50);
        this.painelCentral.setLocation(260, 200);
        this.painelCentral.setLayout(new GridLayout());
        this.labelSegredo = new JLabel("Descubra o segredo:", JLabel.CENTER);
        this.labelSegredo.setFont(new Font("Comic Sans", Font.BOLD, 26));
        this.painelCentral.add(this.labelSegredo);
        getContentPane().add(this.painelCentral);
        fazPainelSegredo();
    }

    //m�todo que faz o painel onde vai ficar a imagem do segredo
    private void fazPainelFigura(String img) {
        this.painelFigura = new JPanel();
        this.painelFigura.setSize(230, 300);
        this.painelFigura.setLocation(750, 150);
        this.painelFigura.setLayout(new BorderLayout());
        this.atnivel = new JLabel("Nível: " + this.nivelAtual, JLabel.CENTER);
        this.atnivel.setFont(new Font("Comic Sans", Font.BOLD, 30));
        this.imgSegredo = new ImageIcon("img\\" + img);
        this.labelfigura = new JLabel(this.imgSegredo, JLabel.CENTER);
        this.painelFigura.add(BorderLayout.CENTER, this.labelfigura);
        this.painelFigura.add(BorderLayout.SOUTH, this.atnivel);
        getContentPane().add(this.painelFigura);
    }

    //m�todo que atualiza o painel da Figura
    private void atualizaPainelFigura(String img) {
        ImageIcon image = new ImageIcon("img\\" + img);
        this.labelfigura.setIcon(image);
        this.atnivel.setText("Nível " + this.nivelAtual);
    }

    //m�todo que faz um novo nivel
    private void novoNivel(String seg, String img) {
        this.painelInicio.setVisible(false);
        atualizaEnforcado(0);
        atualizaPainelSegredo(seg);
        this.painelSegredo.setVisible(true);
        atualizaPainelFigura(img);
        this.painelFigura.setVisible(true);
        habilitaBotoes();
        this.painelLetras.setVisible(true); //teste

    }

    //m�todo que atualiza o painel dos segredo para que as letras possam aparecer assim que o usu�rio digitar
    public void atualizaPainelSegredo(String palavra) {
        int tamanho = palavra.length();
        for (int i = 0; i < 20; i++) {
            if (i < tamanho) {
                this.segredo[i].setVisible(true);
                if (Character.isLetter(palavra.charAt(i))) {
                    this.segredo[i].setText("");
                } else if (palavra.charAt(i) == '-') {
                    this.segredo[i].setText("-");
                }
            } else {
                this.segredo[i].setVisible(false);
            }
        }
    }

    //m�todo que trata os eventos 
    @Override
    public void actionPerformed(ActionEvent e) {
        eventoMenu(e);
        eventoBotaoLetras(e);
    }

    //m�todo que trata dos eventos do menu
    public void eventoMenu(ActionEvent e) {
        if (e.getSource() == this.sobre) {
            ImageIcon icon = new ImageIcon("img//icone.gif");
            JOptionPane.showMessageDialog(null, "JOGO DA FORCA\nDesenvolvido por Gelvazio Camargo\nVersão 1.0\nJunho/2014", "Sobre", JOptionPane.INFORMATION_MESSAGE, icon);
        }
        if (e.getSource() == this.sair) {
            System.exit(0);
        }
        if (e.getSource() == this.solucao) {
            if (this.nivelAtual == 0) {
                JOptionPane.showMessageDialog(null, "Escolha um nível para jogar!", "", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "A SOLUÇÃO É: +this.jogo.getSegredo(),Resposta" + JOptionPane.INFORMATION_MESSAGE);
                desabilitaBotoes();
            }
        }
        if (e.getSource() == this.proximo) {
            if (this.nivelAtual == 0) {
                JOptionPane.showMessageDialog(null, "Escolha um nível para jogar!", "", JOptionPane.WARNING_MESSAGE);
            } else {
                //this.jogo.preparaJogo();
                //novoNivel (this.jogo.getSegredo(),this.jogo.getImagem());
            }
        }

        //Programação Gelvazio
        if (e.getSource() == this.cliente) {
            CadPessoa formulario = new CadPessoa();
            this.barraMenu.add(formulario);
//            formulario.setClosable(true);
//            formulario.setIconifiable(true);
//            formulario.setResizable(true);
//            formulario.setMaximizable(true);
            formulario.setVisible(true);
        }
        if (e.getSource() == this.fornecedor) {
            CadFornecedores formulario = new CadFornecedores();
            this.barraMenu.add(formulario);
            formulario.setClosable(true);
            formulario.setIconifiable(true);
            formulario.setResizable(true);
            formulario.setMaximizable(true);
            formulario.setVisible(true);
        }
        if (e.getSource() == this.vendedor) {
            CadVendedores formulario = new CadVendedores();
            this.barraMenu.add(formulario);
            formulario.setClosable(true);
            formulario.setIconifiable(true);
            formulario.setResizable(true);
            formulario.setMaximizable(true);
            formulario.setVisible(true);
        }
        if (e.getSource() == this.produto) {
            CadProduto formulario = new CadProduto();
            this.barraMenu.add(formulario);
//            formulario.setClosable(true);
            formulario.setIconifiable(true);
            formulario.setResizable(true);
            formulario.setMaximizable(true);
            formulario.setVisible(true);
        }
    }

    //m�todo que trata os eventos dos bot�es das letras
    public void eventoBotaoLetras(ActionEvent e) {
        int quantErro;
        char letra;
        String secret;
        /*
         for (int i = 0; i < 26; i++) {
         if (e.getSource() == this.vetbotao[i]){
         this.vetbotao[i].setEnabled(false);
         letra = (char) ('A' + i);
         quantErro = this.jogo.getErros();
         this.jogo.acertou(letra);
         if (quantErro != this.jogo.getErros()) {
         quantErro++;
         atualizaEnforcado(quantErro);
         }
         secret = this.jogo.getPalavra();
         for (int aux = 0; aux < secret.length(); aux++){
         if (secret.charAt(aux)!= ' '){
         this.segredo[aux].setText(""+secret.charAt(aux));
         }
         }
         if (this.jogo.jogoAcabou()) {
         if (quantErro < 5){
         JOptionPane.showMessageDialog (null,"PARAB�NS! \nVoc� adivinhou o segredo!","",JOptionPane.INFORMATION_MESSAGE);
         desabilitaBotoes ();
         }
         else {
         JOptionPane.showMessageDialog (null,"Tente de Novo! \nO Segredo era "+this.jogo.getSegredo(),"",JOptionPane.WARNING_MESSAGE);
         desabilitaBotoes ();
         }
         }
         break;
         }
         }
         */
    }

    //m�todo que trata dos eventos dos RadioButtons
    @Override
    public void itemStateChanged(ItemEvent e) {
        for (int i = 0; i < 4; i++) {
            if (e.getSource() == this.niveis[i]) {
                this.nivelAtual = i + 1;
                //this.jogo = new Jogo (this.nivelAtual);
                //novoNivel (this.jogo.getSegredo(),this.jogo.getImagem());
                break;
            }
        }
    }

    public static void main(String[] args) {

        Interface Calculadora = new Interface();
        Calculadora.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        Calculadora.setVisible(true);
    }
}
