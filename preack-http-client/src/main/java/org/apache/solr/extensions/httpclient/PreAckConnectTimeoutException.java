package org.apache.solr.extensions.httpclient;

import java.net.SocketTimeoutException;

/**
 * Indicates that the expected magic pre-ack byte sequence did not arrive in the
 * given timeout.
 * 
 * @author Shopping24 GmbH, Torsten Bøgh Köster (@tboeghk)
 */
public class PreAckConnectTimeoutException extends SocketTimeoutException {

   private static final long serialVersionUID = -1905059210843372520L;

   public PreAckConnectTimeoutException() {
      super();
   }

   public PreAckConnectTimeoutException(final String message) {
      super(message);
   }

   public PreAckConnectTimeoutException(final String message, final Throwable cause) {
      super(message);
      initCause(cause);
   }

}
