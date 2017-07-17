package com.ylx.zxingtestdemo.picture;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;
import com.ylx.zxingtestdemo.R;
import com.ylx.zxingtestdemo.base.BasicActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 手机图片列表中的详细
 * Created by hupei on 2016/7/7.
 */
public class PickPictureActivity extends BasicActivity {
    private GridView mGridView;
    private List<String> mList;//此相册下所有图片的路径集合
    private PickPictureAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_picture);
        mGridView = (GridView) findViewById(R.id.child_grid);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setResult(mList.get(position));
            }
        });
        processExtraData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processExtraData();
    }

    private void processExtraData() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        mList = extras.getStringArrayList("data");
        if (mList.size() > 1) {
            SortPictureList sortList = new SortPictureList();
            Collections.sort(mList, sortList);
        }
        mAdapter = new PickPictureAdapter(this, mList);
        mGridView.setAdapter(mAdapter);
    }

    private void setResult(String picturePath) {
//        Intent intent = new Intent();
//        intent.putExtra(PickPictureTotalActivity.EXTRA_PICTURE_PATH, picturePath);
//        setResult(Activity.RESULT_OK, intent);
//        finish();

        Durban.with(PickPictureActivity.this)
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .inputImagePaths(picturePath)
                //.outputDirectory(cropDirectory)
                .maxWidthHeight(500, 500)
                .aspectRatio(1, 1)
                .compressFormat(Durban.COMPRESS_JPEG)
                .compressQuality(90)
                // Gesture: ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_ALL)
                .controller(Controller.newBuilder()
                        .enable(false)
                        .rotation(true)
                        .rotationTitle(true)
                        .scale(true)
                        .scaleTitle(true)
                        .build())
                .requestCode(200)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200: {
                // Analyze the list of paths after cropping.
                ArrayList<String> mImageList = Durban.parseResult(data);
                if(mImageList != null && mImageList.size() > 0){
                    Intent intent = new Intent();
                    intent.putExtra(PickPictureTotalActivity.EXTRA_PICTURE_PATH, mImageList.get(0));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

                break;
            }
        }
    }

    public static void gotoActivity(Activity activity, ArrayList<String> childList) {
        Intent intent = new Intent(activity, PickPictureActivity.class);
        intent.putStringArrayListExtra("data", childList);
        activity.startActivityForResult(intent, PickPictureTotalActivity.REQUEST_CODE_SELECT_ALBUM);
    }
}
