package ru.javaops.topjava2.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.to.DishTo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class JsonUtil {
    private static ObjectMapper mapper;

    public static void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }

    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = mapper.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static Map<LocalDate, List<DishTo>> readMapValues(String json) {
        try {
            Map<LocalDate, List<DishTo>> map = new HashMap<>();
            Map<String, List<Map<String, Object>>> mapStr = mapper.readValue(json, Map.class);

            for (Map.Entry<String, List<Map<String, Object>>> obj : mapStr.entrySet()) {
                LocalDate key = DishUtil.convert(obj.getKey());
                obj.getValue().stream().forEach(o -> new DishTo((int) o.get("id"), (String) o.get("name"), (Double) o.get("price")));
                List<DishTo> value = obj.getValue().stream()
                        .map(o -> new DishTo((int) o.get("id"), (String) o.get("name"), (Double) o.get("price"))).collect(Collectors.toList());
                map.put(key, value);
            }
            return map;

        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid read Map from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> String writeAdditionProps(T obj, String addName, Object addValue) {
        return writeAdditionProps(obj, Map.of(addName, addValue));
    }

    public static <T> String writeAdditionProps(T obj, Map<String, Object> addProps) {
        Map<String, Object> map = mapper.convertValue(obj, new TypeReference<>() {
        });
        map.putAll(addProps);
        return writeValue(map);
    }
}