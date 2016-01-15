package enterprises.orbital.auren.frontend;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.amazon.speech.speechlet.servlet.SpeechletServlet;

import enterprises.orbital.base.NoPersistentPropertyException;
import enterprises.orbital.base.OrbitalProperties;
import enterprises.orbital.base.PersistentProperty;

public class OrbitalAurenServlet extends SpeechletServlet {
  private static final long                       serialVersionUID = 2814724714723274994L;
  private static final List<Pair<String, String>> configuration    = Arrays
      .asList(Pair.of("enterprises.orbital.auren.frontend.speechlet.disableRequestSignatureCheck",
                      "com.amazon.speech.speechlet.servlet.disableRequestSignatureCheck"),
              Pair.of("enterprises.orbital.auren.frontend.speechlet.supportedApplicationIds", "com.amazon.speech.speechlet.servlet.supportedApplicationIds"),
              Pair.of("enterprises.orbital.auren.frontend.speechlet.timestampTolerance", "com.amazon.speech.speechlet.servlet.timestampTolerance"));

  public OrbitalAurenServlet() throws IOException, NoPersistentPropertyException {
    super();

    // Populate properties
    OrbitalProperties.addPropertyFile("Frontend.properties");

    // setup needed properties
    for (Pair<String, String> nextPair : configuration) {
      System.setProperty(nextPair.getRight(), PersistentProperty.getPropertyWithFallback(nextPair.getLeft()));
    }

    // Add our speechlet
    this.setSpeechlet(new OrbitalSpeechlet());

  }
}
