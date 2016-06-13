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

package net.testaholic.brewery.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.widget.Toast;

import net.testaholic.brewery.activity.DrinkCatalogActivity;
import net.testaholic.brewery.app.App;
import net.testaholic.brewery.fragment.BookmarkedLessonsTabFragment;
import net.testaholic.brewery.fragment.BundledLessonsTabFragment;
import net.testaholic.brewery.fragment.UserLessonsTabFragment;

/**
 * Adapter responsible for switching between "bundled drinks", "favorite drinks", "user drinks" tabs
 */
public class DrinksPageAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 3;
    private static final String TAB_TITLES[] = new String[] { "Drinks", "Favorites", "Add Drink" };

    public DrinksPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:

            return BundledLessonsTabFragment.newInstance();
        case 1:

            return BookmarkedLessonsTabFragment.newInstance();
        case 2:

            return UserLessonsTabFragment.newInstance();
        }
        throw new RuntimeException("Invalid tab position: " + position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
