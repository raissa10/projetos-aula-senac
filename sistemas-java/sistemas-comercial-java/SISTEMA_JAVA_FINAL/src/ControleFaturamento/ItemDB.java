package ControleFaturamento;

import ModelCadastro.Tributacao;
import ModeloFaturamento.Item;
import Principal.ConexaoFirebird;
import Principal.MetodosGlobais;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Gelvazio
 */
public class ItemDB extends MetodosGlobais {

    //Métodos
    //Generator
    //Alterar
    //Inserir
    //Excluir
    //Listar Todos os itens da Venda
    private static final String sqlExcluir
            = "DELETE FROM ITENS_ORC WHERE ITENS_ORC.CD_FILIAL =? AND ITENS_ORC.CD_MOVIMENTO=? AND ITENS_ORC.CD_SEQ_ITE_PRO=?";
    private static final String sqlInserir
            = "INSERT INTO ITENS_ORC "
            + "(CD_FILIAL, "
            + "CD_MOVIMENTO, "
            + "CD_SEQ_ITE_PRO, "
            + "CD_REFER_PRO, "
            + "QT_ITE_PRO, "
            + "VL_CUS_ITE_PRO, "
            + "VL_VEN_ITE_PRO, "
            + "VL_REAL_ITE_PRO, "
            + "TX_ICMS, "
            + "VL_BASE_ICM, "
            + "VL_ICM, "
            + "TX_ICMS_SUBSTITUICAO, "
            + "VL_MVA, "
            + "VL_BASE_ICM_SUBSTITUICAO, "
            + "VL_ICM_SUBSTITUICAO,"
            + "TX_ISS, "
            + "VL_BASE_ISS, "
            + "VL_ISS, "
            + "CST_IPI, "
            + "VL_BASE_IPI, "
            + "TX_IPI, "
            + "VL_IPI, "
            + "CST_PIS, "
            + "VL_BASE_PIS, "
            + "TX_PIS, "
            + "VL_PIS, "
            + "CST_COFINS, "
            + "VL_BASE_COFINS, "
            + "TX_COFINS, "
            + "VL_COFINS, "
            + "DT_EMI_DOC, "
            + "CD_TIPO_DOC, "
            + "AB_UNIDADE, "
            + "CD_VENDEDOR, "
            + "CD_USUARIO, "
            + "DT_ALT, "
            + "HR_ALT, "
            + "DT_CAD, "
            + "HR_CAD, "
            + "CFOP, "
            + "VL_DESCONTO, "
            + "VL_ACRESCIMO, "
            + "CD_GRUPO_FISCAL, "
            + "CD_CST, "
            + "VL_PESO_LIQUIDO, "
            + "VL_PESO_BRUTO, "
            + "VL_VOLUME, "
            + "FG_SITUACAO, "
            + "VL_RATEADO, "
            + "VL_FRETE, "
            + "VL_IMPOSTOS) "
            + "VALUES "
            + "(?, ?, ?, ?, ?, "
            + "?, ?, ?, ?, ?, "
            + "?, ?, ?, ?, ?, ?, "
            + "?, ?,?, ?, ?, "
            + "?, ?, ?, ?, ?,"
            + "?, ?, ?, ?, ?,"
            + "?, ?, ?, ?, "
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?, ?, ?, ?, ?,"
            + "?, ?, ?, ?, ?,"
            + "?, ?);";

    private static final String sqlAlterar
            = "UPDATE ITENS_ORC SET CD_REFER_PRO = ?,"
            + "    QT_ITE_PRO = ?,"
            + "    VL_CUS_ITE_PRO = ?,"
            + "    VL_VEN_ITE_PRO = ?,"
            + "    VL_REAL_ITE_PRO = ?,"
            + "    TX_ICMS = ?,"
            + "    VL_BASE_ICM = ?,"
            + "    VL_ICM = ?,"
            + "    TX_ICMS_SUBSTITUICAO = ?,"
            + "    VL_MVA = ?,"
            + "    VL_BASE_ICM_SUBSTITUICAO = ?,"
            + "    VL_ICM_SUBSTITUICAO = ?,"
            + "    TX_ISS = ?,"
            + "    VL_BASE_ISS = ?,"
            + "    VL_ISS = ?,"
            + "    CST_IPI = ?,"
            + "    VL_BASE_IPI = ?,"
            + "    TX_IPI = ?,"
            + "    VL_IPI = ?,"
            + "    CST_PIS = ?,"
            + "    VL_BASE_PIS = ?,"
            + "    TX_PIS = ?,"
            + "    VL_PIS = ?,"
            + "    CST_COFINS =?,"
            + "    VL_BASE_COFINS = ?,"
            + "    TX_COFINS = ?,"
            + "    VL_COFINS = ?,"
            + "    DT_EMI_DOC = ?,"
            + "    CD_TIPO_DOC = ?,"
            + "    AB_UNIDADE = ?,"
            + "    CD_VENDEDOR =?,"
            + "    CD_USUARIO = ?,"
            + "    DT_ALT =?,"
            + "    HR_ALT = ?,"
            + "     DT_CAD = ?,"
            + "     HR_CAD = ?,"
            + "    CFOP = ?,"
            + "    VL_DESCONTO = ?,"
            + "    VL_ACRESCIMO = ?,"
            + "    CD_GRUPO_FISCAL = ?,"
            + "    CD_CST = ?,"
            + "    VL_PESO_LIQUIDO = ?,"
            + "    VL_PESO_BRUTO = ?,"
            + "    VL_VOLUME = ?,"
            + "    FG_SITUACAO = ?,"
            + "    VL_RATEADO = ?,"
            + "    VL_FRETE = ?,"
            + "    VL_IMPOSTOS = ?"
            + "WHERE (CD_FILIAL = ?) AND (CD_MOVIMENTO = ?) AND (CD_SEQ_ITE_PRO = ?);";

