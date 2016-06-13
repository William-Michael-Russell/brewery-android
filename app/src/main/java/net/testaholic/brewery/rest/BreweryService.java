package net.testaholic.brewery.rest;

import net.testaholic.brewery.domain.Drink;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by williamrussell on 6/12/16.
 */
public interface BreweryService {

        @GET("v1/drinks")
        Call<List<Drink>> listDrinks();

        @POST("v1/drink/create")
        Call<Drink> postDrink(@Body Drink drink);

}
