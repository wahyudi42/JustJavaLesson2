package com.example.android.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = new TextView(this);
        textView.setText("Hello");
        textView.setTextSize(56);
        textView.setTextColor(Color.BLUE);

        //setContentView(textView);

    }



    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayQuantity(quantity);
        int price = calculatePrice(quantity);
        String orderDetail = createOrderSummary(quantity, price);
        displayMessage(orderDetail);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + getNama());
        intent.putExtra(Intent.EXTRA_TEXT, orderDetail);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public boolean getState(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        boolean checkbox_1;
        checkbox_1  = checkBox.isChecked();
        Log.v("MainActivity", String.valueOf(checkbox_1));
        return checkbox_1;
    }

    public boolean getState2(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox2);
        boolean checkbox_2;
        checkbox_2  = checkBox.isChecked();
        Log.v("MainActivity", String.valueOf(checkbox_2));
        return checkbox_2;
    }

    public String getNama(){
        EditText nama = (EditText) findViewById(R.id.nama);
        String namaPembeli = nama.getText().toString();
        return namaPembeli;
    }

    public String createOrderSummary(int quantity, int price){
        String orderDetail = getNama();
        orderDetail = orderDetail + "\nAdd Whipped Cream? " + getState();
        orderDetail = orderDetail + "\nAdd Chocolate? " + getState2();
        orderDetail = orderDetail + "\nQuantity: "+ quantity;
        orderDetail = orderDetail + "\nTotal: $" + price +"\nThank You!";
        return orderDetail;
    }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity) {
        int price = 0;
        if (getState()==true){
            price = quantity * (5+1);
            if(getState2()==true){
                price = quantity * (5+3);
            }
        }else if (getState2()==true){
            price = quantity * (5+2);
        }else{
            price = quantity * 5;
        }
        return price;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if(quantity>100){
            Toast.makeText(MainActivity.this, "You cannot have more than 100 Coffees!", Toast.LENGTH_SHORT).show();
        }else{
            quantity = quantity + 1;
            displayQuantity(quantity);
        }

    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if(quantity<1){
            Toast.makeText(MainActivity.this,"Invalid order!",Toast.LENGTH_LONG).show();
        }else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }


    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.quantity_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}