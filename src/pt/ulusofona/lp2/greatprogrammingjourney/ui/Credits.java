package pt.ulusofona.lp2.greatprogrammingjourney.ui;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.theme.ThemeLibrary;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.theme.ThemeType;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public final class Credits {
    private static final List<String> LINES = List.of(
            "THE GREAT PROGRAMMING JOURNEY",
            "",
            "Autor: Ricardo Santos",
            "Curso: Engenharia Informática",
            "Escola: Universidade Lusófona",
            "Ano: 2025/2026"
    );

    private Credits() {}

    public static JPanel buildPanel() {
        AnimatedTerminal panel = new AnimatedTerminal(LINES);
        return panel;
    }

    static final class AnimatedTerminal extends JPanel {
        private final String[] lines;
        private int lineIdx = 0;
        private int charIdx = 0;
        private boolean cursorOn = true;

        private Color textColor = Color.decode(GameConfig.CREDITS_TEXT_COLOR);
        private Color borderColor = Color.decode(GameConfig.CREDITS_BORDER_COLOR);
        private Color backgroundColor = Color.decode(GameConfig.CREDITS_BACKGROUND_COLOR);


        AnimatedTerminal(List<String> content) {
            setBackground(backgroundColor);
            setForeground(textColor);
            setFont(new Font("Monospaced", Font.PLAIN, 14));
            setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            this.lines = content.toArray(new String[0]);

            Thread charDraw = new Thread(() -> {
                try {
                    while (true) {
                        if (lineIdx < lines.length) {
                            charIdx++;
                            if (charIdx > lines[lineIdx].length()) {
                                lineIdx++;
                                charIdx = 0;
                            }
                        }
                        cursorOn = !cursorOn;
                        repaint();
                        Thread.sleep(35);
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();

                }
            });
            charDraw.setDaemon(true);
            charDraw.start();
        }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setColor(borderColor);
            g2.drawRoundRect(6, 6, getWidth()-12, getHeight()-12, 14, 14);

            g2.setColor(textColor);
            int y = 30;
            int lineHeight = g2.getFontMetrics().getHeight();

            String prompt = "> ";
            for (int i = 0; i < lines.length; i++) {
                String prefixo = (i == 0) ? prompt : "  ";
                String completa   = prefixo + lines[i];
                String desenhar;

                if (i < lineIdx) {
                    desenhar = completa;
                } else if (i == lineIdx) {
                    int tamanhoPrefixo = prefixo.length();
                    int keep = Math.min(tamanhoPrefixo + charIdx, completa.length());
                    desenhar = completa.substring(0, keep);
                    if (cursorOn && keep < completa.length()) {
                        desenhar += "_";
                    }
                } else {
                    desenhar = prefixo;
                }

                g2.drawString(desenhar, 16, y);
                y += lineHeight + 2;
            }

            if (lineIdx >= lines.length) {
                g2.setColor(borderColor);
                g2.setFont(getFont().deriveFont(Font.BOLD, 10f));
                String text = "[ESC para fechar]";
                int w = g2.getFontMetrics().stringWidth(text);
                g2.drawString(text, getWidth()-w-14, getHeight()-12);
            }

            g2.dispose();
        }
    }
}