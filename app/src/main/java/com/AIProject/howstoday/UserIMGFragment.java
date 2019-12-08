package com.AIProject.howstoday;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserIMGFragment extends Fragment {

    private ImageView iv_userIMG;
    private Bitmap userIMG;
    private ImageButton ib_cancel;

    public UserIMGFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_img, container, false);

        iv_userIMG = v.findViewById(R.id.img_user);
        ib_cancel =v.findViewById(R.id.btn_cancel);

        iv_userIMG.setVisibility(View.VISIBLE);

        ib_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AcceptIMGActivity)getActivity()).replaceFragment(null);
            }
        });

        userIMG = ((AcceptIMGActivity)getActivity()).getUserImage();

        iv_userIMG.setImageBitmap(userIMG);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iv_userIMG.setVisibility(View.INVISIBLE);
    }
}
