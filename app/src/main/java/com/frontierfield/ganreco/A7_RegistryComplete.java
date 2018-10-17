package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class A7_RegistryComplete extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a7);

        TextView btn = findViewById(R.id.buttonLoginB5);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //startActivity(new Intent(getApplicationContext(),B1_2_GanrecoMain.class));
        startActivity(new Intent(getApplicationContext(),B0_CancerTypeSelect.class));
        finish();
    }

    //「戻る」ボタン無効化
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ダイアログ表示など特定の処理を行いたい場合はここに記述
                    // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
