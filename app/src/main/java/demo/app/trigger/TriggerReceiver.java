package demo.app.trigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TriggerReceiver extends BroadcastReceiver {
    private String ACTION_SHOW = "fig.leaf.show";
    private String ACTION_HIDE = "fig.leaf.hide";
    FigLeafWindow figLeafWindow = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_SHOW.equals(action)) {
            if(figLeafWindow==null){
                figLeafWindow = new FigLeafWindow(context);
            }
            figLeafWindow.showWindow();
        }else if (ACTION_HIDE.equals(action)){
            if(figLeafWindow!=null){
                figLeafWindow.removeWindow();
            }
        }
    }
}
