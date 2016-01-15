package enterprises.orbital.auren.ec_client;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-12-23T04:14:45.140Z")
public class JSON {
  private ObjectMapper mapper;

  public JSON() {
    mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    mapper.registerModule(new JodaModule());
  }

  /**
   * Serialize the given Java object into JSON string.
   * 
   * @param obj
   *          object to serialize
   * @return serialization string
   * @throws ApiException
   *           if an error occurs during serialization
   */
  public String serialize(Object obj) throws ApiException {
    try {
      if (obj != null)
        return mapper.writeValueAsString(obj);
      else
        return null;
    } catch (Exception e) {
      throw new ApiException(400, e.getMessage());
    }
  }

  /**
   * Deserialize the given JSON string to Java object.
   *
   * @param body
   *          The JSON string
   * @param returnType
   *          The type to deserialize into
   * @param <T>
   *          type to cast deserialization to
   * @return The deserialized Java object
   * @throws ApiException
   *           if an error occurs during deserialization
   */
  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  public <T> T deserialize(String body, TypeRef returnType) throws ApiException {
    JavaType javaType = mapper.constructType(returnType.getType());
    try {
      return mapper.readValue(body, javaType);
    } catch (IOException e) {
      if (returnType.getType().equals(String.class))
        return (T) body;
      else
        throw new ApiException(500, e.getMessage(), null, body);
    }
  }
}
