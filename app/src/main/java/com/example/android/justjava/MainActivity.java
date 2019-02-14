package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Write Customer Name
        EditText customerNameEditText = (EditText)findViewById(R.id.customer_name_text);
        String customerName = customerNameEditText.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);


        Intent orderSummaryEmail = new Intent(Intent.ACTION_SENDTO);
        orderSummaryEmail.setData(Uri.parse("mailto:")); // only email apps should handle this
        orderSummaryEmail.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + customerName);
        orderSummaryEmail.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (orderSummaryEmail.resolveActivity(getPackageManager()) != null) {
            startActivity(orderSummaryEmail);
        }

    }

    /**
     * Calculates the price of the order.
     * @param addChocolate for add price for extra chocolate to order
     * @param addWhippedCream for add price for extra whipped cream to order
     *
     * @return total price of order
     */
    private int calculatePrice( boolean addWhippedCream, boolean addChocolate ) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

        /**
     * Create an order summary.`
     *
     * @param price of the order
     * @param addWhippedCream add Whipped cream or not to the coffee
     * @param addChocolate add chocolate or not to the coffee
     * @param customerName  add a customer name in text field
     * @return text summary
     *
     */

    private String createOrderSummary(String customerName, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + customerName;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate?" + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal =  €" + price;
        priceMessage += "\nThanks you!";
        return priceMessage;
    }


    /**
     * This method is called when the increment button is clicked.
     */
    public void increment (View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity (quantity);
    }
    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
