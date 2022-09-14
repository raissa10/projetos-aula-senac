package Testes.LicencaSistema;

/**
 *
 * @author Gelvazio Camargo Entrada e saída binárias
 *
 * Livro de SW: subseção Reading and Writing Binary Data, sec.5.5, p.811-815.
 * Website do livro: resumo sec.5.5, slides. Veja também a página
 * http://algs4.cs.princeton.edu/code/, que tem o código fonte, a API, e dados
 * de teste de todos os programas do livro.
 *
 * Esta pequena aula não trata de uma estrutura de dados mas de ferramentas que
 * ajudam a ler e imprimir sequências de bits. Essas ferramentas são usadas na
 * compressão de dados.
 *
 * Resumo:
 *
 * arquivos/fluxos de bits métodos para leitura e gravação de bits as classes
 * BinaryStdIn e BinaryStdOut as classes BinaryDump, HexDump e PictureDump *
 * Cadeias de bits e fluxos de bits
 *
 * Em grande parte da Computação os dados são representados por caracteres.
 * Nesta página, colocamos os caracteres sob um microscópio e representamos os
 * dados pelos bits que compõem os caracteres. Ums cadeia de bits (ou bitstring)
 * é uma sequência de bits (da mesma forma que uma string é uma sequência de
 * caracteres).
 *
 * 1100010000011101010100111110110011100010100001100000000001111110000011101000111
 *
 * Um fluxo de bits (ou bitstream) é uma cadeia de bits na entrada ou na saída
 * de um programa. (Um fluxo é uma abstração do conceito de arquivo.) * Leitura
 * e gravação de bits
 *
 * A classe BinaryStdIn lê um fluxo de bits a partir da entrada padrão: public
 * class BinaryStdIn boolean readBoolean() lê 1 bit e devolve o valor booleano
 * correspondente char readChar() lê 8 bits e devolve o char correspondente char
 * readChar(int r) lê r (entre 1 and 16) bits e devolve o correspondente char
 * String readString() lê o fluxo em blocos de 8 de bits e devolve a string
 * correspondente int readInt() lê 32 bits e devolve o int correspondente int
 * readInt(int r) lê r (entre 1 and 32) bits e devolve o correspondente int
 * boolean isEmpty() este fluxo de bits está vazio? void close() fecha este
 * fluxo de bits
 *
 * Também readByte (8 bits), readShort (16 bits) e readLong (64 bits). A classe
 * BinaryStdOut escreve um fluxo de bits na saída padrão: public class
 * BinaryStdOut void write(boolean b) escreve o bit representado por b void
 * write(char c) escreve os 8 bits da representação de c void write(char c, int
 * r) escreve os r (entre 1 e 16) bits menos significativos de c void close()
 * fecha este fluxo de bits
 *
 * Métodos análogos a write() tratam de byte (8 bits), short (16 bits), int (32
 * bits) e long (64 bits). Veja a documentação completa das classes BinaryStdIn
 * e BinaryStdOut do livro. Veja também o código BinaryStdIn.java e
 * BinaryStdOut.java. Generalização: a classe BinaryIn permite ler um fluxo de
 * bits de um arquivo; a classe BinaryOut permite escrever um fluxo de bits em
 * um arquivo. * Veja o código BinaryIn.java e BinaryOut.java. * Um exemplo:
 * datas
 *
 * Representação de datas, como 12/31/1999 (em notação mês/dia/ano). Possíveis
 * representações: 10 chars (10×8 bits), 3 ints (3×32 bits), 2 chars e 1 short
 * (2×8 + 16 bits), 21 bits (4 + 5 + 12 bits). * (No último caso temos, na
 * verdade, 24 bits porque o número total de bits deve ser múltiplo de 8).
 *
 * Four ways to put a date onto standard output (p.813) Confira a primeira parte
 * da figura usando a tabela hexadecimal-para-ASCII:
 *
 * 0 1 2 3 4 5 6 7 8 9 A B C D E F
 *
 * 0 NUL SOH STX ETX EOT ENQ ACK BEL BS HT LF VT FF CR SO SI 1 DLE DC1 DC2 DC3
 * DC4 NAK SYN ETB CAN EM SUB ESC FS GS RS US 2 SP ! " # $ % & ' ( ) * + , - . /
 * 3 0 1 2 3 4 5 6 7 8 9 : ;   <   = > ?
 * 4 @ A B C D E F G H I J K L M N O
 * 5 P Q R S T U V W X Y Z [ \ ] ^ _
 * 6 ` a b c d e f g h i j k l m n o
 * 7 p q r s t u v w x y z { | } ~ DEL
 *
 * Binary dump
 *
 * Para examinar uma cadeia de bits use uma "descarga binária" (binary dump),
 * que converte bits nos caracteres 0 e 1 antes imprimir:
 *
 * public class BinaryDump { public static void main(String[] args) { int width
 * = Integer.parseInt(args[0]); int cnt; for (cnt = 0; !BinaryStdIn.isEmpty();
 * cnt++) { if (width == 0) { BinaryStdIn.readBoolean(); continue; } if (cnt !=
 * 0 && cnt % width == 0) StdOut.println(); if (BinaryStdIn.readBoolean())
 * StdOut.print("1"); else StdOut.print("0"); } if (width != 0)
 * StdOut.println(); StdOut.println(cnt + " bits"); } }
 *
 * O programa HexDump.java no website do livro divide a cadeia de bits em blocos
 * de 4 bits e imprime cada bloco como um par de dígitos hexadecimais. O
 * programa PictureDump.java no website do livro imprime uma cadeia de bits como
 * uma sequência de pequenos quadrados pretos e brancos dividida em linhas de
 * comprimento especificado.
 */
public class ExemploDatas {

}