    private static final String sqlBuscaDados
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    ITENS_ORC         "
            + "WHERE                 "
            + "    CD_FILIAL = ?     "
            + "    AND               "
            + "    CD_MOVIMENTO=?    "
            + "    AND               "
            + "    CD_SEQ_ITE_PRO=?  ";
    //Busca dados para a venda
    private static final String sqlBuscaDadosVendaItem1
            = "SELECT\n"
            + "    --CD_FILIAL                 --USAR MÉTODO COM SQL UNICO QUE RETORNA DADOS DA FILIAL\n"
            + "    --CD_MOVIMENTO              --ESTA OK\n"
            + "    --CD_SEQ_ITE_PRO            --ESTA OK\n"
            + "    --CD_REFER_PRO              --ESTA OK\n"
            + "    --QT_ITE_PRO                --ESTA OK\n"
            + "    SUB_TAB_PRECO.VL_CUSTO    VL_CUS_ITE_PRO,--PEGA DO SQL QUE BUSCA DADOS DO PRODUTO\n"
            + "    SUB_TAB_PRECO.VL_VENDA    VL_VEN_ITE_PRO,--PEGA DO SQL QUE BUSCA DADOS DO PRODUTO\n"
            + "    --VL_REAL_ITE_PRO         --ESTA OK É CALCULADO NA TELA\n"
            + "    --TX_ICMS                 --DEVERA TER UM METODO ESPECIFICO BUSCADADOSTIPONOTA\n"
            + "    --VL_BASE_ICM               --ESTA OK\n"
            + "    --VL_ICM                    --ESTA OK\n"
            + "    --TX_ICMS_SUBSTITUICAO      --DEVERA TER UM METODO ESPECIFICO BUSCADADOSTIPONOTA\n"
            + "    NCMSH.VL_MVA,              --PEGA DO SQL QUE BUSCA DADOS DO PRODUTO\n"
            + "    --VL_BASE_ICM_SUBSTITUICAO  --ESTA OK\n"
            + "    --VL_ICM_SUBSTITUICAO       --ESTA OK\n"
            + "    --TX_ISS                  --PEGA DO SQL QUE BUSCA DADOS DO PRODUTO -TEM DE FAZER  NO CAD PRODUTO\n"
            + "    --VL_BASE_ISS               --ESTA OK\n"
            + "    --VL_ISS                    --ESTA OK\n"
            + "    --CST_IPI                   --DEVERA TER UM METODO ESPECIFICO BUSCADADOSTIPONOTA\n"
            + "    --VL_BASE_IPI               --ESTA OK\n"
            + "    --TX_IPI                    --PEGA DO SQL QUE BUSCA DADOS DO PRODUTO TEM DE FAZER  NO CAD PRODUTO\n"
            + "    --VL_IPI                    --ESTA OK\n"
            + "    --CST_PIS                   --USAR MÉTODO COM SQL UNICO QUE RETORNA DADOS DA FILIAL\n"
            + "    --VL_BASE_PIS               --ESTA OK\n"
            + "    --TX_PIS                    --PEGA DO SQL QUE BUSCA DADOS DO TIPO DE NOTA\n"
            + "    --VL_PIS                    --ESTA OK\n"
            + "    --CST_COFINS                --PEGA DO SQL QUE BUSCA DADOS DO TIPO DE NOTA\n"
            + "    --VL_BASE_COFINS            --ESTA OK\n"
            + "    --TX_COFINS                 --USAR MÉTODO COM SQL UNICO QUE RETORNA DADOS DA FILIAL\n"
            + "    --VL_COFINS                 --ESTA OK\n"
            + "    -- DT_EMI_DOC                --ESTA OK\n"
            + "    --CD_TIPO_DOC               --PEGA DA TELA\n"
            + "    UNIDADE_MEDIDA.DS_SIGLA AB_UNIDADE,--PEGA DO SQL QUE BUSCA DADOS DO PRODUTO\n"
            + "    --CD_VENDEDOR               --PEGA DA TELA\n"
            + "    --CD_USUARIO                --PEGA DO SQL QUE BUSCA DADOS DO USUARIO DA VENDA\n"
            + "    --DT_ALT                    --ESTA OK\n"
            + "    --HR_ALT                    --ESTA OK\n"
            + "    --DT_CAD                    --ESTA OK\n"
            + "    --HR_CAD                    --ESTA OK\n"
            + "    --CFOP                      --PEGA DO SQL QUE BUSCA DADOS DO TIPO DE NOTA\n"
            + "    --VL_DESCONTO               --ESTA OK\n"
            + "    --VL_ACRESCIMO              --ESTA OK\n"
            + "    PRODUTO_SIMPLES.CD_GP_FISCAL CD_GRUPO_FISCAL--PEGA DO SQL QUE BUSCA DADOS DO PRODUTO\n"
            + "    --CD_CST                    --PEGA DO SQL QUE BUSCA DADOS DO TIPO DE NOTA\n"
            + "    --VL_PESO_LIQUIDO           --ESTA OK\n"
            + "    --VL_PESO_BRUTO             --ESTA OK\n"
            + "    --VL_VOLUME                 --ESTA OK\n"
            + "    --FG_SITUACAO               --ESTA OK\n"
            + "    --VL_RATEADO                --ESTA OK\n"
            + "    --VL_FRETE                  --ESTA OK\n"
            + "    --VL_IMPOSTOS,               --ESTA OK\n"
            + "     produto_simples.dt_cad,"
            + "     produto_simples.hr_cad"
            + ""
            + "FROM\n"
            + "    PRODUTO_SIMPLES\n"
            + "LEFT OUTER JOIN SUB_TAB_PRECO ON\n"
            + "    SUB_TAB_PRECO.CD_REF=PRODUTO_SIMPLES.CD_REF\n"
            + "LEFT OUTER JOIN NCMSH ON\n"
            + "    NCMSH.CD_NCMSH=PRODUTO_SIMPLES.CD_NCM_SH\n"
            + "LEFT OUTER JOIN UNIDADE_MEDIDA ON\n"
            + "    UNIDADE_MEDIDA.CD_UNIDADE=PRODUTO_SIMPLES.CD_UNIDADE_MEDIDA\n"
            + "WHERE\n"
            + "    (PRODUTO_SIMPLES.CD_REF=?);";
    ///sql ABAIXO QUE BUSCA OS DADOS NO SISFATURA ORIGINAL
/*
     select
     produto.cd_prod,
     produto.cd_ref,
     produto.ds_prod,
     produto.tx_ipi,
     produto.fg_tip_cad_pro,
     produto.ab_unidade,
     produto.qt_min,
     produto.qt_max,
     produto.vl_peso_liq,
     produto.vl_peso_bru,
     produto.fg_ativo,
     produto.cd_gp_fiscal,
     produto.fg_usa_balanca,
     produto.cd_ncm_sh,
     produto.tx_limite_desconto,
     produto.cd_tab_desc_comissao,
     produto.cd_tabela_desc_por_margem_lucro,
     produto.fg_ippt,
     produto.fg_retem_pis_cofins_cssl,
     produto.fg_retem_inss,
     produto.fg_servico_fora_estabelecimento,
     produto.tx_op_custo,
     produto.fg_usa_grade_na_venda,
     produto.ds_codigo_fci,
     produto.cd_fabrica,
     produto.cd_anp_produto,
     produto.cd_indicador_exigibilidade,
     produto.fg_incentivo_fiscal,
     produto.nr_processo_susp_exigibilidade,
     produto.cd_grupo,

     produto_mva.vl_mva tx_mva_cad_prod,
     produto_mva.fg_desconto_mva fg_desconto_mva_cad_prod,

     cor.ds_cor,

     estoque.qt_prod ,
     estoque.qt_prod qtde,

     cst.fg_substituicao,
     cst.fg_nao_incidencia,
     cst.fg_isento,
     cst.fg_csosn,

     cst_uf.fg_substituicao fg_substituicao_uf,
     cst_uf.fg_nao_incidencia fg_nao_incidencia_uf,
     cst_uf.fg_isento fg_isento_uf,
     cst_uf.fg_csosn fg_csosn_uf,
     cst_uf.cd_sit_tri cd_cst_uf,
    
     gp_fiscal.vl_reducao_bc,
     gp_fiscal.tx_markup,
     gp_fiscal.cd_gp_fiscal cd_gp_fiscal1, /* campo p/ validacao */
    /*    gp_fiscal.ds_formula_mva_ajustado,
     gp_fiscal.tx_pis_gp_fiscal,
     gp_fiscal.tx_cofins_gp_fiscal,
     gp_fiscal.cd_cst_pis_diferenciado,
     gp_fiscal.cd_cst_cofins_diferenciado,
     gp_fiscal.tx_diferenciada_ttd,

     gp_fiscal_markup.tx_icms_interno tx_icms_interno_destino,
     gp_fiscal_markup.tx_icms_interestadual tx_icms_interestadual_destino,
     gp_fiscal_markup.cd_estado cd_estado_destino, /* campo p/ validacao */

