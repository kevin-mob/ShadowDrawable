package cn.kevin.sample;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;

import cn.kevin.shadowdrawable.widget.RoundRectDrawableWithShadow;

public class SampleActivity extends AppCompatActivity implements ColorPickerDialog.OnColorChangedListener, SeekBar.OnSeekBarChangeListener {
    View selectedView;
    SeekBar sbChangeAlpha;
    SeekBar sbChangeWidth;
    int selectedColor = 0xFF53FF53;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        sbChangeAlpha = (SeekBar) findViewById(R.id.sb_change_alpha);
        sbChangeWidth = (SeekBar) findViewById(R.id.sb_change_width);
        View  hello = findViewById(R.id.tv_hello);
        selectedView = hello;
        RoundRectDrawableWithShadow shadow1 = new RoundRectDrawableWithShadow(getResources(),
                ColorStateList.valueOf(selectedColor), 8, 8);
        hello.setBackgroundDrawable(shadow1);
        int padding1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());
        hello.setPadding(padding1, padding1, padding1, padding1);

        RoundRectDrawableWithShadow shadow2 = new RoundRectDrawableWithShadow(getResources(),
                ColorStateList.valueOf(Color.YELLOW), 8, 8);
        View flRoot = findViewById(R.id.fl_root);
        flRoot.setBackgroundDrawable(shadow2);
        int padding2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        flRoot.setPadding(padding2, padding2, padding2, padding2);

        sbChangeWidth.setProgress(8);
        sbChangeAlpha.setProgress(8);
        sbChangeAlpha.setOnSeekBarChangeListener(this);
        sbChangeWidth.setOnSeekBarChangeListener(this);
    }

    public void openColorPickerDialog(View v){
        int id = v.getId();
        switch (id){
            case R.id.bt1:
                selectedView = findViewById(R.id.tv_hello);
                break;
            case R.id.bt2:
                selectedView = findViewById(R.id.fl_root);
                break;
        }

        new ColorPickerDialog(this, this, selectedColor).show();
    }

    public void changeColor(View v, int color){
        RoundRectDrawableWithShadow shadowDrawable = (RoundRectDrawableWithShadow)v.getBackground();
        shadowDrawable.setShadowStartColor(color);
    }

    @Override
    public void colorChanged(int color) {
        selectedColor = color;
        changeColor(selectedView, color);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(selectedView == null)
            return;

        RoundRectDrawableWithShadow roundRectDrawableWithShadow = (RoundRectDrawableWithShadow) selectedView.getBackground();

        int id = seekBar.getId();
        switch (id){
            case R.id.sb_change_alpha:
                selectedColor = selectedColor & 0x00FFFFFF | progress << 24;
                roundRectDrawableWithShadow.setShadowStartColor(selectedColor);
                break;
            case R.id.sb_change_width:
                roundRectDrawableWithShadow.setShadowSize(progress);
                roundRectDrawableWithShadow.setCornerRadius(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

