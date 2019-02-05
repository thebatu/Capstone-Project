package com.example.bats.homefoodie.ui.createDish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.bats.homefoodie.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDish extends AppCompatActivity {

    @BindView(R.id.add_ingredient_btn)
    Button add_ingredient_btn;
    @BindView(R.id.activity_add_dish)
    ScrollView scrollView;
    @BindView(R.id.ingredient_list)
    ListView mListView;
    @BindView(R.id.ingredient_et)
    EditText ingredient_et;
    public static final int PICK_IMAGE = 1;
    private Context context;
    //adapter for addIngredient
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        ButterKnife.bind(this);
        context = this;

        mAdapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        //add dish ingredient to listView
        add_ingredient_btn.setOnClickListener(view -> {
            String item = ingredient_et.getText().toString();
            mAdapter.add(item);
            mAdapter.notifyDataSetChanged();
            ingredient_et.setText("");
        });

    }

    /**
     * select a dish image intent
     */
    private void dishImageProfile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    /**
     *
     * @param requestCode identifier
     * @param resultCode result code
     * @param data image
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }else {
                try {
                    //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
                    InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void gatherDishInfo(ViewGroup viewGroup){
        SparseArray<EditText> array = new SparseArray<EditText>();

        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view1 = viewGroup.getChildAt(i);
            if (view1 instanceof LinearLayout)
                gatherDishInfo((ViewGroup) view1);
             else if (view1 instanceof EditText) {
                 EditText edittext = (EditText) view1;
                array.put(edittext.getId(), edittext);
            }
        }

    }






}
