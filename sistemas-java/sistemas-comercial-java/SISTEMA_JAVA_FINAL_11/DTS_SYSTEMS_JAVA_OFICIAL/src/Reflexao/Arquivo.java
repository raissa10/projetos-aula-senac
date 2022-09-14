package Reflexao;

/**
 *
 * @author jessica
 */
public class Arquivo {
    
    private String nome;
    private String numeroDeCCP;
    private int quantidadeCCP;
    private String hashParaTabelaUnica;
    private boolean realizarCheckSum;
    private boolean converterHash;
    private boolean conjugarNomeENumeroCCP;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNumeroDeCCP() {
        return numeroDeCCP;
    }
    public void setNumeroDeCCP(String numeroDeCCP) {
        this.numeroDeCCP = numeroDeCCP;
    }
    public int getQuantidadeCCP() {
        return quantidadeCCP;
    }
    public void setQuantidadeCCP(int quantidadeCCP) {
        this.quantidadeCCP = quantidadeCCP;
    }
    public String getHashParaTabelaUnica() {
        return hashParaTabelaUnica;
    }
    public void setHashParaTabelaUnica(String hashParaTabelaUnica) {
        this.hashParaTabelaUnica = hashParaTabelaUnica;
    }
    public boolean isRealizarCheckSum() {
        return realizarCheckSum;
    }
    public void setRealizarCheckSum(boolean realizarCheckSum) {
        this.realizarCheckSum = realizarCheckSum;
    }
    public boolean isConverterHash() {
        return converterHash;
    }
    public void setConverterHash(boolean converterHash) {
        this.converterHash = converterHash;
    }
    public boolean isConjugarNomeENumeroCCP() {
        return conjugarNomeENumeroCCP;
    }
    public void setConjugarNomeENumeroCCP(boolean conjugarNomeENumeroCCP) {
        this.conjugarNomeENumeroCCP = conjugarNomeENumeroCCP;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (conjugarNomeENumeroCCP ? 1231 : 1237);
        result = prime * result + (converterHash ? 1231 : 1237);
        result = prime
                * result
                + ((hashParaTabelaUnica == null) ? 0 : hashParaTabelaUnica
                        .hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result
                + ((numeroDeCCP == null) ? 0 : numeroDeCCP.hashCode());
        result = prime * result + quantidadeCCP;
        result = prime * result + (realizarCheckSum ? 1231 : 1237);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Arquivo other = (Arquivo) obj;
        if (conjugarNomeENumeroCCP != other.conjugarNomeENumeroCCP)
            return false;
        if (converterHash != other.converterHash)
            return false;
        if (hashParaTabelaUnica == null) {
            if (other.hashParaTabelaUnica != null)
                return false;
        } else if (!hashParaTabelaUnica.equals(other.hashParaTabelaUnica))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (numeroDeCCP == null) {
            if (other.numeroDeCCP != null)
                return false;
        } else if (!numeroDeCCP.equals(other.numeroDeCCP))
            return false;
        if (quantidadeCCP != other.quantidadeCCP)
            return false;
        return realizarCheckSum == other.realizarCheckSum;
    }
}
