package com.example.shenhaichen.capstone_project_accountbook.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shenhaichen.capstone_project_accountbook.R;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class KeyBoardUtil {


    private Activity mActivity;
    private EditText mEditText;
    private Context mContext;
    private Keyboard numberKeyBoard;
    private KeyboardView mKeyBoard;
    private Button mBtn_save, mBtn_more;
    private Double previousValue, currentValue;
    private static boolean PLUSFLAG = false, MINUSFLAG = false, EQUALFLAG = false, CLEARFLAG = false;

    public KeyBoardUtil(Activity activity, EditText editText, Context context, Button btn_more, Button btn_save) {
        this.mActivity = activity;
        this.mContext = context;
        this.mEditText = editText;
        this.mBtn_more = btn_more;
        this.mBtn_save = btn_save;

        numberKeyBoard = new Keyboard(mContext, R.xml.number_symbos);
        mKeyBoard =  activity.findViewById(R.id.keyboard_view);
        mKeyBoard.setKeyboard(numberKeyBoard);
        mKeyBoard.setEnabled(true);
        mKeyBoard.setPreviewEnabled(false);
        mKeyBoard.setOnKeyboardActionListener(listener);

    }

    /**
     *  this main listener for the customize keyboard, especially to get the value and calculate the number
     */
    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
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
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            if (primaryCode == -3) {
                //Complete, click this button will hide the keyboard
                hideKeyboard();
            } else if (primaryCode == -5) {
                // delete button, click this button will delete the content
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == 50001) {
                //plus function,if the button of plus did not be pressed
                if (PLUSFLAG == false) {
                    if (!editable.toString().equals("")) {
                        previousValue = Double.parseDouble(editable.toString());
                    }
                    // click button to clear the edit text;
                    PLUSFLAG = true;
                    CLEARFLAG = true;
                }

            } else if (primaryCode == 50002) {
                //minus function, if the button of minus did not be pressed
                if (MINUSFLAG == false) {
                    if (!editable.toString().equals("")) {
                        previousValue = Double.parseDouble(editable.toString());
                    }
                    MINUSFLAG = true;
                    CLEARFLAG = true;
                }
            } else if (primaryCode == 50003) {
                //equal function, the condition is the user have to click the button of plus or minus.
                String result = null;
                if (PLUSFLAG == true || MINUSFLAG == true || previousValue != null) {
                    //to get the value first
                    if (!editable.toString().equals("")) {
                        currentValue = Double.parseDouble(editable.toString());
                    }
                    // then to judge user's action, plus or minus the number.
                    if (PLUSFLAG == true) {
                        editable.clear();
                        double getNum = currentValue + previousValue;
                        result = String.valueOf(getNum);
                        PLUSFLAG = false;
                    } else if (MINUSFLAG == true) {
                        editable.clear();
                        double getNum = previousValue - currentValue;
                        result = String.valueOf(getNum);
                        MINUSFLAG = false;
                    }
                    //show the result on the editText
                    editable.insert(editable.length(), result);
                    EQUALFLAG = true;
                } else {
                    if (!editable.toString().equals("")) {
                        previousValue = Double.parseDouble(editable.toString());
                    }
                }
            } else {
                //insert the content of user input
                if (CLEARFLAG == true){
                    editable.clear();
                    CLEARFLAG = false;
                }else if (EQUALFLAG == true) {
                    editable.clear();
                    EQUALFLAG = false;
                }
                String newValue = Character.toString((char) primaryCode);
                editable.insert(editable.length(), newValue);
            }
        }
    };

    public void hideKeyboard() {
        int visibility = mKeyBoard.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyBoard.setVisibility(View.GONE);
        }
        mBtn_save.setVisibility(View.VISIBLE);
        mBtn_more.setVisibility(View.VISIBLE);

    }

    public void showKeyboard() {
        int visibility = mKeyBoard.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyBoard.setVisibility(View.VISIBLE);
        }
        mBtn_save.setVisibility(View.GONE);
        mBtn_more.setVisibility(View.GONE);
    }
}