    /*    gp_fiscal_markup_origem.tx_icms_interno tx_icms_interno_origem,
     gp_fiscal_markup_origem.tx_icms_interestadual tx_icms_interestadual_origem,
     gp_fiscal_markup_origem.cd_estado cd_estado_origem, /* campo p/ validacao */

    /*    itens_mva_por_ncmsh.vl_mva vl_mva_uf,
     itens_mva_por_ncmsh.fg_desconto_mva fg_desconto_mva_uf,

     sub_tab_preco.vl_custo,
     sub_tab_preco.vl_venda,
     sub_tab_preco.vl_revenda,
     sub_tab_preco.vl_especial,
     sub_tab_preco.vl_tabela,

     coalesce(promocao.vl_venda, 0) vl_promocao,

     tipo_nota_fiscal.cd_cst,
     tipo_nota_fiscal.cd_cfop_estadual,
     tipo_nota_fiscal.cd_cfop_interestadual,
     tipo_nota_fiscal.tp_calculo_base,
     tipo_nota_fiscal.tp_calculo_icms,
     tipo_nota_fiscal.tp_calculo_base_i,
     tipo_nota_fiscal.tp_calculo_icms_i,
     tipo_nota_fiscal.cd_tipo cd_tipo1, /* campo p/ validacao */
    /*    tipo_nota_fiscal.cd_cst_cofins,
     tipo_nota_fiscal.tp_calculo_base_st,
     tipo_nota_fiscal.tp_calculo_icms_st,
     tipo_nota_fiscal.tp_calculo_base_i_st,
     tipo_nota_fiscal.tp_calculo_icms_i_st,
     tipo_nota_fiscal.cd_cst_ipi,
     tipo_nota_fiscal.cd_cst_pis,
     tipo_nota_fiscal.tx_icms_diferido,
     tipo_nota_fiscal.tx_icms_org_desonerado,
     tipo_nota_fiscal.cd_motivo_desoneracao,
     tipo_nota_fiscal_uf.cd_cst cd_cst_estado,
     tipo_nota_fiscal_uf.cd_cfop cd_cfop_estado,
     tipo_nota_fiscal_uf.bl_obs bl_obs_estado,
     produto_composicao.cd_ref_composicao,
     produto.vl_volume,
     produto.ds_marca,
     gp_fiscal.tx_base_pis_cofins,
     cst_pis_cofins.fg_substituicao fg_substituicao_pis,
     cst_pis_cofins.fg_isento fg_isento_pis,
     cst_pis_cofins.fg_nao_incidencia fg_nao_incidencia_pis,
     mva_por_ncmsh.vl_mva,
     mva_por_ncmsh.fg_desconto_mva,
     unidade_medida.fg_venda_fracionaria,
     produto.nr_pontos,
     lista_servicos_nfem.cd_cnae,
     lista_servicos_nfem.cd_servico,
     lista_servicos_nfem.vl_aliquota,
     taxa_impostos.tx_alicota_nacional,
     taxa_impostos.tx_alicota_internacional,

     produto_pauta_preco.vl_pauta,

     grupo.cd_conta_receita,
     grupo.cd_conta_despesa,

     tam.ds_tam,

     grupo_pis_cofins.cd_tributado cd_tributado_pis_cofins_grupo,
     grupo_pis_cofins.cd_st cd_st_pis_cofins_grupo,
     grupo_pis_cofins.cd_cst_pis cd_cst_pis_grupo,
     grupo_pis_cofins.tx_pis tx_pis_grupo,
     grupo_pis_cofins.tx_reducao_bc_pis tx_reducao_bc_pis_grupo,
     grupo_pis_cofins.cd_cst_cofins cd_cst_cofins_grupo,
     grupo_pis_cofins.tx_cofins tx_cofins_grupo,
     grupo_pis_cofins.tx_reducao_bc_cofins tx_reducao_bc_cofins_grupo,
     grupo_pis_cofins.cd_info_complementar cd_info_complementar_pis_cofins_grupo
     from
     produto
     left outer join estoque on
     (estoque.cd_filial = ?         )
     and
     (estoque.cd_ref = produto.cd_ref)
     inner join sub_tab_preco on
     (sub_tab_preco.cd_tabela = ?            )
     and
     (sub_tab_preco.cd_ref = produto.cd_ref)
     inner join gp_fiscal on
     (gp_fiscal.cd_gp_fiscal = ?            )
     left outer join gp_fiscal_markup on
     (gp_fiscal_markup.cd_gp_fiscal = ?            )
     and
     (gp_fiscal_markup.cd_estado = ?             )
     left outer join gp_fiscal_markup gp_fiscal_markup_origem on
     (gp_fiscal_markup_origem.cd_gp_fiscal = ?            )
     and
     (gp_fiscal_markup_origem.cd_estado = ?                )
     left outer join tipo_nota_fiscal on
     (tipo_nota_fiscal.cd_tipo = ?            )
     and
     (tipo_nota_fiscal.cd_gp_fiscal = ?            )
     and
     (tipo_nota_fiscal.tp_consumo = ?          )
     left outer join tipo_nota_fiscal_uf on
     (tipo_nota_fiscal_uf.cd_tipo = ?            )
     and
     (tipo_nota_fiscal_uf.cd_gp_fiscal = ?            )
     and
     (tipo_nota_fiscal_uf.tp_consumo = ?          )
     and
     (tipo_nota_fiscal_uf.cd_estado = ?             )
     left outer join sit_trib cst on
     (cst.cd_sit_tri = tipo_nota_fiscal.cd_cst)
     left outer join sit_trib cst_uf on
     (cst_uf.cd_sit_tri = tipo_nota_fiscal_uf.cd_cst)
     left outer join cst_pis_cofins on
     cst_pis_cofins.cd_cst_pis_cofins = tipo_nota_fiscal.cd_cst_pis
     left outer join mva_por_ncmsh on
     mva_por_ncmsh.cd_ncm_sh = produto.cd_ncm_sh
     left outer join produto_composicao on
     produto_composicao.cd_ref = produto.cd_ref
     and
     produto_composicao.sq_item = 1
     left outer join unidade_medida on
     unidade_medida.cd_unidade = produto.cd_unidade
     left outer join lista_servicos_nfem on
     produto.cd_servico = lista_servicos_nfem.cd_cnae
     left outer join itens_mva_por_ncmsh on
     ((itens_mva_por_ncmsh.cd_ncm_sh = produto.cd_ncm_sh)
     and
     (itens_mva_por_ncmsh.cd_estado = ?             ))
     left outer join cor on
     cor.cd_cor = produto.cd_cor
     left outer join promocao on
     (promocao.cd_filial = ?         )
     and
     (promocao.cd_ref = produto.cd_ref)
     and
     ((cast('now' as date) >= promocao.dt_inicial) and (cast('now' as date) <= promocao.dt_final))
     left outer join taxa_impostos on
     taxa_impostos.cd_excecao = produto.cd_excecao_fiscal
     and
     taxa_impostos.cd_ncm_nbs = produto.cd_ncm_sh
     and
     taxa_impostos.cd_tabela = produto.fg_tip_cad_pro
     left outer join produto_pauta_preco on
     (produto_pauta_preco.cd_referencia = produto.cd_ref)
     and
     (produto_pauta_preco.ds_estado = ?             )
     left outer join grupo on
     grupo.cd_grupo = produto.cd_grupo
     left outer join tam on
     tam.cd_tam = produto.cd_tam
     left outer join produto_mva on
     produto_mva.cd_referencia = produto.cd_ref
     and
     produto_mva.cd_estado = ?             
     left outer join grupo_pis_cofins on
     grupo_pis_cofins.cd_grupo_pis_cofins = produto.cd_grupo_pis_cofins
     where
     (produto.cd_ref = ?   )
     */
    //ABAIXO SQL MONTADO PARA ITENS DESTE SISTEMA
    /*
     SELECT
     --LISTA DE TABELAS CRIADAS PARA A EXECUÇÃO DESTE SQL
     --CST_PIS_COFINS -FAZER TELA DE CADASTRO
     --CST_IPI        -FAZER TELA DE CADASTRO
     --SITUACAO_TRIBUTARIA-FAZER TELA DE CADASTRO
     PRODUTO_SIMPLES.CD_PROD,
     PRODUTO_SIMPLES.CD_REF,
     PRODUTO_SIMPLES.DS_PROD,
     PRODUTO_SIMPLES.TX_IPI,
     UNIDADE_MEDIDA.DS_SIGLA,
     PRODUTO_SIMPLES.FG_ATIVO,
     PRODUTO_SIMPLES.CD_GP_FISCAL,
     PRODUTO_SIMPLES.CD_NCM_SH,
     PRODUTO_SIMPLES.CD_FABRICA,
     NCMSH.VL_MVA TX_MVA_CAD_NCMSH,
     ESTOQUE.QUANTIDADE QTDE,
     CST.FG_SUBSTITUICAO,
     CST.FG_NAO_INCIDENCIA,
     CST.FG_ISENTO,
     CST.FG_CSOSN,
     GRUPO_FISCAL.CD_GRUPO_FISCAL CD_GP_FISCAL1, -- CAMPO P/ VALIDACAO 

     MARKUP.TX_ICMS_INTERNO TX_ICMS_INTERNO_DESTINO,
     MARKUP.TX_ICMS_INTERESTADUAL TX_ICMS_INTERESTADUAL_DESTINO,
     MARKUP.CD_ESTADO CD_ESTADO_DESTINO, -- CAMPO P/ VALIDACAO --

     GP_FISCAL_MARKUP_ORIGEM.TX_ICMS_INTERNO TX_ICMS_INTERNO_ORIGEM,
     GP_FISCAL_MARKUP_ORIGEM.TX_ICMS_INTERESTADUAL TX_ICMS_INTERESTADUAL_ORIGEM,
     GP_FISCAL_MARKUP_ORIGEM.CD_ESTADO CD_ESTADO_ORIGEM, -- CAMPO P/ VALIDACAO 


     SUB_TAB_PRECO.VL_CUSTO,
     SUB_TAB_PRECO.VL_VENDA,
     SUB_TAB_PRECO.VL_PROMOCAO,
     SUB_TAB_PRECO.VL_ESPECIAL,
     SUB_TAB_PRECO.VL_CUSTO_MED,

     TIPO_NOTA_FISCAL.CD_CST,
     TIPO_NOTA_FISCAL.CD_CFOP_ESTADUAL,
     TIPO_NOTA_FISCAL.CD_CFOP_INTERESTADUAL,
     TIPO_NOTA_FISCAL.TP_CALCULO_BASE,
     TIPO_NOTA_FISCAL.TP_CALCULO_ICMS,
     TIPO_NOTA_FISCAL.TP_CALCULO_BASE_I,
     TIPO_NOTA_FISCAL.TP_CALCULO_ICMS_I,
     TIPO_NOTA_FISCAL.CD_TIPO CD_TIPO1, --CAMPO P/ VALIDACAO 
     TIPO_NOTA_FISCAL.CD_CST_COFINS,
     TIPO_NOTA_FISCAL.TP_CALCULO_BASE_ST,
     TIPO_NOTA_FISCAL.TP_CALCULO_ICMS_ST,
     TIPO_NOTA_FISCAL.TP_CALCULO_BASE_I_ST,
     TIPO_NOTA_FISCAL.TP_CALCULO_ICMS_I_ST,
     TIPO_NOTA_FISCAL.CD_CST_IPI,
     TIPO_NOTA_FISCAL.CD_CST_PIS,

     CST_PIS_COFINS.FG_SUBSTITUICAO FG_SUBSTITUICAO_PIS,
     CST_PIS_COFINS.FG_ISENTO FG_ISENTO_PIS,
     CST_PIS_COFINS.FG_NAO_INCIDENCIA FG_NAO_INCIDENCIA_PIS

     FROM
     PRODUTO_SIMPLES
     LEFT OUTER JOIN ESTOQUE ON
     (ESTOQUE.CD_FILIAL = 1   )
     AND
     (ESTOQUE.CD_REF = PRODUTO_SIMPLES.CD_REF)
     INNER JOIN SUB_TAB_PRECO ON
     (SUB_TAB_PRECO.CD_REF = PRODUTO_SIMPLES.CD_REF)

     INNER JOIN GRUPO_FISCAL  ON
     (GRUPO_FISCAL.CD_GRUPO_FISCAL = 1            )

     LEFT OUTER JOIN MARKUP ON
     (MARKUP.CD_GRUPO_FISCAL = 1           )
     AND
     (MARKUP.CD_ESTADO = ?           )

     LEFT OUTER JOIN MARKUP GP_FISCAL_MARKUP_ORIGEM ON
     (GP_FISCAL_MARKUP_ORIGEM.CD_GRUPO_FISCAL = 1            )
     AND
     (GP_FISCAL_MARKUP_ORIGEM.CD_ESTADO = ?             )

     LEFT OUTER JOIN TIPO_NOTA_FISCAL ON
     (TIPO_NOTA_FISCAL.CD_TIPO = ?            )
     AND
     (TIPO_NOTA_FISCAL.CD_GP_FISCAL = ?            )
     AND
     (TIPO_NOTA_FISCAL.TP_CONSUMO = 1         )


     LEFT OUTER JOIN SITUACAO_TRIBUTARIA CST ON
     (CST.CD_SITUACAO_TRIBUTARIA = TIPO_NOTA_FISCAL.CD_CST)

     LEFT OUTER JOIN CST_PIS_COFINS ON
     CST_PIS_COFINS.CD_CST_PIS_COFINS = TIPO_NOTA_FISCAL.CD_CST_PIS

     LEFT OUTER JOIN NCMSH ON
     NCMSH.CD_NCMSH = PRODUTO_SIMPLES.CD_NCM_SH


     LEFT OUTER JOIN UNIDADE_MEDIDA ON
     UNIDADE_MEDIDA.CD_UNIDADE = PRODUTO_SIMPLES.CD_UNIDADE_MEDIDA

     WHERE
     (PRODUTO_SIMPLES.CD_REF = 1  )

    
     */
//SQL Completo    Oficial Final
    private static final String sqlBuscaDadosVendaItem
            = "  SELECT                                                                                                    --\n"
            + "		FILIAL.CD_FILIAL,                                                                                  --\n"
            + "		CAST( SUB_TAB_PRECO.VL_CUSTO AS NUMERIC (16,2))VL_CUSTO,                                     --\n"
            + "		CAST( SUB_TAB_PRECO.VL_VENDA AS NUMERIC (16,2)) VL_VENDA,                                    --\n"
            + "		CAST(GP_FISCAL_MARKUP_DESTINO.TX_ICMS_INTERNO AS NUMERIC (16,2)) TX_ICMS_INTERNO_DESTINO,                            --\n"
            + "		CAST(GP_FISCAL_MARKUP_DESTINO.TX_ICMS_INTERESTADUAL AS NUMERIC (16,2))TX_ICMS_INTERESTADUAL_DESTINO,                 --\n"
            + "		GP_FISCAL_MARKUP_DESTINO.CD_ESTADO CD_ESTADO_DESTINO,                                                                --\n"
            + "		CAST(GP_FISCAL_MARKUP_ORIGEM.TX_ICMS_INTERNO AS NUMERIC (16,2))TX_ICMS_INTERNO_ORIGEM,             --\n"
            + "		CAST(GP_FISCAL_MARKUP_ORIGEM.TX_ICMS_INTERESTADUAL AS NUMERIC (16,2)) TX_ICMS_INTERESTADUAL_ORIGEM,--\n"
            + "		GP_FISCAL_MARKUP_ORIGEM.CD_ESTADO CD_ESTADO_ORIGEM,                                                --\n"
            + "		CAST(NCMSH.VL_MVA AS NUMERIC (16,2))VL_MVA,                                                        --\n"
            + "		CAST(PRODUTO_SIMPLES.TX_ISS AS NUMERIC (16,2))TX_ISS,                                              --\n"
            + "		TIPO_NOTA_FISCAL.CD_CST_IPI CST_IPI,                                                               --\n"
            + "		CAST(PRODUTO_SIMPLES.TX_IPI AS NUMERIC (16,2))TX_IPI,                                              --\n"
            + "		TIPO_NOTA_FISCAL.CD_CST_PIS CST_PIS,                                                               --\n"
            + "		CAST( FILIAL.TX_PIS AS NUMERIC (16,2))TX_PIS,                                                      --\n"
            + "		TIPO_NOTA_FISCAL.CD_CST_COFINS CST_COFINS,                                                         --\n"
            + "		CAST( FILIAL.TX_COFINS AS NUMERIC (16,2))TX_COFINS,                                                --\n"
            + "		UNIDADE_MEDIDA.DS_SIGLA AB_UNIDADE,                                                                --\n"
            + "		TIPO_NOTA_FISCAL.CD_CFOP_ESTADUAL CFOP_ESTADUAL,"
            + "         TIPO_NOTA_FISCAL.CD_CFOP_INTERESTADUAL CFOP_INTERESTADUAL,                                                   --\n"
            + "		PRODUTO_SIMPLES.CD_GP_FISCAL CD_GRUPO_FISCAL,                                                      --\n"
            + "		TIPO_NOTA_FISCAL.CD_CST CD_CST,                                                                    --\n"
            + "		PRODUTO_SIMPLES.DT_CAD,                                                                            --\n"
            + "		PRODUTO_SIMPLES.HR_CAD                                                                             --\n"
            + "	FROM                                                                                                   --\n"
            + "		PRODUTO_SIMPLES                                                                                    --\n"
            + "	LEFT OUTER JOIN ESTOQUE ON                                                                             --\n"
            + "		(ESTOQUE.CD_FILIAL = ?   )                                                                         --\n"
            + "		AND                                                                                                --\n"
            + "		(ESTOQUE.CD_REF = PRODUTO_SIMPLES.CD_REF)                                                          --\n"
            + "	INNER JOIN FILIAL ON                                                                                   --\n"
            + "		(FILIAL.CD_FILIAL=?)                                                                               --\n"
            + "		AND                                                                                                --\n"
            + "		(FILIAL.CD_FILIAL=PRODUTO_SIMPLES.CD_FILIAL)                                                       --\n"
            + "	INNER JOIN SUB_TAB_PRECO ON                                                                            --\n"
            + "		(SUB_TAB_PRECO.CD_REF = PRODUTO_SIMPLES.CD_REF)                                                    --\n"
            + "	INNER JOIN GRUPO_FISCAL  ON                                                                            --\n"
            + "		(GRUPO_FISCAL.CD_GRUPO_FISCAL = ?            )                                                     --\n"
            + "	LEFT OUTER JOIN MARKUP GP_FISCAL_MARKUP_DESTINO ON                                                                              --\n"
            + "		(GP_FISCAL_MARKUP_DESTINO.CD_GRUPO_FISCAL = ?           )                                                            --\n"
            + "		AND                                                                                                --\n"
            + "		(GP_FISCAL_MARKUP_DESTINO.CD_ESTADO = ?             )                                                                --\n"
            + "	LEFT OUTER JOIN MARKUP GP_FISCAL_MARKUP_ORIGEM ON                                                      --\n"
            + "		(GP_FISCAL_MARKUP_ORIGEM.CD_GRUPO_FISCAL = ? )                                                     --\n"
            + "		AND                                                                                                --\n"
            + "		(GP_FISCAL_MARKUP_ORIGEM.CD_ESTADO = ?  )                                                          --\n"
            + "	LEFT OUTER JOIN TIPO_NOTA_FISCAL ON                                                                    --\n"
            + "		(TIPO_NOTA_FISCAL.CD_TIPO = ? )                                                                    --\n"
            + "		AND                                                                                                --\n"
            + "		(TIPO_NOTA_FISCAL.CD_GP_FISCAL =?           )                                                      --\n"
            + "		AND                                                                                                --\n"
            + "		(TIPO_NOTA_FISCAL.TP_CONSUMO = ?        )                                                          --\n"
            + "	LEFT OUTER JOIN SITUACAO_TRIBUTARIA CST ON                                                             --\n"
            + "		(CST.CD_SITUACAO_TRIBUTARIA = TIPO_NOTA_FISCAL.CD_CST)                                             --\n"
            + "	LEFT OUTER JOIN CST_PIS_COFINS ON                                                                      --\n"
            + "		CST_PIS_COFINS.CD_CST_PIS_COFINS = TIPO_NOTA_FISCAL.CD_CST_PIS                                     --\n"
            + "	LEFT OUTER JOIN NCMSH ON                                                                               --\n"
            + "		NCMSH.CD_NCMSH = PRODUTO_SIMPLES.CD_NCM_SH                                                         --\n"
            + "	LEFT OUTER JOIN UNIDADE_MEDIDA ON                                                                      --\n"
            + "		UNIDADE_MEDIDA.CD_UNIDADE = PRODUTO_SIMPLES.CD_UNIDADE_MEDIDA                                      --\n"
            + "	WHERE                                                                                                  --\n"
            + "		(PRODUTO_SIMPLES.CD_REF = ?  )                                                                     ";

