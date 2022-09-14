/*
 * Copyright (C) 2015 Gelvazio Camargo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo
 */
/*
 Tabela Banco de Dados
 cd_ref                  
 vl_custo                
 vl_venda                
 vl_promocao             
 vl_especial             
 vl_custo_med            
 tx_margem_lucro_venda   
 tx_margem_lucro_promocao
 tx_margem_lucro_especial
 cd_usuario              
 dt_alt                  
 hr_alt                  
 dt_cad                  
 hr_cad                  
 cd_filial               

 */
public class SubTabPreco {

    private long cd_ref;
    private double vl_custo;
    private double vl_venda;
    private double vl_promocao;
    private double vl_especial;
    private double vl_custo_med;
    private double tx_margem_lucro_venda;
    private double tx_margem_lucro_promocao;
    private double tx_margem_lucro_especial;
    private int cd_usuario;
    private int cd_filial;

    public SubTabPreco(long cd_ref, double vl_custo, double vl_venda, double vl_promocao, double vl_especial, double vl_custo_med, double tx_margem_lucro_venda, double tx_margem_lucro_promocao, double tx_margem_lucro_especial, int cd_usuario, int cd_filial) {
        this.cd_ref = cd_ref;
        this.vl_custo = vl_custo;
        this.vl_venda = vl_venda;
        this.vl_promocao = vl_promocao;
        this.vl_especial = vl_especial;
        this.vl_custo_med = vl_custo_med;
        this.tx_margem_lucro_venda = tx_margem_lucro_venda;
        this.tx_margem_lucro_promocao = tx_margem_lucro_promocao;
        this.tx_margem_lucro_especial = tx_margem_lucro_especial;
        this.cd_usuario = cd_usuario;
        this.cd_filial = cd_filial;
    }

    public long getCd_ref() {
        return cd_ref;
    }

    public void setCd_ref(long cd_ref) {
        this.cd_ref = cd_ref;
    }

    public double getVl_custo() {
        return vl_custo;
    }

    public void setVl_custo(double vl_custo) {
        this.vl_custo = vl_custo;
    }

    public double getVl_venda() {
        return vl_venda;
    }

    public void setVl_venda(double vl_venda) {
        this.vl_venda = vl_venda;
    }

    public double getVl_promocao() {
        return vl_promocao;
    }

    public void setVl_promocao(double vl_promocao) {
        this.vl_promocao = vl_promocao;
    }

    public double getVl_especial() {
        return vl_especial;
    }

    public void setVl_especial(double vl_especial) {
        this.vl_especial = vl_especial;
    }

    public double getVl_custo_med() {
        return vl_custo_med;
    }

    public void setVl_custo_med(double vl_custo_med) {
        this.vl_custo_med = vl_custo_med;
    }

    public double getTx_margem_lucro_venda() {
        return tx_margem_lucro_venda;
    }

    public void setTx_margem_lucro_venda(double tx_margem_lucro_venda) {
        this.tx_margem_lucro_venda = tx_margem_lucro_venda;
    }

    public double getTx_margem_lucro_promocao() {
        return tx_margem_lucro_promocao;
    }

    public void setTx_margem_lucro_promocao(double tx_margem_lucro_promocao) {
        this.tx_margem_lucro_promocao = tx_margem_lucro_promocao;
    }

    public double getTx_margem_lucro_especial() {
        return tx_margem_lucro_especial;
    }

    public void setTx_margem_lucro_especial(double tx_margem_lucro_especial) {
        this.tx_margem_lucro_especial = tx_margem_lucro_especial;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

}
