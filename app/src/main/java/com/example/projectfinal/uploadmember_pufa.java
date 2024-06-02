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

public class uploadmember_pufa extends AppCompatActivity {

    ImageView uploadimage;
    Button savebutton;
    EditText studentname, studentid, Batch;

    String imageURL;

    Uri uri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadactivity);

        uploadimage = findViewById(R.id.uploadimage);
        studentname = findViewById(R.id.studentname);
        studentid = findViewById(R.id.studentid);
        Batch = findViewById(R.id.batch);
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
                            Toast.makeText(uploadmember_pufa.this, "No Image Selected", Toast.LENGTH_SHORT).show();

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
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("pufa vcd Member")
                .child(uri.getLastPathSegment());


        AlertDialog.Builder builder = new AlertDialog.Builder(uploadmember_pufa.this);
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
        String name = studentname.getText().toString();
        String id = studentid.getText().toString();
        String batch = Batch.getText().toString();


        Data dataClass = new Data(name, id, batch, imageURL);

        //save data to database
        FirebaseDatabase.getInstance().getReference("pufa").child(name) //thatsss keyyy
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(uploadmember_pufa.this,"saved",  Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(uploadmember_pufa.this, pufa_member.class);
                            startActivity(intent);


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(uploadmember_pufa.this,e.getMessage().toString(),  Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
