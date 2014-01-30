package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {
	private static Gson Converter = new GsonBuilder().create();
	public static String MessageToJsonString(Message message) {
		return Converter.toJson(message, Message.class);
	}
	public static Message JsonStringToMessage(String json) {
		return Converter.fromJson(json, Message.class);
	}
	public static String ListToJsonString(ArrayList<UserInformation> users) {
		return Converter.toJson(users, UserInformation[].class);
	}
	public static ArrayList<UserInformation> JsonStringToList(String json) {
		UserInformation[] list = Converter.fromJson(json, UserInformation[].class);
		return new ArrayList<UserInformation>(Arrays.asList(list));
	}
}
