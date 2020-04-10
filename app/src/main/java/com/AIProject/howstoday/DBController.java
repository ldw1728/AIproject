package com.AIProject.howstoday;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBController {

    public static FirebaseDatabase db;

    public DBController(){
         this.db = FirebaseDatabase.getInstance();
    }


    public static void writeBoardItemToDB(BoardItem item, final Consumer<Object> consumer){

        DatabaseReference ref = db.getReference();
        String key = ref.child("BoardItem").push().getKey();
        item.setBIKey(key);
        ref.child("BoardItem").child(key).setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                consumer.accept(null);
            }
        });
    }

    public static boolean updateChild(BoardItem item){
        DatabaseReference ref = db.getReference().child("BoardItem").child(item.getBIKey());
        if(ref.setValue(item).isSuccessful()){
            return true;
        }
        else return false;
    }
    public static void readBoardItem(final Consumer<ArrayList<BoardItem>> c){

        DatabaseReference ref = db.getReference().child("BoardItem");
        ValueEventListener postListener =  new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() != false) {
                    ArrayList<BoardItem> items = new ArrayList<>();
                  for(DataSnapshot ds : dataSnapshot.getChildren()){
                      BoardItem item = ds.getValue(BoardItem.class);
                      items.add(item);
                  }
                    c.accept(items);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ref.addValueEventListener(postListener);
    }

}


