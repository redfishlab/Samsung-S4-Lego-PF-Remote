package com.redfishlab.legopfs4v1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.os.Handler;

public class MainActivity extends Activity {
    //global var
    int d_chn = 1;
    boolean d_red_direction_cw = true;
    boolean d_blue_direction_cw = true;
    boolean d_red_up_pressed = false;
    boolean d_red_down_pressed = false;
    boolean d_blue_up_pressed = false;
    boolean d_blue_down_pressed = false;
    FF d_obj;
    private Handler d_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d_obj = new FF(this);

        //handler
        d_handler = new Handler();
        d_handler.post(F_Runnable_IR);

        //Button handlers
        //up, down, red, blue
        ImageButton t_b_redup = (ImageButton) findViewById(R.id.imageButton_up_red);
        t_b_redup.setOnTouchListener(F_Listener_B_RedUp);
        ImageButton t_b_reddown = (ImageButton) findViewById(R.id.imageButton_down_red);
        t_b_reddown.setOnTouchListener(F_Listener_B_RedDown);
        ImageButton t_b_blueup = (ImageButton) findViewById(R.id.imageButton_up_blue);
        t_b_blueup.setOnTouchListener(F_Listener_B_BlueUp);
        ImageButton t_b_bluedown = (ImageButton) findViewById(R.id.imageButton_down_blue);
        t_b_bluedown.setOnTouchListener(F_Listener_B_BlueDown);
        //directions
        ImageButton t_b_reddirection = (ImageButton) findViewById(R.id.imageButton_direction_red);
        t_b_reddirection.setOnTouchListener(F_Listener_B_RedDirection);
        ImageButton t_b_bluedirection = (ImageButton) findViewById(R.id.imageButton_direction_blue);
        t_b_bluedirection.setOnTouchListener(F_Listener_B_BlueDirection);
        //channels
        ImageButton t_b_chn1 = (ImageButton) findViewById(R.id.imageButton_chn1);
        t_b_chn1.setOnTouchListener(F_Listener_B_Chn1);
        ImageButton t_b_chn2 = (ImageButton) findViewById(R.id.imageButton_chn2);
        t_b_chn2.setOnTouchListener(F_Listener_B_Chn2);
        ImageButton t_b_chn3 = (ImageButton) findViewById(R.id.imageButton_chn3);
        t_b_chn3.setOnTouchListener(F_Listener_B_Chn3);
        ImageButton t_b_chn4 = (ImageButton) findViewById(R.id.imageButton_chn4);
        t_b_chn4.setOnTouchListener(F_Listener_B_Chn4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Runnable, fire the IR
    private Runnable F_Runnable_IR = new Runnable() {
        public void run() {
            //firing IR
            if (d_red_up_pressed && d_blue_up_pressed) d_obj.Red_Up_Blue_Up(d_chn, d_red_direction_cw, d_blue_direction_cw);
            else if (d_red_up_pressed && d_blue_down_pressed) d_obj.Red_Up_Blue_Down(d_chn, d_red_direction_cw, d_blue_direction_cw);
            else if (d_red_down_pressed && d_blue_up_pressed) d_obj.Red_Down_Blue_Up(d_chn, d_red_direction_cw, d_blue_direction_cw);
            else if (d_red_down_pressed && d_blue_down_pressed) d_obj.Red_Down_Blue_Down(d_chn, d_red_direction_cw, d_blue_direction_cw);
            else if (d_red_up_pressed) d_obj.Red_Up(d_chn, d_red_direction_cw);
            else if (d_red_down_pressed) d_obj.Red_Down(d_chn, d_red_direction_cw);
            else if (d_blue_up_pressed) d_obj.Blue_Up(d_chn, d_blue_direction_cw);
            else if (d_blue_down_pressed) d_obj.Blue_Down(d_chn, d_blue_direction_cw);
            d_handler.postDelayed(this, 100);

        }
    };


    ////// Handlers for buttons //////
    //Red Up button
    private View.OnTouchListener F_Listener_B_RedUp = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_b_redup = (ImageButton) findViewById(R.id.imageButton_up_red);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    //Toast.makeText(getApplicationContext(), "B1 Down"+t_msg, Toast.LENGTH_SHORT).show();
                    t_b_redup.setImageResource(R.drawable.button_up_on);
                    d_red_up_pressed = true;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    t_b_redup.setImageResource(R.drawable.button_up_on);
                    d_red_up_pressed = true;
                    return true;
                case MotionEvent.ACTION_UP:
                    t_b_redup.setImageResource(R.drawable.button_up_off);
                    d_red_up_pressed = false;
                    return true;
            }
            return false;
        }
    };
    //Red Down button
    private View.OnTouchListener F_Listener_B_RedDown = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_b_reddown = (ImageButton) findViewById(R.id.imageButton_down_red);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    t_b_reddown.setImageResource(R.drawable.button_down_on);
                    d_red_down_pressed = true;
