# FlatNatureButtons
Android Custom View Buttons. Support color changes and elevation
 
![](http://s8.hostingkartinok.com/uploads/images/2017/02/c7ff741ac89eb3c254d78c1441ae7c23.gif)

 
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
