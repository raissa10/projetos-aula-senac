package Testes.XML_NFE;

import java.util.ArrayList;
import java.util.List;

public class ListCriptografia {

    protected List<Criptografia> listCriptografia;

    public ListCriptografia() {
        listCriptografia = new ArrayList<>();
    }

    public List<Criptografia> getListCriptografia() {
        return listCriptografia;
    }

    public void setListCriptografia(List<Criptografia> listMensagem) {
        this.listCriptografia = listMensagem;
    }

    public void incluirBuscandoCodigo(Criptografia ctf) {
        int codigo = retornaCodigo();
        ctf.setCodigo(codigo);
        listCriptografia.add(ctf);
    }

    public void incluirSBuscarCodigo(Criptografia ctf) {
        listCriptografia.add(ctf);
    }

    public void alterarCriptografia(int posicao, Criptografia ctf) {
        listCriptografia.set(posicao, ctf);
    }

    public void removerPorPosicao(int posicao) {
        listCriptografia.remove(posicao);
    }

    public void removerPorCodigo(Criptografia ctf) {
        for (int x = 0; x < listCriptografia.size(); x++) {
            if (listCriptografia.get(x).getCodigo() == ctf.getCodigo()) {
                listCriptografia.remove(x);
            }
        }
    }

    public String listarCriptografia() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < listCriptografia.size(); x++) {
            sb.append(listCriptografia.get(x).toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public Criptografia RetornaCriptografia(int codigo) {
        for (int x = 0; x < listCriptografia.size(); x++) {
            if (listCriptografia.get(x).getCodigo() == codigo) {
                return listCriptografia.get(x);
            }
        }
        return null;
    }

    public int RetornaPosicaoCriptografia(int codigo) {
        for (int x = 0; x < listCriptografia.size(); x++) {
            if (listCriptografia.get(x).getCodigo() == codigo) {
                return x;
            }
        }
        return -1;
    }

    public int retornaCodigo() {
        int codigo = 0;
        int maior = -1;
        if (listCriptografia.size() <= 0) {
            codigo = 1;
        } else {
            for (int x = 0; x < listCriptografia.size(); x++) {
                if (listCriptografia.get(x).getCodigo() > maior) {
                    maior = listCriptografia.get(x).getCodigo();
                }
            }
            codigo = maior + 1;
        }

        return codigo;
    }

    public int buscaCodigoMensagemDaCrip(int codigoCrip) {
        for (int x = 0; x < listCriptografia.size(); x++) {
            if (listCriptografia.get(x).getCodigo() == codigoCrip) {
                return listCriptografia.get(x).getMensagem().getCodigo();
            }
        }
        return -1;

    }

}
