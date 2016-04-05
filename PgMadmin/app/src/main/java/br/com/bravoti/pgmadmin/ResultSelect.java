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
public class ResultSelect extends AsyncTask<String,Object,List<HashMap<String, String>>> {

    @Override
    public List<HashMap<String, String>> doInBackground(String... string) {
        List<HashMap<String, String>> retval = new ArrayList<HashMap<String, String>>();
        try
        {
            if(ConectaPostgreSQL.getInstance().getConn() != null) {

                Statement st = ConectaPostgreSQL.getInstance().getConn().createStatement();
                String SQL = string[0].replace("\\", "\\\\");

                ResultSet rs = st.executeQuery(SQL);
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
        }
        return retval;
    }

    protected void onPreExecute() {
    }

    public void onPostExecute(List<HashMap<String, String>> result) {
        super.onPostExecute(result);
    }
}