package me.haroldmartin.firebaseextensions;


import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBX {
    public static class db {
        public static DatabaseReference getBaseRef() {
            return FirebaseDatabase.getInstance().getReference();
        }

        public static DatabaseReference ref(String reference) {
            return getBaseRef().child(reference);
        }

        public static String getPathFromRef(DatabaseReference ref) {
            return Uri.parse(ref.toString()).getPath();
        }
    }

    public static class auth {
        public static FirebaseUser getCurrentUser() {
            return FirebaseAuth.getInstance().getCurrentUser();
        }

        public static String getCurrentUserId() {
            FirebaseUser user = getCurrentUser();
            if (user != null) {
                return user.getUid();
            }
            return null;
        }
    }
}
