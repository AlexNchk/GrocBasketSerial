import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;
    protected final String[] product;
    protected final int[] price;
    protected int[] sumOneProduct;

    public Basket(String[] product, int[] price) {
        this.product = product;
        this.price = price;
        if (sumOneProduct == null) {
            sumOneProduct = new int[product.length];
        }
    }

    @Override
    public String toString() {
        return "Basket" + "product, " + Arrays.toString(product) +
                "price" + Arrays.toString(price) + "sumOneProduct" + Arrays.toString(sumOneProduct);
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

    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < sumOneProduct.length; i++) {
            if (sumOneProduct[i] != 0) {
                System.out.println(i + 1 + " " + product[i] +
                        " " + price[i] + " руб/шт " + sumOneProduct[i] * price[i] + " руб в сумме");
            }
        }
    }

    public void saveBin(File file) throws IOException {
        try (ObjectOutputStream saveBinFile = new ObjectOutputStream(new FileOutputStream(file))) {
            saveBinFile.writeObject(this);
            System.out.println("Файл bin создан. Сериализация");
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream loadBinFile = new ObjectInputStream(new FileInputStream(file))) {
            Basket baskets = (Basket) loadBinFile.readObject();
            loadBinFile.close();
            System.out.println("Файл bin загружен. Десериализация");
            return baskets;
        }
    }
}
