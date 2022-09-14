package Testes.XML_NFE;

import java.util.ArrayList;
import java.util.List;

public class ListMensagem {

    protected List<Mensagem> listMensagem;

    public ListMensagem() {
        listMensagem = new ArrayList<>();
    }

    public List<Mensagem> getListMensagem() {
        return listMensagem;
    }

    public void setListMensagem(List<Mensagem> listMensagem) {
        this.listMensagem = listMensagem;
    }

    public void incluirBuscandoCodigo(Mensagem msg) {
        int codigo = retornaCodigo();
        msg.setCodigo(codigo);
        listMensagem.add(msg);
    }

    public void incluirSBuscarCodigo(Mensagem msg) {
        listMensagem.add(msg);
    }

    public void alterarMensagem(int posicao, Mensagem msg) {
        listMensagem.set(posicao, msg);
    }

    public void removerPorPosicao(int posicao) {
        listMensagem.remove(posicao);
    }

    public void removerPorCodigo(Mensagem msg) {
        for (int x = 0; x < listMensagem.size(); x++) {
            if (listMensagem.get(x).getCodigo() == msg.getCodigo()) {
                listMensagem.remove(x);
            }
        }
    }

    public String listarMensagem() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < listMensagem.size(); x++) {
            sb.append(listMensagem.get(x).toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public Mensagem RetornaMensagem(int codigo) {
        for (int x = 0; x < listMensagem.size(); x++) {
            if (listMensagem.get(x).getCodigo() == codigo) {
                return listMensagem.get(x);
            }
        }
        return null;
    }

    public int RetornaPosicaoMensagem(int codigo) {
        for (int x = 0; x < listMensagem.size(); x++) {
            if (listMensagem.get(x).getCodigo() == codigo) {
                return x;
            }
        }
        return -1;
    }

    public int retornaCodigo() {
        int codigo = 0;
        int maior = -1;
        if (listMensagem.size() <= 0) {
            codigo = 1;
        } else {
            for (int x = 0; x < listMensagem.size(); x++) {
                if (listMensagem.get(x).getCodigo() > maior) {
                    maior = listMensagem.get(x).getCodigo();
                }
            }
            codigo = maior + 1;
        }

        return codigo;
    }

}
