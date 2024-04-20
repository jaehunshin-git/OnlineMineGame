import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;

// 24.04.06 서버와 클라이언트가 서로 메시지를 주고 받을 수 있는 버전.
// 24.04.08 메서드 주석처리 완료.
class Server {
    public static int inPort = 9999;
    public static Vector<Client> clients = new Vector<Client>();
    public Scanner sc = new Scanner(System.in);

    public static OnlineDetectionGame mineGame;
    public static OnlineMap onlineMap;

    public static void main(String[] args) throws Exception {
        new Server().createServer();
    }

    public void createServer() throws Exception {
        ServerSocket server = new ServerSocket(inPort);
        System.out.println("Waiting for client to join...");
//        GameStart gs = new GameStart();
        GameStartAuto autoGS = new GameStartAuto();

        while (true) {
            Socket socket = server.accept();
            Client c = new Client(socket);
            clients.add(c);
            System.out.println("Accepted clients: " + clients.size());
            c.send("Map Size: " + onlineMap.width + " * " + onlineMap.width);
            c.send("Mine Num: " + onlineMap.num_mine);
            c.send()
        }
    }

    // sendtoall() 메서드는 clients 에 있는 모든 Client c 객체에 메시지를 뿌려준다.
    // 따라서 메시지를 보낸 사람은 자신이 보낸 메시지를 다시 콘솔창으로 받게된다.
    public void sendtoall(String msg) {
        for (Client c : clients)
            c.send(msg);
    }

//    class GameStart extends Thread{
//        public GameStart() {
//            start();
//        }
//
//        public void run() {
//            String line = sc.nextLine();
//            while (!line.equals("exit")) {
//                if (line.equals("set")) {
//                    mineGame = new OnlineDetectionGame(sc);
//                    onlineMap = mineGame.getMap();
//                    System.out.println("<Mine Map>");
//                    onlineMap.printMap(onlineMap.mineMap);
//                    sendtoall("Map Size: " + onlineMap.width + " * " + onlineMap.width);
//                    sendtoall("Mine Num: " + onlineMap.num_mine);
//                    sendtoall("Wait until server starts the game...");
//                    line = sc.nextLine();
//                }
//                else if (line.equals("start")) {
//                    sendtoall(line);
//                }
//                else {
//                    line = sc.nextLine();
//                }
//            }
//            System.out.println("Exiting game...");
//            sendtoall("Server stops the game.");
//            sendtoall("Chat with the player of previous game!");
//        }
//    }

    class GameStartAuto {
        public GameStartAuto() {
            mineGame = new OnlineDetectionGame();
            onlineMap = mineGame.getMap();
            System.out.println("<Mine Map>");
            onlineMap.printMap(onlineMap.mineMap);

        }

    }

    // extends Thread means: Client class 가 java.lang package subclass 이다.
    // 각 Client 의 행동을 동시에 병렬적으로 처리하기 위해 Thread 에서 상속받는게 필수.
    class Client extends Thread {
        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;

        public Client(Socket socket) throws Exception {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            start();    // start() method 가 선언될 때 run() method가 실행된다.
        }

        // 해당 c 객체에게만 메시지를 보낼 때 사용하는 method
        public void send(String msg) {
            out.println(msg);
        }


        // Client class 가 Thread class 의 하위 클래스인데 Thread class 에 있는 run() method 를 오버라이딩해서 CLient class 가 쓰고있다.
        // @Override 는 없어도 에러가 나지는 않지만 명시적으로 run() 메서드가 오버라이딩 되었다는걸 나타낸다.
        @Override
        public void run() {
            String line;
            try {
                // 밑에 while 문에서 클라이언트의 입력을 받아서 서버에 출려주고 모든 클라이언트에 뿌려준다.
                while (true) {
                    line = in.readLine();   // 클라이언트의 메시지를 입력받는다.
                    System.out.println("("+ socket.getInetAddress()+ ") " + line);
//                    System.out.println(line);   // 클라이언트가 보낸 메시지를 서버 콘솔에 출력한다.
                    sendtoall(line);    // 모두에게 메시지를 보낸다.
                }
            } catch (IOException e) {
            } finally {
                try {
                    if (out != null)
                        out.close();
                    if (in != null) {
                        in.close();
                        socket.close();
                    }
                } catch (IOException e) {
                }
            }
        }

    }



}