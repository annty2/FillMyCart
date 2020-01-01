package com.example.fillmycart;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelperUsers {
    private FirebaseDatabase mDataBase;
    private DatabaseReference mRef;
    private DatabaseReference mRef2;
    private List<PendingProduct> pendingProducts = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<PendingProduct> pendingProducts, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelperUsers() {
        mDataBase = FirebaseDatabase.getInstance();
        mRef = mDataBase.getReference("Products");
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

    public void deletePending(String key, final DataStatus dataStatus) {
        mRef.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
    public void updatePending(String key,PendingProduct pendingProduct, final DataStatus dataStatus) {
        mRef.child(key).setValue(pendingProduct)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsUpdated();
                    }
                });
    }
    public void addProduct(String str, PendingProduct pendingProduct,final DataStatus dataStatus){
        String key = mRef2.push().getKey();
        mRef2.child(key).setValue(pendingProduct);
        deletePending(str,dataStatus);
    }
}
