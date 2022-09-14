
package Persistencia;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Jessica
 */
public class Persistencia {
    
    /**
     * @var ModelEstado
     */
    protected Object $Model;
    
    protected String sqlInserir
            = "INSERT INTO CADASTRO.TBESTADO (CD_ESTADO, DS_ESTADO,"
            + " CD_IBGE, CD_FILIAL, CD_USUARIO, DT_ALT, HR_ALT, DT_CAD, HR_CAD)"
            + "VALUES "
            + "(?,?,?,?,?,"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME))";

    protected ArrayList getCamposRelacionamento(){
        ArrayList aCamposPersistencia = null;
        aCamposPersistencia.add("getCd_estado");
        aCamposPersistencia.add("getDs_estado()");
        aCamposPersistencia.add("getCd_ibge()");
        aCamposPersistencia.add("getCd_filial()");
        aCamposPersistencia.add("getCd_usuario(");
        return aCamposPersistencia;
    }
    
    protected void setaParametrosClasse(PreparedStatement pstmt){
        ArrayList aCampos = this.getCamposRelacionamento();
//        for(getCamposRelacionamento())
//        pstmt.setString(1, estado.getCd_estado());
//        pstmt.setString(2, estado.getDs_estado());
//        pstmt.setInt(3, estado.getCd_ibge());
//        pstmt.setInt(4, estado.getCd_filial());
//        pstmt.setInt(5, estado.getCd_usuario());
    }
}
