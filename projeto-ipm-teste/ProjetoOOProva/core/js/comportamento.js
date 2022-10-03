function invokeFormulario(sPagina, sAcao, xChave) {
    var oForm = document.createElement('form');
    oForm.setAttribute('action', '?pagina=' + sPagina + '&acao=' + sAcao + '&chave=' + xChave);
    oForm.setAttribute('method', 'POST');
    oForm.submit();
}

function excluiPessoa(iCodigo) {
    if (window.confirm("Deseja apagar o registro?")) {
        document.getElementById('pescodigo').value = iCodigo;
        document.getElementById('acao').value = 'del';
        document.getElementById('formularioPessoa').submit();
    }
}
function validaFormPessoa() {
    var oCodigo = document.getElementById('pescodigo').value;
    var oNome = document.getElementById('pesnome').value;
    var oDatanascimento = document.getElementById('pesdatanascimento').value;
    var oPesbairro = document.getElementById('pesbairro').value;
    var opescidade = document.getElementById('pescidade').value;
    var opesestado = document.getElementById('pesestado').value;

    if (oCodigo === '') {
        alert('Preencha o código!');
        return false;
    } else if (oNome === '') {
        alert('Preencha o nome!');
        return false;
    } else if (oDatanascimento === '') {
        alert('Preencha a datas de nascimento!');
        return false;
    } else if (oPesbairro === '') {
        alert('Preencha o bairro!');
        return false;
    } else if (opescidade === '') {
        alert('Preencha a cidade!');
        return false;
    } else if (opesestado === '') {
        alert('Preencha o estado!');
        return false;
    } else {
        document.getElementById('formularioPessoa').submit();
    }
}

function verificaEstadoICMS() {
    var opcao = document.getElementById("pesestado").value;
    opcao = parseInt(opcao);

    if ((opcao == 2) || (opcao == 3) || (opcao == 4)) {
        //mostra o label e o campo de pesicms se torna visivel
        var labelNome = document.getElementById('label_icms');
        labelNome.innerHTML = 'ICMS:';
        //altera para visivel o input da tela
        var oICMS = document.getElementById('pesicms');
        oICMS.setAttribute('type', 'text');
    } else {
        //mostra o label e o campo de pesicms se torna visivel
        var labelNome = document.getElementById('label_icms');
        labelNome.innerHTML = '';
        //altera para visivel o input da tela
        var oICMS = document.getElementById('pesicms');
        oICMS.setAttribute('type', 'hidden');
    }
}