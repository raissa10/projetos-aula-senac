package Testes.XML_NFE;

/**
 *
 * @author Rodrigo
 */
public class Criptografia {

    private int codigo;
    private String criptografia;
    protected Mensagem mensagem;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Criptografia() {
        mensagem = new Mensagem();
    }

    public String getCriptografia() {
        return criptografia;
    }

    public void setCriptografia(String criptografia) {
        this.criptografia = criptografia;
    }

    public Mensagem getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagem mensagem) {
        this.mensagem = mensagem;
    }

    public void criptografarMensagem(Mensagem mensagem) {
        //variaveis para o texto
        String texto = mensagem.getTexto();
        texto = Criptografia.embaralharTexto(texto);
        String textoCriptografado = "";
        char letra = 'a';
        //variaveis para a chave
        String chave = mensagem.getChave();
        int indiceChave = 0;
        //final int tamanhoChave = 4; estava assim antes agora mudei 
        final int tamanhoChave = chave.length();
        String splitChave[] = new String[tamanhoChave];
        splitChave = chave.split("");
        //outras variaveis
        final int INTERVALO_INI = 33;
        final int INTERVALO_FIN = 126;
        int codigo = 0;

        for (int x = 0; x < texto.length(); x++) {
            /*PRIMEIRAMENTE VERIFICA SE INDICI EH PAR
             * SE FOR, ADICIONA CARACTERES ALEATORIOS*/
            if (x % 2 == 0) {
                textoCriptografado += (char) numeroAleatorioIntervalo(INTERVALO_INI, INTERVALO_FIN);
                textoCriptografado += (char) numeroAleatorioIntervalo(INTERVALO_INI, INTERVALO_FIN);
                textoCriptografado += (char) numeroAleatorioIntervalo(INTERVALO_INI, INTERVALO_FIN);
            }
            //INICIA CALCULOS PARA TROCAR CODIGO DA LETRA PARA OUTRO
            codigo = (int) texto.charAt(x);
            char aux = 'a';
            codigo += (Long.parseLong(splitChave[indiceChave]));
            if (codigo > 255) {
                codigo = 31 + (codigo - 255);
            }
            letra = (char) codigo;
            textoCriptografado += letra;

            if (indiceChave >= tamanhoChave - 1) {
                indiceChave = 0;
            } else {
                indiceChave++;
            }
        }
        this.criptografia = Criptografia.embaralharTexto(textoCriptografado);
    }

    public String decriptografarMensagem(Criptografia criptografia) {
        //variaveis do texto criptografado
        String texto = criptografia.getCriptografia();
        texto = Criptografia.desembaralharTexto(texto);
        String txtSCaracEspecial = "", textoLegivel = "";
        int contador = 0;
        char letra = 'a';
        //variaveis da chave
        String chave = criptografia.getMensagem().getChave();
        int indiceChave = 0;
        //final int tamanhoChave = 4; estava assim antes agora mudei 
        final int tamanhoChave = chave.length();
        String splitChave[] = new String[tamanhoChave];
        splitChave = chave.split("");
        //outra variaveis
        int codigo = 0;

        //RETIRA CARACTERES ALEATORIOS DO TEXTO
        for (int x = 0; x < texto.length(); x++) {
            if (contador == 3) {
                txtSCaracEspecial += texto.charAt(x);
            } else if (contador == 4) {
                txtSCaracEspecial += texto.charAt(x);
                contador = -1;
            }
            contador++;
        }
        for (int x = 0; x < txtSCaracEspecial.length(); x++) {
            codigo = (int) txtSCaracEspecial.charAt(x);
            codigo -= (Long.parseLong(splitChave[indiceChave]));
            if (codigo < 32) {
                codigo = 256 - (32 - codigo);
            }
            letra = (char) codigo;
            textoLegivel += letra;
            if (indiceChave >= tamanhoChave - 1) {
                indiceChave = 0;
            } else {
                indiceChave++;
            }

        }
        return Criptografia.desembaralharTexto(textoLegivel);
    }

    /*METODOS PRIVADOS UTILIZADOS PARA CRIPTOGRAFIA*/
    private static String embaralharTexto(String texto) {
        String retorno = "";
        if (texto.length() % 2 == 0) {
            for (int x = 0; x < texto.length() / 2; x++) {
                retorno += texto.charAt(x);
            }
            for (int x = texto.length() / 2; x < texto.length(); x++) {
                retorno += texto.charAt(x);
            }
        } else {
            for (int x = texto.length() - 1; x >= 0; x--) {
                retorno += texto.charAt(x);
            }
        }

        return retorno;
    }

    private static String desembaralharTexto(String texto) {
        String retorno = "";
        if (texto.length() % 2 == 0) {
            for (int x = 0; x < texto.length() / 2; x++) {
                retorno += texto.charAt(x);
            }
            for (int x = texto.length() / 2; x < texto.length(); x++) {
                retorno += texto.charAt(x);
            }
        } else {
            for (int x = texto.length() - 1; x >= 0; x--) {
                retorno += texto.charAt(x);
            }
        }

        return retorno;
    }

    private static int numeroAleatorioIntervalo(int Inicio, int Fim) {
        return (int) Math.ceil(Math.random() * (Fim - Inicio + 1)) - 1 + Inicio;
    }

    @Override
    public String toString() {
        return "Criptografia{" + "codigo=" + codigo + ", criptografia=" + criptografia + ", mensagem=" + mensagem + '}';
    }

}
