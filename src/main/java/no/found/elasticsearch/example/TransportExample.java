package no.found.elasticsearch.example;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.concurrent.TimeUnit;

public class TransportExample {

    public ESLogger logger = ESLoggerFactory.getLogger(getClass().getCanonicalName());

    public static void main(String[] args)  {
        new TransportExample().run(args);
    }

    public void run(String[] args) {
        TransportCommandLineParameters parameters = new TransportCommandLineParameters().parse(args, logger);

        logger.info("Connecting to cluster: [{}] using api key: [{}] in region [{}]", parameters.clusterId, parameters.apiKey, parameters.region);

        // Build the settings for our client.
        Settings settings = ImmutableSettings.settingsBuilder()
            // Setting "transport.type" enables this module:
            .put("transport.type", "no.found.elasticsearch.transport.netty.FoundNettyTransportModule")
            // Create an api key via the console and add it here:
            .put("transport.found.api-key", parameters.apiKey)

                    // Used by Elasticsearch:
            .put("cluster.name", parameters.clusterId)
            .put("client.transport.ignore_cluster_name", false)

            .build();

        // Instantiate a TransportClient and add Found Elasticsearch to the list of addresses to connect to.
        // Only port 9343 (SSL-encrypted) is currently supported.
        Client client = new TransportClient(settings)
            .addTransportAddress(new InetSocketTransportAddress(parameters.clusterId + "-" + parameters.region + ".foundcluster.com", 9343));

        while(true) {
            try {
                logger.info("Getting cluster health... ");
                ActionFuture<ClusterHealthResponse> healthFuture = client.admin().cluster().health(Requests.clusterHealthRequest());
                ClusterHealthResponse healthResponse = healthFuture.get(5, TimeUnit.SECONDS);
                logger.info("Got cluster health response: [{}]", healthResponse.getStatus());
            } catch(Throwable t) {
                logger.error("Unable to get cluster health response: [{}]", t.getMessage());
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ie) { ie.printStackTrace(); }
        }
    }
}
