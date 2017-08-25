package com.ichter.android.keyboardtem;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import java.util.List;

/**
 * Created by armor on 2/9/2017.
 * java file for the keyboard.
 * Might need to change the class name
 */

public class SimpleIME extends InputMethodService
    implements KeyboardView.OnKeyboardActionListener{

    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean caps = false;

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);

        switch (primaryCode){
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                if(caps) {
                    keyboard = new Keyboard(this, R.xml.main_caps);
                }
                else {
                    keyboard = new Keyboard(this, R.xml.main);
                }
                kv.setKeyboard(keyboard);
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case 1000:
                keyboard = new Keyboard(this, R.xml.numeric);
                kv.setKeyboard(keyboard);
                break;
            case 1001:
                keyboard = new Keyboard(this, R.xml.symbols);
                kv.setKeyboard(keyboard);
                break;
            case 1002:
                keyboard = new Keyboard(this, R.xml.main);
                kv.setKeyboard(keyboard);
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.main);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

/*    @Override
    public void onStartInputView(EditorInfo editorInfo, boolean restarting) {
        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);

        List<Keyboard.Key> keys = keyboard.getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes[0] == 770) {
                NinePatchDrawable keyBackground = (NinePatchDrawable) getResources().
                        getDrawable(R.drawable.key_diacritic_background);
                keyBackground.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                keyBackground.draw(canvas);

                key.icon.draw(canvas);
            }
        }
    }
*/
    private void playClick(int keyCode) {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (keyCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }
}
