package br.com.bravoti.pgmadmin;

/**
 * Created by Vitor on 12/04/2015.
 */

import android.content.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
/**
 *
 * @author Vitor
 */
public final class ConectaPostgreSQL {

    public static String HostBanco = "186.202.152.24";
    public static String PortaBanco = "5432";
    public static String NomeBanco = "bravoti";
    public static String UsuarioBanco = "bravoti";
    public static String SenhaBanco = "vbrbd223344";

    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://" + HostBanco + ":" + PortaBanco + "/" + NomeBanco + "";
    private String user = UsuarioBanco;
    private String pwd = SenhaBanco;

    private Connection conecta = null;

    // getInstance() utilizado para acessar o metodo somente em uma linha
    private static ConectaPostgreSQL instance;

    public static ConectaPostgreSQL getInstance() {
        if (instance == null) {
            instance = new ConectaPostgreSQL();
        }
        return instance;
    }

    private ConectaPostgreSQL() {
        try {
            Class.forName(driver);
            //DriverManager.registerDriver(new org.postgresql.Driver());
            DriverManager.setLoginTimeout(20);
            conecta = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void DesconectaPostgreSQL() {
        try {
            conecta.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn() {
        try {
            if ((conecta == null) || (conecta.isClosed())) {
                new ConectaPostgreSQL();
                //System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            conecta = null;
        }
        return conecta;
    }
// outros metodos

    public List<HashMap<String, String>> selectSQL(String sql, Context context)
    {
        List<HashMap<String, String>> retval = new ArrayList<HashMap<String, String>>();
        try

        {
            if(getConn() != null) {

                Statement st = getConn().createStatement();
                sql = sql.replace("\\", "\\\\");

                ResultSet rs = st.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();

                while (rs.next()) {
                    HashMap<String, String> reg = new HashMap<String, String>();

                    for (int i = 1; i <= meta.getColumnCount(); i++)
                        reg.put(meta.getColumnName(i), rs.getString(i));
                    retval.add(reg);
                }

                rs.close();
                st.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(" 1 - Erro ao conectar no banco");
            alertDialog.setMessage(e.toString());
            alertDialog.setButton("OK", new OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                }
            });
            alertDialog.show();
        }
        return retval;
    }
}