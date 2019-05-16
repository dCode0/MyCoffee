package com.example.mycoffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    /**
     * This method displays background image.
     */
    public void display(View view) {
        LinearLayout layout = findViewById(R.id.LinearLayoutImage);
        layout.setBackgroundResource(R.drawable.cafee);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        if (quantity>0) {
        Toast.makeText(MainActivity.this, "Thank you for your patronage",
                Toast.LENGTH_LONG).show();

        EditText nameField = findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whippedcream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String message = orderSummary(name, price, hasWhippedCream, hasChocolate);
        displayMessage(message);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

        else if (quantity<0){
            Toast.makeText(MainActivity.this, "Kindly increase the quantity",
                    Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream cost of a cup of coffee with WhippedCream.
     * @param addChocolate cost of a cup of coffee with Chocolate.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int pricePerCup = 5;
        if (addWhippedCream){
            pricePerCup+=3;
        }

        if (addChocolate){
            pricePerCup+=2;
        }


        return quantity * pricePerCup;
    }

    /**
     * Displays order summary
     */
    private String orderSummary(String name,int price, boolean addWhippedCream,boolean addChocolate) {

    String priceMessage = getString(R.string.order_summary_name, name);
    priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
    priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
    priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
    priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
    priceMessage += "\n" + getString(R.string.thank_you);
    return priceMessage;
    }


    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {

        quantity += 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {

        if (quantity<1){
            Toast.makeText(MainActivity.this, "Quantity cannot be less than 1.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen./method is defined
     */
    private void displayQuantity(int mass) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + mass);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


}