//
                    return true;
                case MotionEvent.ACTION_MOVE:
                    t_b_reddown.setImageResource(R.drawable.button_down_on);
                    d_red_down_pressed = true;
                    return true;
                case MotionEvent.ACTION_UP:
                    t_b_reddown.setImageResource(R.drawable.button_down_off);
                    d_red_down_pressed = false;
                    return true;
            }
            return false;
        }
    };
    //Blue Up button
    private View.OnTouchListener F_Listener_B_BlueUp = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button = (ImageButton) findViewById(R.id.imageButton_up_blue);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    t_button.setImageResource(R.drawable.button_up_on);
                    d_blue_up_pressed = true;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    t_button.setImageResource(R.drawable.button_up_on);
                    d_blue_up_pressed = true;
                    return true;
                case MotionEvent.ACTION_UP:
                    t_button.setImageResource(R.drawable.button_up_off);
                    d_blue_up_pressed = false;
                    return true;
            }
            return false;
        }
    };
    //Blue Down button
    private View.OnTouchListener F_Listener_B_BlueDown = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button = (ImageButton) findViewById(R.id.imageButton_down_blue);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    t_button.setImageResource(R.drawable.button_down_on);
                    d_blue_down_pressed = true;
                    return true;
                case MotionEvent.ACTION_MOVE:
                    t_button.setImageResource(R.drawable.button_down_on);
                    d_blue_down_pressed = true;
                    return true;
                case MotionEvent.ACTION_UP:
                    t_button.setImageResource(R.drawable.button_down_off);
                    d_blue_down_pressed = false;
                    return true;
            }
            return false;
        }
    };
    //Red Direction
    private View.OnTouchListener F_Listener_B_RedDirection = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button = (ImageButton) findViewById(R.id.imageButton_direction_red);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    if (d_red_direction_cw) {
                        d_red_direction_cw = false;
                        t_button.setImageResource(R.drawable.button_direction2);
                    }
                    else {
                        d_red_direction_cw = true;
                        t_button.setImageResource(R.drawable.button_direction1);
                    }
                    return true;
            }
            return false;
        }
    };
    //Blue Direction
    private View.OnTouchListener F_Listener_B_BlueDirection = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button = (ImageButton) findViewById(R.id.imageButton_direction_blue);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    if (d_blue_direction_cw) {
                        d_blue_direction_cw = false;
                        t_button.setImageResource(R.drawable.button_direction2);
                    }
                    else {
                        d_blue_direction_cw = true;
                        t_button.setImageResource(R.drawable.button_direction1);
                    }
                    return true;
            }
            return false;
        }
    };
    //Channel 1
    private View.OnTouchListener F_Listener_B_Chn1 = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button_1 = (ImageButton) findViewById(R.id.imageButton_chn1);
            ImageButton t_button_2 = (ImageButton) findViewById(R.id.imageButton_chn2);
            ImageButton t_button_3 = (ImageButton) findViewById(R.id.imageButton_chn3);
            ImageButton t_button_4 = (ImageButton) findViewById(R.id.imageButton_chn4);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    d_chn = 1;
                    t_button_1.setImageResource(R.drawable.button_chn1_on);
                    t_button_2.setImageResource(R.drawable.button_chn2_off);
                    t_button_3.setImageResource(R.drawable.button_chn3_off);
                    t_button_4.setImageResource(R.drawable.button_chn4_off);
                    return true;
            }
            return false;
        }
    };
    //Channel 2
    private View.OnTouchListener F_Listener_B_Chn2 = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button_1 = (ImageButton) findViewById(R.id.imageButton_chn1);
            ImageButton t_button_2 = (ImageButton) findViewById(R.id.imageButton_chn2);
            ImageButton t_button_3 = (ImageButton) findViewById(R.id.imageButton_chn3);
            ImageButton t_button_4 = (ImageButton) findViewById(R.id.imageButton_chn4);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    d_chn = 2;
                    t_button_1.setImageResource(R.drawable.button_chn1_off);
                    t_button_2.setImageResource(R.drawable.button_chn2_on);
                    t_button_3.setImageResource(R.drawable.button_chn3_off);
                    t_button_4.setImageResource(R.drawable.button_chn4_off);
                    return true;
            }
            return false;
        }
    };
    //Channel 3
    private View.OnTouchListener F_Listener_B_Chn3 = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button_1 = (ImageButton) findViewById(R.id.imageButton_chn1);
            ImageButton t_button_2 = (ImageButton) findViewById(R.id.imageButton_chn2);
            ImageButton t_button_3 = (ImageButton) findViewById(R.id.imageButton_chn3);
            ImageButton t_button_4 = (ImageButton) findViewById(R.id.imageButton_chn4);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    d_chn = 3;
                    t_button_1.setImageResource(R.drawable.button_chn1_off);
                    t_button_2.setImageResource(R.drawable.button_chn2_off);
                    t_button_3.setImageResource(R.drawable.button_chn3_on);
                    t_button_4.setImageResource(R.drawable.button_chn4_off);
                    return true;
            }
            return false;
        }
    };
    //Channel 4
    private View.OnTouchListener F_Listener_B_Chn4 = new View.OnTouchListener(){
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton t_button_1 = (ImageButton) findViewById(R.id.imageButton_chn1);
            ImageButton t_button_2 = (ImageButton) findViewById(R.id.imageButton_chn2);
            ImageButton t_button_3 = (ImageButton) findViewById(R.id.imageButton_chn3);
            ImageButton t_button_4 = (ImageButton) findViewById(R.id.imageButton_chn4);
            switch ( event.getAction() ) {
                case MotionEvent.ACTION_DOWN:
                    d_chn = 4;
                    t_button_1.setImageResource(R.drawable.button_chn1_off);
                    t_button_2.setImageResource(R.drawable.button_chn2_off);
                    t_button_3.setImageResource(R.drawable.button_chn3_off);
                    t_button_4.setImageResource(R.drawable.button_chn4_on);
                    return true;
            }
            return false;
        }
    };
}
