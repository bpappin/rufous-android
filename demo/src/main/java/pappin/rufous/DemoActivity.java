package pappin.rufous;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import pappin.rufous.widget.fresco.MiltiDraweeStackedLayoutManager;
import pappin.rufous.widget.fresco.MiltiDraweeTiledLayoutManager;
import pappin.rufous.widget.fresco.MultiDraweeBasicDataSource;
import pappin.rufous.widget.fresco.MultiDraweeLetterTileDataSource;
import pappin.rufous.widget.fresco.MultiDraweeView;

public class DemoActivity extends AppCompatActivity {


    private BottomNavigationView navigation;
    private MultiDraweeView draweeGrid;
    private MultiDraweeView draweeStack;
    private MultiDraweeView draweeGridCircle;
    private MultiDraweeView draweeStackCircle;
    private TextView message;
    private OnNavigationItemSelectedListener onNavigationItemSelectedListener = new OnNavigationItemSelectedListener() {

        /**
         * Called when an item in the bottom navigation menu is selected.
         *
         * @param item The selected item
         * @return true to display the item as the selected item and false if the item should not
         * be selected. Consider setting non-selectable items as disabled preemptively to
         * make them appear non-interactive.
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    message.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    message.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    message.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        message = findViewById(R.id.message);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        final String[] imageUrls = {"http://lorempixel.com/400/200/people/One", "http://lorempixel.com/400/200/people/Two", "http://lorempixel.com/400/200/people/Three", null};
        final String[] names = {"One Name", "Two Name", "Three Name", "Four Name"};
        final String[][] letterData = {imageUrls, names};

        final MultiDraweeBasicDataSource source1 = new MultiDraweeBasicDataSource(this, imageUrls);
        final MultiDraweeLetterTileDataSource source2 = new MultiDraweeLetterTileDataSource(this, letterData, true);

        draweeGrid = findViewById(R.id.drawee_grid);
        draweeGrid.setLayoutManager(new MiltiDraweeTiledLayoutManager(this));
        draweeGrid.setImageUris(source1);

        draweeGridCircle = findViewById(R.id.drawee_grid_circle);
        draweeGridCircle.setLayoutManager(new MiltiDraweeTiledLayoutManager(this, true));
        draweeGridCircle.setImageUris(source1);


        draweeStack = findViewById(R.id.drawee_stack);
        draweeStack.setBackgroundResource(R.color.cardview_dark_background);
        draweeStack.setLayoutManager(new MiltiDraweeStackedLayoutManager(this));
        draweeStack.setImageUris(source1);

        draweeStackCircle = findViewById(R.id.drawee_stack_circle);
        draweeStackCircle.setBackgroundResource(R.color.cardview_dark_background);
        draweeStackCircle.setLayoutManager(new MiltiDraweeStackedLayoutManager(this, true));
        draweeStackCircle.setImageUris(source1);
    }
}
