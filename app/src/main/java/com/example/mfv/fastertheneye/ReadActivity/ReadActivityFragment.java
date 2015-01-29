package com.example.mfv.fastertheneye.ReadActivity;

/**
 * Created by mfv on 27.01.15.
 */

import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.example.mfv.fastertheneye.Engine.TextStreamer;
import com.example.mfv.fastertheneye.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReadActivityFragment extends Fragment
{

	private TextView leftTextView;
	private TextView pivotTextView;
	private TextView rightTextView;
	private TextView nextTextView;

	private String testString = "похожие на кожу шарпея, тосканские популярные закуски лампредотто и триппа не сказать, чтобы уж просятся в рот. Да и перечень ингредиентов не разжигает аппетита: это вывернутые наизнанку вареные коровьи желудки. Но что-то флорентийцы в этой снеди находят, раз обедают ею аж с XV века. Триппу и лампредотто продают на центральных площадях, в чистом виде или в сэндвичах с помидорами и луком. Если хочется заправиться «быстрыми сахарами», стоит попробовать фрителле-ди-ризо — похожие на пончики шарики из риса, изюма, орехов, молока, которые особенно хорошо идут в прохладную погоду.";

	Timer timer;
	TextStreamer textStreamer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		leftTextView = (TextView)rootView.findViewById(R.id.leftTextView);
		pivotTextView = (TextView)rootView.findViewById(R.id.pivotTextView);
		rightTextView = (TextView)rootView.findViewById(R.id.rightTextView);
		nextTextView = (TextView)rootView.findViewById(R.id.nextTextView);

		textStreamer = new TextStreamer(testString);
		timer = new Timer();
		timer.schedule(new UpdateScreen(), 1000, 500);

		return rootView;
	}

	private class UpdateScreen extends TimerTask
	{

		@Override
		public void run()
		{
			final TextStreamer.Box box = textStreamer.next();
			getActivity().runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					if(leftTextView != null)
					{
						leftTextView.setText(box.leftPart);
						pivotTextView.setText(box.pivot);
						rightTextView.setText(box.rightPart);
						//TODO убрать костыль " "
						nextTextView.setText(" " + box.nextFrame);
					}
				}
			});
		}
	}


}