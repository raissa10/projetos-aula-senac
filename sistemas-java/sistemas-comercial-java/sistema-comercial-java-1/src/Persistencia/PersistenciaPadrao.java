
package Persistencia;

/**
 *
 * @author Gelvazio Camargo
 */
public class PersistenciaPadrao extends Persistencia {    
    
    public String getSqlBuscaRegistro(){
        return "SELECT * FROM estado  WHERE cd_estado=?";
    }
    
    public boolean excluir(String oChave){
        return true;
    }  
//    
//    public boolean inserir(Estados estado) {
//        boolean executou = false;
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        try {
//            conn = Conexao.getConexao();
////            this.setaParametrosClasse(pstmt);
////            pstmt = conn.prepareStatement(sqlInserir);                    
//            pstmt.execute();
//            executou = true;
//        } catch (SQLException erro) {
//            JOptionPane.showMessageDialog(null, "Erro no sql. inserirEstado():\n" + erro.getMessage());
//        } finally {
//            Conexao.closeAll(conn);
//        }
//        return executou;
//    }

}
