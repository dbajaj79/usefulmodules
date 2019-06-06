package com.example.recyclerviewswipanddelete;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.recyclerviewswipanddelete.adapter.PersonAdapter;
import com.example.recyclerviewswipanddelete.databinding.ActivityMainBinding;
import com.example.recyclerviewswipanddelete.model.Person;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setAdapter();
    }

    private void setAdapter()
    {
        PersonAdapter personAdapter = new PersonAdapter(this,getPersonList());
        activityMainBinding.setAdapter(personAdapter);
    }


    private List<Person> getPersonList()
    {
        List<Person>personList = new ArrayList<>();
        for(int i=0;i<20;i++)
        {
            Person person = new Person("Deepak"+i,20+i,"9914216291","deepak@gmail.com","https://images.pexels.com/photos/56866/garden-rose-red-pink-56866.jpeg");

            personList.add(person);
        }
        return personList;
    }

}
