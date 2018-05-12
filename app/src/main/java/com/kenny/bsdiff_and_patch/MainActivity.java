package com.kenny.bsdiff_and_patch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kenny.utils.ApkExtract;
import com.kenny.utils.BsPatch;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button mBtnPatch;
    private TextView mTvVersionName;
    private TextView mTvVersionCode;
    private TextView mTvPahchCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        mBtnPatch = (Button) findViewById(R.id.id_btn_patch);
        mBtnPatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    doBspatch();
                }

            }
        });

    }

    private void doBspatch() {
        final File destApk = new File(Environment.getExternalStorageDirectory(), "dest.apk");
        final File patch = new File(Environment.getExternalStorageDirectory(), "PATCH.patch");

        Log.e("hongyang", "patch = " + patch.exists() + " , " + patch.getAbsolutePath());

        BsPatch.bspatch(ApkExtract.extract(this),
                destApk.getAbsolutePath(),
                patch.getAbsolutePath());

        Log.e("hongyang", new File(Environment.getExternalStorageDirectory(), "old").getAbsolutePath() + " , " + destApk.exists());

        if (destApk.exists())
            ApkExtract.install(this, destApk.getAbsolutePath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doBspatch();
            }
        }
    }

    private void initView() {
        mTvVersionName = (TextView) findViewById(R.id.tv_version_name);
        mTvVersionCode = (TextView) findViewById(R.id.tv_version_code);
        mTvPahchCode = (TextView) findViewById(R.id.tv_pahch_code);
        mTvVersionName.setText("版本名 ：" + Constants.VERSION_NAME);
        mTvVersionCode.setText("版本号 ：" + Constants.VERSION_CODE);
        mTvPahchCode.setText("补丁号 ：" + Constants.PATCH_CODE);
    }
}
