package br.com.bravoti.pgmadmin;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Vitor on 27/03/2016.
 */
public class FuncoesSQL {

    public static List<HashMap<String, String>> RetornaDatabase(Context context, String tipo)
    {
        try {
            String SQL = null;

            if(tipo == "database") {
                SQL = "SELECT datname FROM pg_database WHERE datistemplate=false ORDER BY datname";
            }else if(tipo == "tablespace") {
                SQL = "SELECT spcname FROM pg_tablespace ORDER BY spcname";
            }
            else if(tipo == "pg_roles_grupo") {
                SQL = "SELECT rolname FROM pg_roles WHERE rolvaliduntil is not null ORDER BY rolname";
            }
            else if(tipo == "pg_roles_user") {
                SQL = "SELECT rolname FROM pg_roles WHERE rolvaliduntil is null ORDER BY rolname";
            }

            List<HashMap<String, String>> result =  new ResultSelect().execute(SQL).get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int RetornaTotalDatabase(Context context, String tipo)
    {
        try {
            String SQL = null;

            if(tipo == "database") {
                SQL = "SELECT count(*) AS total FROM pg_database WHERE datistemplate=false";
            }else if(tipo == "tablespace") {
            SQL = "SELECT count(*) AS total FROM pg_tablespace";
            }
            else if(tipo == "pg_roles_grupo") {
                SQL = "SELECT count(*) AS total FROM pg_roles WHERE rolvaliduntil is not null";
            }
            else if(tipo == "pg_roles_user") {
                SQL = "SELECT count(*) AS total FROM pg_roles WHERE rolvaliduntil is null";
            }

            int total = 0;

            List<HashMap<String, String>> result =  new ResultSelect().execute(SQL).get();

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    total = Integer.parseInt(result.get(i).get("total"));
                }
            }

            return total;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<HashMap<String, String>>  RetornaDatabaseDados(Context context, String tipo)
    {
        try {
            String SQL = null;

            switch (tipo) {
                case "extension":
                    SQL = "SELECT extname FROM pg_extension ORDER BY extname;";
                    break;
                case "schema":
                    // Remove 'system' schemata e tudo que inicia com pg_
                    SQL = "SELECT schema_name FROM information_schema.schemata WHERE schema_name <> 'information_schema' AND schema_name !~ E'^pg_'";
                    break;
            }

            List<HashMap<String, String>> result =  new ResultSelect().execute(SQL).get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
