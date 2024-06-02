package com.example.projectfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

public class LecturervcdAdapter extends FirebaseRecyclerAdapter<Datalecturer, LecturervcdAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LecturervcdAdapter(@NonNull FirebaseRecyclerOptions<Datalecturer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull Datalecturer model) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            if (userEmail != null && (userEmail.equals("admin@gmail.com") || userEmail.equals("admin123@gmail.com"))) {
                // Admin users are allowed to see and interact with the buttons
                holder.btnedit.setVisibility(View.VISIBLE);
                holder.btndelete.setVisibility(View.VISIBLE);
            } else {
                // Non-admin users don't get to see or interact with the buttons
                holder.btnedit.setVisibility(View.GONE);
                holder.btndelete.setVisibility(View.GONE);
            }
        } else {
            // User is not logged in, hide the buttons
            holder.btnedit.setVisibility(View.GONE);
            holder.btndelete.setVisibility(View.GONE);
        }


        holder.lecturername.setText(model.getLecturerName());
        holder.telephone.setText(model.getTelephone());
        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getDataImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .into(holder.img);

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatelecturer_template))
                        .setExpanded(true, 1200)
                        .create();

//                dialogPlus.show();

                View view = dialogPlus.getHolderView();
                EditText lecturername = view.findViewById(R.id.lecturername);
                EditText telp = view.findViewById(R.id.telephone);
                EditText email = view.findViewById(R.id.email);
                EditText surl = view.findViewById(R.id.imageurl);

                Button btnUpdate = view.findViewById(R.id.btnupdate);

                lecturername.setText(model.getLecturerName());
                telp.setText(model.getTelephone());
                email.setText(model.getEmail());
                surl.setText(model.getDataImage());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("lecturerName", lecturername.getText().toString());
                        map.put("telephone", telp.getText().toString());
                        map.put("email", email.getText().toString());
                        map.put("dataImage", surl.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Lecturer_vcd")
                                .child(getRef(position).getKey()).updateChildren(map)

                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.lecturername.getContext(),"data updated succesfully", Toast.LENGTH_SHORT).show();
//                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.lecturername.getContext(), "error", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                });


            }
        });


        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.lecturername.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("deleted cant undo");

                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Lecturer_vcd")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.lecturername.getContext(),"Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showdata_lecturer,parent, false);
        return new myViewHolder(view);
    }

    class  myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView lecturername, telephone, email;

        Button btnedit, btndelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img1);
            lecturername = (TextView)itemView.findViewById(R.id.lecturername);
            telephone = (TextView)itemView.findViewById(R.id.telephone);
            email = (TextView)itemView.findViewById(R.id.email);

            btnedit = (Button) itemView.findViewById(R.id.btnedit);
            btndelete = (Button) itemView.findViewById(R.id.btndelete);
        }
    }
}

