package css.cis3334.firebaseauthentication;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //OnCreate method initializes this activity and calls seContentView to set the layout fields//
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus); //mapping the layout fields to the activity commands
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mAuth = FirebaseAuth.getInstance();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {                           //OnClick method initializes when login button is clicked//
                Log.d("CIS3334", "normal login ");          //Allows user to login with their account with their normal credentials//
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {                           //OnClick method initializes when create login button is clicked//
                Log.d("CIS3334", "Create Account ");        //Pulls the data from the email and password text fields/
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {                           //OnClick method initializes when the Google login button is clicked//
                Log.d("CIS3334", "Google login ");          //Allows user to login with their Google account//
                googleSignIn();
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {                           //OnClick method initializes when sign out button is clicked//
                Log.d("CIS3334", "Logging out - signOut "); //Allows user to sign out ofg their account//
                signOut();
            }
        });


    }

    @Override
    public void onStart() {     //OnStart method is called  when the activity becomes visible to the user//
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (currentUser != null) {
            // User is signed in
                                                    //When user is signed into the app a message displays "Signed In"//
            textViewStatus.setText("Signed In");
        } else {
            // User is signed out

            textViewStatus.setText("Signed Out");   //When user is signed out of the app a message displays "Signed Out"//
        }
    }

    private void createAccount(String email, String password) {     //createAccount method calls the data from the create account button
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {            //initializes the activity to add the user to the Firebase account
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CIS3334", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);//
                        } else {
                            // If sign in fails, display a message to the user.             //Sends a fail message if password does not meet the standards
                            Log.w("CIS3334", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            textViewStatus.setText("Signed Out");
                        }

                        // ...
                    }
                });

    }


    private void signIn(String email, String password){                         //Method initializes the activity to allow to user sign in//
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {        //Displays successful login message if user signs in successfully
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("CIS3334", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CIS334", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            textViewStatus.setText("Signed Out");
                        }

                        // ...
                    }
                });

    }

    private void signOut () {
        mAuth.signOut();

    }

    private void googleSignIn() {

    }




}
