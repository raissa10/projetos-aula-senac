<?php

class ControllerCategoria extends ControllerPadrao {

    public function processa() {
        if (Redirecionador::getParametro('acao')) {
            $oPersistenciaCategoria = new PersistenciaCategoria();
            $oPersistenciaCategoria->setModel($this->setModelFromView());
			
			if ((Redirecionador::getParametro('acao')) == 'inc') {
                if ($oPersistenciaCategoria->get()) {
                    $this->processaAlteracao();
                } else {
                    $this->processaInclusao();
                }
            }else if ((Redirecionador::getParametro('acao')) == 'verificaexclusao'){				
				 //verifica se existe dependencia da tabela antes de excluir            
				if ($oPersistenciaCategoria->getCategoriaFromFilme()) {
					echo ('<h1><p class="mensagem_operacao_alterar_excluir">Antes exclua os filmes com esta categoria!<br>');
					$oModeloCategoria = new ModelCategoria();
					$oModeloCategoria->setCodigo(Redirecionador::getParametro('catcodigo'));
					$oModeloCategoria->setNome(Redirecionador::getParametro('catnome'));
					echo ('Categoria: '.$oModeloCategoria->getNome().'</p></h1>');
				}else{
					$this->processaExclusao();
				}	
			}
        }
        $this->MontaTela();
    }

    public function setModelFromView() {
        $oModeloCategoria = new ModelCategoria();
        $oModeloCategoria->setCodigo(Redirecionador::getParametro('catcodigo'));
        $oModeloCategoria->setNome(Redirecionador::getParametro('catnome'));
        return $oModeloCategoria;
    }

    public function processaInclusao() {
        $oPersistenciaCategoria = new PersistenciaCategoria();
        $oPersistenciaCategoria->setModel($this->setModelFromView());
        $oPersistenciaCategoria->insere();
    }

    public function processaAlteracao() {
        $oPersistenciaCategoria = new PersistenciaCategoria();
        $oPersistenciaCategoria->setModel($this->setModelFromView());
        $oPersistenciaCategoria->altera();
    }

    public function processaExclusao() {
        $oPersistenciaCategoria = new PersistenciaCategoria();
        $oPersistenciaCategoria->setModel($this->setModelFromView());
        $oPersistenciaCategoria->exclui();
    }

    public function MontaTela() {
        $oPersistenciaCategoria = new PersistenciaCategoria();
        $aListaCategorias = $oPersistenciaCategoria->getAll();

        $oViewCategoria = new ViewCategoria();
        $oViewCategoria->setListaModel($aListaCategorias);
        $oViewCategoria->montaTela();
    }

}
