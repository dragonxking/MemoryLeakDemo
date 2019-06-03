package demo.app.trigger;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import static android.view.WindowManager.LayoutParams.TYPE_TOAST;

public class FigLeafWindow {

    private WindowManager wManager;
    private Context context;
    private final FrameLayout container;
    private WindowManager.LayoutParams wParams;


    public FigLeafWindow(Context context) {
        this.context = context;
        container = new FrameLayout(context);
        container.setBackgroundColor(Color.BLACK);
        wManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wParams = new WindowManager.LayoutParams();
        wParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wParams.type = TYPE_TOAST;
        wParams.format = PixelFormat.RGBA_8888;
        wParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public void showWindow() {
        if (!container.isShown()) {
            wManager.addView(container, wParams);
        } else {
            wParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wManager.updateViewLayout(container, wParams);
        }
    }

    public void removeWindow() {
        if (container != null && wManager != null && container.isShown()) {
            wManager.removeView(container);
        }
    }
}
