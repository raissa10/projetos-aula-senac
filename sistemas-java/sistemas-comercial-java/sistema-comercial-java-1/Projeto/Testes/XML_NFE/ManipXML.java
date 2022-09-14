package Testes.XML_NFE;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class ManipXML {

    final static String NOMEDOARQUIVO = "criptografia";
    final static String LOCALHOST = "src/xml/";

    public static void gravarXMLCriptografia(List<Criptografia> listaCriptografia) {		//
        // Cria o elemento que ser? o root
        Element config = new Element("Mensagens_criptografias") {
        };

        //define config como root
        Document documento = new Document(config);

        Element titulo = new Element("titulo");
        titulo.setText("Criptografias");

        config.addContent(titulo);

        for (int x = 0; x < listaCriptografia.size(); x++) {
            Element criptografia = new Element("Criptografia");
            Element mensagem = new Element("Mensagem");

            //dados criptografia
            criptografia.setAttribute("codigo", String.valueOf(listaCriptografia.get(x).getCodigo()));

            Element textoCrip = new Element("textoCriptografado");
            textoCrip.setText(listaCriptografia.get(x).getCriptografia());

            //dados mensagem
            mensagem.setAttribute("codigo", String.valueOf(listaCriptografia.get(x).getMensagem().getCodigo()));

            Element tituloCrip = new Element("tituloDaCriptografia");
            tituloCrip.setText(listaCriptografia.get(x).getMensagem().getTitulo());
            Element texto = new Element("texto");
            texto.setText(listaCriptografia.get(x).getMensagem().getTexto());
            Element chave = new Element("chave");
            chave.setText(listaCriptografia.get(x).getMensagem().getChave());

            mensagem.addContent(tituloCrip);
            mensagem.addContent(texto);
            mensagem.addContent(chave);

            criptografia.addContent(textoCrip);
            criptografia.addContent(mensagem);
            config.addContent(criptografia);
        }

        //classe respons?vel para imprimir / gerar o xml
        XMLOutputter xout = new XMLOutputter();
        try {
            //criando o arquivo de saida
            BufferedWriter arquivo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOCALHOST + NOMEDOARQUIVO + ".xml"), "UTF-8"));
            //imprimindo o xml no arquivo
            xout.output(documento, arquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ListCriptografia lerXMLCriptografia() {
        ListCriptografia listCriptografia = new ListCriptografia();
        //ListMensagem listMensagem = new ListMensagem();

        Document doc = null;
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(LOCALHOST + NOMEDOARQUIVO + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element config = doc.getRootElement();
        List lista = config.getChildren("Criptografia");

        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            Element element = (Element) iter.next();
            Criptografia ctf = new Criptografia();
            Mensagem msg = new Mensagem();

            ctf.setCodigo(Integer.parseInt(element.getAttributeValue("codigo")));
            ctf.setCriptografia(element.getChildText("textoCriptografado"));

            msg.setCodigo(Integer.parseInt(element.getChild("Mensagem").getAttributeValue("codigo")));
            msg.setTitulo(element.getChild("Mensagem").getChildText("tituloDaCriptografia"));
            msg.setTexto(element.getChild("Mensagem").getChildText("texto"));
            msg.setChave(element.getChild("Mensagem").getChildText("chave"));

            ctf.setMensagem(msg);

            listCriptografia.incluirSBuscarCodigo(ctf);
        }
        return listCriptografia;
    }

    public static ListMensagem lerXMLMensagem() {
        // ListCriptografia listCriptografia = new ListCriptografia();
        ListMensagem listMensagem = new ListMensagem();

        Document doc = null;
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(LOCALHOST + NOMEDOARQUIVO + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element config = doc.getRootElement();
        List lista = config.getChildren("Criptografia");

        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            Element element = (Element) iter.next();
            Mensagem msg = new Mensagem();

            msg.setCodigo(Integer.parseInt(element.getChild("Mensagem").getAttributeValue("codigo")));
            msg.setTitulo(element.getChild("Mensagem").getChildText("tituloDaCriptografia"));
            msg.setTexto(element.getChild("Mensagem").getChildText("texto"));
            msg.setChave(element.getChild("Mensagem").getChildText("chave"));

            listMensagem.incluirSBuscarCodigo(msg);
        }
        return listMensagem;
    }
}
