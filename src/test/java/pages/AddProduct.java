package pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utilities.CommonMethods;
import utilities.ExcelUtils;

import java.io.IOException;
import java.nio.file.Paths;

public class AddProduct extends CommonMethods {
    Page page;
    ExtentTest test;

    Locator products;
    Locator addProduct;
    Locator title;
    Locator price;
    Locator categoryID;
    Locator description;
    Locator imageURL;
    Locator createProduct;

    public AddProduct(Page page, ExtentTest test) {
        this.page = page;
        this.test = test;
        this.products = page.locator("//body/nav[1]/div[1]/div[1]/ul[1]/li[2]/a[1]");
        this.addProduct = page.locator("//button[@data-bs-target='#addProductModal']");
        this.title = page.locator("(//input[@type='text'])[1]");
        this.price = page.locator("(//input[@type='number'])[1]");
        this.categoryID = page.locator("(//input[@type='number'])[2]");
        this.description = page.locator("(//textarea[@class='form-control'])[1]");
        this.imageURL = page.locator("//input[@type='url']");
        this.createProduct = page.locator("//button[contains(text(),'Create Product')]");

    }

    // Method to log a success message with ExtentReports
    public void handlePass(String message) {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
    }

    public void handlePassWithScreenshot(String message, String screenshotName) {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
                .setFullPage(true));

        String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
        test.pass(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
    }

    public void handleFail(String message, String screenshotName) {
        test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
        Exception e = new Exception("Login failed: " + message);
        test.fail(e);

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
                .setFullPage(true));

        String dest = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
    }

    public void addProducts() throws IOException {
        try {
            test.info("Please click  your products");
            if (products.isVisible()) {
                products.click();
                handlePass("You have successfully clicked the products page");
                page.waitForTimeout(3000);
                test.info("Please select the add product option");
                if (addProduct.isVisible()) {
                    addProduct.click();
                    handlePass("You have successfully select the add product option ");
                    page.waitForTimeout(3000);
                    test.info("Please write your product title");
                    if (title.isVisible()) {
                        title.fill("Polo T-Shirt");
                        handlePass("You have successfully write the product title ");
                        page.waitForTimeout(5000);
                        test.info("Please write your product price");
                        if (price.isVisible()) {
                            price.fill("900");
                            handlePass("You have successfully write the product price ");
                            page.waitForTimeout(5000);
                            test.info("Please write your product categoryID");
                            if (categoryID.isVisible()) {
                                categoryID.fill("50");
                                handlePass("You have successfully write the product category ID ");
                                page.waitForTimeout(5000);
                                test.info("Please write your product description");
                                if (description.isVisible()) {
                                    description.fill("Lorem ipsum is a dummy");
                                    handlePass("You have successfully write the product description ");
                                    page.waitForTimeout(5000);
                                    test.info("Please write your product image url");
                                    if (imageURL.isVisible()) {
                                        imageURL.fill("https://imgur.com/cBuLvBi");
                                        handlePass("You have successfully store the product image url ");
                                        page.waitForTimeout(5000);
                                        test.info("Please click the Create Product Button");
                                        if (createProduct.isVisible()) {
                                            createProduct.click();
                                            page.waitForTimeout(5000);

                                            handlePassWithScreenshot("You have successfully add to the product", "create_product_pass");
                                        }else {
                                            handleFail("Create product button is not locatable, please check the error message","createProduct_fail");
                                        }
                                    } else {
                                        handleFail("Image url ID is not locatable,please check the error message", "image_fail");
                                    }
                                }else {
                                        handleFail("Description is not locatable,please check the error message", "description_fail");
                                }
                            }else {
                                handleFail("Category ID is not locatable,please check the error message", "categoryID_fail");
                            }
                        }else{
                            handleFail("Price is not locatable,please check the error message", "price_fail");
                        }
                        }else {
                        handleFail("Title is not locatable,please check the error message", "title_fail");
                    }
                    } else {
                        handleFail("Add product button is not locatable, please check the error message", "addProduct_fail");
                    }
            } else {
                handleFail("Products page is not locatable, please check the error message", "productsPage_click_fail");
            }
        } catch (Exception e) {
            handleFail("Something went wrong, please check the error message", "products_page_error");
        }
    }
}
