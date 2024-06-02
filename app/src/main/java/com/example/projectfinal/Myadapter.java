package com.example.projectfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Display;
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

import de.hdodenhof.circleimageview.CircleImageView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Myadapter extends FirebaseRecyclerAdapter<Data, Myadapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Myadapter(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull Data model) {

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

//        holder.name.setText(model.getDataName());
//        holder.course.setText(model.getDataBatch());
//        holder.email.setText(model.getDataId());

        holder.name.setText(model.getDataName());
        holder.batch.setText(model.getDataBatch());
        holder.studentid.setText(model.getDataId());

        Glide.with(holder.img.getContext())
                .load(model.getDataImage())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .into(holder.img);

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();

//                dialogPlus.show();

                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.studentname);
                EditText id = view.findViewById(R.id.studentid);
                EditText batch = view.findViewById(R.id.studentbatch);
                EditText surl = view.findViewById(R.id.imageurl);

                Button btnUpdate = view.findViewById(R.id.btnupdate);

                name.setText(model.getDataName());
                id.setText(model.getDataId());
                batch.setText(model.getDataBatch());
                surl.setText(model.getDataImage());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("dataName", name.getText().toString());
                        map.put("dataId", id.getText().toString());
                        map.put("dataBatch", batch.getText().toString());
                        map.put("dataImage", surl.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("puma informatics")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(),"data updated succesfully", Toast.LENGTH_SHORT).show();
//                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.name.getContext(), "error", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                });


            }
        });


        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("deleted cant undo");

                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("puma informatics")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(),"Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showdata,parent, false);
        return new myViewHolder(view);
    }

    class  myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name, studentid, batch;

        Button btnedit, btndelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img1);

            name = (TextView)itemView.findViewById(R.id.studentname);
            studentid = (TextView)itemView.findViewById(R.id.studentid);
            batch = (TextView)itemView.findViewById(R.id.batch);

            btnedit = (Button) itemView.findViewById(R.id.btnedit);
            btndelete = (Button) itemView.findViewById(R.id.btndelete);
        }
    }
}

