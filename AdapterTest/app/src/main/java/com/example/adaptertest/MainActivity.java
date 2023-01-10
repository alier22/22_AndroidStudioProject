package com.example.adaptertest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import kotlin.LateinitKt;

public class MainActivity extends AppCompatActivity {
    ListView list;

    String[][] moiveInfo = {//영화정보
            {"The Wizard Oz","Fantasy","1939","9.5"},
            {"Citizen Kane","Mystery","1941","9.1"},
            {"All About Eve","Drama","195","9.3"},
            {"The Third Man","Thriller","1949","9.6"},
            {"A Hard Day's Night","Rock","1964","8.9"},
            {"Modern Times","Comedy","1936","9.2"},
            {"Metropolis","SF","1927","10"},
    };

    Integer[] images = { //이미지
            R.drawable.movie1,
            R.drawable.movie2,
            R.drawable.movie3,
            R.drawable.movie4,
            R.drawable.movie5,
            R.drawable.movie6,
            R.drawable.movie7,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomListAdaptor adaptor = new CustomListAdaptor(MainActivity.this, 0);//adaptor 하나 만들어서 리스트 뷰에다가 세팅
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adaptor);
    }

    class CustomListAdaptor extends ArrayAdapter<String>{
        private final Activity context;

        public CustomListAdaptor(Activity context, int resource){
            super(context,resource);
            this.context = context;
        }

        // 전체리스트뷰에 들어가는 list항목 갯수 초기화 항목
        @Override
        public int getCount() {
            return moiveInfo.length;
        }

        @NonNull
        @Override //view 그리기
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {//callback으로 불러져서 그려짐짐
           LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.item, null, true );
            ImageView image = (ImageView) rowView.findViewById(R.id.image);
            TextView title = (TextView) rowView.findViewById(R.id.title);
            TextView rating = (TextView) rowView.findViewById(R.id.rating);
            TextView genre = (TextView) rowView.findViewById(R.id.genre);
            TextView year = (TextView) rowView.findViewById(R.id.year);

            image.setImageResource(images[position]);
            title.setText(moiveInfo[position][0]);
            genre.setText(moiveInfo[position][1]);
            year.setText(moiveInfo[position][2]);
            rating.setText(moiveInfo[position][3]);

            return rowView;
        }
    }



}