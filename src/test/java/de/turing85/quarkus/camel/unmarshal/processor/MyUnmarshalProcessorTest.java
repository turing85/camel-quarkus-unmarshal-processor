package de.turing85.quarkus.camel.unmarshal.processor;

import com.google.common.truth.Truth;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

class MyUnmarshalProcessorTest extends CamelTestSupport {
  @Test
  void test() throws Exception {
    // GIVEN
    String expectedMessage = "Hello, world!";
    String body = """
        ------=_divider
        Content-Type: text/plain; charset=ISO-8859-15
        Content-Transfer-Encoding: 8bit

        %s
        ------=_divider
        Content-Type: application/octet-stream
        Content-Transfer-Encoding: base64
        Content-Disposition: attachment; filename=JMS_Normalized_Message_Properties
        Content-ID: JMS_Normalized_Message_Properties

        SGVsbG8sIHdvcmxkCg==
        ------=_divider--
        """.formatted(expectedMessage);
    Exchange exchange = ExchangeBuilder.anExchange(context())
        .withHeader(Exchange.CONTENT_TYPE,
            "multipart/related; type=\"text/plain\";boundary=\"----=_divider\"")
        .withBody(body).build();

    // when
    new MyUnmarshalProcessor().process(exchange);

    // then
    String actualMessage = exchange.getMessage().getBody(String.class);
    System.out.println(actualMessage);
    Truth.assertThat(actualMessage).isEqualTo(expectedMessage);
  }
}
