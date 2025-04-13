import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // buat frame
        JFrame frame = new JFrame("Flappy Birb");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // frame.setVisible(true); set visible pindah ke paling bawah

        // buat objek Jpanel
        FlappyBirb flappyBirb = new FlappyBirb();
        frame.add(flappyBirb);
        frame.pack();
        flappyBirb.requestFocus();
        frame.setVisible(true); // set visible ditaruh setelah semua komponen dibuat
    }
}
