package me.haroldmartin.firebaseextensions.db;


import android.arch.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;


public class FirebaseStreamingLiveData<T> extends LiveData<T> {
    private final Query query;
    private final Class clazz;

    public FirebaseStreamingLiveData(Query query, Class<T> clazz) {
        this.query = query;
        this.clazz = clazz;
    }

    void setSnapshot(DataSnapshot dataSnapshot) {
        setValue((T) dataSnapshot.getValue(clazz));
    }

    ChildEventListener childListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
            setSnapshot(dataSnapshot);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            setSnapshot(dataSnapshot);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
//            Log.d(TAG, "onChildRemoved: " + dataSnapshot.getKey());
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//            Log.d(TAG, "onChildMoved: " + dataSnapshot.getKey());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
//            Log.w(TAG, "onCancelled: ", databaseError.toException());
        }
    };

    @Override
    protected void onActive() {
        query.addChildEventListener(childListener);
    }

    @Override
    protected void onInactive() {
        if (!hasActiveObservers()) {
            query.removeEventListener(childListener);
        }
    }
}
