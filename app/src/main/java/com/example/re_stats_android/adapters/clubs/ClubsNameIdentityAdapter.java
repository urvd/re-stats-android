package com.example.re_stats_android.adapters.clubs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.re_stats_android.R;
import com.example.re_stats_android.communs.CommonCallImpl;
import com.example.re_stats_android.communs.ICommonCall;
import com.example.re_stats_android.models.ClubsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ClubsNameIdentityAdapter extends RecyclerView.Adapter<ClubsNameIdentityAdapter.MyViewHolder> {
    Context mContext;
    List<ClubsModel> mClubsModels;
    ICommonCall commonCall;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("teams");

    public ClubsNameIdentityAdapter(Context mContext, List<ClubsModel> mClubsModels) {
        this.mContext = mContext;
        this.mClubsModels = mClubsModels;
        commonCall = new CommonCallImpl();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.identity_with_image, parent, false);
        return new ClubsNameIdentityAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ClubsModel clubsModel = mClubsModels.get(position);
        holder.name.setText(clubsModel.getName());
        holder.logo.setImageDrawable(commonCall.loadImageFromWebOperations(clubsModel.getLogo()));
    }

    @Override
    public int getItemCount() {
        return mClubsModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView name;

        RecyclerView recyclerViewClubs;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    public String getStringUrl(String clubs){
        for(ClubsModel clubsModel: mClubsModels){
            if(clubsModel.equals(clubs)){
                return clubsModel.getLogo();
            }
        }
        return null;
    }
}
