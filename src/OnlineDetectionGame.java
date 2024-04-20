import java.util.*;

public class OnlineDetectionGame {
    public static OnlineMap map;
    public static int width;
    public static int num_mine;
    public static int attempt;
    public Scanner odgScanner = null;
    public Random rand = new Random();

    /*public static void main(String[] args) {
        int find = 0;
        initial();

        while (find < num_mine)
            find += guess();

        System.out.println("Success found " + find + " mines !");
        System.out.println("You tried " + attempt + " times to find " + num_mine + " mines !");
        double rate = num_mine / (double) attempt * 100;
        System.out.printf("Mine Detection Rate: %.2f%%\n", rate);

    }*/
    public OnlineMap getMap() {
        return map;
    }

    public OnlineDetectionGame() {
        // just game play purpose.
        odgScanner = new Scanner(System.in);
        initial();
//        int find = 0;

        /*while (find < num_mine)
            find += guess();

        System.out.println("Success found " + find + " mines !");
        System.out.println("You tried " + attempt + " times to find " + num_mine + " mines !");
        double rate = num_mine / (double) attempt * 100;
        System.out.printf("Mine Detection Rate: %.2f%%\n", rate);*/
    }

    public void initial() {
        System.out.println("Setting the Mine Game !");

        /*// map 크기 설정
        System.out.print("Enter the width (1~10, width x width) :");
        width = odgScanner.nextInt();
        // 예외 처리
        while ((width < 1) || (width > 10)) {
            System.out.println("Invalid width, enter 1~10 !");
            width = odgScanner.nextInt();
        }*/
        /*// 지뢰 개수 설정
        System.out.print("Enter number of mines : ");
        num_mine = odgScanner.nextInt();

        // 지뢰 개수 예외 처리
        while ((num_mine >= width * width) || (num_mine < 1)) {
            System.out.println("Invalid number of mines, must be 0 ~ " + width * width);
            num_mine = odgScanner.nextInt();
        }*/
        width = rand.nextInt(10) + 1;
        num_mine = rand.nextInt(5) + 1;

        // 입력받은 map 크기, 지뢰 개수에 따른 map 초기화 -> map.java 파일로 이동
        map = new OnlineMap(width, num_mine);
    }

    public static int guess() {
        Scanner scan = new Scanner(System.in);
        System.out.print(" Enter x coordinate(0 ~ " + (width - 1) + ") :");
        int x = scan.nextInt();
        while ((x < 0) || (x >= width)) {
            System.out.println(" Invalid x, enter a new x coordinate");
            x = scan.nextInt();
        }
        System.out.print(" Enter y coordinate(0 ~ " + (width - 1) + ") :");
        int y = scan.nextInt();
        while ((y < 0) || (y >= width)) {
            System.out.println(" Invalid y, enter a new y coordinate");
            y = scan.nextInt();
        }

        attempt++;  // 시도한 횟수 증가 +1
        if (map.checkMine(x, y) >= 0) {
            map.updateMap(x, y);
            return 1;
        } else
            return 0;
    }
}
