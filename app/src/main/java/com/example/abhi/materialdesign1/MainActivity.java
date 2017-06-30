package com.example.abhi.materialdesign1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.view.ViewAnimationUtils;


public class MainActivity extends AppCompatActivity {

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         b = (Button)findViewById(R.id.button);

    }
    public void open(View v){
        showDialog();
    }

    private void showDialog() {
        final View dialogView  = View.inflate(this,R.layout.dialogbox,null);
        final Dialog alert= new Dialog(this);
        alert.setContentView(dialogView);
        alert.setCancelable(false);
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if(Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP)
                    alert.show();
                else
                    revealShow(dialogView,true,null);
            }
        });
        dialogView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               if(Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP)
                   alert.dismiss();
               else
                   revealShow(dialogView,false,alert);
           }
        });
        alert.show();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void revealShow(View rootView, Boolean reveal,final Dialog dialog){
        final View v = rootView.findViewById(R.id.dialog);
        int w = v.getWidth();
        int h = v.getHeight();
        float Radius = (float)Math.sqrt(w*w/4+h*h/4);
        if(reveal){
            Animator ani = ViewAnimationUtils.createCircularReveal(v,w/2,h/2,0,Radius);
            v.setVisibility(View.VISIBLE);
            ani.start();
        }else{
            Animator anim = ViewAnimationUtils.createCircularReveal(v,w/2,h/2,Radius,0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation){
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    v.setVisibility(View.INVISIBLE);
                }

            });
            anim.start();
        }
    }
}
