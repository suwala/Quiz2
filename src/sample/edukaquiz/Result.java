package sample.edukaquiz;

import sample.edukaquiz.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Result extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        
        
        
        //getStringExtra 渡された追加文字列を受け取る　getStringExtra("keyword") 返り値が渡した文字列ぽい
        //staticでも解決できることが判明そっと見送る
        
        //String data = i.getStringExtra("a_count");

        
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText("あなたの正解数は"+Question.a_c.toString()+"です！");
        
    }
	
	public void toTitle(View view){
		Intent i=new Intent(this,MainActivity.class);
		//遷移先のアクティビティが稼動済みの場合それより上にあるアクティビティをキルする
		//要するに結果画面＞バックキー＞問題の画面に戻るのを防ぐ
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//良くワカラン調べることi.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		this.startActivity(i);
	}

}
