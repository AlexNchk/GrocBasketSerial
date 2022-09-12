import java.io.*;

public class Basket {
    protected final String product;
    protected final int price;
    private static int[] sumOneProduct;

    public Basket(String product, int price) {
        this.product = product;
        this.price = price;
    }

    public static void setSumOneProduct(int productsLength) {
        sumOneProduct = new int[productsLength];
    }

    public static void addToCart(int productNum, int amount) {
        sumOneProduct[productNum - 1] += amount;
    }

    public static void printCart(String[] products, int[] prices) {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < sumOneProduct.length; i++) {
            if (sumOneProduct[i] != 0) {
                System.out.println(products[i] + " " + sumOneProduct[i] +
                        " шт " + prices[i] + " руб/шт " + sumOneProduct[i] * prices[i] + " руб в сумме");
            }
        }
    }

    public static void saveTxt(File basketUser) throws IOException {
        try (PrintWriter out = new PrintWriter(basketUser)) {
            for (int e : sumOneProduct)
                out.print(e + " ");
        }
    }

    public static void loadTxt() throws IOException {
        BufferedReader basketUserLoad = new BufferedReader(new FileReader("basket.txt"));
        String readFile = String.valueOf(basketUserLoad.readLine());
        String[] loadBasket = readFile.split(" ");
        for (int i = 0; i < sumOneProduct.length; i++) {
            sumOneProduct[i] = Integer.parseInt(loadBasket[i]);
        }
    }
}
