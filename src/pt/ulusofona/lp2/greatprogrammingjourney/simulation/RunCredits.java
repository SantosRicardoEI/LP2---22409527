package pt.ulusofona.lp2.greatprogrammingjourney.simulation;

import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RunCredits {

    public static void main(String[] args) {
        GameManager gm = new GameManager();
        JPanel credits = gm.getAuthorsPanel();

        JDialog dialog = new JDialog((Frame) null, "Créditos", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        if (credits == null) {
            JPanel panelFallback = new JPanel();
            panelFallback.setLayout(new BoxLayout(panelFallback, BoxLayout.Y_AXIS));
            String[] lines = {"Altera isto", "implementando a função", "getAuthorsPanel()"};
            for (String line : lines) {
                panelFallback.add(new JLabel(line, SwingConstants.CENTER));
            }
            dialog.add(panelFallback);
        } else {
            dialog.add(credits);
        }

        Action closeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        };
        KeyStroke esc = KeyStroke.getKeyStroke(27, 0);
        dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(esc, "ESCAPE");
        dialog.getRootPane().getActionMap().put("ESCAPE", closeAction);

        dialog.setPreferredSize(new Dimension(300, 300));
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }
}
