package university.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener {
    private final String hint;
    private boolean showingHint;

    Font gainFont = new Font("Tahoma", Font.PLAIN, 11);
    Font lostFont = new Font("Tahoma", Font.ITALIC, 11);

    public HintTextField(final String hint) {
        super(hint);
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (getText().equals(hint)) {
            setText("");
            setFont(gainFont);
        } else {
            setText(getText());
            setFont(gainFont);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().equals(hint) || getText().length() == 0) {
            setText(hint);
            setFont(lostFont);
            setForeground(Color.GRAY);
        } else {
            setText(getText());
            setFont(gainFont);
            setForeground(Color.BLACK);
        }
    }
}
