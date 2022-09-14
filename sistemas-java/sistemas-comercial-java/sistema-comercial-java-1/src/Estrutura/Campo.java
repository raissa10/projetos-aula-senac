package Estrutura;

/**
 *
 * @author Gelvazio Camargo
 * @since 28/07/2019
 */
public class Campo {
    
    private String nomeBanco;
    private String nome;
    private String titulo;
    private boolean isChave;
    private String tipo;
    
    public static String TIPO_NUMERICO = "numerico";
    public static String TIPO_TEXTO = "texto";

    public Campo(String nomeBanco, String nome, String titulo, boolean isChave, String tipo) {
        this.nomeBanco = nomeBanco;
        this.nome = nome;
        this.titulo = titulo;
        this.isChave = isChave;
        this.tipo = tipo;
    }
    
    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean getIsChave() {
        return isChave;
    }

    public void setIsChave(boolean isChave) {
        this.isChave = isChave;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
        
}
