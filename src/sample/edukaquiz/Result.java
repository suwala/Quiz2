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

        
        
        
        //getStringExtra �n���ꂽ�ǉ���������󂯎��@getStringExtra("keyword") �Ԃ�l���n����������ۂ�
        //static�ł������ł��邱�Ƃ����������ƌ�����
        
        //String data = i.getStringExtra("a_count");

        
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText("���Ȃ��̐��𐔂�"+Question.a_c.toString()+"�ł��I");
        
    }
	
	public void toTitle(View view){
		Intent i=new Intent(this,MainActivity.class);
		//�J�ڐ�̃A�N�e�B�r�e�B���ғ��ς݂̏ꍇ�������ɂ���A�N�e�B�r�e�B���L������
		//�v����Ɍ��ʉ�ʁ��o�b�N�L�[�����̉�ʂɖ߂�̂�h��
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//�ǂ����J�������ׂ邱��i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		this.startActivity(i);
	}

}
