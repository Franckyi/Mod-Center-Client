package com.franckyi.modcenter.client.core.json;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import com.franckyi.modcenter.api.EnumCategory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class MCCJson {

	private static Gson gson = create();

	public static Gson getGson() {
		return gson;
	}

	private static Gson create() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(EnumCategory.class, new CategoryAdapter());
		builder.registerTypeAdapter(Date.class, new DateAdapter());
		return builder.create();
	}

	private static class CategoryAdapter extends TypeAdapter<EnumCategory> {

		@Override
		public void write(JsonWriter out, EnumCategory value) throws IOException {
			out.value(value.getDbKey());
		}

		@Override
		public EnumCategory read(JsonReader in) throws IOException {
			return EnumCategory.toCategory(in.nextString());
		}

	}

	private static class DateAdapter extends TypeAdapter<Date> {

		@Override
		public void write(JsonWriter out, Date value) throws IOException {
			out.value(value.toLocalDate().toString());
		}

		@Override
		public Date read(JsonReader in) throws IOException {
			return Date.valueOf(LocalDate.parse(in.nextString()));
		}
	}

}
