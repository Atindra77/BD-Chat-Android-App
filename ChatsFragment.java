package com.example.bdchat.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bdchat.Adapter.UsersAdapter;
import com.example.bdchat.databinding.FragmentChats2Binding;
import com.example.bdchat.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatsFragment extends Fragment {



    public ChatsFragment() {
        // Required empty public construct

    }
        FragmentChats2Binding binding;
    ArrayList<Users>list= new ArrayList<>();
    FirebaseDatabase database;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentChats2Binding.inflate(inflater, container,false);
        database=FirebaseDatabase.getInstance();
        UsersAdapter adapter=  new UsersAdapter(list, getContext());

        binding.ChatRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        binding.ChatRecyclerView.setLayoutManager(layoutManager);
         database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 list.clear();
                 for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                     Users users= dataSnapshot.getValue(Users.class);
                     users.setUserId(dataSnapshot.getKey());
                     list.add(users);

                 }
                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        // Inflate the layout for this fragment
        return binding.getRoot();

    }
}