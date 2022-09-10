package principal;

import java.util.Vector;
import javax.swing.JOptionPane;

public class Cliente extends Pessoa {

    ManipTXT manip = new ManipTXT("Clientes");

    Vector<String> v = manip.ler();

    private boolean alteracao;
    private Vector<String> listaCli;

    public void setAlteracao(boolean alteracao) {
        this.alteracao = alteracao;
    }

    public boolean getAlteracao() {

        if (alteracao) {
            return true;
        }

        return false;
    }

    public void setListaCli(Vector<String> listaCli) {
        this.listaCli = listaCli;
    }

    public Vector<String> getListaCli() {
        return listaCli;
    }

    public boolean gravarCliente() {
        try {
            StringBuilder texto = new StringBuilder();

            texto.append(super.toString());

            v.add(texto.toString());

            manip.abrirArquivo();

            for (int x = 0; x < v.size(); x++) {
                manip.escrever(v.elementAt(x));
            }

            manip.fecharArquivo();

            return true;
        } catch (Exception e) {
            System.out.println("ERRO (gravarCliente): " + e.toString());
        }

        return false;
    }

    public boolean GravarAlterar(int codigo, String nome, String cpf, String endereco) {

        try {

            if (nome.length() == 0) {
                JOptionPane.showMessageDialog(null, "Informe o Nome!");
                return false;
            } else if (cpf.length() == 0) {
                JOptionPane.showMessageDialog(null, "Informe o CPF!");
                return false;
            } else if (endereco.length() == 0) {
                JOptionPane.showMessageDialog(null, "Informe o Endereço!");
                return false;
            }

            setCodigo(codigo);
            setCpf(cpf);
            setEndereco(endereco);
            setNome(nome);

            if (alteracao) {
                if (alterarCliente()) {
                    return true;
                }
            } else {
                if (gravarCliente()) {
                    return true;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: (GravarAlterar) - " + e.toString());
        }

        return false;
    }

    public boolean alterarCliente() {
        try {

            StringBuilder texto = new StringBuilder();
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {
                    texto.append(super.toString());

                    manip.abrirArquivo();

                    v.set(x, texto.toString());

                    for (int f = 0; f < v.size(); f++) {
                        manip.escrever(v.elementAt(f));
                    }

                    manip.fecharArquivo();
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("ERRO (AlterarCliente): " + e.toString());
        }

        return false;
    }

    public boolean deletarCliente() {

        try {
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {
                    manip.abrirArquivo();

                    v.remove(x);

                    for (int f = 0; f < v.size(); f++) {
                        manip.escrever(v.elementAt(f));
                    }

                    manip.fecharArquivo();

                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO (DeletarCliente): " + e.toString());
        }

        return false;
    }

    public boolean procurarCliente() {
        try {

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {

                    setCpf(linhaResul[3]);
                    setEndereco(linhaResul[2]);
                    setNome(linhaResul[1]);

                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("ERRO (ProcurarCliente): " + e.toString());
        }

        return false;
    }

    public int retornaUltimo() {
        int codigo = 0;

        try {
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (Integer.parseInt(linhaResul[0]) > codigo) {
                    codigo = Integer.parseInt(linhaResul[0]);
                }
            }
        } catch (Exception e) {
            codigo = 0;
        }

        return codigo + 1;
    }

    public Vector<String> retornaClientes() {
        try {
            v = manip.ler();

            return v;
        } catch (Exception e) {
            System.out.println("ERRO (retornaClientes): " + e.toString());
        }

        return null;
    }

}
