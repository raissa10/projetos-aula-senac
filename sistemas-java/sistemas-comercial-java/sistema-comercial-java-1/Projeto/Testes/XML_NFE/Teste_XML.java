package Testes.XML_NFE;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdom.*;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Gelvazio Camargo
 */
public class Teste_XML {

    final static String NOMEDOARQUIVO = "xml_gelvazio_teste";
    final static String LOCALHOST = "src/Testes/XML_NFE/";

    public static void gravarXML() {
        // Cria o elemento que sera o root
        Element config = new Element("Mensagens_criptografias") {
        };

        //define nfeProc como root
        Document documento = new Document(config);

        Element titulo = new Element("titulo");
        titulo.setText("XML Testes Gelvazio Camargo");

        config.addContent(titulo);

        int tempCodigoCriptografia = 0;
        String tempMensagem = "MensagemPosicao";
        String tempTitulo = "TituloPosicao";
        String tempTexto = "TextoPosicao";
        String tempChave = "ChavePosicao";

        //String tempMensagem="MensagemPosicao";
        for (int x = 1; x < 5; x++) {
            Element criptografia = new Element("Criptografia");
            Element mensagem = new Element("Mensagem");

            //dados criptografia
            criptografia.setAttribute("codigo", String.valueOf(x));

            Element textoCrip = new Element("textoCriptografado");

            tempCodigoCriptografia = x;// tempCodigoCriptografia + String.valueOf((x));

            textoCrip.setText(String.valueOf(tempCodigoCriptografia));

            //dados mensagem
            tempMensagem = tempMensagem + String.valueOf((x));

            mensagem.setAttribute("codigo", tempMensagem);

            Element tituloCrip = new Element("tituloDaCriptografia");
            tempTitulo = tempTitulo + String.valueOf((x));
            tituloCrip.setText(tempTitulo);

            Element texto = new Element("texto");
            tempTexto = tempTexto + String.valueOf((x));
            texto.setText(tempTexto);

            Element chave = new Element("chave");
            tempChave = tempChave + String.valueOf((x));
            chave.setText(tempChave);

            mensagem.addContent(tituloCrip);
            mensagem.addContent(texto);
            mensagem.addContent(chave);

            criptografia.addContent(textoCrip);
            criptografia.addContent(mensagem);
            config.addContent(criptografia);

            //Altera para pegar o mesmo texto como testes
            tempCodigoCriptografia = 0;
            tempMensagem = null;
            tempTitulo = null;
            tempTexto = null;
            tempChave = null;

            tempCodigoCriptografia = 0;
            tempMensagem = "MensagemPosicao";
            tempTitulo = "TituloPosicao";
            tempTexto = "TextoPosicao";
            tempChave = "ChavePosicao";
        }

        //classe para imprimir / gerar o xml
        XMLOutputter xout = new XMLOutputter();
        try {
            //criando o arquivo de saida
            BufferedWriter arquivo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOCALHOST + NOMEDOARQUIVO + ".xml"), "UTF-8"));
            //imprimindo o xml no arquivo
            xout.output(documento, arquivo);
            JOptionPane.showMessageDialog(null, "Arquivo XML criado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gravarXML_NFE() {

// <editor-fold defaultstate="collapsed" desc="nfeProc">
        // Cria o elemento que sera o root
        Element nfeProc = new Element("nfeProc") {

        };
        nfeProc.setAttribute("versao", "2.0");
        nfeProc.addNamespaceDeclaration(Namespace.XML_NAMESPACE);

        //define nfeProc como root
        Document documento = new Document(nfeProc);

        // <editor-fold defaultstate="collapsed" desc="NFe">
        Element NFe = new Element("NFe");
        NFe.addNamespaceDeclaration(Namespace.NO_NAMESPACE);

        // <editor-fold defaultstate="collapsed" desc="infNFe">
        Element infNFe = new Element("infNFe");
        infNFe.setText("");

        Element ide = new Element("ide");
        ide.setText("");

        Element cUF = new Element("cUF");
        cUF.setText("42");

        Element cNF = new Element("cNF");
        cNF.setText("10003598");

        Element natOp = new Element("natOp");
        natOp.setText("VENDA DE ATIVO IMOBILIZADO");

        Element indPag = new Element("indPag");
        indPag.setText("0");

        Element mod = new Element("mod");
        mod.setText("55");

        Element serie = new Element("serie");
        serie.setText("10");

        Element nNF = new Element("nNF");
        nNF.setText("272");

        Element dEmi = new Element("dEmi");
        dEmi.setText("2013-11-19");

        Element dSaiEnt = new Element("dSaiEnt");
        Element hSaiEnt = new Element("hSaiEnt");
        Element tpNF = new Element("tpNF");
        Element cMunFG = new Element("cMunFG");
        Element tpImp = new Element("tpImp");
        Element tpEmis = new Element("tpEmis");
        Element cDV = new Element("cDV");
        Element tpAmb = new Element("tpAmb");
        Element finNFe = new Element("finNFe");
        Element procEmi = new Element("procEmi");
        Element verProc = new Element("verProc");

        dSaiEnt.setText("2013-11-19");
        hSaiEnt.setText("14:00");
        tpNF.setText("1");
        cMunFG.setText("4214805");
        tpImp.setText("1");
        tpEmis.setText("1");
        cDV.setText("2");
        tpAmb.setText("1");
        finNFe.setText("1");
        procEmi.setText("0");
        verProc.setText("DTS SYSTEMS 1.0");

        //Adicionando Campos ao Content ide
        ide.addContent(dSaiEnt);
        ide.addContent(hSaiEnt);
        ide.addContent(tpNF);
        ide.addContent(cMunFG);
        ide.addContent(tpImp);
        ide.addContent(tpEmis);
        ide.addContent(cDV);
        ide.addContent(tpAmb);
        ide.addContent(finNFe);
        ide.addContent(procEmi);
        ide.addContent(verProc);

        Element emit = new Element("emit");
        emit.setText("");

        Element CNPJ = new Element("CNPJ");
        Element xNome = new Element("xNome");
        Element xFant = new Element("xFant");
        Element enderEmit = new Element("enderEmit");

        Element xLgr = new Element("xLgr");
        Element nro = new Element("nro");
        Element xBairro = new Element("xBairro");
        Element cMun = new Element("cMun");
        Element xMun = new Element("xMun");
        Element UF = new Element("UF");
        Element CEP = new Element("CEP");
        Element cPais = new Element("cPais");
        Element xPais = new Element("xPais");
        Element fone = new Element("fone");

        //Adicionando Tags acima ao enderEmit
        enderEmit.addContent(xLgr);
        enderEmit.addContent(nro);
        enderEmit.addContent(xBairro);
        enderEmit.addContent(cMun);
        enderEmit.addContent(xMun);
        enderEmit.addContent(UF);
        enderEmit.addContent(CEP);
        enderEmit.addContent(cPais);
        enderEmit.addContent(xPais);
        enderEmit.addContent(fone);

        Element IE = new Element("IE");
        Element CRT = new Element("CRT");

        //Adicionando Tags acima á emit
        emit.addContent(CNPJ);
        emit.addContent(xNome);
        emit.addContent(xFant);
        /*
         emit.addContent(enderEmit);
         emit.addContent(xMun   	);
         emit.addContent(UF     	);
         emit.addContent(CEP    	);
         emit.addContent(cPais  	);
         emit.addContent(xPais  	);
         emit.addContent(fone   	);
         */
        emit.addContent(IE);
        emit.addContent(CRT);

        Element dest = new Element("dest");
        dest.setText("");

        Element CPF = new Element("CPF");
        xNome = new Element("xNome");
        Element enderDest = new Element("enderDest");

        xLgr = new Element("xLgr");
        nro = new Element("nro");
        xBairro = new Element("xBairro");
        cMun = new Element("cMun");
        xMun = new Element("xMun");
        UF = new Element("UF");
        CEP = new Element("CEP");
        cPais = new Element("cPais");
        xPais = new Element("xPais");

        //Adcionando tags acima á enderDest
        enderDest.addContent(xLgr);
        enderDest.addContent(nro);
        enderDest.addContent(xBairro);
        enderDest.addContent(cMun);
        enderDest.addContent(xMun);
        enderDest.addContent(UF);
        enderDest.addContent(CEP);
        enderDest.addContent(cPais);
        enderDest.addContent(xPais);

        IE = new Element("IE");
        //Adicionando Tags acima a dest
        dest.addContent(CPF);
        dest.addContent(xNome);
        dest.addContent(enderDest);
        dest.addContent(IE);

        Element det = new Element("det");
        det.setText("");

        Element prod = new Element("prod");
        prod.setText("");

        Element cProd = new Element("cProd");
        Element cEAN = new Element("cEAN");
        Element xProd = new Element("xProd");
        Element NCM = new Element("NCM");
        Element CFOP = new Element("CFOP");
        Element uCom = new Element("uCom");
        Element qCom = new Element("qCom");
        Element vUnCom = new Element("vUnCom");
        Element vProd = new Element("vProd");
        Element cEANTrib = new Element("cEANTrib");
        Element uTrib = new Element("uTrib");
        Element qTrib = new Element("qTrib");
        Element vUnTrib = new Element("vUnTrib");
        Element indTot = new Element("indTot");

        //Adcinando Tags acima a tag prod
        prod.addContent(cProd);
        prod.addContent(cEAN);
        prod.addContent(xProd);
        prod.addContent(NCM);
        prod.addContent(CFOP);
        prod.addContent(uCom);
        prod.addContent(qCom);
        prod.addContent(vUnCom);
        prod.addContent(vProd);
        prod.addContent(cEANTrib);
        prod.addContent(uTrib);
        prod.addContent(qTrib);
        prod.addContent(vUnTrib);
        prod.addContent(indTot);

        Element imposto = new Element("imposto");
        imposto.setText("");

        Element vTotTrib = new Element("vTotTrib");
        vTotTrib.setText("2000");

        Element ICMS = new Element("ICMS");
        ICMS.setText("100");

        Element ICMSSN900 = new Element("ICMSSN900");
        ICMSSN900.setText("100");

        Element orig = new Element("orig");
        Element CSOSN = new Element("CSOSN");
        Element modBC = new Element("modBC");
        Element vBC = new Element("vBC");
        Element pICMS = new Element("pICMS");
        Element vICMS = new Element("vICMS");
        Element modBCST = new Element("modBCST");
        Element vBCST = new Element("vBCST");
        Element pICMSST = new Element("pICMSST");
        Element vICMSST = new Element("vICMSST");
        Element pCredSN = new Element("pCredSN");
        Element vCredICMSSN = new Element("vCredICMSSN");

        //Adcionando tag acma a ICMSSN900
        ICMSSN900.addContent(orig);
        ICMSSN900.addContent(CSOSN);
        ICMSSN900.addContent(modBC);
        ICMSSN900.addContent(vBC);
        ICMSSN900.addContent(pICMS);
        ICMSSN900.addContent(vICMS);
        ICMSSN900.addContent(modBCST);
        ICMSSN900.addContent(vBCST);
        ICMSSN900.addContent(pICMSST);
        ICMSSN900.addContent(vICMSST);
        ICMSSN900.addContent(pCredSN);
        ICMSSN900.addContent(vCredICMSSN);

        Element PIS = new Element("PIS");
        PIS.setText("100");

        Element PISNT = new Element("PISNT");
        PISNT.setText("100");

        Element CST = new Element("CST");
        CST.setText("100");

        //Adicionando tag a PISNT
        PISNT.addContent(CST);

        //Adicionando tag a PIS
        PIS.addContent(PISNT);

        Element COFINS = new Element("COFINS");
        COFINS.setText("100");

        Element COFINSNT = new Element("COFINSNT");
        COFINSNT.setText("100");

        CST = new Element("CST");
        CST.setText("100");

        //Adicionando tag a PISNT
        COFINSNT.addContent(CST);

        //Adicionando tag a PIS
        COFINS.addContent(COFINSNT);

        //Adicionando tag a imposto
        imposto.addContent(vTotTrib);
        imposto.addContent(ICMS);
        imposto.addContent(PIS);
        imposto.addContent(COFINS);

        Element infAdProd = new Element("infAdProd");
        infAdProd.setText("MOD/FABR: 2013/2013 COMB: GASOLINA COR: PRETA");

        //Adicionando Tags Acima a tag det
        det.addContent(prod);
        det.addContent(imposto);
        det.addContent(infAdProd);

        Element total = new Element("total");
        total.setText("");

        Element ICMSTot = new Element("ICMSTot");
        ICMSTot.setText("");

        vBC = new Element("vBC");
        vICMS = new Element("vICMS");
        vBCST = new Element("vBCST");
        Element vST = new Element("vST");
        vProd = new Element("vProd");
        Element vFrete = new Element("vFrete");
        Element vSeg = new Element("vSeg");
        Element vDesc = new Element("vDesc");
        Element vII = new Element("vII");
        Element vIPI = new Element("vIPI");
        Element vPIS = new Element("vPIS");
        Element vCOFINS = new Element("vCOFINS");
        Element vOutro = new Element("vOutro");
        Element vNF = new Element("vNF");
        vTotTrib = new Element("vTotTrib");

        //Adcinando tags acima a ICMSTot
        ICMSTot.addContent(vBC);
        ICMSTot.addContent(vICMS);
        ICMSTot.addContent(vBCST);
        ICMSTot.addContent(vST);
        ICMSTot.addContent(vProd);
        ICMSTot.addContent(vFrete);
        ICMSTot.addContent(vSeg);
        ICMSTot.addContent(vDesc);
        ICMSTot.addContent(vII);
        ICMSTot.addContent(vIPI);
        ICMSTot.addContent(vPIS);
        ICMSTot.addContent(vCOFINS);
        ICMSTot.addContent(vOutro);
        ICMSTot.addContent(vNF);
        ICMSTot.addContent(vTotTrib);

        //Adicionando Tags acima a total
        total.addContent(ICMSTot);

        Element transp = new Element("transp");
        transp.setText("");

        Element modFrete = new Element("modFrete");
        modFrete.setText("");

        //Adicionando tag acima a transp
        transp.addContent(modFrete);

        Element cobr = new Element("cobr");
        cobr.setText("");

        Element fat = new Element("fat");
        fat.setText("");

        Element nFat = new Element("nFat");
        nFat.setText("");

        Element vOrig = new Element("vOrig");
        vOrig.setText("");

        //Adicionando Tags á fat
        fat.addContent(nFat);
        fat.addContent(vOrig);

        Element dup = new Element("dup");
        dup.setText("");

        Element nDup = new Element("nDup");
        nDup.setText("");

        Element dVenc = new Element("dVenc");
        dVenc.setText("");

        Element vDup = new Element("vDup");
        vDup.setText("");

        //Adicionando Tags á dup
        dup.addContent(nDup);
        dup.addContent(dVenc);
        dup.addContent(vDup);

        //Adicionando Tags á cobr
        cobr.addContent(fat);
        cobr.addContent(dup);

        Element infAdic = new Element("infAdic");
        infAdic.setText("");

        Element infCpl = new Element("infCpl");
        infCpl.setText("\"DOCUMENTO EMITIDO POR ME OPTANTE PELO SIMPLES NACIONAL NAO GERA DIREITO A CREDITO FISCAL DE ISS E IPI Valor Aproximado do Imposto R$2.827,50 (37,7%) Fonte: IBPT");

        //Adicionando Tags á infAdic
        infAdic.addContent(infCpl);

        //Adicionando Tags á infNFe
        infNFe.addContent(ide);
        infNFe.addContent(emit);
        infNFe.addContent(dest);
        infNFe.addContent(det);
        infNFe.addContent(total);
        infNFe.addContent(transp);
        infNFe.addContent(cobr);
        infNFe.addContent(infAdic);

        // </editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Signature">
        Element Signature = new Element("Signature");
        Signature.setText("");

        //<editor-fold defaultstate="collapsed" desc="SignedInfo">
        Element SignedInfo = new Element("SignedInfo");
        SignedInfo.setText("");

        Element CanonicalizationMethod = new Element("CanonicalizationMethod");
        CanonicalizationMethod.setText("");

        Element SignatureMethod = new Element("SignatureMethod");
        SignatureMethod.setText("");

        Element Reference = new Element("Reference");
        Reference.setText("");

        Element Transforms = new Element("Transforms");
        Transforms.setText("-DEVE TER UM ITERADOR!");

        Element Transform = new Element("Transform");
        Transform.setText("Transform1-DEVE TER UM ITERADOR!");

        //Adicionando tags á Transforms
        Transforms.addContent(Transform);

        Element DigestMethod = new Element("DigestMethod");
        DigestMethod.setText("");

        Element DigestValue = new Element("DigestValue");
        DigestValue.setText("resl0EWzbkhrY5fCtL38DaOlfLY=");

        //Adicionando Tags á Reference
        Reference.addContent(Transforms);
        Reference.addContent(DigestMethod);
        Reference.addContent(DigestValue);

        //Adicionando Tags á SignedInfo
        SignedInfo.addContent(CanonicalizationMethod);
        SignedInfo.addContent(SignatureMethod);
        SignedInfo.addContent(Reference);

        // </editor-fold>
        //<editor-fold defaultstate="collapsed" desc="SignatureValue">
        Element SignatureValue = new Element("SignatureValue");
        SignatureValue.setText("SynKm8xh/ftfdDlRL3M6lIY4n3s9GlyDsXiaLmUtuUghqze5ctaMzPqNHy33crpPuM+VDF/N/YHXA587Kih4PAR0rVtErdWN8snR4e/jjeo2nSqE4d9vmE66AOUezbxkt/gE7fVMIvsR6DlgA1eO0SNaURPLhw3S1uxFQ6FbI6M7ON3PCB4BjPV6i2Dh/lBej4AM1qSni9/cSx35yqWZ5QXazEGb2EnPeruVxqzrPKNzIdXUqUhe/rsxcM79yd71rCN6PMMCm6nZd84IoLCxJLSAp/l9rE/CzAS5snpl00mREeQiti7VZ1+hOfc4Vj9M4WW+3p3rgG98/o3XB2Dc5Q==");

        // </editor-fold>
        //<editor-fold defaultstate="collapsed" desc="KeyInfo">
        Element KeyInfo = new Element("KeyInfo");
        KeyInfo.setText("");

        Element X509Data = new Element("X509Data");
        X509Data.setText("");

        Element X509Certificate = new Element("X509Certificate");
        X509Certificate.setText("MIIIRTCCBi2gAwIBAgIQewCd7TUgBZDN2LHEd/zYNjANBgkqhkiG9w0BAQsFADB4MQswCQYDVQQGEwJCUjETMBEGA1UEChMKSUNQLUJyYXNpbDE2MDQGA1UECxMtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRwwGgYDVQQDExNBQyBDZXJ0aXNpZ24gUkZCIEc0MB4XDTEzMDgyNzAwMDAwMFoXDTE0MDgyNjIzNTk1OVowgewxCzAJBgNVBAYTAkJSMRMwEQYDVQQKFApJQ1AtQnJhc2lsMQswCQYDVQQIEwJTQzETMBEGA1UEBxQKUklPIERPIFNVTDE2MDQGA1UECxQtU2VjcmV0YXJpYSBkYSBSZWNlaXRhIEZlZGVyYWwgZG8gQnJhc2lsIC0gUkZCMRYwFAYDVQQLFA1SRkIgZS1DTlBKIEExMSIwIAYDVQQLFBlBdXRlbnRpY2FkbyBwb3IgQVIgRmFjaXNjMTIwMAYDVQQDEylNQVhJIERJU1RSSUJVSURPUkEgTFREQSBNRTowMTYzMzM1OTAwMDE2OTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAINSl9PDjSKckuYYtNb5cdE33xO61rdzNSX0ecyxSHf6oA3jFYZvblxg6LkoK69DyIDpWk+eaKRwFlnWpGp088NsSajK0xhZ+lHWCvOa4DPiWhvQ1vz9wadWeWtTY2/Q6vcGbjkhu+yoxoopISZo8YrADJT7uS1epdS1EtIOORmZhTsk61Xfy3eJ2pPA30AMb129/SfgmnDBeR1seHLg1v/so62PtMUxpFwZPUksDYhGdGgjQWA4TX0R/Ig8e5JpHAQCDOAjlqDPLPJ/pUIRdmMGnozZ0PY78SRGX3naIUad4NUZsL0zBJKkVUSIefcYiCP02x5bKAFp1EbAOoADmrsCAwEAAaOCA1QwggNQMIG5BgNVHREEgbEwga6gPQYFYEwBAwSgNAQyMzAwNjE5Njk1NzY1MTg1MDk2MzExNDU2MDcxNDk2MDAwMDAwMDAxODkwNDA0U1NQU0OgIQYFYEwBAwKgGAQWTUFSSUxFVVNBIEJBVElTVEEgWkFHT6AZBgVgTAEDA6AQBA4wMTYzMzM1OTAwMDE2OaAXBgVgTAEDB6AOBAwwMDAwMDAwMDAwMDCBFm1pY2hlbGVAc2NodWxsZS5jbnQuYnIwCQYDVR0TBAIwADAfBgNVHSMEGDAWgBQukerWbeWyWYLcOIUpdjQWVjzQPjAOBgNVHQ8BAf8EBAMCBeAwfwYDVR0gBHgwdjB0BgZgTAECAQwwajBoBggrBgEFBQcCARZcaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9kcGMvQUNfQ2VydGlzaWduX1JGQi9EUENfQUNfQ2VydGlzaWduX1JGQi5wZGYwggEWBgNVHR8EggENMIIBCTBXoFWgU4ZRaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9sY3IvQUNDZXJ0aXNpZ25SRkJHNC9MYXRlc3RDUkwuY3JsMFagVKBShlBodHRwOi8vaWNwLWJyYXNpbC5vdXRyYWxjci5jb20uYnIvcmVwb3NpdG9yaW8vbGNyL0FDQ2VydGlzaWduUkZCRzQvTGF0ZXN0Q1JMLmNybDBWoFSgUoZQaHR0cDovL3JlcG9zaXRvcmlvLmljcGJyYXNpbC5nb3YuYnIvbGNyL0NlcnRpc2lnbi9BQ0NlcnRpc2lnblJGQkc0L0xhdGVzdENSTC5jcmwwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMEMIGbBggrBgEFBQcBAQSBjjCBizBfBggrBgEFBQcwAoZTaHR0cDovL2ljcC1icmFzaWwuY2VydGlzaWduLmNvbS5ici9yZXBvc2l0b3Jpby9jZXJ0aWZpY2Fkb3MvQUNfQ2VydGlzaWduX1JGQl9HNC5wN2MwKAYIKwYBBQUHMAGGHGh0dHA6Ly9vY3NwLmNlcnRpc2lnbi5jb20uYnIwDQYJKoZIhvcNAQELBQADggIBABVAisyWa92iA2BtMa/HiQvUSbXdGRuCwH3B7dd2UunZtSRRgyHDv1CRtnGgKB7Hn7Yj8TXazH8/LnZbYrpdYXeqRm6V6yH77KSN/mRd26cuvFha08E8j4iDi04VGnk9BmTKRq8oJGl0ubBnV5RTMJfs3CaP0oITKWLA31BMSauayF2BYyx8PLjgkHcR7iov9SN95Ftg69jUP6Xirsjj3V3TQoq5zF0QwE+XT7IyOoVWWJap+0+sRIF83VdrnR7u4ZT1YjAYc/ljbnvhKYC/hNhbM9iV57kieU1ppgJKfxNV/jcXTfx+twl5A2bOWu42TM52tqGnymo7n4jTpFk/Ve5GldG7ztlIJa9qrLWaOK8S9/MPHvC7ZF305TiaU6GyFR7JA7pQjCf/yC5P9jJTNopVl40B3kfZuLrLIUVxTNCj+c/o/5D2KfnHapTPXbZF43vm0MyonW6FW0OrsSUBImAbnbxna1MU6Tf+YSCwiC4sQTJX8h/om1BrOTPzVkc0C53il6+Kt4ndqmU9TSRjhgDP9mhDhVMIoxnDcxbCChNfzeKmVFH0a55nu4d5UIZtrXzaTiazklLuOVnAWuBOwojXR3DgBoiJM09DPwb/pXyZytmjbwRLIz1RhEeDYRKjmjDEkIiKBROhUStNWOgL8KIreiI0jEU4tZ57j0AaAxc3");

        //Adicionando tag a X509Data
        X509Data.addContent(X509Certificate);
        //Adicionando tag a KeyInfo
        KeyInfo.addContent(X509Data);

        // </editor-fold>
        //Adicionando Tags de Signature
        Signature.addContent(SignedInfo);
        Signature.addContent(SignatureValue);
        Signature.addContent(KeyInfo);

        // </editor-fold>
        //Adiciona os 2 contents acima á TAG NFe
        NFe.addContent(infNFe);
        NFe.addContent(Signature);
// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="protNFe"> 
        Element protNFe = new Element("protNFe");
        protNFe.setText("");

        Element infProt = new Element("infProt");
        infProt.setText("");
        //Elementos dentro de infProt
        tpAmb = new Element("tpAmb");
        tpAmb.setText("1");

        Element verAplic = new Element("verAplic");
        verAplic.setText("SVRS20131104142903");

        Element chNFe = new Element("chNFe");
        chNFe.setText("42131101633359000169550010000002721100035982");

        Element dhRecbto = new Element("dhRecbto");
        dhRecbto.setText("2013-11-19T17:53:34");

        Element nProt = new Element("nProt");
        nProt.setText("342130125013028");

        Element digVal = new Element("digVal");
        digVal.setText("resl0EWzbkhrY5fCtL38DaOlfLY=");

        Element cStat = new Element("cStat");
        cStat.setText("100");

        Element xMotivo = new Element("xMotivo");
        xMotivo.setText("Autorizado o uso da NF-e");

        //Adicionando Campos acima ao Content infProt
        infProt.addContent(tpAmb);
        infProt.addContent(verAplic);
        infProt.addContent(chNFe);
        infProt.addContent(dhRecbto);
        infProt.addContent(nProt);
        infProt.addContent(digVal);
        infProt.addContent(cStat);
        infProt.addContent(xMotivo);

        protNFe.addContent(infProt);

        nfeProc.addContent(NFe);
        nfeProc.addContent(protNFe);

        // </editor-fold>
// </editor-fold>
        //classe para imprimir / gerar o xml
        XMLOutputter xout = new XMLOutputter();
        try {
            //criando o arquivo de saida
            BufferedWriter arquivo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LOCALHOST + NOMEDOARQUIVO + ".xml"), "UTF-8"));
            //imprimindo o xml no arquivo
            xout.output(documento, arquivo);
            JOptionPane.showMessageDialog(null, "Arquivo XML criado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Gerar o arquivo XML!" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ListCriptografia lerXMLCriptografia() {
        ListCriptografia listCriptografia = new ListCriptografia();

        Document doc = null;
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(LOCALHOST + NOMEDOARQUIVO + ".xml");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler Arquivo XML da classe lerXMLCriptografia(): \n" + e.getMessage());
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

    public static void main(String[] args) {
        gravarXML_NFE();
        //lerXMLCriptografia();
        //lerXMLMensagem();
    }

}
