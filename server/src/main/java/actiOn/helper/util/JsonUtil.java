package actiOn.helper.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()  // HTML 이스케이프 비활성화
            .setPrettyPrinting()    // 읽기 쉬운 형식으로 출력
            .create();

    // 객체를 JSON 문자열로 변환
    public static String toJson(Object src, Type typeOfSrc) {
        return gson.toJson(src, typeOfSrc);
    }
}
