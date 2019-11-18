package com.example.howstoday;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class AcceptIMGActivity extends AppCompatActivity {

    private static final int CAMERA = 0;
    private static final int ALBUM = 1;

    public static Activity AccepIMGActivity = null;

    private Bitmap userImage;
    private ImageButton btn_camera;
    private ImageButton btn_album;
    private ImageButton btn_cancel;
    private Button btn_start;
    private ImageView userImgView;
    private String UUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_acceptimg);
        requestPermission();
        new DBController();
        AccepIMGActivity = AcceptIMGActivity.this;
        UUID = GetDevicesUUID(getApplicationContext());

        replaceFragment(null);

        btn_album = (ImageButton) findViewById(R.id.btn_album);
        userImgView = (ImageView) findViewById(R.id.img_user);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setEnabled(false);


        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, ALBUM);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_start.isEnabled()) {
                    Intent intent = new Intent(AcceptIMGActivity.this, LoadingActivity.class);
                    String img_str = ImgHandler.bitmapToString(userImage);
                    SharedPreferences sp = getSharedPreferences("userImage", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("img", img_str);
                    editor.commit();
                    intent.putExtra("UUID", UUID);
                    startActivity(intent);
                }
                else Toast.makeText(getApplicationContext(), "사진을 등록해 주세요", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        else if (requestCode == CAMERA && data != null) { //카메라로 찍은 사진
            Bundle b = data.getExtras();

            userImage = (Bitmap) b.get("data");
            userImgView.setImageBitmap(userImage);
        } else if (requestCode == ALBUM && data != null) { //앨범에서 가져온 사진
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                userImage = BitmapFactory.decodeStream(in);
                in.close();

                userImgView.setImageBitmap(userImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (userImage != null) {

            btn_start.setEnabled(true);
        }
        else{
            Error.errorAlert("오류 발생(userImg=null)", null, null);
        }
    }

    private String GetDevicesUUID(Context mContext) {
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AcceptIMGActivity.this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},0);
            return null;
        }
        String deviceId = tManager.getDeviceId();
        return deviceId;
    }

    static void goToOtherActivity(Activity activity, Class otherActivity, Map map){
        Intent intent = new Intent(activity,otherActivity);
        if(map != null){
           Set<String> keys = map.keySet();
            Iterator<String> it = keys.iterator();
            while(it.hasNext()){
                String key = it.next();
               if(isString(map.get(key))){
                   intent.putExtra(key, (String) map.get(key));
               }else{
                   intent.putExtra(key, (Serializable) map.get(key));
               }
            }
        }
        activity.startActivity(intent);
        //activity.finish();
    }

    public static boolean isString(Object o){
        try{
             o = (BoardItem)o;
             return false;
        }catch (Exception e){
            return true;
        }
    }

   public void replaceFragment(Bitmap userIMG){
       FragmentManager fm = getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(userIMG == null){
            userImage = null;
            fragmentTransaction.replace(R.id.frg_frame, new CameraFragment()).commit();
        }
        else {
            this.userImage = userIMG;
            if(userImage != null){
                btn_start.setEnabled(true);
            }
            else
                btn_start.setEnabled(false);

            fragmentTransaction.replace(R.id.frg_frame, new UserIMGFragment()).commit();
        }
   }


   public Bitmap getUserImage(){
        return this.userImage;
   }

   public void requestPermission(){
       int permissonCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

       if(permissonCheck == PackageManager.PERMISSION_GRANTED){

       }else{

           //권한설정 dialog에서 거부를 누르면
           //ActivityCompat.shouldShowRequestPermissionRationale 메소드의 반환값이 true가 된다.
           //단, 사용자가 "Don't ask again"을 체크한 경우
           //거부하더라도 false를 반환하여, 직접 사용자가 권한을 부여하지 않는 이상, 권한을 요청할 수 없게 된다.
           if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
               //이곳에 권한이 왜 필요한지 설명하는 Toast나 dialog를 띄워준 후, 다시 권한을 요청한다.
               Toast.makeText(getApplicationContext(), "파일 쓰기 권한이 필요합니다", Toast.LENGTH_SHORT).show();
               ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
           }else{
               ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
           }
       }

   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 0 :
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "권한 승인", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(), "권한 거부", Toast.LENGTH_LONG).show();
                break;
        }
    }
}


