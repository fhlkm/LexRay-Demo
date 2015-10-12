package com.lextech.quiz.json.com.lextech.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lextech.adapter.MainAdapter;
import com.lextech.callback.PostProcess;
import com.lextech.model.LexData;
import com.lextech.model.Result;
import com.lextech.model.Transactions;
import com.lextech.quiz.json.R;
import com.lextech.restutil.FetchingDataTask;

import java.util.List;

public class LextechJSONQuizActivity extends AppCompatActivity implements PostProcess {

	/** Called when the activity is first created. */
	private LexData mData= null;
	private ListView mlistView = null;
	private TextView price_text = null;
	private ProgressBar mProgressBar= null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mlistView = (ListView) findViewById(android.R.id.list);
		price_text = (TextView)findViewById(R.id.text_total);
		mProgressBar = (ProgressBar)findViewById(android.R.id.empty);
		String url = getString(R.string.ws_url);
		FetchingDataTask mTask = new FetchingDataTask(LextechJSONQuizActivity.this);
		mTask.execute(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_transactions, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.refresh:
				price_text.setText("0.0");
				if(mData!= null) {
					mData.getTransactions().clear();
					((BaseAdapter) mlistView.getAdapter()).notifyDataSetChanged();
				}
				FetchingDataTask mTask = new FetchingDataTask(LextechJSONQuizActivity.this);
				String url = getString(R.string.ws_url);
				mTask.execute(url);

				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public ProgressBar getmProgressBar() {
		return mProgressBar;
	}



	@Override
	public void callback(Result mResult) {
		if(mResult.isSuccess()){
			String data = mResult.getSuccessMessage();
			Gson gson = new Gson();
			mData= gson.fromJson(data, LexData.class);
			if(null != mData) {
				MainAdapter mAdapter = new MainAdapter(LextechJSONQuizActivity.this, mData.getTransactions());
				mlistView.setAdapter(mAdapter);
				calculatePrice(mData.getTransactions());
			}

		}else{
			Toast.makeText(LextechJSONQuizActivity.this,mResult.getFailureMessage(),Toast.LENGTH_LONG).show();
		}
	}

	private void calculatePrice(List<Transactions> transactionses){
		float price =0.0f;
		for(Transactions tr:transactionses){
			price = price+ tr.getPrice();
		}
		if(price>0){
			price_text.setText(price+"");
		}


	}
}
