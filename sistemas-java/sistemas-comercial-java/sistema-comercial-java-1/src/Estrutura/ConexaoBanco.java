package Estrutura;

/**
 *
 * @author Gelvazio Camargo
 */
public class ConexaoBanco {
        
    private String driver;
    private String host;
    private String banco;
    private String conexao;
    private String usuario;
    private String senha;

    public ConexaoBanco(String driver, String host, String banco, String usuario, String senha, String conexao) {
        this.driver = driver;
        this.host = host;
        this.banco = banco;
        this.usuario = usuario;
        this.senha = senha;
        this.conexao = conexao;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getConexao() {
        return conexao;
    }

    public void setConexao(String conexao) {
        this.conexao = conexao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public ConexaoBanco getDadosConexaoBanco(int opcao) {
        String DRIVER  = "";
        String HOST    = "";
        String BANCO   = "";
        String USUARIO = "";
        String SENHA   = ""; 
        String CONEXAO = ""; 
                
        switch(opcao){
            case 1:
                DRIVER  = "org.postgresql.Driver";
                HOST    = "salt.db.elephantsql.com";
                BANCO   = "ftgcyhzw";
                CONEXAO = "jdbc:postgresql://" + HOST + ":5432/" + BANCO;
                USUARIO = "ftgcyhzw";
                SENHA   = "Qgs5XLVecOqlU6ZkzfAbmxpi7tQcYLbI";        
                break;
            case 2:
                DRIVER  = "org.postgresql.Driver";
                HOST    = "salt.db.elephantsql.com";
                BANCO   = "comdvayv";
                CONEXAO = "jdbc:postgresql://tuffi.db.elephantsql.com/comdvayv";
                USUARIO = "comdvayv";
                SENHA   = "87Hq8BqppVuDNxtdPlDeSsDmoWylDp2e";
		break; 
            case 3:
                DRIVER  = "org.postgresql.Driver";
                HOST    = "jdbc:postgresql://servidor-postgresql-azure.postgres.database.azure.com/postgres";
                USUARIO = "gelvazio_postgresql@servidor-postgresql-azure";
                SENHA   = "Ewa2eKfK%pp9";
            break;
            case 4:  
                DRIVER  = "org.firebirdsql.jdbc.FBDriver";
                BANCO   = "NERIFIREBIRD.FDB";
                HOST    = "jdbc:firebirdsql:localhost/3050:D:/videoaulas/java SE II/netbeans6/aula_neri/banco_dados/NERIFIREBIRD.FDB";
                USUARIO = "SYSDBA";
                SENHA   = "masterkey";
                break;
            case 5:  
                //ConexaoDBHeroku
                DRIVER  = "org.postgresql.Driver";
                HOST    = "ec2-18-204-101-137.compute-1.amazonaws.com";
                BANCO   = "d3gcuuncl1dsrp";
                USUARIO = "zherahydsltxbg";
                SENHA   = "8c3c7c4e916e003529d4ac1f5704e6801a5e05be7410b4a0bec9bf0fb28a9dd1";                
                break;
        }

        ConexaoBanco conexao = new ConexaoBanco(DRIVER, HOST, BANCO, USUARIO, SENHA, CONEXAO);
        return conexao;
    }
}
