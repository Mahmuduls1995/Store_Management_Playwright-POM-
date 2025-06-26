package pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utilities.CommonMethods;

import java.io.IOException;
import java.nio.file.Paths;

public class UpdateProduct extends CommonMethods {
    Page page;
    ExtentTest test;

    Locator products;
    Locator editProduct;
    Locator title;
    Locator price;
    Locator categoryID;
    Locator description;
    Locator saveChanges;

    public UpdateProduct(Page page, ExtentTest test) {
        this.page = page;
        this.test = test;

        this.products = page.locator("//body/nav[1]/div[1]/div[1]/ul[1]/li[2]/a[1]");
        this.editProduct = page.locator("//tbody/tr[1]/td[6]/button[1]/i[1]");
        this.title = page.locator("//input[@id='editTitle']");
        this.price = page.locator("//input[@id='editPrice']");
        this.categoryID = page.locator("//input[@id='editCategoryId']");
        this.description = page.locator("//textarea[@id='editDescription']");
        this.saveChanges = page.locator("//button[contains(text(),'Save Changes')]");

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

    public void updateProducts() throws IOException {
        try {
            test.info("Please click  your products");
            if (products.isVisible()) {
                products.click();
                handlePass("You have successfully clicked the products page");
                page.waitForTimeout(3000);
            test.info("Please click your edit product option");
            if (title.isVisible()) {
                title.fill("Polo T-Shirt");
                handlePass("You have successfully click product edit option ");
                page.waitForTimeout(5000);
                    test.info("Please update your product title");
                    if (title.isVisible()) {
                        title.fill("Polo T-Shirt");
                        handlePass("You have successfully update the product title ");
                        page.waitForTimeout(5000);
                        test.info("Please edit your product price");
                        if (price.isVisible()) {
                            price.fill("900");
                            handlePass("You have successfully update the product price ");
                            page.waitForTimeout(5000);
                            test.info("Please edit your product categoryID");
                            if (categoryID.isVisible()) {
                                categoryID.fill("50");
                                handlePass("You have successfully update the product category ID ");
                                page.waitForTimeout(5000);
                                test.info("Please edit your product description");
                                if (description.isVisible()) {
                                    description.fill("Lorem ipsum is a dummy");
                                    handlePass("You have successfully update the product description ");
                                    page.waitForTimeout(5000);
                                        test.info("Please click the save changes Button");
                                        if (editProduct.isVisible()) {
                                            editProduct.click();
                                            page.waitForTimeout(5000);
                                            handlePassWithScreenshot("You have successfully add to the product", "edit_product_pass");
                                        }else {
                                            handleFail("Edit product button is not locatable, please check the error message","editProduct_fail");
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
            }else {
                handleFail("Edit option is not locatable,please check the error message", "title_fail");
            }
            }else {
                    handleFail("Products page is not locatable, please check the error message", "productsPage_click_fail");
                }
        } catch (Exception e) {
            handleFail("Something went wrong, please check the error message", "products_page_error");
        }
    }
}
