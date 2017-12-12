package org.techtown.firebasetermproject.fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.techtown.firebasetermproject.MainActivity;
import org.techtown.firebasetermproject.NoticeActivity;
import org.techtown.firebasetermproject.R;
import org.techtown.firebasetermproject.dto.ClassDTO;
import org.techtown.firebasetermproject.dto.PostDTO;
import org.techtown.firebasetermproject.dto.TaskDTO;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FirstFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private TextView name, subname;
    private ListView listView;
    String[] name_str = {"공지사항", "공지사항", "공지사항", "공지사항", "공지사항", "공지사항", "공지사항", "공지사항", "공지사항","공지사항","공지사항","공지사항","공지사항","공지사항"};
    String[] subname_str = {"안드로이드A", "안드로이드A", "안드로이드A", "안드로이드A", "안드로이드B", "안드로이드B", "안드로이드C", "안드로이드C",
            "안드로이드C","안드로이드C","안드로이드A","안드로이드A","안드로이드A","안드로이드A"};

    private FirebaseAuth mAuth;
    private FirebaseStorage mFirebaseStorage;
    FirebaseDatabase database;

    private RecyclerView recyclerView;
    //private MainViewAdapter adapter;
    private ArrayList<PostDTO> postDTOS = new ArrayList<PostDTO>();

    int size = 0;



    public FirstFragment(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment1 = new PageFragment(page ,name_Str, location_Str, state, PhoneNum);
        this.setArguments(args);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d("박정환", "onAttach()");

        super.onAttach(activity);





    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = null;
        if (mPage == 0) {
            view = inflater.inflate(R.layout.fragment_first, container, false);//fragment_page
            FirebaseDatabase database;
            final List<PostDTO> postDTOS = new ArrayList<>();

            database = FirebaseDatabase.getInstance();
            final View finalView = view;
            database.getReference().child("post").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // postDTOS.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        PostDTO postDTO = snapshot.getValue(PostDTO.class);
                        postDTOS.add(postDTO);
                        Log.d("박정환","추가 될때 마다 찍어라"+String.valueOf(size));
                        size++;
                        createListView(finalView);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        return view;
    }
    public void createListView(View v) {
        listView = (ListView) v.findViewById(R.id.list_one);

        ArrayList<String> items = new ArrayList<String>();
        items.clear();
        for (int i = 0; i < 9; i++) {
            items.add(name_str[i]);
        }
        CustomAdapter adapter = new CustomAdapter(getActivity(), 0, items);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;
        CustomViewHolder holder;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final int allposition = position;

            LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.custom_listview_one, null);
            holder = new CustomViewHolder();
            holder.modelName = (TextView) convertView.findViewById(R.id.listview_one_name);
            holder.m_name = (TextView) convertView.findViewById(R.id.listview_one_subname);

            holder.modelName.setText(items.get(position));
            holder.m_name.setText(subname_str[position]);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent( getContext(), NoticeActivity.class);
                    startActivity(intent);

                }
            });
            return convertView;

        }
        public class CustomViewHolder {
            public TextView modelName;
            public TextView m_name;
        }
    }

    //클릭 리스너들
    public void onClickChatting( View view){
        Intent intent = new Intent( getActivity(), NoticeActivity.class);
        startActivity( intent);
    }

}