<?php

class ViewHome extends ViewPadrao {

    /**
     * @var ModelFilme
     */
    protected $Model;
    protected $listaFilme;
    protected $listaAtor;

    function getModel() {
        return $this->Model;
    }

    function getListaFilme() {
        return $this->listaFilme;
    }

    function getListaAtor() {
        return $this->listaAtor;
    }

    function setModel(ModelFilmeAtor $Model) {
        $this->Model = $Model;
    }

    function setListaFilme($listaFilme) {
        $this->listaFilme = $listaFilme;
    }

    function setListaAtor($listaAtor) {
        $this->listaAtor = $listaAtor;
    }

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Catálogo</h2>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaConsulta() {
        $sHTML = '';
        $sHTML .= '<table class="tabela_consulta">';

        if (count($this->listaFilme) > 0) {
            foreach ($this->listaFilme as $indice => /* @var $oModelFilme ModelFilme */ $oModelFilme) {
                //var_dump($oModelFilme);
                $sHTML.= '<tr>';
                $sHTML.= '<b>Título:</b>' . $oModelFilme['filtitulo'] . '<br><br>';
                $sHTML.= '<b>Estréia:</b>' . $oModelFilme['fildataestreia'] . '<br><br>';
                $sHTML.= '<b>Categoria:</b>' . $oModelFilme['catnome'] . '<br><br>';


                $this->setListaAtor($this->getListaAtor());
                $aListaAtores = $oModelFilme ['atores'];
                /* Controle das virgulas dos atores */
                $contaLinha = 0;
                $contaArray = 0;
                if (count($aListaAtores) > 0) {
                    foreach ($aListaAtores as $aValor) {
                        $contaLinha++;
                    }
                }
                if (count($aListaAtores) > 0) {
                    foreach ($aListaAtores as $aValor) {
                        $contaArray++;
                        if (($contaLinha > 1) && ($contaArray < $contaLinha)) {
                            $sHTML.= '<b>Ator:</b>' . $aValor['atonome'] . ' <b>Papel:</b>' . $aValor['atfpapel'] . ' , ';
                        } else if ($contaArray == $contaLinha) {
                            $sHTML.= '<b>Ator:</b>' . $aValor['atonome'] . ' <b>Papel:</b>' . $aValor['atfpapel'];
                        }
                    }
                } else {
                    $sHTML.= 'Não existe ator relacionado á este filme';
                }
                $sHTML.= '<br><br>';
                $sHTML.= '<b>Sinopse:</b>' . $oModelFilme['filsinopse'] . '<br><br>';
                $sHTML.= '<object class = "classVideo" width = "350" height = "250" data = "' . $oModelFilme['filtrailer'] . '"> </object>';
                $sHTML.= '</tr>';
                $sHTML .= '<hr>';
                $sHTML .= '<hr>';
            }
        } else {
            $sHTML .= 'Não existe filmes cadastrados';
        }
        $sHTML .= '</table>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

}
