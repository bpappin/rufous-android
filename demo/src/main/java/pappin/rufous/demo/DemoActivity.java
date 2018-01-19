package pappin.rufous.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pappin.rufous.widget.fresco.MiltiDraweePiledLayoutManager;
import pappin.rufous.widget.fresco.MiltiDraweeStackedLayoutManager;
import pappin.rufous.widget.fresco.MiltiDraweeTiledLayoutManager;
import pappin.rufous.widget.fresco.MultiDraweeBasicDataSource;
import pappin.rufous.widget.fresco.MultiDraweeLetterTileDataSource;
import pappin.rufous.widget.fresco.MultiDraweeView;

public class DemoActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private MultiDraweeView draweeGrid;
    private MultiDraweeView draweePiled;
    private MultiDraweeView draweeGridCircle;
    private MultiDraweeView draweePiledCircle;
    private MultiDraweeView draweeStacked;
    private MultiDraweeView draweeStackedCircle;
    private MultiDraweeView draweeGridSquareEmpty;
    private MultiDraweeView draweeGridCircleEmpty;
    private MultiDraweeView draweeStackedLetterSquareEmpty;
    private MultiDraweeView draweeStackedLetterCircleEmpty;
    private MultiDraweeView draweeGridSquareLimited;
    private MultiDraweeView draweeGridCircleLimited;

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


        // XXX 4 images
        //        final String[] imageUrls = {"http://lorempixel.com/400/200/people/One", "http://lorempixel.com/400/200/people/Two", "http://lorempixel.com/400/200/people/Three", "http://lorempixel.com/400/200/people/Four"};
        //        final String[] names = {"One Name", "Two", "Three Name", "Four Name"};
        //        final String[][] letterData = {names, imageUrls};

        // XXX 3 images
        final String[] imageUrls = {"http://lorempixel.com/400/200/people/One", "http://lorempixel.com/400/200/people/Two", "http://lorempixel.com/400/200/people/Three"};
        final String[] names = {"One Name", null, "Three Name"};
        final String[][] letterData = {names, imageUrls};

        final String[] imageUrls2 = {null, null, null};
        final String[] names2 = {"One Name", null, "Three Name"};
        final String[][] letterData2 = {names2, imageUrls2};

        // XXX 2 images
        //        final String[] imageUrls = {"http://lorempixel.com/400/200/people/One", "http://lorempixel.com/400/200/people/Two"};
        //        final String[] names = {"One Name", "Three Name"};
        //        final String[][] letterData = {names, imageUrls};

        // XXX 1 images
        //        final String[] imageUrls = {"http://lorempixel.com/400/200/people/One"};
        //        final String[] names = {"One Name"};
        //        final String[][] letterData = {names, imageUrls};

        final MultiDraweeBasicDataSource source1 = new MultiDraweeBasicDataSource(this, imageUrls);
        final MultiDraweeLetterTileDataSource source2 = new MultiDraweeLetterTileDataSource(this, letterData, true);
        final MultiDraweeLetterTileDataSource source3 = new MultiDraweeLetterTileDataSource(this, letterData2);
        final MultiDraweeLetterTileDataSource source4 = new MultiDraweeLetterTileDataSource(this, letterData2, true);

        draweeGrid = findViewById(R.id.drawee_grid_square);
        draweeGrid.setLayoutManager(new MiltiDraweeTiledLayoutManager(this));
        draweeGrid.setImageUris(source1);

        draweeGridCircle = findViewById(R.id.drawee_grid_circle);
        draweeGridCircle.setLayoutManager(new MiltiDraweeTiledLayoutManager(this, true));
        draweeGridCircle.setImageUris(source2);


        draweePiled = findViewById(R.id.drawee_piled_square);
        //        draweeStack.setBackgroundResource(R.color.cardview_dark_background);
        draweePiled.setLayoutManager(new MiltiDraweePiledLayoutManager(this));
        draweePiled.setImageUris(source1);

        draweePiledCircle = findViewById(R.id.drawee_piled_circle);
        //        draweeStackCircle.setBackgroundResource(R.color.cardview_dark_background);
        draweePiledCircle.setLayoutManager(new MiltiDraweePiledLayoutManager(this, true));
        draweePiledCircle.setImageUris(source2);


        draweeStacked = findViewById(R.id.drawee_stacked_square);
        //        draweeStack.setBackgroundResource(R.color.cardview_dark_background);
        draweeStacked.setLayoutManager(new MiltiDraweeStackedLayoutManager(this));
        draweeStacked.setImageUris(source1);

        draweeStackedCircle = findViewById(R.id.drawee_stacked_circle);
        //        draweeStackCircle.setBackgroundResource(R.color.cardview_dark_background);
        draweeStackedCircle.setLayoutManager(new MiltiDraweeStackedLayoutManager(this, true));
        draweeStackedCircle.setImageUris(source2);


        draweeGridSquareEmpty = findViewById(R.id.drawee_grid_square_empty);
        //        draweeStack.setBackgroundResource(R.color.cardview_dark_background);
        draweeGridSquareEmpty.setLayoutManager(new MiltiDraweeTiledLayoutManager(this));
        draweeGridSquareEmpty.setImageUris(source3);

        draweeGridCircleEmpty = findViewById(R.id.drawee_grid_circle_empty);
        //        draweeStackCircle.setBackgroundResource(R.color.cardview_dark_background);
        draweeGridCircleEmpty.setLayoutManager(new MiltiDraweeTiledLayoutManager(this, true));
        draweeGridCircleEmpty.setImageUris(source4);


        draweeStackedLetterSquareEmpty = findViewById(R.id.drawee_stacked_letter_square_empty);
        //        draweeStack.setBackgroundResource(R.color.cardview_dark_background);
        draweeStackedLetterSquareEmpty.setLayoutManager(new MiltiDraweeStackedLayoutManager(this));
        draweeStackedLetterSquareEmpty.setImageUris(source3);

        draweeStackedLetterCircleEmpty = findViewById(R.id.drawee_stacked_letter_circle_empty);
        //        draweeStackCircle.setBackgroundResource(R.color.cardview_dark_background);
        draweeStackedLetterCircleEmpty.setLayoutManager(new MiltiDraweeStackedLayoutManager(this, true));
        draweeStackedLetterCircleEmpty.setImageUris(source4);


        draweeGridSquareLimited = findViewById(R.id.drawee_grid_square_limited);
        //        draweeStack.setBackgroundResource(R.color.cardview_dark_background);
        draweeGridSquareLimited.setLayoutManager(new MiltiDraweeStackedLayoutManager(this));
        draweeGridSquareLimited.setImageUris(source3);

        draweeGridCircleLimited = findViewById(R.id.drawee_grid_circle_limited);
        //        draweeStackCircle.setBackgroundResource(R.color.cardview_dark_background);
        draweeGridCircleLimited.setLayoutManager(new MiltiDraweeStackedLayoutManager(this, true));
        draweeGridCircleLimited.setImageUris(source4);


    }
}
