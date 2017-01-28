package ICQ;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
public class chat extends javax.swing.JFrame {
    static String a="";
    BufferedReader socketReader;
    File l=new File("log.txt");
    BufferedWriter socketWriter;
    Socket s;
    public chat()   {
        initComponents();
    }
    private void initComponents()   {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jTextArea1);
        jButton1.setText("Отправить");
        jButton2.setText("Отпить");
        final BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        jTextArea1.setText("");
        jTextArea1 = new javax.swing.JTextArea();

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
                    userString=chat.a;
                    if (userString == null || userString.length() == 0 || s.isClosed()) {
                } else {
                    try {
                        if (!chat.a.equals("")) {
                            socketWriter.write(chat.a);
                            chat.a="";
                          //  socketWriter.write(chat.a);
                            socketWriter.write("\n");
                            socketWriter.flush();
                        }
                      //  else //System.out.println(chat.a);
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
                jTextArea3.setText("");
            }
        });
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);
        jScrollPane1.setViewportView(jTextArea1);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3))
                                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;



}

