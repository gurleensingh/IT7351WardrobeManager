package com.example.wardrobemanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.RelativeLayout;

/*
 * LinearLayoutThatDetectsSoftKeyboard - a variant of LinearLayout that can detect when 
 * the soft keyboard is shown and hidden (something Android can't tell you, weirdly). 
 */

public class CustomLayout extends RelativeLayout {

	public CustomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public interface Listener {
		public void onSoftKeyboardShown(boolean isShowing);
	}
	private Listener listener;
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		Activity activity = (Activity)getContext();
		
		Rect rect = new Rect();
		
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		
		int statusBarHeight = rect.top;
		
		Display display = activity.getWindowManager().getDefaultDisplay();
		
		Point size = new Point();
		
		display.getSize(size);
		
		int screenHeight = size.y;
		
		int diff = (screenHeight - statusBarHeight) - height;
		
		if (listener != null)
		{
			listener.onSoftKeyboardShown(diff>128); // assume all soft keyboards are at least 128 pixels high
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);       
	}

}