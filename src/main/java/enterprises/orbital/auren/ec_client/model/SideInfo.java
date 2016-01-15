package enterprises.orbital.auren.ec_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import enterprises.orbital.auren.ec_client.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2015-12-23T04:14:45.140Z")
public class SideInfo {

  private ForQueryInfo forQuery    = null;
  private Long         volume      = null;
  private Double       wavg        = null;
  private Double       avg         = null;
  private Double       variance    = null;
  private Double       stdDev      = null;
  private Double       median      = null;
  private Double       fivePercent = null;
  private Double       max         = null;
  private Double       min         = null;
  private Boolean      highToLow   = null;
  private Long         generated   = null;

  @ApiModelProperty(value = "")
  @JsonProperty("forQuery")
  public ForQueryInfo getForQuery() {
    return forQuery;
  }

  public void setForQuery(ForQueryInfo forQuery) {
    this.forQuery = forQuery;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("volume")
  public Long getVolume() {
    return volume;
  }

  public void setVolume(Long volume) {
    this.volume = volume;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("wavg")
  public Double getWavg() {
    return wavg;
  }

  public void setWavg(Double wavg) {
    this.wavg = wavg;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("avg")
  public Double getAvg() {
    return avg;
  }

  public void setAvg(Double avg) {
    this.avg = avg;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("variance")
  public Double getVariance() {
    return variance;
  }

  public void setVariance(Double variance) {
    this.variance = variance;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("stdDev")
  public Double getStdDev() {
    return stdDev;
  }

  public void setStdDev(Double stdDev) {
    this.stdDev = stdDev;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("median")
  public Double getMedian() {
    return median;
  }

  public void setMedian(Double median) {
    this.median = median;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("fivePercent")
  public Double getFivePercent() {
    return fivePercent;
  }

  public void setFivePercent(Double fivePercent) {
    this.fivePercent = fivePercent;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("max")
  public Double getMax() {
    return max;
  }

  public void setMax(Double max) {
    this.max = max;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("min")
  public Double getMin() {
    return min;
  }

  public void setMin(Double min) {
    this.min = min;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("highToLow")
  public Boolean getHighToLow() {
    return highToLow;
  }

  public void setHighToLow(Boolean highToLow) {
    this.highToLow = highToLow;
  }

  @ApiModelProperty(value = "")
  @JsonProperty("generated")
  public Long getGenerated() {
    return generated;
  }

  public void setGenerated(Long generated) {
    this.generated = generated;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SideInfo {\n");

    sb.append("    forQuery: ").append(StringUtil.toIndentedString(forQuery)).append("\n");
    sb.append("    volume: ").append(StringUtil.toIndentedString(volume)).append("\n");
    sb.append("    wavg: ").append(StringUtil.toIndentedString(wavg)).append("\n");
    sb.append("    avg: ").append(StringUtil.toIndentedString(avg)).append("\n");
    sb.append("    variance: ").append(StringUtil.toIndentedString(variance)).append("\n");
    sb.append("    stdDev: ").append(StringUtil.toIndentedString(stdDev)).append("\n");
    sb.append("    median: ").append(StringUtil.toIndentedString(median)).append("\n");
    sb.append("    fivePercent: ").append(StringUtil.toIndentedString(fivePercent)).append("\n");
    sb.append("    max: ").append(StringUtil.toIndentedString(max)).append("\n");
    sb.append("    min: ").append(StringUtil.toIndentedString(min)).append("\n");
    sb.append("    highToLow: ").append(StringUtil.toIndentedString(highToLow)).append("\n");
    sb.append("    generated: ").append(StringUtil.toIndentedString(generated)).append("\n");
    sb.append("}");
    return sb.toString();
  }
}
