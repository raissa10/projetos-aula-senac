
package Estrutura;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class Log {
        //
      public void gravarLog(String Erro) {
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	Date date = new Date();        
	String dataAtual = dateFormat.format(date); 
        boolean gravaLog = true;        
        if (gravaLog) {
            //Instancia a classe que le e grava arquivos do Java
            BufferedWriter arquivo;
            Erro = "Erro ocorrido!" + dataAtual + " \n" + Erro;
            String valores = Erro;                    
            try {
                String nomeArquivo = "Log.txt";
                // abre o arquivo de texto para acrescentar linhas
                arquivo = new BufferedWriter(new FileWriter(nomeArquivo, true));
                // escreve a linha de texto
                arquivo.write(valores);
                // insere uma quebra de linha
                arquivo.newLine();
                // força a gravação dos dados em disco
                arquivo.flush();
                arquivo.close();
                //JOptionPane.showMessageDialog(null, "Log gravado com sucesso!");
                //valores = "";
            } catch (IOException erro) {
                JOptionPane.showMessageDialog(null, "Erro ao gravar o Log!" + erro.getMessage());
            }
        }
    }
}
