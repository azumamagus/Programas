package br.com.bravoti.pgmadmin;

import android.os.AsyncTask;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vitor on 12/04/2015.
 */
public class ResultInUpDe extends AsyncTask<String,Object,String> {

    @Override
    public String doInBackground(String... string) {
        String retval = "";

        try {
            if(ConectaPostgreSQL.getInstance().getConn() != null) {
                Statement st = ConectaPostgreSQL.getInstance().getConn().createStatement();
                String SQL = string[0].replace("\\", "\\\\");
                //st.executeUpdate(SQL, st.RETURN_GENERATED_KEYS);
                st.executeUpdate(SQL);

                    /*//Retorna o ID do Cliente
                    ResultSet rs = st.getGeneratedKeys();

                    while (rs.next()) {
                        retval = rs.getString(1);
                    }*/

                retval = "";
                st.close();
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
            retval = e.toString();
        }
        return retval;
    }

    protected void onPreExecute() {
    }

    public void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}