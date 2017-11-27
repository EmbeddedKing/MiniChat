import java.net. * ;
import java.io. * ;
import java.util. * ;

public class Main {
  int port;
  List < Socket > clients;
  ServerSocket server;
  public static void main(String[] args) {
    new Main();
  }
  public Main() {
    try {
      port = 8899; //�˿ں�
      clients = new ArrayList < Socket > (); //ʵ����socket list����
      server = new ServerSocket(port); //����������socket
      while (true) {
        Socket socket = server.accept(); //��ѭ�������ͻ�������
        clients.add(socket); //�򱣴�ͻ���socket list���socketʵ��
        Mythread mythread = new Mythread(socket, clients.size() - 1); //ʵ�������̲߳����͵�ǰsocketʵ��id
        mythread.start(); //����һ�����߳�
      }

    } catch(Exception ex) {}
  }
  class Mythread extends Thread { //���̸߳���ÿ���ͻ�����Ϣ����
    Socket tsocket;
    private BufferedReader br;
    private PrintWriter pw;
    public String msg;
    public int id;
    public Mythread(Socket s, int is) {
      tsocket = s;
      id = id;
    }
    public void run() {

      try {
        br = new BufferedReader(new InputStreamReader(tsocket.getInputStream(), "UTF-8"));
        msg = "��IP:" + tsocket.getInetAddress() + "�����ߡ� ��������:" + clients.size();

        sendMsg();//��������

        while ((msg = br.readLine()) != null) {//ѭ�����տͻ�����Ϣ

          if (msg.equals("exitthis")) { //����յ�exitthis���˳��̣߳�����socket��
            clients.remove(id);
            break;

          }
          sendMsg();//������Ϣ�����пͻ���

        }
      } catch(Exception ex) {

}
    }
    //������Ϣ
    public void sendMsg() {
      try {

        System.out.println(msg);

        for (int i = clients.size() - 1; i >= 0; i--) { //ѭ������
          pw = new PrintWriter(clients.get(i).getOutputStream(), true);
          pw.println(msg);
          pw.flush();
        }

      } catch(Exception ex) {}
    }
  }

}