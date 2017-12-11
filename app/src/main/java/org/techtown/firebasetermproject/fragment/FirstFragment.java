package org.techtown.firebasetermproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    String[] name_str = {"유혁", "류혁", "김태양", "초파리", "피카츄", "라이츄", "파이리", "꼬부기", "버터플","버터플","버터플","버터플","버터플","버터플"};
    String[] subname_str = {"엉터리블로그", "운동", "안녕", "D-100", "백만볼트", "천만볼트", "불", "물", "나비","버터플","버터플","버터플","버터플","버터플"};

    public FirstFragment(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        //PageFragment fragment1 = new PageFragment(page ,name_Str, location_Str, state, PhoneNum);
        this.setArguments(args);
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
            createListView(view);
        }

        return view;
    }
    public void createListView(View v) {
        listView = (ListView) v.findViewById(R.id.list_one);

        final ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < name_str.length; i++) {
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
                    Intent intent = new Intent( getActivity(), NoticeActivity.class);
                    startActivity( intent);
                    //   Toast.makeText(getActivity(), items.get(allposition), Toast.LENGTH_SHORT).show();
                    //// TODO: 2017-01-02 여기서 데이터 받고 뿌려주자 그래야 익셉션이안뜸
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
