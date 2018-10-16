package team2.team2game;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Locale;

class Animations {

    private int Textures[];
    private int texture;

    private MainActivity main;

    Animations(int[] textures, MainActivity main) {
        this.Textures = textures;
        this.main = main;
    }

    void WallFall(){
        final RelativeLayout layoutBottom = main.findViewById(R.id.backgroundBottom);
        final RelativeLayout layoutTop = main.findViewById(R.id.backgroundTop);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.00f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(100000L);

        animator.addListener(new ValueAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {}

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {
                if(Textures.length-1 == texture){
                    texture  = -1;
                }
                layoutBottom.setBackground(layoutTop.getBackground());
                layoutTop.setBackground( ContextCompat.getDrawable(main,Textures[++texture]));
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = layoutBottom.getHeight();
                final float translationY = height * progress;
                layoutBottom.setTranslationY(translationY);
                layoutTop.setTranslationY(translationY - height);

                if(true){
                    Button but = main.findViewById(R.id.debugWall);
                    but.setVisibility(View.VISIBLE);
                    but.setText(String.format(Locale.getDefault(),"%.5f",progress));
                }
            }
        });
        new Handler().postDelayed(
                new Runnable(){
                    @Override
                    public void run() {
                        main.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animator.start();
                            }
                        });
                    }},1000);
    }

}
