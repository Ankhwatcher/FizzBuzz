package ie.appz.fizzbuzz;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class FizzBuzzActivity extends Activity {
	private MediaPlayer fizzMP, buzzMP;
	private TableLayout displayTable;
	private String srowText, srowNumb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		displayTable = (TableLayout) findViewById(R.id.table);
		fizzMP = MediaPlayer.create(FizzBuzzActivity.this, R.raw.fizz);
		buzzMP = MediaPlayer.create(FizzBuzzActivity.this, R.raw.buzz);
		new Thread(mUpdate).start();
	}
	
	

	private Runnable mUpdate = new Runnable() {
		public void run() {
			for (int i = 1; i < 101; i++) {
				srowText = "";
				srowNumb = i + "|";

				srowText = "";
				if (i % 3 == 0) {
					fizzMP.start();
					srowText += "Fizz";
				}

				if (i % 5 == 0) {
					srowText += "Buzz";
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(250);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							buzzMP.start();

						}
					}).start();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						TableRow newRow = new TableRow(FizzBuzzActivity.this);
						TextView rowNumb = new TextView(FizzBuzzActivity.this);
						TextView rowText = new TextView(FizzBuzzActivity.this);
						rowNumb.setText(srowNumb);
						rowText.setText(srowText);
						newRow.addView(rowNumb, 0);
						newRow.addView(rowText, 1);
						displayTable.addView(newRow,
								new TableLayout.LayoutParams(
										LayoutParams.FILL_PARENT,
										LayoutParams.WRAP_CONTENT));
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			complete(buzzMP, fizzMP);
		}

		void complete(MediaPlayer MP1, MediaPlayer MP2) {
			MP1.release();
			MP2.release();
		}
	};
}