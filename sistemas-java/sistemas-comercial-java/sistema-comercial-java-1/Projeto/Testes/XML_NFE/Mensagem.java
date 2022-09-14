package Testes.XML_NFE;

public class Mensagem {

    private int codigo;
    private String titulo;
    private String texto;
    private String chave;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "codigo=" + codigo + ", titulo=" + titulo + ", texto=" + texto + ", chave=" + chave + '}';
    }

}
