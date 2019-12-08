package com.AIProject.howstoday;



import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment implements SurfaceHolder.Callback{

    private Camera camera;
    private Bitmap userIMG;
    private File userFile;
    private SurfaceView sv_cameraView;
    private SurfaceHolder surfaceHolder;
    private ImageButton btn_camera;
    private boolean previewing = false;


    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_camera, container, false);

        sv_cameraView = v.findViewById(R.id.sv_cameraView);
        btn_camera = v.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(msc, myPictureCallback_RAW, myPictureCallback_JPG );

            }
        });
        surfaceHolder = sv_cameraView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        return v;
    }

    Camera.ShutterCallback msc = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {

        }
    };

    Camera.PictureCallback myPictureCallback_RAW = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

        }
    };

    Camera.PictureCallback myPictureCallback_JPG = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

            try {
                /*

                File file = new File(userFile.getAbsolutePath());
                Bitmap bitmap = MediaStore.Images.Media
                        .getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                userIMG = imgRotate(bitmap);
               */
                savePicture(imgRotate(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)));

            } catch (IOException e) {
                e.printStackTrace();
            }

            ((AcceptIMGActivity)getActivity()).replaceFragment(userIMG);
            onDestroy();
        }
    };


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
        } else {

            camera = Camera.open();
            camera.setDisplayOrientation(90);

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if(previewing){
            camera.stopPreview();
            previewing = false;
        }

        if(camera != null){
            try{
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }

    public void savePicture(Bitmap data) throws IOException {

        String strg = Environment.getExternalStorageDirectory().getAbsolutePath();

        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imgFileName = timeStamp+"_";
        String path = strg+imgFileName;


        File file =  File.createTempFile(imgFileName, ".jpg", getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        Log.d("path -------", file.getAbsolutePath());
        if(!file.isDirectory()){
            file.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(file);


        data.compress(Bitmap.CompressFormat.JPEG,100,out);
        out.close();

        userIMG = data;
        userFile = file;

        Toast.makeText(getActivity(), "사진 저장", Toast.LENGTH_LONG).show();
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public Bitmap imgRotate(Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        Matrix mat = new Matrix();
        mat.postRotate(90);

        Bitmap resizeBmp = Bitmap.createBitmap(bmp, 0, 0, width, height, mat, true);
        return resizeBmp;
    }


}
