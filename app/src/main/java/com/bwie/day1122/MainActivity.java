package com.bwie.day1122;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private WaveView mWv;
    private ImageView mImgCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initView();
        WaveView.OnWaveChangeListener listener = new WaveView.OnWaveChangeListener() {
            @Override
            public void onChanged(float y) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mImgCursor.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, (int) y);
                mImgCursor.setLayoutParams(layoutParams);
            }
        };
        mWv.setOnWaveChangeListener( listener );
    }

    private void initView() {
        mWv = (WaveView) findViewById( R.id.wv );
        mImgCursor = (ImageView) findViewById( R.id.img_cursor );
    }
}
