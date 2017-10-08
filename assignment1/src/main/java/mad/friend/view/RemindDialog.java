package mad.friend.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;

import hinaskye.assignment1.R;

/**
 * RemindDialog
 * Shows a dialog to pick a number to set reminder to
 */
public class RemindDialog extends Dialog {

    public RemindDialog(@NonNull Context context) {
        super(context);
    }

    public void setContents()
    {
        this.setContentView(R.layout.remind_dialog);
        this.setTitle("Remindiner");

        Button cancel = (Button) this.findViewById(R.id.remind_cancel);
        Button ok = (Button) this.findViewById(R.id.remind_ok);

        // listeners
        //cancel.setOnClickListener();
        //ok.setOnClickListener();
    }
}
