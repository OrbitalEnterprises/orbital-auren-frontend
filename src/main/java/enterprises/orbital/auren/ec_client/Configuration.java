package enterprises.orbital.auren.ec_client;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-12-23T04:14:45.140Z")
public class Configuration {
  private static ApiClient defaultApiClient = new ApiClient();

  /**
   * Get the default API client, which would be used when creating API instances without providing an API client.
   * 
   * @return default API client
   */
  public static ApiClient getDefaultApiClient() {
    return defaultApiClient;
  }

  /**
   * Set the default API client, which would be used when creating API instances without providing an API client.
   * 
   * @param apiClient
   *          client to set
   */
  public static void setDefaultApiClient(ApiClient apiClient) {
    defaultApiClient = apiClient;
  }
}
