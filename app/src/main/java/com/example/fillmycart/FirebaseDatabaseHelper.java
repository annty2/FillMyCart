package com.example.fillmycart;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDataBase;
    private DatabaseReference mRef;
    private List<PendingProduct> pendingProducts = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDataBase = FirebaseDatabase.getInstance();
        mRef = mDataBase.getReference("Pending");
    }

    public void readPending(final DataStatus dataStatus){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pendingProducts.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    PendingProduct p = keyNode.getValue(PendingProduct.class);
                    pendingProducts.add(p);
                }
                dataStatus.DataIsLoaded(pendingProducts,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
