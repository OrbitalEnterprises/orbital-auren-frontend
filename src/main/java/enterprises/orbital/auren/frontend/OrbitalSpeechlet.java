package enterprises.orbital.auren.frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import enterprises.orbital.auren.ec_client.ApiException;
import enterprises.orbital.auren.ec_client.api.MarketStatApi;
import enterprises.orbital.auren.ec_client.model.MarketInfo;
import enterprises.orbital.base.PersistentProperty;
import enterprises.orbital.evexmlapi.EveXmlApiAdapter;
import enterprises.orbital.evexmlapi.EveXmlApiConfig;
import enterprises.orbital.evexmlapi.IEveXmlApi;
import enterprises.orbital.evexmlapi.svr.IServerAPI;
import enterprises.orbital.evexmlapi.svr.IServerStatus;

public class OrbitalSpeechlet implements Speechlet {
  private static final String    INTENT_AMAZON_HELP   = "AMAZON.HelpIntent";
  private static final String    INTENT_SERVER_STATUS = "ServerStatusIntent";
  private static final String    INTENT_TIME          = "ServerTimeIntent";
  private static final String    INTENT_MARKET_PRICE  = "MarketPriceIntent";
  private static final String    AGENT_OP             = "enterprises.orbital.auren.frontend.agent";
  private static final Logger    log                  = Logger.getLogger(OrbitalSpeechlet.class.getName());

  protected Map<String, Integer> typeNameMap          = new HashMap<String, Integer>();

  public OrbitalSpeechlet() {
    // Initialize typeNameMap
    BufferedReader reader = null;
    try {
      InputStream typesStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("market_types.txt");
      reader = new BufferedReader(new InputStreamReader(typesStream));
      String next = reader.readLine();
      while (next != null) {
        String[] pair = next.split(",");
        typeNameMap.put(pair[0].toLowerCase(), Integer.valueOf(pair[1]));
        next = reader.readLine();
      }
    } catch (FileNotFoundException e) {
      log.log(Level.SEVERE, "failed to initialize typeNameMap, price queries won't work!", e);
    } catch (IOException e) {
      log.log(Level.SEVERE, "Error while initializing typeNameMap, price queries may not work!", e);
    } finally {
      if (reader != null) try {
        reader.close();
      } catch (IOException e) {
        log.log(Level.SEVERE, "unexpected exception while closing reader", e);
      }
    }
    log.info("typeNameMap initialized!");
  }

