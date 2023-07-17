package com.andriokta202102288.a202102288_crudakhir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KonselingActivity extends AppCompatActivity {
    EditText nik, nama, tgllahir, alamat, keluhan;
    Button simpan, tampil, edit, hapus;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konseling);

        nik = findViewById(R.id.nik);
        nama = findViewById(R.id.nama);
        tgllahir = findViewById(R.id.tgllahir);
        alamat = findViewById(R.id.alamat);
        keluhan = findViewById(R.id.keluhan);
        simpan = findViewById(R.id.btnsimpan1);
        tampil = findViewById(R.id.btntampil1);
        edit = findViewById(R.id.btnedit1);
        hapus = findViewById(R.id.btnhapus1);

        db = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ni = nik.getText().toString();
                String nm = nama.getText().toString();
                String tgl = tgllahir.getText().toString();
                String alm = alamat.getText().toString();
                String kel = keluhan.getText().toString();

                if (TextUtils.isEmpty(ni) || TextUtils.isEmpty(nm) || TextUtils.isEmpty(tgl) || TextUtils.isEmpty(alm) || TextUtils.isEmpty(kel))
                    Toast.makeText(KonselingActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean cekInputData= db.checkkodenik(ni);
                    if (cekInputData == false){
                        Boolean insert = db.insertDataKonseling(ni, nm, tgl, alm, kel);
                        if (insert == true){
                            Toast.makeText(KonselingActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(KonselingActivity.this,"Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(KonselingActivity.this,"Data Mahasiswa Sudah Ada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDataKonseling();
                if (res.getCount()==0){
                    Toast.makeText(KonselingActivity.this, "Tidak ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer =  new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("NIK : "+res.getString(0)+"\n");
                    buffer.append("Nama : "+res.getString(1)+"\n");
                    buffer.append("Tgl Lahir : "+res.getString(2)+"\n");
                    buffer.append("Alamat : "+res.getString(3)+"\n");
                    buffer.append("Keluhan : "+res.getString(4)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(KonselingActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Konseling");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ni = nik.getText().toString();
                Boolean cekHapusData = db.hapusDataKonseling(ni);
                if (cekHapusData == true)
                    Toast.makeText(KonselingActivity.this, "Data Berhasil Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(KonselingActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ni = nik.getText().toString();
                String nm = nama.getText().toString();
                String tgl = tgllahir.getText().toString();
                String alm = alamat.getText().toString();
                String kel = keluhan.getText().toString();

                if (TextUtils.isEmpty(ni) || TextUtils.isEmpty(nm) || TextUtils.isEmpty(tgl) || TextUtils.isEmpty(alm) || TextUtils.isEmpty(kel))
                    Toast.makeText(KonselingActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_LONG).show();
                else {
                    Boolean edit = db.editDataKonseling(ni, nm, tgl, alm, kel);
                    if (edit == false){
                        Toast.makeText(KonselingActivity.this, "Data berhasil diedit", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(KonselingActivity.this,"Data gagal diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}