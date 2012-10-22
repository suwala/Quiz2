package sample.edukaquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	static final Integer version = 1;
	static final CursorFactory factory = null;
	static private String tableName = "selectionQ";
	
	public DBHelper(Context context) {
		super(context, null, factory, version);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
	}

	//�e�[�u������Ԃ����\�b�h
	public static  String getTableName(){
		return tableName;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
		//answer�ɓ�����dummy�ɊԈႦ������@�󂯎������V���b�t��������@�e�[�u�����ǂ����悤�@�N�C�Y���Ƃɕ�����H
		db.execSQL("create table "+tableName+" ("+
				" question text not null,"+
				" answer text not null,"+
				" dummy1 text not null,"+
				" dummy2 text not null,"+
				" dummy3 text not null"+
				");"
			);
		
		
		//execsql(���,����,�_�~�[*3)��n���@���͂��܂������h�N�T�C
		db.execSQL(execsql("���{�ň�Ԗʐς��������s���{���́H","���쌧","���ꌧ","�Q�n��","�T�C�^�}�[!"));
		db.execSQL(execsql("X���𔭌������l���́H","�����g�Q��","�L�����[","�X�P���g��","�_�C�i�}�C�g"));
		db.execSQL(execsql("�b�q�����ꂪ����s���{���́H","���Ɍ�","���{","���s�{","������"));
		db.execSQL(execsql("�A���t�@�x�b�g�̗R���ƂȂ������̂̓A���t�@�Ɖ��H","�x�[�^","�r�[�^","�x�b�h","�r�[�Y"));
		db.execSQL(execsql("I Love You���u�����Y��ł��ˁv�Ɩ󂵂��l���́H","�Ėڟ���","����@�g","��{���n","�H�열�V��"));
		db.execSQL(execsql("�����ٖ̈��������d�͂����݂ɑ��鍂�M�Ȃ鏗���R�m�ƌ����΁H","���C�g�j���O","�G�A���X","�Z���X","�W�F�N�g"));
		
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

	}
	
	//execSQL�H�̈�����Ԃ����\�b�h
	public String execsql(String question,String answer,String dummy1,String dummy2,String dummy3){
				
		return "insert into "+tableName+" (question,answer,dummy1,dummy2,dummy3) values ('"+question+"','"+answer+"','"+dummy1+"', '"+dummy2+"','"+dummy3+"');";
		
	}
	

}
