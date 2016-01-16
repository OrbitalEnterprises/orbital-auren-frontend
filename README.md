# Orbital Auren (frontend)

Auren is an [Amazon Alexa skill](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/getting-started-guide) that handles a few simple EVE Online related requests.  We named our skill "Auren" to complement "Aura", the in-game digital assistant (hah hah).  At the moment, this is mostly a demonstration project and this Alexa skill is still in development mode (i.e. you can't add it to your Alexa device).  We haven't yet invested the time to make this truly awesome.  Our [blog](http://blog.orbital.enterprises/) has a tutorial/story about how we created this skill.  Any plans we make to expand this skill will also be added to our blog.

This module provides the servlet which handles Auren's ["intents"](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/defining-the-voice-interface#The Intent Schema).  The main servlet is `enterprises.orbital.auren.frontend.OrbitalAurenServlet`, which is just a boilerplate extension of `com.amazon.speech.speechlet.servlet.SpeechletServlet` provided by the Amazon Alexa Skills Kit.  The real work of implementing the intents is in the "speechlet" - `enterprises.orbital.auren.frontend.OrbitalSpeechlet` - which extends `com.amazon.speech.speechlet.Speechlet`.  See the [documentation](https://github.com/amzn/alexa-skills-kit-java) for the Alexa Skills Kit java library for more details on how to use the ASK Speechlet class.

One of the demonstration intents calls [eve-central](https://eve-central.com/) to get EVE market data.  These calls are handled by an auto-generated [Swagger](http://swagger.io) client.  The specification for our generated client can be found [here](https://raw.githubusercontent.com/OrbitalEnterprises/swagger-specs/master/eve-central.yaml).  You can generate your own client in the language of your choice using the [Online Swagger Editor](http://editor.swagger.io/#/).  Select "File->Import URL..." and copy `https://raw.githubusercontent.com/OrbitalEnterprises/swagger-specs/master/eve-central.yaml` into the text box.

We run a live instance of this servlet at https://alexa.orbital.enterprises.  You can run the servlet locally if you like, but if you want to test against Amazon Alexa, you'll need to run the servlet somewhere that is Internet accessible.  You'll also need to either set up a proper SSL certificate, or follow the instructions on the Alexa development site for setting up a test certificate.

## Configuration

The servlet needs to know the application IDs of the Alexa skills it is providing.  Amazon expects this value to be stored in the Java property `com.amazon.speech.speechlet.servlet.supportedApplicationIds`.  For Auren, we actually store this property at `enterprises.orbital.auren.frontend.speechlet.supportedApplicationIds` in the property file `Frontend.properties`.  The Auren servlet copies this value to the correct Amazon property at startup.  We do things this way so that we can override the property in the usual Orbital ways (e.g. database, other property file, etc).  If you don't want to mess with this, you can change the startup code in `OrbitalAurenServlet`.  If you're building with Maven, the expectation is that you'll set `enterprises.orbital.auren.frontend.speechlet.supportedApplicationIds` in your local `settings.xml` file.

A proper Alexa skill needs an intent schema, a list of custom slot values, and sample utterances.  These are all stored in the `speechAssets` directory.  See the appropriate section below for instructions on how to use these settings when configuring an Alexa skill.

Finally, this demonstration illustrates how to look up market prices for in game items.  To do this properly, we need a list of in-game items and their corresponding type IDs.  To do this even more properly, these things should be stored in a database or other easy to query source.  However, for this demonstration, we simply store this data in a file which we load into memory at servlet startup.  That file is `market_types.txt` and consists of a list of (item, typeID) pairs.  This snapshot of data was taken just before the ["Frostline"](http://updates.eveonline.com/date/2015-12-08/) release, and is probably out of date by the time you're reading this.

## Build

### Maven

We use [Maven](http://maven.apache.org) to build the frontend for Auren.  Once you clone this repository, use "mvn install" to build a war which can then be deployed on your favorite servlet engine.  See the instructions below for deploying to Tomcat 7.

Note that the Alexa Skills Kit java library is not in [Maven Central](http://search.maven.org) so we've copied it into a local repository in the `repo` directory.  You'll need to update this directory or otherwise change dependencies in `pom.xml` if you need a different version.

### Non-Maven

The Auren frontend has the following dependencies which you'll need to build and run properly:

* [eve-xml-api v1.0.0](https://github.com/OrbitalEnterprises/eve-xml-api)
* [javax.servlet-api 3.1.0](http://search.maven.org/#artifactdetails%7Cjavax.servlet%7Cjavax.servlet-api%7C3.1.0%7Cjar)
* [log4j 1.2.17](http://search.maven.org/#artifactdetails%7Clog4j%7Clog4j%7C1.2.17%7Cjar)
* [org.slf4j-api 1.7.10](http://search.maven.org/#artifactdetails%7Corg.slf4j%7Cslf4j-api%7C1.7.10%7Cjar)
* [slf4j-log4j12 1.7.10](http://search.maven.org/#artifactdetails%7Corg.slf4j%7Cslf4j-log4j12%7C1.7.10%7Cjar)
* [org.apache.commons-lang3 3.3.2](http://search.maven.org/#artifactdetails%7Corg.apache.commons%7Ccommons-lang3%7C3.3.2%7Cjar)
* [org.apache.commons.io 2.4](http://search.maven.org/#artifactdetails%7Corg.apache.directory.studio%7Corg.apache.commons.io%7C2.4%7Cjar)
* [swagger-annotations 1.5.4](http://search.maven.org/#artifactdetails%7Cio.swagger%7Cswagger-annotations%7C1.5.4%7Cjar)
* [jersey-client 1.18](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey%7Cjersey-client%7C1.18%7Cjar)
* [jersey-multipart 1.18.1](http://search.maven.org/#artifactdetails%7Ccom.sun.jersey.contribs%7Cjersey-multipart%7C1.18.1%7Cjar)
* [jackson-core 2.4.2](http://search.maven.org/#artifactdetails%7Ccom.fasterxml.jackson.core%7Cjackson-core%7C2.4.2%7Cjar)
* [jackson-annotations 2.4.2](http://search.maven.org/#artifactdetails%7Ccom.fasterxml.jackson.core%7Cjackson-annotations%7C2.4.2%7Cjar)
* [jackson-databind 2.4.2](http://search.maven.org/#artifactdetails%7Ccom.fasterxml.jackson.core%7Cjackson-databind%7C2.4.2%7Cjar)
* [jackson-datatype-joda 2.1.5](http://search.maven.org/#artifactdetails%7Ccom.fasterxml.jackson.datatype%7Cjackson-datatype-joda%7C2.1.5%7Cjar)
* [joda-time 2.3](http://search.maven.org/#artifactdetails%7Cjoda-time%7Cjoda-time%7C2.3%7Cjar)

## Configuring An Alexa Skill

To configure an Alexa Skill you'll need an [Amazon Developer Account](https://developer.amazon.com).  Once you have an account, go to "APPS & SERVICES" and select "Alexa Skills Kit".  Click the "Add a New Skill" button which will take you to a six page form.  You need to fill out the first three pages in order to live test your skill.

The first page is where you configure your invocation name and the type of endpoint you're using.  If you're using this module, then you're using an HTTPS endpoint.  Write down the Application ID you are assigned.  You'll need this at build time as described in the configuration section above.

The second page is where you configure your intents, any custom slots you declare, and your sample utterances.  You can copy the appropriate files from this module or make your own.  You can also create the same custom slot we described on our blog by clicking on "Add Slot Type" and copying over the `ItemSlotValues.txt` file.  Note that you are currently limited to 5000 total slot values across *all* your custom slots.  This is what prevented us from adding all the EVE item types to our custom slot, not that the values we added really helped with voice recognition anyway.

On the third page you'll configure your SSL certificate.  For testing you can just use a self-signed certificate.  To fully deploy, however, you'll need to provide a certificate signed by a trusted authority.

## Testing

There are two ways you can test:

1. On the fourth page of the new skill form on the Amazon developer site.
2. Using your Echo or other Alexa device if it is registered to the same login as your development account.

The page on the new skill form lets you enter a text string and shows the response from your servlet (if all goes well).  This is a good way to test well-formed requests.  You can even hear what Alexa will say by clicking the "Listen" button on the test page.  If your servlet is not working, you'll either see an error response on the test page, or you'll see whatever your servlet decided to respond with.  When testing with a text string, you shouldn't ever have a problem parsing your intent since no voice recognition is involved.  So errors at this point are usually communication errors between Alexa and your servlet.

The really cool thing about the skills kit is that it automatically adds your skill to your Echo device if you create your development account using the same login associated with your device.  If your servlet is responding properly from the test page, then you can simply walk up to your Echo and ask your skill a question, e.g. "Alexa, ask Auren for the current time".  If all goes well, you'll hear Alexa say your response.  Problems at this phase are usually voice recognition problems.  An easy way to see how Alexa translated your question is to go to the [Alexa website](http://alexa.amazon.com/) and look at the card that was generated for your query.  Click on the "more" tab and you'll see a "Voice feedback" box showing how your question was translated.

## Deploying to Tomcat 7

This section covers deploying the servlet to a servlet container, not deploying your Alexa skill.  To deploy your Alexa skill, fill out the rest of the new skill form on the Amazon site and submit your skill for approval.

The default pom.xml in the project includes the [Tomcat Maven plugin](http://tomcat.apache.org/maven-plugin.html) which makes it easy to deploy directly to a Tomcat 7 instance.  This is normally done by adding two stanzas to your settings.xml:

```xml
<servers>
  <server>
    <id>LocalTomcatServer</id>
    <username>admin</username>
    <password>password</password>
  </server>    
</servers>

<profiles>
  <profile>
    <id>LocalTomcat</id>
    <properties>
      <enterprises.orbital.alexa.frontend.tomcat.url>http://localhost:8080/manager/text</enterprises.orbital.alexa.frontend.tomcat.url>
      <enterprises.orbital.alexa.frontend.tomcat.server>LocalTomcatServer</enterprises.orbital.alexa.frontend.tomcat.server>
      <enterprises.orbital.alexa.frontend.tomcat.path>/alexa-frontend</enterprises.orbital.alexa.frontend.tomcat.path>
    </properties>	
  </profile>
</profiles>
```

The first stanza specifies the management credentials for your Tomcat 7 instance.  The second stanza defines the properties needed to install into the server you just defined.  Note that the server you deploy to must be accessible form the Internet in order to test from Amazon's test page or your Echo.

With these settings, you can deploy to your Tomcat 7 instance as follows:

```
mvn -P LocalTomcat tomcat7:deploy
```

If you've already deployed, use "redploy" instead.  See the [Tomcat Maven plugin documentation](http://tomcat.apache.org/maven-plugin-2.2/) for more details on how the deployment plugin works.
