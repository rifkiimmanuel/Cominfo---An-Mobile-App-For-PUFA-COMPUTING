package com.example.projectfinal;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class uploadlecturer extends AppCompatActivity {

    ImageView uploadimage;
    Button savebutton;
    EditText lecturername, telephone, email;

    String imageURL;

    Uri uri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadlecturer);

        uploadimage = findViewById(R.id.uploadimage);
        lecturername = findViewById(R.id.lecturername);
        telephone = findViewById(R.id.telephone);
        email = findViewById(R.id.email);
        savebutton = findViewById(R.id.savebutton);

        //get the data of the image from galery
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadimage.setImageURI(uri);

                        } else {
                            Toast.makeText(uploadlecturer.this, "No Image Selected", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );

        //syntax upload image from galery
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        //button save data
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

            }
        });
    }

    //save the image to firebase storage
    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Lecturer_it")
                .child(uri.getLastPathSegment());


        AlertDialog.Builder builder = new AlertDialog.Builder(uploadlecturer.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        //upload file photos to firebase storage
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while
                (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }


    //upload data to realtime database that related to the picture stored before.
    public void uploadData(){
        //
        String name = lecturername.getText().toString();
        String telp = telephone.getText().toString();
        String contemail = email.getText().toString();


        Datalecturer datalecturerClass = new Datalecturer(name, telp, contemail, imageURL);

        //save data to database
        FirebaseDatabase.getInstance().getReference("Lecturer_it").child(name) //thatsss keyyy
                .setValue(datalecturerClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(uploadlecturer.this,"saved",  Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(uploadlecturer.this, lecturerIT.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(uploadlecturer.this,e.getMessage().toString(),  Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(uploadlecturer.this, lecturerIT.class);
                        startActivity(intent);
                    }
                });
    }

}
