package org.apache.solr.extensions.httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;

/**
 * A Pooling client connection manager that expects to receive a defined pre-ack
 * byte string from the connected server in a very short timeout.
 * 
 * @author Shopping24 GmbH, Torsten Bøgh Köster (@tboeghk)
 */
public class PreAckPoolingClientConnectionManager extends PoolingClientConnectionManager {

   private final static DnsResolver dnsr = new SystemDefaultDnsResolver();
   
   public PreAckPoolingClientConnectionManager() {
      this(SchemeRegistryFactory.createDefault());
   }

   public PreAckPoolingClientConnectionManager(final SchemeRegistry schreg) {
      this(schreg, -1, TimeUnit.MILLISECONDS);
   }

   public PreAckPoolingClientConnectionManager(
         final SchemeRegistry schemeRegistry,
         final long timeToLive, final TimeUnit tunit) {
      this(schemeRegistry, timeToLive, tunit, dnsr);
   }

   public PreAckPoolingClientConnectionManager(final SchemeRegistry schemeRegistry,
         final long timeToLive, final TimeUnit tunit,
         final DnsResolver dnsResolver) {
      super(schemeRegistry, timeToLive, tunit, dnsResolver);
   }

   /**
    * 
    */
   @Override
   protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg) {
      return new DefaultClientConnectionOperator(schreg, dnsr) {
         @Override
         public OperatedClientConnection createConnection() {
            return new PreAckClientConnection();
         }
      };
   }

}
