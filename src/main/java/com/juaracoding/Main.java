package com.juaracoding;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    private static int loginAttempts = 0;
    private static final int maxLoginAttempts = 3;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","C:\\MyTools\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://shop.demoqa.com");
        System.out.println("Open URL");
        driver.get("https://shop.demoqa.com/my-account/");
        System.out.println("Login Page");
        driver.findElement(By.id("username")).sendKeys("fitriauliaa3112@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Fitriaulia");
        driver.findElement(By.xpath("//*[@id=\"customer_login\"]/div[1]/form/p[3]/button")).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifikasi bahwa login berhasil berdasarkan elemen dengan class "woocommerce-MyAccount-content"
        WebElement myAccountContent = driver.findElement(By.className("woocommerce-MyAccount-content"));
        if (myAccountContent.getText().contains("Hello fitriauliaa3112")) {
            System.out.println("Login berhasil.");
        } else {
            System.out.println("Login gagal.");
            loginAttempts++;
            if (loginAttempts == maxLoginAttempts) {
                System.out.println("Gagal login 3 kali. Akan di blok sementara.");
            }
        }

        driver.get("https://shop.demoqa.com");
        //add to cart
        WebElement product = driver.findElement(By.xpath("//*[@id=\"noo-site\"]/div[2]/div[3]/div/div[2]/div/div/div/div[2]/div[2]/div[1]/div/h3/a"));
        String productName = product.getText();
        product.click();
        driver.findElement(By.xpath("//*[@id=\"pa_color\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"pa_color\"]/option[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"pa_size\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"pa_size\"]/option[3]")).click();
        WebElement addToCart = driver.findElement(By.xpath("//*[@id=\"product-1497\"]/div[1]/div[2]/form/div/div[2]/button"));
        addToCart.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement successMessage = driver.findElement(By.className("woocommerce-message"));
        String messageText = successMessage.getText();

        // Menggunakan if else untuk verifikasi
        if (messageText.contains("has been added to your cart.")) {
            System.out.println("Produk '" + productName + "' berhasil ditambahkan ke cart.");
        } else {
            System.out.println("Produk '" + productName + "' gagal ditambahkan ke cart.");
        }

        // Memastikan produk berhasil ditambahkan ke cart dengan assert if else
        assert messageText.contains("has been added to your cart.") : "Produk '" + productName + "' gagal ditambahkan ke cart.";

        driver.quit();
    }

    static void delay(){
        try {
            Thread.sleep(5000); // delay 5 detik
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}