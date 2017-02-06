package com.debam.pretest_gojek.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.debam.pretest_gojek.MyApplication;
import com.debam.pretest_gojek.R;
import com.debam.pretest_gojek.interfaces.AddContactPresenter;
import com.debam.pretest_gojek.interfaces.AddContactView;
import com.debam.pretest_gojek.presenters.AddContactPresenters;
import com.debam.pretest_gojek.presenters.ContactPresenter;
import com.debam.pretest_gojek.repository.ContactRepository;
import com.debam.pretest_gojek.services.APIServices;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Debam on 2/6/2017.
 */

public class AddContactActivity extends AppCompatActivity implements AddContactView{

    private static final int SELECT_FILE = 26;
    private static final int REQUEST_CAMERA = 27;
    @BindView(R.id.tilFirstName)TextInputLayout tilFirstName;
    @BindView(R.id.tilLastName)TextInputLayout tilLastName;
    @BindView(R.id.tilEmail)TextInputLayout tilEmail;
    @BindView(R.id.tilMobileNumber)TextInputLayout tilMobileNumber;

    @BindView(R.id.tvFirstName)EditText etFirstName;
    @BindView(R.id.tvLastName)EditText etLastName;
    @BindView(R.id.tvEmail)EditText etEmail;
    @BindView(R.id.tvMobileNumber)EditText etMobileNumber;

    @BindView(R.id.imgProfilePic)CircleImageView imgProfil;
    @BindView(R.id.btnSave)Button btnSave;

    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.toolbar_title)TextView toolbarTittle;

    @BindString(R.string.error_validate)String err_validate;
    @BindString(R.string.loading)String loading;
    @BindString(R.string.success_add_contact) String success;
    @BindString(R.string.failed_add_contact)String failed;

    @BindDrawable(R.drawable.grey)Drawable greyimg;

    private AddContactPresenter mPresenter;
    private Context ctx;
    private ProgressDialog dialog;

    private int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    @Inject
    SharedPreferences sp;

    @Inject
    ContactRepository db;

    @Inject
    APIServices api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);

        ctx = this;

        ButterKnife.bind(AddContactActivity.this);

        ((MyApplication) getApplication()).getDc().inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mPresenter = new AddContactPresenters(this, ctx, api, db);

        imgProfil.setImageDrawable(greyimg);
        requestAllPermission();


    }

    @OnClick(R.id.btnSave)
    void setBtnSave(){
        mPresenter.start();
    }

    @OnClick(R.id.imgProfilePic)
    void openImage(){
        final CharSequence[] items = { "Take Photo", "Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
        builder.setTitle("Add Image Profile");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } 
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imgProfil.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgProfil.setImageBitmap(thumbnail);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationFailed(TextInputLayout til) {
        til.setError(err_validate);
        til.setErrorEnabled(true);
    }

    @Override
    public void onValidationSuccess(TextInputLayout til) {
        til.setError("");
        til.setErrorEnabled(false);
    }


    @Override
    public void onSuccessfull() {
        dialog.dismiss();
        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setMessage(success);
        adb.show();

    }

    @Override
    public void onLoad() {
        dialog = new ProgressDialog(AddContactActivity.this);
        dialog.setMessage(loading);
        dialog.show();
    }

    @Override
    public void onFailed() {
        dialog.dismiss();
        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setMessage(failed);
        adb.show();
    }

    @Override
    public TextInputLayout gettilFirstName() {
        return tilFirstName;
    }

    @Override
    public TextInputLayout gettilLastName() {
        return tilLastName;
    }

    @Override
    public TextInputLayout gettilEmail() {
        return tilEmail;
    }

    @Override
    public TextInputLayout gettilPhone() {
        return tilMobileNumber;
    }

    @Override
    public EditText getetFirstName() {
        return etFirstName;
    }

    @Override
    public EditText getetLastName() {
        return etLastName;
    }

    @Override
    public EditText getetEmail() {
        return etEmail;
    }

    @Override
    public EditText getPhone() {
        return etMobileNumber;
    }

    @Override
    public void setPresenter(AddContactPresenter presenter) {
        if(presenter!=null){
            mPresenter = presenter;
        }
    }

    public void requestAllPermission(){
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void requestSinglePermission(String perm){
        if(!hasPermissions(this, perm)){
            ActivityCompat.requestPermissions(this, new String[]{perm}, PERMISSION_ALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 ){
                    for(int i=0; i<grantResults.length ; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            requestSinglePermission(permissions[i]);
                        }
                    }
                }
                break;
        }
    }
}
