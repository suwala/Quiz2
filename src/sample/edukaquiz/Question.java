package sample.edukaquiz;

import sample.edukaquiz.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Question extends Activity{

    public boolean q = true;	//��ʏ�Ԃ̃t���O�@true���Ɩ�肪false���ƌ��ʂ��\������Ă���
    public Integer questions,q_Index=0;    //DB�ɓo�^����Ă��鑍��萔�̃J�E���g�@DB�̃��\�b�h�ŉ����ł���H q_Index = ���݂����ڂ��̕ێ�
    
    
    public final Integer syutudai = 3; //�o�萔
    
    public  Integer[] order;//DB��Index�����ɂ������̏o�菇�@����萔���V���b�t������
    public String answer;
    
    static Integer a_c; //���𐔂̃J�E���g
    public String mondai; 
    
    private Handler timerHandler = new Handler();
    private Handler deleteHandler = new Handler();
    private long start;
    	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        
        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getReadableDatabase();
        String sql = "SELECT COUNT(*) from "+DBHelper.getTableName();
        
        //rawQuery�͐���SQL�����g����@�ȒP�I
        Cursor cursor = db.rawQuery(sql,null);
        
        //cursor�̎����N���[�Y���[�h�H�J����index�����ɑS��萔���Q�b�g *�̏ꍇ�̃J���������s��
        this.startManagingCursor(cursor);
        //Integer index  = cursor.getColumnIndex("*");
        cursor.moveToFirst();
        this.questions = cursor.getInt(0);
        
        Log.d("oncre",String.valueOf(this.questions));//����萔�̈�v�m�F
        dbh.close();
        
        this.order= new Integer[questions];
        //�o�菇�̌���
        this.setOrder();
        
        this.question();
        
        //�o�ߎ��Ԃ̐ݒ�@����MAX5�b
        ProgressBar pb = (ProgressBar)this.findViewById(R.id.progressBar1);
        pb.setMax(5000);
        
        a_c = 0;
    }
	
	private Runnable CallbackTimer = new Runnable() {
		
		public void run() {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			timerHandler.postDelayed(this, 10);
			
			TextView tv = (TextView)findViewById(R.id.quetions);
			int length = (int)(System.currentTimeMillis()-start)/100;
			if(length > mondai.length())
				length = mondai.length();
			
			tv.setText(mondai.subSequence(0, length));
			
			ProgressBar pb = (ProgressBar)findViewById(R.id.progressBar1);
			pb.setProgress((int)(System.currentTimeMillis()-start));
		}
	};
	
	private Runnable CallbackDelete = new Runnable() {
        public void run() {
            /* �R�[���o�b�N���폜���Ď����������~ */
            timerHandler.removeCallbacks(CallbackTimer);
        }
    };

	
	//order[���݂̖�萔]�Ɋ�Â��Ė����擾�@�����̂�answer�Ɋi�[
	 public void question(){
		 
		 if(this.q_Index < this.syutudai){	
			 

			 //�n�}���@c.move�̓I�t�Z�b�g���ݒn����̈ړ��̂���toFirst�ōŏ��̒n�_�֖߂��K�v��������

			 DBHelper dbh = new DBHelper(this);
			 SQLiteDatabase db = dbh.getReadableDatabase();

			 Cursor c = db.query(DBHelper.getTableName(), new String[] {"question","answer","dummy1","dummy2","dummy3"}, null,null,null,null,null);
			 this.startManagingCursor(c);
			 int clmIndex;

			 Log.d("question",String.valueOf(this.q_Index));
			 boolean isEof = c.moveToFirst();
			 if(isEof){
				 //���̎擾
				 clmIndex = c.getColumnIndex("question");
				 c.move(this.order[this.q_Index]);
				 Log.d("question",String.valueOf(this.order[this.q_Index]));
				 
				 TextView tv = (TextView)findViewById(R.id.quetions);
				 //tv.setText(c.getString(clmIndex));
				 this.mondai = c.getString(clmIndex);

				 Integer[] select = this.randamSelect();
				 clmIndex = c.getColumnIndex("answer");
				 this.answer = c.getString(clmIndex);

				 for(int i=0;i<select.length;i++){
					 tv = (TextView)this.findViewById(this.getResources().getIdentifier("button"+select[i].toString(), "id", this.getPackageName()));
					 tv.setText(c.getString(clmIndex));
					 clmIndex = c.getColumnIndex("dummy"+String.valueOf(i+1));
				 }
			 }
			 dbh.close();
			 
			 //start�Ɍ��ݎ������Z�b�g���@Handler���쓮�����o�ߎ��Ԃ�\��������
			 this.start = System.currentTimeMillis();
			 this.timerHandler.postDelayed(CallbackTimer, 100);
			 
		 }else{
			 
			 Intent i = new Intent(this,Result.class);
			 this.startActivity(i);
			 
			 this.finish();
		 }
			 
	 }
	 
	public void answer(View view){
		
		
		//q��true�̎��͖��Afalse�̎��͉񓚂��\������Ă�@�Ƃ������Ƃɂ��悤
		//���\���̎��͉񓚂̏����@�񓚕\���̎��͖��̕\����2����
		
		
		if(q){
			//�o�ߎ��Ԃ̃X�g�b�v
			this.deleteHandler.postDelayed(CallbackDelete, 0);
			Button btn = (Button)view;
			
			//������btn��text���擾��db�̓����Əƍ��@���ۂŕ���
			if(btn.getText().toString().equals(this.answer)){
				
				btn = (Button) findViewById(R.id.button1);
				btn.setText("");
				btn = (Button) findViewById(R.id.button2);
				btn.setText("");
				btn = (Button) findViewById(R.id.button3);
				btn.setText("");
				btn = (Button) findViewById(R.id.button4);
				btn.setText("");
				
				btn = (Button)view;

				btn.setText("�����I");

				a_c++;
				q=false;
			}else{
				//�I����������
				btn = (Button) findViewById(R.id.button1);
				btn.setText("");
				btn = (Button) findViewById(R.id.button2);
				btn.setText("");
				btn = (Button) findViewById(R.id.button3);
				btn.setText("");
				btn = (Button) findViewById(R.id.button4);
				btn.setText("");


				btn = (Button)view;

				btn.setText("�c�O�I");

				q=false;
			}
		}else{
			this.q_Index++;
			this.question();
			this.q=true;
		}
	}

	//order�ɏo��ԍ�������@���������Ɠ�����肪�o�Ă���_�ɒ���
	public void setOrder(){
		//�z���1�`����萔������
		for(int i=0;i<this.questions;i++){
			this.order[i] = i;
		}
		
		for(int i=0;i<this.order.length;i++){
			int dst = (int)Math.floor(Math.random()*(i+1));
			this.swap(this.order,i,dst);
		}		
	}
	
	public void swap(Integer[] box,int _i,int _dst){
		int j = box[_i];
		box[_i] = box[_dst];
		box[_dst] = j;
	}
	
	public Integer[] randamSelect(){
		Integer[] select = {1,2,3,4};
		
		for(int i=0;i<select.length;i++){
			int dst = (int)Math.floor(Math.random()*(i+1));
			this.swap(select, i, dst);
		}
		return select;
	}
}
