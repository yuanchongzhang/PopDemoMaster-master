package com.jiuxin.popdemomaster;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout xialaLayout;

    private ArrayList<String> list = new ArrayList<>();

    private AnswerQuestionAdapter myAdapter;


    private PopupWindow popLeft;

    private View layoutLeft;

    // 左中右三个ListView控件（弹出窗口里）
    private ListView menulistLeft;

    TextView tvLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xialaLayout = (LinearLayout) findViewById(R.id.xiala_layout);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        xialaLayout.setOnClickListener(this);
        initData();
    }


    private void initData() {
        for (int i = 0; i < 15; i++) {
            list.add(1 + i + "");
        }

    }


    public void getpullData() {
        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {
            layoutLeft = getLayoutInflater().inflate(
                    R.layout.pop_menulist, null);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);

            myAdapter = new AnswerQuestionAdapter(MainActivity.this);
            menulistLeft.setAdapter(myAdapter);


            menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvLeft.setText(list.get(position));
                    // popupWindow.dismiss();
                    popLeft.dismiss();
                }
            });
            popLeft = new PopupWindow(layoutLeft, xialaLayout.getWidth(),
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            ColorDrawable cd = new ColorDrawable(-0000);
            popLeft.setBackgroundDrawable(cd);
            popLeft.setAnimationStyle(R.style.PopupAnimation);
            popLeft.update();
            popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popLeft.setTouchable(true); // 设置popupwindow可点击
            popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popLeft.setFocusable(true); // 获取焦点

            // 设置popupwindow的位置（相对tvLeft的位置）
            //  int topBarHeight = rlTopBar.getBottom();
//                        popLeft.showAsDropDown(tvLeft, 0,
//                                (topBarHeight - tvLeft.getHeight()) / 2);
            popLeft.showAsDropDown(xialaLayout, 0, 0);
            popLeft.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popLeft.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiala_layout:
                getpullData();

                break;
        }
    }
}
