package sistema.sistema_java_ok.src.principal;

public class Pessoa {

    private int codigo;
    private String nome;
    private String endereco;
    private String cpf;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.length() > 0) {
            this.nome = nome;
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        if (endereco.length() > 0) {
            this.endereco = endereco;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf.length() > 0) {
            this.cpf = cpf;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(codigo);
        builder.append(";");
        builder.append(nome);
        builder.append(";");
        builder.append(endereco);
        builder.append(";");
        builder.append(cpf);
        return builder.toString();
    }
}