    public boolean alterar(Item item) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setLong(1, item.getCd_refer_pro());
            pstmt.setDouble(2, item.getQt_ite_pro());
            pstmt.setDouble(3, item.getVl_cus_ite_pro());
            pstmt.setDouble(4, item.getVl_ven_ite_pro());
            pstmt.setDouble(5, item.getVl_real_ite_pro());
            pstmt.setDouble(6, item.getTx_icms());
            pstmt.setDouble(7, item.getVl_base_icm());
            pstmt.setDouble(8, item.getVl_icm());
            pstmt.setDouble(9, item.getTx_icms_substituicao());
            pstmt.setDouble(10, item.getVl_mva());
            pstmt.setDouble(11, item.getVl_base_icm_substituicao());
            pstmt.setDouble(12, item.getVl_icm_substituicao());
            pstmt.setDouble(13, item.getTx_iss());
            pstmt.setDouble(14, item.getVl_base_iss());
            pstmt.setDouble(15, item.getVl_iss());
            pstmt.setInt(16, item.getCst_ipi());
            pstmt.setDouble(17, item.getVl_base_ipi());
            pstmt.setDouble(18, item.getTx_ipi());
            pstmt.setDouble(19, item.getVl_ipi());
            pstmt.setDouble(20, item.getCst_ipi());
            pstmt.setDouble(21, item.getVl_base_pis());
            pstmt.setDouble(22, item.getTx_pis());
            pstmt.setDouble(23, item.getVl_pis());
            pstmt.setInt(24, item.getCst_cofins());
            pstmt.setDouble(25, item.getVl_base_cofins());
            pstmt.setDouble(26, item.getTx_cofins());
            pstmt.setDouble(27, item.getVl_cofins());
            pstmt.setDate(28, (Date) item.getDt_emi_doc());
            pstmt.setDouble(29, item.getCd_tipo_doc());
            pstmt.setString(30, item.getAb_unidade());
            pstmt.setInt(31, item.getCd_vendedor());
            pstmt.setInt(32, item.getCd_usuario());
            pstmt.setDate(33, (Date) item.getDt_alt());
            pstmt.setTime(34, item.getHr_alt());
            pstmt.setDate(35, (Date) item.getDt_cad());
            pstmt.setTime(36, item.getHr_cad());
            pstmt.setInt(37, item.getCfop());
            pstmt.setDouble(38, item.getVl_desconto());
            pstmt.setDouble(39, item.getVl_acrescimo());
            pstmt.setInt(40, item.getCd_grupo_fiscal());
            pstmt.setInt(41, item.getCd_cst());
            pstmt.setDouble(42, item.getVl_peso_liquido());
            pstmt.setDouble(43, item.getVl_peso_bruto());
            pstmt.setDouble(44, item.getVl_volume());
            pstmt.setInt(45, item.getFg_situacao());
            pstmt.setDouble(46, item.getVl_rateado());
            pstmt.setDouble(47, item.getVl_frete());
            pstmt.setDouble(48, item.getVl_impostos());
            pstmt.setInt(49, item.getCd_filial());
            pstmt.setInt(50, item.getCd_movimento());
            pstmt.setInt(51, item.getCd_seq_ite_pro());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("sql. alterarItem(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserir(Item item) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, item.getCd_filial());
            pstmt.setInt(2, item.getCd_movimento());
            pstmt.setInt(3, item.getCd_seq_ite_pro());
            pstmt.setLong(4, item.getCd_refer_pro());
            pstmt.setDouble(5, item.getQt_ite_pro());
            pstmt.setDouble(6, item.getVl_cus_ite_pro());
            pstmt.setDouble(7, item.getVl_ven_ite_pro());
            pstmt.setDouble(8, item.getVl_real_ite_pro());
            pstmt.setDouble(9, item.getTx_icms());
            pstmt.setDouble(10, item.getVl_base_icm());
            pstmt.setDouble(11, item.getVl_icm());
            pstmt.setDouble(12, item.getTx_icms_substituicao());
            pstmt.setDouble(13, item.getVl_mva());
            pstmt.setDouble(14, item.getVl_base_icm_substituicao());
            pstmt.setDouble(15, item.getVl_icm_substituicao());
            pstmt.setDouble(16, item.getTx_iss());
            pstmt.setDouble(17, item.getVl_base_iss());
            pstmt.setDouble(18, item.getVl_iss());
            pstmt.setInt(19, item.getCst_ipi());
            pstmt.setDouble(20, item.getVl_base_ipi());
            pstmt.setDouble(21, item.getTx_ipi());
            pstmt.setDouble(22, item.getVl_ipi());
            pstmt.setDouble(23, item.getCst_ipi());
            pstmt.setDouble(24, item.getVl_base_pis());
            pstmt.setDouble(25, item.getTx_pis());
            pstmt.setDouble(26, item.getVl_pis());
            pstmt.setInt(27, item.getCst_cofins());
            pstmt.setDouble(28, item.getVl_base_cofins());
            pstmt.setDouble(29, item.getTx_cofins());
            pstmt.setDouble(30, item.getVl_cofins());
            pstmt.setDate(31, (Date) item.getDt_emi_doc());
            pstmt.setDouble(32, item.getCd_tipo_doc());
            pstmt.setString(33, item.getAb_unidade());
            pstmt.setInt(34, item.getCd_vendedor());
            pstmt.setInt(35, item.getCd_usuario());
            pstmt.setDate(36, (Date) item.getDt_alt());
            pstmt.setTime(37, item.getHr_alt());
            pstmt.setDate(38, (Date) item.getDt_cad());
            pstmt.setTime(39, item.getHr_cad());
            pstmt.setInt(40, item.getCfop());
            pstmt.setDouble(41, item.getVl_desconto());
            pstmt.setDouble(42, item.getVl_acrescimo());
            pstmt.setInt(43, item.getCd_grupo_fiscal());
            pstmt.setInt(44, item.getCd_cst());
            pstmt.setDouble(45, item.getVl_peso_liquido());
            pstmt.setDouble(46, item.getVl_peso_bruto());
            pstmt.setDouble(47, item.getVl_volume());
            pstmt.setInt(48, item.getFg_situacao());
            pstmt.setDouble(49, item.getVl_rateado());
            pstmt.setDouble(50, item.getVl_frete());
            pstmt.setDouble(51, item.getVl_impostos());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            mensagemErro("sql. inserirItem(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluir(int cd_filial, int cd_movimento, int sequencia) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_filial);
            pstmt.setInt(2, cd_movimento);
            pstmt.setInt(3, sequencia);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            mensagemErro("sql. excluirItem(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return excluiu;
    }

    public boolean getItem(int cd_filial, int cd_movimento, int sequencia) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDados);
            pstmt.setInt(1, cd_filial);
            pstmt.setInt(2, cd_movimento);
            pstmt.setInt(3, sequencia);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("SQL. getItem(): \n" + e.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return existe;
    }

    public ArrayList listaDadosItemNaVenda(int cd_filialparametro, int CD_GRUPO_FISCALparametro, String CD_ESTADOparametro, int CD_TIPO_NOTAparametro, int TIPOCONSUMOparametro, int referenciaparametro) {
        ArrayList listaDadosItem = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //mensagemErro("SQL da Busca: \n"+sqlBuscaDadosVendaItem);
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDadosVendaItem);
            pstmt.setInt(1, cd_filialparametro);
            pstmt.setInt(2, cd_filialparametro);
            pstmt.setInt(3, CD_GRUPO_FISCALparametro);
            pstmt.setInt(4, CD_GRUPO_FISCALparametro);
            pstmt.setString(5, CD_ESTADOparametro);
            pstmt.setInt(6, CD_GRUPO_FISCALparametro);
            pstmt.setString(7, CD_ESTADOparametro);
            pstmt.setInt(8, CD_TIPO_NOTAparametro);
            pstmt.setInt(9, CD_GRUPO_FISCALparametro);
            pstmt.setInt(10, TIPOCONSUMOparametro);
            pstmt.setInt(11, referenciaparametro);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int auxcd_filial = rs.getInt("cd_filial");
                double auxTX_ICMS_INTERNO_ORIGEM = rs.getDouble("TX_ICMS_INTERNO_ORIGEM");
                double auxVL_MVA = rs.getInt("VL_MVA");
                double auxTX_ISS = rs.getInt("TX_ISS");
                int auxCST_IPI = rs.getInt("cst_ipi");
                double auxTX_IPI = rs.getDouble("TX_IPI");
                int auxCST_PIS = rs.getInt("cst_pis");
                double auxTX_PIS = rs.getDouble("TX_PIS");

                int auxCST_COFINS = rs.getInt("CST_COFINS");
                double auxTX_COFINS = rs.getDouble("TX_COFINS");
                int auxCFOP = rs.getInt("CFOP");
                int auxCD_CST = rs.getInt("CD_CST");

                int auxVL_CUSTO = rs.getInt("VL_CUSTO");
                double auxVL_VENDA = rs.getDouble("VL_VENDA");
                String auxAB_UNIDADE = rs.getString("AB_UNIDADE");
                int auxCD_GRUPO_FISCAL = rs.getInt("CD_GRUPO_FISCAL");
                Date auxdt_emi_doc = rs.getDate("dt_cad");
                Time auxhr_cad = rs.getTime("hr_cad");

                int cd_filial = auxcd_filial;
                int cd_movimento = 0;
                int cd_seq_ite_pro = 0;
                long cd_refer_pro = 0;
                double qt_ite_pro = 0;
                double vl_cus_ite_pro = auxVL_CUSTO;
                double vl_ven_ite_pro = auxVL_VENDA;
                double vl_real_ite_pro = 0;
                double tx_icms = auxTX_ICMS_INTERNO_ORIGEM;

                double vl_base_icm = 0;
                double vl_icm = 0;
                double tx_icms_substituicao = 17;
                double vl_mva = auxVL_MVA;
                double vl_base_icm_substituicao = 0;
                double vl_icm_substituicao = 0;
                double tx_iss = auxTX_ISS;
                double vl_base_iss = 0;
                double vl_iss = 0;
                int cst_ipi = auxCST_IPI;
                double vl_base_ipi = 0;
                double tx_ipi = auxTX_IPI;
                double vl_ipi = 0;
                int cst_pis = auxCST_PIS;
                double vl_base_pis = 0;
                double tx_pis = auxTX_PIS;
                double vl_pis = 0;
                int cst_cofins = auxCST_COFINS;
                double vl_base_cofins = 0;
                double tx_cofins = auxTX_COFINS;
                double vl_cofins = 0;
                Date dt_emi_doc = auxdt_emi_doc;
                int cd_tipo_doc = 0;//retorna da tela
                String ab_unidade = auxAB_UNIDADE;
                int cd_vendedor = 0;
                int cd_usuario = 0;
                Date dt_alt = auxdt_emi_doc;
                Time hr_alt = auxhr_cad;
                Date dt_cad = auxdt_emi_doc;
                Time hr_cad = auxhr_cad;
                int cfop = auxCFOP;
                double vl_desconto = 0;
                double vl_acrescimo = 0;
                int cd_grupo_fiscal = auxCD_GRUPO_FISCAL;
                int cd_cst = auxCD_CST;
                double vl_peso_liquido = 0;
                double vl_peso_bruto = 0;
                double vl_volume = 0;
                int fg_situacao = 0;
                double vl_rateado = 0;
                double vl_frete = 0;
                double vl_impostos = 0;

                Item item = new Item(
                        cd_filial,
                        cd_movimento,
                        cd_seq_ite_pro,
                        cd_refer_pro,
                        qt_ite_pro,
                        vl_cus_ite_pro,
                        vl_ven_ite_pro,
                        vl_real_ite_pro,
                        tx_icms,
                        vl_base_icm,
                        vl_icm,
                        tx_icms_substituicao,
                        vl_mva,
                        vl_base_icm_substituicao,
                        vl_icm_substituicao,
                        tx_iss,
                        vl_base_iss,
                        vl_iss,
                        cst_ipi,
                        vl_base_ipi,
                        tx_ipi,
                        vl_ipi,
                        cst_pis,
                        vl_base_pis,
                        tx_pis,
                        vl_pis,
                        cst_cofins,
                        vl_base_cofins,
                        tx_cofins,
                        vl_cofins,
                        dt_emi_doc,
                        cd_tipo_doc,
                        ab_unidade,
                        cd_vendedor,
                        cd_usuario,
                        dt_alt,
                        hr_alt,
                        dt_cad,
                        hr_cad,
                        cfop,
                        vl_desconto,
                        vl_acrescimo,
                        cd_grupo_fiscal,
                        cd_cst,
                        vl_peso_liquido,
                        vl_peso_bruto,
                        vl_volume,
                        fg_situacao,
                        vl_rateado,
                        vl_frete,
                        vl_impostos
                );
                listaDadosItem.add(item);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no ArrayList listaDadosItemNaVenda: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaDadosItem;
    }

