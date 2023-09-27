package vn.edu.usth.facebook.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.usth.facebook.model.Users;

public class Connect_Firebase_Database {

    // declare database
    private DatabaseReference mDatabase;

    private FirebaseDatabase mFirebaseDatabase;
    public void get_Database_ref() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
    }

    // initialize database
//    public void ReadAndWriteSnippets(DatabaseReference database) {
//        // [START initialize_database_ref]
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        // [END initialize_database_ref]
//    }

    //write new user to data base
    public void writeNewUser(String user_id, String first_name, String last_name, String email){
        Users user = new Users(user_id,first_name,last_name,email);
        mDatabase.child("users").child("user_id").setValue(user_id);
        mDatabase.child("users").child(user_id).child("name").child("first_name").setValue(first_name);
    }
}
