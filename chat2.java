package ICQ;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

//import static java.awt.SystemColor.text;

public class chat2 extends javax.swing.JFrame {
    static String a="";
    BufferedReader socketReader;
    File l=new File("log.txt");
    BufferedWriter socketWriter;
    Socket s;
    static String text;
    public chat2() {
        initComponents();
    }
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jButton1.setText("Отправить");
        jButton2.setText("Сохранить");

        class client extends javax.swing.JFrame implements Runnable  {
            final Socket s;  // это будет сокет для сервера
            final BufferedReader socketReader;
            // BufferedReader buf;

            final BufferedWriter socketWriter;
            //    private javax.swing.JTextArea jTextArea1;
            public client(String host, int port) throws IOException {
                jTextArea1 = new javax.swing.JTextArea();
                s = new Socket("localhost", 45000);
                socketReader = new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
                socketWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
                new Thread(new client.Receiver()).start();
            }
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String userString = null;
                    userString=chat2.a;
                    if (userString == null || userString.length() == 0 || s.isClosed()) {
                    } else {
                        try {
                            if (!chat2.a.equals("")) {
                                socketWriter.write(chat2.a);
                                chat2.a="";
                                //  socketWriter.write(chat2.a);
                                socketWriter.write("\n");
                                socketWriter.flush();
                            }
                            //  else //System.out.println(chat2.a);
                        } catch (IOException e) {
                            close();
                        }
                    }
                }




            }
            public synchronized void close() {
                if (!s.isClosed()) {
                    try {
                        s.close();
                        System.exit(0);
                    } catch (IOException ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
            class Receiver implements Runnable{

                public void run() {
                    while (!s.isClosed()) {
                        String line = null;
                        try {
                            line = socketReader.readLine();
                        } catch (IOException e) {
                            if ("Socket closed".equals(e.getMessage())) {
                                break;
                            }
                            System.out.println("Connection lost");
                            close();
                        }
                        if (line == null) {
                            System.out.println("Server has closed connection");
                            close();
                        } else {
                            System.out.println("Server:" + line);
                            jTextArea1.setText (jTextArea1.getText()+"\n"+line);
                            //jTextArea1.set("\n");
                        }
                    }
                }
            }
        }
        try {
            Thread tt1= new Thread(new client("localhost", 45000));
            tt1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            //  final a = jTextArea1.getText();
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a=jTextArea3.getText();
                text=jTextArea1.getText();
                jTextArea3.setText("");
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            //  final a = jTextArea1.getText();
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                  text = jTextArea1.getText(); // строка для записи
                try(FileOutputStream fos=new FileOutputStream("C://SomeDir//notes.txt"))
                {
                    // перевод строки в байты
                    byte[] buffer = text.getBytes();

                    fos.write(buffer, 0, buffer.length);
                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }




        }
        });






        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane2.setViewportView(jTextArea3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane2)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(82, 82, 82))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        



    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    // End of variables declaration                   
}
