package com.ylx.zxingtestdemo.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylx.zxingtestdemo.R;
import com.ylx.zxingtestdemo.base.BasicActivity;
import com.ylx.zxingtestdemo.scanner.common.Scanner;
import com.ylx.zxingtestdemo.scanner.result.ISBNResult;
import com.ylx.zxingtestdemo.scanner.result.ProductResult;

import java.io.Serializable;

public class BarcodeActivity extends BasicActivity {
    private static final String TAG = "BarcodeActivity";
    private ImageView mImageView;
    private TextView mTextView4, mTextView5, mTextView6, mTextView7, mTextView8, mTextView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        mTextView4 = (TextView) findViewById(R.id.textView4);
        mTextView5 = (TextView) findViewById(R.id.textView5);
        mTextView6 = (TextView) findViewById(R.id.textView6);
        mTextView7 = (TextView) findViewById(R.id.textView7);
        mTextView8 = (TextView) findViewById(R.id.textView8);
        mTextView9 = (TextView) findViewById(R.id.textView9);
        mImageView = (ImageView) findViewById(R.id.imageView2);

        Bundle extras = getIntent().getExtras();
        if (extras == null) finish();

        Serializable serializable = extras.getSerializable(Scanner.Scan.RESULT);
        if (serializable == null) finish();

        String productID = "";
        if (serializable instanceof ProductResult) {
            ProductResult productResult = (ProductResult) serializable;
            productID = productResult.getProductID();
        } else if (serializable instanceof ISBNResult) {
            ISBNResult isbnResult = (ISBNResult) serializable;
            productID = isbnResult.getISBN();
        }

        //根据ID请求商品
        mTextView4.setText("扫码结果为：" + productID);

    }

    public static void gotoActivity(Activity activity, Bundle bundle) {
        activity.startActivity(new Intent(activity, BarcodeActivity.class).putExtras(bundle));
    }
}
