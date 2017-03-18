package com.ichter.android.keyboardtem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by armor on 3/18/2017.
 */

public class MyKeyboardView extends android.inputmethodservice.KeyboardView {

    Context context;

    public MyKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(40);

        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes[0] == 770) {
                NinePatchDrawable npd = (NinePatchDrawable) context.getResources().
                        getDrawable(R.drawable.key_diacritic_background);
                npd.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                npd.draw(canvas);

                canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                        key.y + 25, paint);

                key.icon.draw(canvas);
            }
        }
    }
}

//        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Hippie.otf");
//        paint.setTypeface(font);