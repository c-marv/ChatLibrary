package common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonConverter {
	public static Gson Converter = new GsonBuilder().create();
	public static String MessageToJsonString(Message message) {
		return Converter.toJson(message, Message.class);
	}
	public static Message JsonStringToMessage(String json) {
		return Converter.fromJson(json, Message.class);
	}
}
