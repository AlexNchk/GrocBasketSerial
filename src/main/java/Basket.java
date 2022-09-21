import java.io.*;
import java.util.Arrays;

public class Basket {
    protected final String[] product;
    protected final int[] price;
    private int[] sumOneProduct;

    public Basket(String[] product, int[] price) {
        this.product = product;
        this.price = price;
        if (sumOneProduct == null) {
            sumOneProduct = new int[product.length];
        }
    }

    public void addToCart(int productNum, int amount) {
        sumOneProduct[productNum - 1] += amount;
    }

    public void printSale() {
        System.out.println("Список возможных товаров для покупок:");
        for (int i = 0; i < product.length; i++) {
            System.out.println(i + 1 + ". " + product[i] + " " + price[i] + " руб/шт");
        }
    }

    public void printCart(String[] products, int[] prices) {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < sumOneProduct.length; i++) {
            if (sumOneProduct[i] != 0) {
                System.out.println(products[i] + " " + sumOneProduct[i] +
                        " шт " + prices[i] + " руб/шт " + sumOneProduct[i] * prices[i] + " руб в сумме");
            }
        }
    }

    public void saveTxt(File basketUser) throws IOException {
        try (PrintWriter out = new PrintWriter(basketUser)) {
            for (int e : sumOneProduct)
                out.print(e + " ");
        }
    }

    public void loadTxt() throws IOException {
        try (BufferedReader basketUserLoad = new BufferedReader(new FileReader("basket.txt"))) {
            String readFile = String.valueOf(basketUserLoad.readLine());
            String[] loadBasket = readFile.split(" ");
            for (int i = 0; i < sumOneProduct.length; i++) {
                sumOneProduct[i] = Integer.parseInt(loadBasket[i]);
            }
        }
        ;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "price=" + Arrays.toString(price) +
                ", product=" + Arrays.toString(product) +
                ", basket=" + Arrays.toString(sumOneProduct) +
                '}';
    }
}