    public ArrayList listaDadosTributacaoVenda(int cd_filialparametro, int CD_GRUPO_FISCALparametro,String CD_ESTADO_DESTINO, String CD_ESTADO_ORIGEM, int CD_TIPO_NOTAparametro, int TIPOCONSUMOparametro, int referenciaparametro) {
        ArrayList listaDadosItem = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDadosVendaItem);
            pstmt.setInt(1, cd_filialparametro);
            pstmt.setInt(2, cd_filialparametro);
            pstmt.setInt(3, CD_GRUPO_FISCALparametro);
            pstmt.setInt(4, CD_GRUPO_FISCALparametro);
            pstmt.setString(5, CD_ESTADO_DESTINO);
            pstmt.setInt(6, CD_GRUPO_FISCALparametro);
            pstmt.setString(7, CD_ESTADO_ORIGEM);
            pstmt.setInt(8, CD_TIPO_NOTAparametro);
            pstmt.setInt(9, CD_GRUPO_FISCALparametro);
            pstmt.setInt(10, TIPOCONSUMOparametro);
            pstmt.setInt(11, referenciaparametro);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                double vl_cus_ite_pro = rs.getDouble("VL_CUSTO");
                double vl_ven_ite_pro = rs.getDouble("VL_VENDA");
                double tx_icms_interno_destino = rs.getDouble("TX_ICMS_INTERNO_DESTINO");
                double tx_icms_interestadual_destino = rs.getDouble("TX_ICMS_INTERNO_DESTINO");
                String cd_estado_destino = rs.getString("CD_ESTADO_DESTINO");
                double tx_icms_interno_origem = rs.getDouble("TX_ICMS_INTERNO_ORIGEM");
                double tx_icms_interestadual_origem = rs.getDouble("TX_ICMS_INTERESTADUAL_ORIGEM");
                String cd_estado_origem = rs.getString("CD_ESTADO_ORIGEM");
                double tx_icms = rs.getDouble("TX_ICMS_INTERNO_ORIGEM");
                double tx_icms_substituicao = rs.getDouble("TX_ICMS_INTERNO_ORIGEM");
                double vl_mva = rs.getInt("VL_MVA");
                double tx_iss = rs.getInt("TX_ISS");
                int cst_ipi = rs.getInt("cst_ipi");
                double tx_ipi = rs.getDouble("TX_IPI");
                int cst_pis = rs.getInt("cst_pis");
                double tx_pis = rs.getDouble("TX_PIS");
                int cst_cofins = rs.getInt("CST_COFINS");
                double tx_cofins = rs.getDouble("TX_COFINS");
                int cfop_estadual = rs.getInt("CFOP_ESTADUAL");
                int cfop_inter_estadual = rs.getInt("CFOP_INTERESTADUAL");
                String ab_unidade = rs.getString("AB_UNIDADE");
                int cd_grupo_fiscal = rs.getInt("CD_GRUPO_FISCAL");
                int cd_cst = rs.getInt("CD_CST");

                Tributacao tributacao = new Tributacao(
                        vl_cus_ite_pro,
                        vl_ven_ite_pro,
                        tx_icms_interno_destino,
                        tx_icms_interestadual_destino,
                        cd_estado_destino,
                        tx_icms_interno_origem,
                        tx_icms_interestadual_origem,
                        cd_estado_origem,
                        tx_icms,
                        tx_icms_substituicao,
                        vl_mva,
                        tx_iss,
                        cst_ipi,
                        tx_ipi,
                        cst_pis,
                        tx_pis,
                        cst_cofins,
                        tx_cofins,
                        ab_unidade,
                        cfop_estadual,
                        cfop_inter_estadual,
                        cd_grupo_fiscal,
                        cd_cst
                );
                listaDadosItem.add(tributacao);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no ArrayList listaDadosTributacaoVenda: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaDadosItem;
    }

}
