# FlatNatureButtons
Android Custom View Buttons.
 
![](http://s8.hostingkartinok.com/uploads/images/2017/02/0f0a6b5111aeb9c1fa04be409150d1ce.gif)

Also you can change with elevation
![](http://s8.hostingkartinok.com/uploads/images/2017/02/d050dd3a2ad3e51f354a27b238094ffa.gif)
XML example:

        <com.example.anton.custombuttons.views.waterButton
            android:id="@+id/my_custom_button_water"
            android:layout_width="70dp"
            android:layout_height="70dp"
            android:layout_margin="15dp"
            app:water_background_color="#27AAE1"
            android:elevation="2dp"/>
            />

        <com.example.anton.custombuttons.views.fireButton
            android:id="@+id/my_custom_button_fire"
            android:layout_width="70dp"
            android:layout_height="70dp"
            app:fire_background_color="#EE3769"
            android:layout_margin="15dp"
            android:elevation="2dp"/>

        <com.example.anton.custombuttons.views.thunderButton
            android:id="@+id/my_custom_button_thunder"
            android:layout_width="70dp"
            android:layout_height="70dp"
            android:layout_margin="15dp"
            app:thunder_background_color="#5A5A5A"
            android:elevation="2dp"/>
            
            
How to start animation:

    mybuttonThunder.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mybuttonThunder.clickAnimation();
      }
    });

    mybuttonWater.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mybuttonWater.clickAnimation();
      }
    });

    mybuttonFire.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mybuttonFire.clickAnimation();
      }
    });

Inspired by: https://dribbble.com/shots/1480083-Fun-Buttons
