package enterprises.orbital.auren.ec_client.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enterprises.orbital.auren.ec_client.ApiClient;
import enterprises.orbital.auren.ec_client.ApiException;
import enterprises.orbital.auren.ec_client.Configuration;
import enterprises.orbital.auren.ec_client.Pair;
import enterprises.orbital.auren.ec_client.TypeRef;
import enterprises.orbital.auren.ec_client.model.MarketInfo;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-12-23T04:14:45.140Z")
public class MarketStatApi {
  private ApiClient apiClient;

  public MarketStatApi() {
    this(Configuration.getDefaultApiClient());
  }

  public MarketStatApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Provide EVE market summary https://eve-central.com/home/develop.html
   * 
   * @param typeid
   *          The type ID of the item you are requesting. I.e., 34 for Tritanium.
   * @param hours
   *          Limit statistics to those with a reported time up to this value. Defaults to 24.
   * @param minQ
   *          The minimum quantity in an order to consider it for the statistics. Defaults to a heuristic scaled per type ID.
   * @param regionlimit
   *          Restrict statistics to a region.
   * @param usesystem
   *          Restrict statistics to a system.
   * @return a list of MarketInfo
   * @throws ApiException
   *           wrapping any exception which occurs during the invocation
   */
  public List<MarketInfo> requestMarketstat(List<Integer> typeid, List<Integer> hours, Integer minQ, List<Long> regionlimit, Long usesystem)
    throws ApiException {
    Object postBody = null;
    byte[] postBinaryBody = null;

    // verify the required parameter 'typeid' is set
    if (typeid == null) { throw new ApiException(400, "Missing the required parameter 'typeid' when calling requestMarketstat"); }

    // create path and map variables
    String path = "/marketstat/json".replaceAll("\\{format\\}", "json");

    // query params
    List<Pair> queryParams = new ArrayList<Pair>();
    Map<String, String> headerParams = new HashMap<String, String>();
    Map<String, Object> formParams = new HashMap<String, Object>();

    queryParams.addAll(apiClient.parameterToPairs("multi", "hours", hours));

    queryParams.addAll(apiClient.parameterToPairs("multi", "typeid", typeid));

    queryParams.addAll(apiClient.parameterToPairs("", "minQ", minQ));

    queryParams.addAll(apiClient.parameterToPairs("multi", "regionlimit", regionlimit));

    queryParams.addAll(apiClient.parameterToPairs("", "usesystem", usesystem));

    final String[] accepts = {
        "application/json"
    };
    final String accept = apiClient.selectHeaderAccept(accepts);

    final String[] contentTypes = {

    };
    final String contentType = apiClient.selectHeaderContentType(contentTypes);

    String[] authNames = new String[] {};

    @SuppressWarnings("rawtypes")
    TypeRef returnType = new TypeRef<List<MarketInfo>>() {};
    return apiClient.invokeAPI(path, "GET", queryParams, postBody, postBinaryBody, headerParams, formParams, accept, contentType, authNames, returnType);

  }

}
