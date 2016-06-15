/**
 * Copyright (C) dbychkov.com.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.testaholic.brewery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.aboutlibraries.LibsBuilder;

import net.testaholic.brewery.R;
import net.testaholic.brewery.adapter.DrinksPageAdapter;
import net.testaholic.brewery.app.App;
import net.testaholic.brewery.dagger.component.ActivityComponent;
import net.testaholic.brewery.data.LessonsImporter;
import net.testaholic.brewery.presentation.LessonCatalogActivityPresenter;
import net.testaholic.brewery.util.LogHelper;
import net.testaholic.brewery.util.MarketService;
import net.testaholic.brewery.util.ShareService;
import net.testaholic.brewery.view.LessonCatalogView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Main activity with list of drinks (installed drinks, bookmarked drinks, user drinks).
 */
public class DrinkCatalogActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        LessonCatalogView {

    private static final String TAG = LogHelper.makeLogTag(DrinkCatalogActivity.class);

    public static final int OFF_SCREEN_PAGE_LIMIT = 2;

    @Inject
    LessonCatalogActivityPresenter lessonCatalogActivityPresenter;

    @Inject
    MarketService marketService;

    @Inject
    ShareService shareService;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    LessonsImporter lessonsImporter;

    @BindString(R.string.title_activity_lesson_catalog)
    String title;

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onStart(){
        super.onStart();
        Toast.makeText(DrinkCatalogActivity.this, "DrinkCatalogActivity : onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(DrinkCatalogActivity.this, "DrinkCatalogActivity : onResume", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStop(){
        super.onStop();
        Toast.makeText(DrinkCatalogActivity.this, "DrinkCatalogActivity : onStop", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Toast.makeText(DrinkCatalogActivity.this, "DrinkCatalogActivity : onRestart", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Toast.makeText(DrinkCatalogActivity.this, "DrinkCatalogActivity : onDestroy", Toast.LENGTH_SHORT).show();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(DrinkCatalogActivity.this, "DrinkCatalogActivity : onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_lesson_catalog);
        ButterKnife.bind(this);
        initToolbar();
        initTabs();
        initTitle();
        lessonCatalogActivityPresenter.setView(this);
        lessonCatalogActivityPresenter.initialize();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }


    private void initTabs() {
        viewPager.setAdapter(new DrinksPageAdapter(getFragmentManager()));
        viewPager.setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
    }

    private void initTitle() {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void injectActivity(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                lessonCatalogActivityPresenter.refreshButtonClicked();
                return true;
            case R.id.share:
                lessonCatalogActivityPresenter.shareButtonClicked();
                return true;
            case R.id.rate:
                lessonCatalogActivityPresenter.rateButtonClicked();
                return true;
            case R.id.about:
                lessonCatalogActivityPresenter.aboutButtonClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                Toast.makeText(App.getInstance().getApplicationContext(), "BundledLessonsTabFragment : New Lessons Fragment", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(App.getInstance().getApplicationContext(), "BookmarkedLessonsTabFragment : New Favorites Fragment", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(App.getInstance().getApplicationContext(), "UserLessonsTabFragment : New Add Drink Fragment", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        if (position == 2) {
            floatingActionButton.show();
        } else {
            floatingActionButton.hide();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void renderRateScreen() {
        marketService.showAppPageInGooglePlay(DrinkCatalogActivity.this.getPackageName());
    }

    @Override
    public void renderShareScreen() {
        shareService.shareAsPlainText(DrinkCatalogActivity.this);
    }

    @Override
    public void renderAboutScreen() {
        new LibsBuilder()
                .withAutoDetect(true)
                .withLicenseShown(true)
                .withVersionShown(true)
                .withActivityTitle(getString(R.string.menu_about))
                .withAboutAppName(getString(R.string.app_name))
                .withAboutIconShown(true)
                .withAboutVersionShown(true)
                .withActivityTheme(R.style.Base_Theme_Design_Dark)
                .start(DrinkCatalogActivity.this);
    }

    @Override
    public void renderDrinkListScreen() {
        Toast.makeText(getApplicationContext(), "Getting Drink list... please wait", Toast.LENGTH_SHORT).show();
        lessonsImporter.requestLessons();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onPause(){
        super.onPause();
        Toast.makeText(DrinkCatalogActivity.this, "DrinkCatalogActivity : onPause", Toast.LENGTH_SHORT).show();
    }

}
