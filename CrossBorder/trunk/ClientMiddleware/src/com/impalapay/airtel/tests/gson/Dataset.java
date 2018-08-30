package com.impalapay.airtel.tests.gson;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Dataset {
	public String album_id;
	public String album_title;
	//We can use annotation on the field to specify the json name 
	//to use for a particular java property. Here's how to do that 
	@SerializedName("album_images")
	List<AlbumImages> images = new ArrayList<AlbumImages>();

}
