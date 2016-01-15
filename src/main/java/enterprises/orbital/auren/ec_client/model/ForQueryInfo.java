package enterprises.orbital.auren.ec_client.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import enterprises.orbital.auren.ec_client.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-12-23T04:14:45.140Z")
public class ForQueryInfo {

  private Boolean       bid     = null;
  private List<Integer> types   = new ArrayList<Integer>();
  private List<Long>    regions = new ArrayList<Long>();
  private List<Long>    systems = new ArrayList<Long>();
  private Integer       hours   = null;
  private Integer       minq    = null;

  @ApiModelProperty(value = "")
  @JsonProperty("bid")
  public Boolean getBid() {
    return bid;
  }

  public void setBid(Boolean bid) {
    this.bid = bid;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("types")
  public List<Integer> getTypes() {
    return types;
  }

  public void setTypes(List<Integer> types) {
    this.types = types;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("regions")
  public List<Long> getRegions() {
    return regions;
  }

  public void setRegions(List<Long> regions) {
    this.regions = regions;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("systems")
  public List<Long> getSystems() {
    return systems;
  }

  public void setSystems(List<Long> systems) {
    this.systems = systems;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("hours")
  public Integer getHours() {
    return hours;
  }

  public void setHours(Integer hours) {
    this.hours = hours;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("minq")
  public Integer getMinq() {
    return minq;
  }

  public void setMinq(Integer minq) {
    this.minq = minq;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ForQueryInfo {\n");

    sb.append("    bid: ").append(StringUtil.toIndentedString(bid)).append("\n");
    sb.append("    types: ").append(StringUtil.toIndentedString(types)).append("\n");
    sb.append("    regions: ").append(StringUtil.toIndentedString(regions)).append("\n");
    sb.append("    systems: ").append(StringUtil.toIndentedString(systems)).append("\n");
    sb.append("    hours: ").append(StringUtil.toIndentedString(hours)).append("\n");
    sb.append("    minq: ").append(StringUtil.toIndentedString(minq)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
