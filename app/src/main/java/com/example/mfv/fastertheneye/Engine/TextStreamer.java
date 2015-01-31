package com.example.mfv.fastertheneye.Engine;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;

/**
 * TextStreamer - организация потококового ввывода из переданного файла.
 */
public class TextStreamer
{
	public TextStreamer(String text)
	{
		ArrayList<String> rowFrames = new ArrayList<>(Arrays.asList(text.split("[\b]")));
	}

	public Box next()
	{
		Box resultBox = new Box();
		if(currentIndex < frames.length)
		{
			resultBox = makeSeparation(frames[currentIndex]);
			if(currentIndex + 1 < frames.length)
			{
				resultBox.nextFrame = frames[currentIndex + 1];
			}
		}
		currentIndex++;
		return resultBox;
	}

	public class Box
	{
		public String leftPart;
		public String pivot;
		public String rightPart;
		public String nextFrame;

		public Box()
		{
			leftPart = "";
			pivot = "";
			rightPart = "";
			nextFrame = "";
		}
	}

	//TODO pivot должен быть аккуратен с "<letter><symbol>"
	private Box makeSeparation(String frame)
	{
		Box resultBox = new Box();
		Matcher matcher = pattern.matcher(frame);
		if(matcher.find())
		{
			String forSeparation = matcher.group(1);
			if(forSeparation.length() == 1)
			{
				resultBox.pivot = forSeparation;
			}
			if(2 <= forSeparation.length() && forSeparation.length() <= 3)
			{
				resultBox.leftPart = forSeparation.substring(0, 1);
				resultBox.pivot = forSeparation.substring(1, 2);
				resultBox.rightPart = forSeparation.substring(2);
			}
			if(4 <= forSeparation.length() && forSeparation.length() <= 6)
			{
				resultBox.leftPart = forSeparation.substring(0, 1);
				resultBox.pivot = forSeparation.substring(1, 2);
				resultBox.rightPart = forSeparation.substring(2);
			}
			if(6 <= forSeparation.length() && forSeparation.length() <= 9)
			{
				resultBox.leftPart = forSeparation.substring(0, 2);
				resultBox.pivot = forSeparation.substring(2, 3);
				resultBox.rightPart = forSeparation.substring(3);
			}
			if(10 <= forSeparation.length() && forSeparation.length() <= 14)
			{
				resultBox.leftPart = forSeparation.substring(0, 3);
				resultBox.pivot = forSeparation.substring(3, 4);
				resultBox.rightPart = forSeparation.substring(4);
				assert (resultBox.leftPart + resultBox.pivot + resultBox.rightPart).equals(forSeparation);
			}
		}
		else
		{
			resultBox.leftPart = frame;
		}
		return resultBox;
	}

	private int currentIndex = 0;
	private String[] frames;
	private Pattern pattern = Pattern.compile("\\b\\w+\\b");
}
