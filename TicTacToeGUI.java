import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JButton resetButton;

    private String currentPlayer = "X";
    private boolean gameOver = false;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(4, 3));

        buttons = new JButton[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                add(buttons[row][col]);
            }
        }

        statusLabel = new JLabel("Player X's turn");
        add(statusLabel);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        add(resetButton);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (!button.getText().isEmpty() || gameOver) {
                return;
            }

            button.setText(currentPlayer);

            if (checkWin()) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                gameOver = true;
            } else if (checkDraw()) {
                statusLabel.setText("It's a draw!");
                gameOver = true;
            } else {
                currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
    }

    private boolean checkWin() {
        String[][] board = new String[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = buttons[row][col].getText();
            }
        }

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0].equals(board[row][1]) && board[row][0].equals(board[row][2]) && !board[row][0].isEmpty()) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col].equals(board[1][col]) && board[0][col].equals(board[2][col]) && !board[0][col].isEmpty()) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].isEmpty()) {
            return true;
        }

        if (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].isEmpty()) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }

        currentPlayer = "X";
        gameOver = false;
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        TicTacToeGUI gui = new TicTacToeGUI();
        gui.setVisible(true);
    }
}
