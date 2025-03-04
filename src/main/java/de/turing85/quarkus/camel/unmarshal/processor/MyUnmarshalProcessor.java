package de.turing85.quarkus.camel.unmarshal.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;
import org.apache.camel.dataformat.mime.multipart.MimeMultipartDataFormat;
import org.apache.camel.support.processor.UnmarshalProcessor;

public class MyUnmarshalProcessor implements Processor {
  @Override
  public void process(Exchange exchange) throws Exception {
    exchange.setProperty(Exchange.CONTENT_TYPE, "text/plain");

    unmarshal(exchange);
    // exchange.setIn(exchange.getOut());
    AttachmentMessage msg = exchange.getIn(AttachmentMessage.class);
  }

  private void unmarshal(Exchange exchange) throws Exception {
    try (MimeMultipartDataFormat dataFormat = new MimeMultipartDataFormat()) {
      new UnmarshalProcessor(dataFormat).process(exchange);
    }
  }
}
