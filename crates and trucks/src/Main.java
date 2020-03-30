import java.util.Scanner;


public class Main
{
    public static void main (String args [])
    {

        // Контейнер ≤ 27 ящиков
        // Грузовик ≤ 12 контейнеров или 324 ящика

        int box = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Сколько коробок нужно доставить? ");
            box = scanner.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cargo cargo = new Cargo(box);
        System.out.println();
        cargo.fullPrint();
    }
}

class Cargo {
    private final int BOXES_IN_CONTAINER = 27;
    private int truck;
    private int container;
    private int box;

    public Cargo(int box) {
        this.box = box;
        if (box != 0) {
            container = box / BOXES_IN_CONTAINER + (box % BOXES_IN_CONTAINER == 0 ? 0 : 1); // box % 27 == 0 ? box / 27 : box / 27 + 1
        }
        if (container != 0) {
            truck = container % 12 == 0 ? container / 12 : container / 12 + 1;
        }
    }

    public int getTruck() {
        return truck;
    }

    public int getContainer() {
        return container;
    }

    public int getBox() {
        return box;
    }

    public void fullPrint() {
        int b = 1;
        int c = 1;
        for (int i = 1; i <= truck; i++) {
            System.out.printf("Грузовик %d:\n ", i);
            int j = 0;
            while (j < 12 && c <= container) {
                System.out.printf("Контейнер %d:\n ", c);
                int k = 0;
                while (k < 27 && b <= box) {
                    System.out.printf("Ящик %d\n ", b);
                    k++;
                    b++;
                }
                j++;
                c++;
                System.out.println();
            }
        }
    }
    }

