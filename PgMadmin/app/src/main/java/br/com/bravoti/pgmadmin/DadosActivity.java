package br.com.bravoti.pgmadmin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DadosActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_grupo_item_dados);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        "Mostra - " + listDataHeader.get(groupPosition),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        "Oculta - " + listDataHeader.get(groupPosition),
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Databases (" + FuncoesSQL.RetornaTotalDatabase(DadosActivity.this, "database")+")");
        listDataHeader.add("Tablespaces (" + FuncoesSQL.RetornaTotalDatabase(DadosActivity.this, "tablespace")+")");
        listDataHeader.add("Jobs (0)");
        listDataHeader.add("Group Roles (" + FuncoesSQL.RetornaTotalDatabase(DadosActivity.this, "pg_roles_grupo")+")");
        listDataHeader.add("Login Roles (" + FuncoesSQL.RetornaTotalDatabase(DadosActivity.this, "pg_roles_user")+")");

        // Adding child data
        List<String> databases = new ArrayList<String>();
        List<HashMap<String, String>> lDatabases = FuncoesSQL.RetornaDatabase(DadosActivity.this, "database");

        if (!lDatabases.isEmpty()) {
            for (int i = 0; i < lDatabases.size(); i++) {
                databases.add(lDatabases.get(i).get("datname"));
            }
        }

        List<String> tablespaces = new ArrayList<String>();
        List<HashMap<String, String>> lTablespaces = FuncoesSQL.RetornaDatabase(DadosActivity.this, "tablespace");

        if (!lTablespaces.isEmpty()) {
            for (int i = 0; i < lTablespaces.size(); i++) {
                tablespaces.add(lTablespaces.get(i).get("spcname"));
            }
        }

        List<String> jobs = new ArrayList<String>();
        jobs.add("");

        List<String> groupRoles = new ArrayList<String>();
        List<HashMap<String, String>> lGroupRoles = FuncoesSQL.RetornaDatabase(DadosActivity.this, "pg_roles_grupo");

        if (!lGroupRoles.isEmpty()) {
            for (int i = 0; i < lGroupRoles.size(); i++) {
                groupRoles.add(lGroupRoles.get(i).get("rolname"));
            }
        }

        List<String> loginRoles = new ArrayList<String>();
        List<HashMap<String, String>> lLoginRoles = FuncoesSQL.RetornaDatabase(DadosActivity.this, "pg_roles_user");

        if (!lLoginRoles.isEmpty()) {
            for (int i = 0; i < lLoginRoles.size(); i++) {
                loginRoles.add(lLoginRoles.get(i).get("rolname"));
            }
        }

        listDataChild.put(listDataHeader.get(0), databases); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tablespaces);
        listDataChild.put(listDataHeader.get(2), jobs);
        listDataChild.put(listDataHeader.get(3), groupRoles);
        listDataChild.put(listDataHeader.get(4), loginRoles);
    }
}