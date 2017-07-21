package me.haroldmartin.firebaseextensions.db;


import android.arch.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class FirebaseLiveData<T> extends LiveData<Resource<T>> {
    private final DatabaseReference dbRef;
    private final Class clazz;

    public FirebaseLiveData(DatabaseReference dbRef, Class<T> clazz) {
        this.dbRef = dbRef;
        this.clazz = clazz;
        setValue(Resource.loading(null));
    }

    ValueEventListener updateListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            setValue(Resource.success((T) dataSnapshot.getValue(clazz)));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            setValue(Resource.error(databaseError.getDetails(), null));
        }
    };

    @Override
    protected void onActive() {
        dbRef.addValueEventListener(updateListener);
    }

    @Override
    protected void onInactive() {
        if (!hasActiveObservers()) {
            dbRef.removeEventListener(updateListener);
        }
    }
}
