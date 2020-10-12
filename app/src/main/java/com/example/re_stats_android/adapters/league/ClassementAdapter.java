package com.example.re_stats_android.adapters.league;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.re_stats_android.R;
import com.example.re_stats_android.adapters.clubs.ClubsNameIdentityAdapter;
import com.example.re_stats_android.models.ClubsModel;
import com.example.re_stats_android.models.IClubsListManagment;
import com.example.re_stats_android.models.ClassementData;
import com.example.re_stats_android.provider.ClubsListDataImpl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ClassementAdapter extends RecyclerView.Adapter<ClassementAdapter.MyViewHolder> {

    Context mContext;
    List<ClassementData> mClassement;
    String league;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("teams");

    public ClassementAdapter(Context mContext, List<ClassementData> mClassement, String league) {
        this.mContext = mContext;
        this.mClassement = mClassement;
        this.league = league;
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        //ImageView clubLogo;
        TextView clubs;
        TextView mj;
        TextView mg;
        TextView mn;
        TextView mp;
        TextView bm;
        TextView bc;
        TextView diff;
        TextView points;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //identityAdapter
            clubs = itemView.findViewById(R.id.identity);
            mg = itemView.findViewById(R.id.MG);
            mj = itemView.findViewById(R.id.MJ);
            mp = itemView.findViewById(R.id.MP);
            mn = itemView.findViewById(R.id.MN);
            bc = itemView.findViewById(R.id.BC);
            bm = itemView.findViewById(R.id.BM);
            points = itemView.findViewById(R.id.Total);
            diff = itemView.findViewById(R.id.Diff);
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.classement_item, parent, false);
        return new ClassementAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ClassementData classementData = mClassement.get(position);
        String cbl = classementData.getClubs();
        holder.clubs.setText(cbl);
        holder.mj.setText((CharSequence) String.valueOf(classementData.getJoue()));
        holder.mg.setText((CharSequence) String.valueOf(classementData.getGagne()));
        holder.mn.setText((CharSequence) String.valueOf(classementData.getNul()));
        holder.mp.setText((CharSequence) String.valueOf(classementData.getPerdu()));
        holder.bm.setText((CharSequence) String.valueOf(classementData.getBut_pour()));
        holder.bc.setText((CharSequence) String.valueOf(classementData.getBut_contre()));
        holder.points.setText((CharSequence) String.valueOf(classementData.getPts()));
        holder.diff.setText((CharSequence) String.valueOf(classementData.getBut_pour() - classementData.getBut_contre()));
        //readTeams(holder);
    }


    @Override
    public int getItemCount() {
        return mClassement.size();
    }

//    private void readTeams(final MyViewHolder holder){
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<ClubsModel> clubsModels = new ArrayList<>();
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    clubsModels.add(dataSnapshot.getValue(ClubsModel.class));
//                }
//                IClubsListManagment managment = new ClubsListDataImpl();
//                Map<String,String> filtredUrlMap = managment.clubListFilterByName(mClassement,clubsModels);
//                holder.clubLogo.setImageDrawable(LoadImageFromWebOperations(filtredUrlMap.get(holder.clubs.getText().toString())));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//    }


    public Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
