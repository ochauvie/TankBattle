package com.tankbattle.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tankbattle.R;
import com.tankbattle.adapter.IDataSpinnerAdapter;
import com.tankbattle.adapter.VictoireListAdapter;
import com.tankbattle.model.Genre;
import com.tankbattle.model.IRefData;
import com.tankbattle.model.Nation;
import com.tankbattle.model.Tank;
import com.tankbattle.model.TankVictoires;
import com.tankbattle.service.EquipeService;
import com.tankbattle.service.TankService;
import com.tankbattle.tools.SpinnerTool;

import java.util.ArrayList;
import java.util.List;

public class AddTankActivity extends ListActivity implements MyDialogInterface.DialogReturn {

    private Spinner spinnerNation, spinnerGenre;
    private EditText editTextNom, editTextPv;
    private Tank tank = null;
    private MyDialogInterface myInterface;
    private ListView listView;
    private VictoireListAdapter victoireListAdapter;
    private List<TankVictoires> victoiresAndDefaites = new ArrayList<TankVictoires>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tank);

        spinnerNation = (Spinner) findViewById(R.id.spNation);
        loadSpinnerNation();

        spinnerGenre = (Spinner) findViewById(R.id.spGenre);
        loadSpinnerGenre();

        editTextNom = (EditText)  findViewById(R.id.editTextNom);
        editTextPv = (EditText)  findViewById(R.id.editTextPv);

        myInterface = new MyDialogInterface();
        myInterface.setListener(this);

        listView = getListView();

        initView();

        // Creation et initialisation de l'Adapter
        victoireListAdapter = new VictoireListAdapter(this, victoiresAndDefaites, tank.getId());


        //Initialisation de la liste avec les donnees
        setListAdapter(victoireListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_tank, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemD = menu.findItem(R.id.action_delete_tank);
        MenuItem itemS = menu.findItem(R.id.action_add_tank);
        if (tank!=null) {
            if (EquipeService.isTankInEquipe(tank)) {
                itemD.setEnabled(false);
                itemD.getIcon().setAlpha(130);
                itemS.setEnabled(false);
                itemS.getIcon().setAlpha(130);
            } else {
                itemD.setEnabled(true);
                itemD.getIcon().setAlpha(255);
                itemS.setEnabled(true);
                itemS.getIcon().setAlpha(255);
            }
        } else {
            itemD.setEnabled(false);
            itemD.getIcon().setAlpha(130);
            itemS.setEnabled(true);
            itemS.getIcon().setAlpha(255);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_tank:
                if (onSave()) {
                    Intent listTankActivity = new Intent(getApplicationContext(), ListTankActivity.class);
                    startActivity(listTankActivity);
                    finish();
                    return true;
                }
                return false;
            case R.id.action_close_tank:
                Intent listTankActivity = new Intent(getApplicationContext(), ListTankActivity.class);
                startActivity(listTankActivity);
                finish();
                return true;
            case R.id.action_delete_tank:
                onDelete();
                return false;
        }
        return false;
    }

    private void loadSpinnerNation() {
        ArrayList<IRefData> list = new ArrayList<IRefData>();
        for (Nation nation:Nation.values()) {
            list.add(nation);
        }
        spinnerNation.setAdapter(new IDataSpinnerAdapter(this, list));
    }

    private void loadSpinnerGenre() {
        ArrayList<IRefData> list = new ArrayList<IRefData>();
        for (Genre genre:Genre.values()) {
            list.add(genre);
        }
        spinnerGenre.setAdapter(new IDataSpinnerAdapter(this, list));
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            //tank = (Tank)bundle.getSerializable(Tank.class.getName());
            long tankId = bundle.getLong(Tank.TANK_ID);
            tank = Tank.load(Tank.class, tankId);
            if (tank!=null) {
                editTextNom.setText(tank.getNom());
                editTextPv.setText(String.valueOf(tank.getPv()));
                SpinnerTool.SelectSpinnerItemByValue(spinnerNation, tank.getNation().name());
                SpinnerTool.SelectSpinnerItemByValue(spinnerGenre, tank.getGenre().name());
            }
            victoiresAndDefaites = tank.victoires();
            victoiresAndDefaites.addAll(TankService.findDefaites(tank));
        }
    }

    private boolean onSave() {
        Editable edName = editTextNom.getText();
        Editable edPv = editTextPv.getText();
        if (edName==null || "".equals(edName.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.nom_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else if (edPv==null || "".equals(edPv.toString())) {
            Toast.makeText(getBaseContext(), getString(R.string.pv_mandatory), Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (tank == null) {
                tank = new Tank();
            }
            tank.setNation((Nation) spinnerNation.getSelectedItem());
            tank.setGenre((Genre) spinnerGenre.getSelectedItem());
            tank.setNom(edName.toString());
            tank.setPv(Integer.valueOf(edPv.toString()));

            tank.save();
            Toast.makeText(getBaseContext(), getString(R.string.tank_save), Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private boolean onDelete() {
        if (tank != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setIcon(R.drawable.delete);
            builder.setTitle(tank.getNom());
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(true, null);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    myInterface.getListener().onDialogCompleted(false, null);
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return true;
    }

    @Override
    public void onDialogCompleted(boolean answer, String type) {
        if (answer && tank!=null) {
            tank.delete();
            Toast.makeText(getBaseContext(), getString(R.string.tank_delete), Toast.LENGTH_LONG).show();
            Intent listTankActivity = new Intent(getApplicationContext(), ListTankActivity.class);
            startActivity(listTankActivity);
            finish();
        }
    }
}
