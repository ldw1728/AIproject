package com.AIProject.howstoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class UserBoardActivity extends AppCompatActivity {

    private ArrayList<BoardItem> items;
    private RecyclerView rv_usercardsview;
    private UserBoardAdapter adapter;
    private LinearLayoutManager lm;
    private SwipeRefreshLayout srl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_board);

        initComponent();

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                srl.setRefreshing(false);
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh();
    }



    public void refresh(){
        adapter.notifyDataSetChanged();
    }

    public void initComponent(){


        srl = findViewById(R.id.srlLayout);

        rv_usercardsview = findViewById(R.id.rv_usercardsview);
        lm = new GridLayoutManager(UserBoardActivity.this, 1);
        rv_usercardsview.setLayoutManager(lm);
        adapter = new UserBoardAdapter(this);

        items = new ArrayList<>();
        adapter.setItems(items);
        rv_usercardsview.setAdapter(adapter);

        DBController.readBoardItem(new Consumer<ArrayList<BoardItem>>() {
            @Override
            public void accept(ArrayList<BoardItem> boardItems) {
                adapter.setItems(boardItems);
                adapter.notifyDataSetChanged();
                Log.d("items count-----------", String.valueOf(boardItems.size()));
            }
        });


    }
}
