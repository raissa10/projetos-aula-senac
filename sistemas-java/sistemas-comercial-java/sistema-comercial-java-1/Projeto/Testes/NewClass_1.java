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
package Principal;

/**
 *
 * @author Gelvazio Camargo
 */
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class NewClass_1 {

    final static String LOCALHOST = "src/Principal/ArquivoIniConexao/Conexao.ini";

    public static void main(String[] args) throws IOException {

        java.awt.Desktop.getDesktop().open(new File(LOCALHOST));

        Scanner entrada = new Scanner(System.in);

        System.out.print("Entre com um endereço válido de um arquivo: ");
        String caminho = entrada.nextLine();

        metodos(caminho);
    }

    public static void metodos(String caminho) {
        File arquivo = new File(caminho);

        if (arquivo.exists()) {
            System.out.println("O caminho especificado existe !\nVamos aos testes:\n");

            if (arquivo.isAbsolute()) {
                System.out.println("É um caminho absoluto");
            } else {
                System.out.println("Não é um caminho absoluto");
            }

            if (arquivo.isFile()) {
                System.out.printf("É um arquivo de tamanho %s bytes\n"
                        + "Útima vez modificado %s\n"
                        + "Cujo caminho é %s\n"
                        + "De caminho absoluto %s\n"
                        + "E está no diretório pai %s\n",
                        arquivo.length(), arquivo.lastModified(), arquivo.getPath(), arquivo.getAbsolutePath(), arquivo.getParent());
            } else {
                System.out.println("É um diretório cujo conteúdo tem os seguintes arquivos: ");
                String[] arquivos = arquivo.list();

                for (String file : arquivos) {
                    System.out.println(file);
                }
            }

        } else {
            System.out.println("Endereço errado");
        }
    }
}