  @Override
  public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
    log.info("onSessionStarted requestId=" + request.getRequestId() + ", sessionId=" + session.getSessionId());
  }

  @Override
  public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
    log.info("onLaunch requestId=" + request.getRequestId() + ", sessionId=" + session.getSessionId());
    return getWelcomeResponse();
  }

  @Override
  public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
    log.info("onIntent requestId=" + request.getRequestId() + ", sessionId=" + session.getSessionId());

    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;

    if (INTENT_SERVER_STATUS.equals(intentName)) {
      return getServerStatusResponse();
    } else if (INTENT_TIME.equals(intentName)) {
      return getServerTimeResponse();
    } else if (INTENT_MARKET_PRICE.equals(intentName)) {
      return getMarketPriceResponse(intent.getSlot("Item").getValue());
    } else if (INTENT_AMAZON_HELP.equals(intentName)) {
      return getHelpResponse();
    } else {
      throw new SpeechletException("Invalid Intent");
    }
  }

  @Override
  public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
    log.info("onSessionEnded requestId=" + request.getRequestId() + ", sessionId=" + session.getSessionId());
  }

  /**
   * Creates and returns a {@code SpeechletResponse} with a welcome message.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getWelcomeResponse() {
    String speechText = "Welcome to Orbital, ask us a question about EVE Online!";

    // Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle("EVE Online Information");
    card.setContent(speechText);

    // Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    // Create reprompt
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }

  /**
   * Creates a {@code SpeechletResponse} for the server status intent.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getServerStatusResponse() {
    String agent = PersistentProperty.getPropertyWithFallback(AGENT_OP, "");
    String speechText = "I'm sorry, I wasn't able to determine EVE Online server status at this time.";
    try {
      IEveXmlApi api = new EveXmlApiAdapter(EveXmlApiConfig.get().agent(agent));
      IServerAPI serverAPI = api.getServerAPIService();
      IServerStatus status = serverAPI.requestServerStatus();
      if (serverAPI.isError()) {
        speechText = "I encountered a network error while attempting to determine server status.  Please try again in a few moments.";
      } else {
        if (!status.isServerOpen()) {
          speechText = "Tranquility is currently down.";
        } else {
          speechText = "Tranquility is currently up with " + status.getOnlinePlayers() + " players online.";
        }
      }
    } catch (IOException e) {
      // ignore, leave error speech text
    } catch (URISyntaxException e) {
      // this should never happen. If it does, then leave the error text as the response
      e.printStackTrace();
    }

    // Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle("EVE Online Server Status");
    card.setContent(speechText);

    // Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    SpeechletResponse result = SpeechletResponse.newTellResponse(speech, card);
    result.setShouldEndSession(true);

    return result;
  }

  /**
   * Creates a {@code SpeechletResponse} for the server time intent.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getServerTimeResponse() {
    // EVE time is always UTC so just return that directly
    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    String speechText = "The current EVE time is " + formatter.format(now) + ".";

    // Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle("EVE Online Time");
    card.setContent(speechText);

    // Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    SpeechletResponse result = SpeechletResponse.newTellResponse(speech, card);
    result.setShouldEndSession(true);

    return result;
  }

  /**
   * Creates a {@code SpeechletResponse} for a market price intent.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getMarketPriceResponse(String item) {
    // Convert item name to lowercase and attempt to find a match.
    // If we find a match, then call Eve Central to attempt to find the price.
    item = item.toLowerCase();
    String speechText = "I'm sorry, I couldn't find an EVE market item matching that name.  Either that item isn't on the market, isn't in my database, or I don't understand your pronunciation.";
    if (typeNameMap.containsKey(item)) {
      // Found it, look for a price
      int typeID = typeNameMap.get(item);
      MarketStatApi api = new MarketStatApi();
      try {
        List<MarketInfo> info = api.requestMarketstat(Collections.<Integer> singletonList(new Integer(typeID)), null, null, null, 30000142L);
        if (!info.isEmpty()) {
          MarketInfo data = info.get(0);
          Double bid = data.getBuy() != null ? data.getBuy().getMax() : null;
          Double ask = data.getSell() != null ? data.getSell().getMin() : null;
          BigDecimal bidPrice = new BigDecimal(bid.doubleValue());
          BigDecimal askPrice = new BigDecimal(ask.doubleValue());
          bidPrice = bidPrice.setScale(2, RoundingMode.HALF_UP);
          askPrice = askPrice.setScale(2, RoundingMode.HALF_UP);
          speechText = "Jita is reporting ";
          speechText += bid == null ? "no current bid" : "a bid of " + bidPrice.toPlainString() + " isk";
          speechText += " and ";
          speechText += ask == null ? "no current ask" : "an ask of " + askPrice.toPlainString() + " isk";
          speechText += " for " + item + ".";
        } else {
          speechText = "I'm sorry, there is no market information available for " + item + ", perhaps this is a rare item?";
          log.info("Empty market info list for \"" + item + "\"");
        }
      } catch (ApiException e) {
        log.log(Level.WARNING, "Error occurred while calling EveCentral with type: " + typeID + " (" + item + ")", e);
        speechText = "I'm sorry, an error occurred while looking up market information for " + item + ", please try again";
      }
    } else {
      log.info("Unable to match item \"" + item + "\" to a type, skipping");
    }

    // Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle("EVE Market Price");
    card.setContent(speechText);

    // Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);
    SpeechletResponse result = SpeechletResponse.newTellResponse(speech, card);
    result.setShouldEndSession(true);

    return result;
  }

  /**
   * Creates a {@code SpeechletResponse} for the help intent.
   *
   * @return SpeechletResponse spoken and visual response for the given intent
   */
  private SpeechletResponse getHelpResponse() {
    String speechText = "You can ask me a question about EVE Online, such as: what is the current server status?";

    // Create the Simple card content.
    SimpleCard card = new SimpleCard();
    card.setTitle("EVE Online Information");
    card.setContent(speechText);

    // Create the plain text output.
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    // Create reprompt
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(speech);

    return SpeechletResponse.newAskResponse(speech, reprompt, card);
  }

}
