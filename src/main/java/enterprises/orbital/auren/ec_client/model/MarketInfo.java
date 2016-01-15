package enterprises.orbital.auren.ec_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import enterprises.orbital.auren.ec_client.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-12-23T04:14:45.140Z")
public class MarketInfo {

  private SideInfo buy  = null;
  private SideInfo all  = null;
  private SideInfo sell = null;

  @ApiModelProperty(value = "")
  @JsonProperty("buy")
  public SideInfo getBuy() {
    return buy;
  }

  public void setBuy(SideInfo buy) {
    this.buy = buy;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("all")
  public SideInfo getAll() {
    return all;
  }

  public void setAll(SideInfo all) {
    this.all = all;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("sell")
  public SideInfo getSell() {
    return sell;
  }

  public void setSell(SideInfo sell) {
    this.sell = sell;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MarketInfo {\n");

    sb.append("    buy: ").append(StringUtil.toIndentedString(buy)).append("\n");
    sb.append("    all: ").append(StringUtil.toIndentedString(all)).append("\n");
    sb.append("    sell: ").append(StringUtil.toIndentedString(sell)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
