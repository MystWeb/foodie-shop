package cn.myst.web.rabbitmq.common.serializer.impl;

import cn.myst.web.rabbitmq.common.serializer.Serializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 参考：https://blog.csdn.net/zzhongcy/article/details/120066586
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@Slf4j
public class JacksonSerializer implements Serializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // 默认为false，该属性主要是美化json输出
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        //反序列化时,遇到未知属性会不会报错
        //true - 遇到没有的属性就报错 false - 没有的属性不会管，不会报错
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        /*
         * 该特性决定parser将是否允许解析使用Java/C++ 样式的注释（包括'/'+'*' 和'//' 变量）。 由于JSON标准说明书上面没有提到注释是否是合法的组成，所以这是一个非标准的特性；
         * 尽管如此，这个特性还是被广泛地使用。
         *
         * 注意：该属性默认是false，因此必须显式允许，即通过JsonParser.Feature.ALLOW_COMMENTS 配置为true。
         */
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        // 该特性决定parser是否允许单引号来包住属性名称和字符串值。
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        /*
         * 这个特性决定parser是否将允许使用非双引号属性名字， （这种形式在Javascript中被允许，但是JSON标准说明书中没有）。
         *
         * 注意：由于JSON标准上需要为属性名称使用双引号，所以这也是一个非标准特性，默认是false的。
         * 同样，需要设置JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES为true，打开该特性。
         */
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    private final JavaType type;

    private JacksonSerializer(JavaType type) {
        this.type = type;
    }

    public JacksonSerializer(Type type) {
        this.type = mapper.getTypeFactory().constructType(type);
    }

    public static JacksonSerializer createParametricType(Class<?> cls) {
        return new JacksonSerializer(mapper.getTypeFactory().constructType(cls));
    }

    public byte[] serializeRaw(Object data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error("序列化出错", e);
        }
        return null;
    }

    public String serialize(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("序列化出错", e);
        }
        return null;
    }

    public <T> T deserialize(String content) {
        try {
            return mapper.readValue(content, type);
        } catch (IOException e) {
            log.error("反序列化出错", e);
        }
        return null;
    }

    public <T> T deserialize(byte[] content) {
        try {
            return mapper.readValue(content, type);
        } catch (IOException e) {
            log.error("反序列化出错", e);
        }
        return null;
    }

}
