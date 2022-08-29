package sistema.sistema_java_ok.src.principal;

import java.util.Vector;
import javax.swing.JOptionPane;

public class Vendedor extends Pessoa {

    private String usuario;
    private String senha;
    private boolean alteracao;
    private Vector<String> listaVend;

    ManipTXT manip = new ManipTXT("Vendedores");

    Vector<String> v = manip.ler();

    public Vendedor() {

    }

    public Vendedor(String usuario, String senha) {
        super();
        setUsuario(usuario);
        setSenha(senha);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        if (usuario.length() > 0) {
            this.usuario = usuario;
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha.length() > 0) {
            this.senha = senha;
        }
    }

    public void setAlteracao(boolean alteracao) {
        this.alteracao = alteracao;
    }

    public boolean getAlteracao() {
        return alteracao;
    }

    public void setListaVend(Vector<String> listaVend) {
        this.listaVend = listaVend;
    }

    public Vector<String> getListaVend() {
        return listaVend;
    }

    public boolean gravarVendedor() {
        try {
            StringBuilder texto = new StringBuilder();

            texto.append(super.toString());
            texto.append(";");
            texto.append(usuario);
            texto.append(";");
            texto.append(senha);

            v.add(texto.toString());

            manip.abrirArquivo();

            for (int x = 0; x < v.size(); x++) {
                manip.escrever(v.elementAt(x));
            }

            manip.fecharArquivo();

            return true;
        } catch (Exception e) {
            System.out.println("ERRO (gravarVendedor): " + e.toString());
        }

        return false;
    }

    public boolean GravarAlterar(int codigo, String nome, String cpf, String endereco, String usuario, String senha) {

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
            } else if (usuario.length() == 0) {
                JOptionPane.showMessageDialog(null, "Informe o Usuário!");
                return false;
            } else if (senha.length() == 0) {
                JOptionPane.showMessageDialog(null, "Informe a Senha!");
                return false;
            }

            setCodigo(codigo);
            setCpf(cpf);
            setEndereco(endereco);
            setNome(nome);
            setUsuario(usuario);
            setSenha(senha);

            if (alteracao) {
                if (alterarVendedor()) {
                    return true;
                }
            } else if (gravarVendedor()) {
                return true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: (GravarAlterar) - " + e.toString());
        }

        return false;
    }

    public boolean alterarVendedor() {
        try {

            StringBuilder texto = new StringBuilder();
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {
                    texto.append(super.toString());
                    texto.append(";");
                    texto.append(usuario);
                    texto.append(";");
                    texto.append(senha);

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
            System.out.println("ERRO (AlterarVendedor): " + e.toString());
        }

        return false;
    }

    public boolean deletarVendedor() {

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
            System.out.println("ERRO (DeletarVendedor): " + e.toString());
        }

        return false;
    }

    public boolean procurarVendedor() {
        try {

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {

                    setCpf(linhaResul[3]);
                    setEndereco(linhaResul[2]);
                    setNome(linhaResul[1]);
                    setUsuario(linhaResul[4]);
                    setSenha(linhaResul[5]);

                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("ERRO (ProcurarVendedor): " + e.toString());
        }

        return false;
    }

    public boolean verificaUsuario() {

        try {
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[4].equals(String.valueOf(getUsuario()))) {
                    if (linhaResul[5].equals(String.valueOf(getSenha()))) {
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("ERRO (VerificaUsuario): " + e.toString());
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

    public Vector<String> retornaVendedores() {
        try {
            v = manip.ler();
            return v;
        } catch (Exception e) {
            System.out.println("ERRO (retornaVendedores): " + e.toString());
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Vendedor [usuario=");
        builder.append(usuario);
        builder.append(", senha=");
        builder.append(senha);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
