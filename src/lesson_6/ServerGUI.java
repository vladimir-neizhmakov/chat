package ru.gb.jtwo.chat.server.gui;

import ru.gb.jtwo.chat.server.core.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
    private static final int POS_X = 1000;
    private static final int POS_Y = 550;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 100;

    private final ChatServer server;
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");

    //Добавляю поле и кнопку для отправки всем
    private final JTextField tfMessageAll = new JTextField();
    private final JButton btnSendAll = new JButton("SendAll");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
//        throw new RuntimeException("Main died!");
        System.out.println("Main finished");
    }

    ServerGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 4));
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);

        //Добавляю листнеры отправки всем
        tfMessageAll.addActionListener(this);
        btnSendAll.addActionListener(this);

        add(btnStart);
        add(btnStop);
        add(tfMessageAll);
        add(btnSendAll);
        server = new ChatServer();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnStart) {
            server.start(8189);
        } else if (src == btnStop) {
            server.stop();
//            throw new RuntimeException("Hello from EDT!");
        }

        //Отправляем всем сообщение с сервера
        else if (src == btnSendAll || src==tfMessageAll){
            server.sendAll("FromServerToAll: " + tfMessageAll.getText());
        }
        else {
            throw new RuntimeException("Unknown source:" + src);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = String.format("Exception in \"%s\" %s: %s\n\tat %s",
                t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
