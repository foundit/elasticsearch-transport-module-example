package no.found.elasticsearch.example;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import org.elasticsearch.common.logging.ESLogger;

@Parameters(commandDescription = "Shows an example usage of the Found Elasticsearch Transport Module.", separators = "=")
public class TransportCommandLineParameters {
    @Parameter(names = {"-c", "--cluster-id"}, required = true, description = "Cluster id")
    public String clusterId;

    @Parameter(names = {"-a", "--api-key"}, required = true, description = "API key from the cluster ACL.")
    public String apiKey;

    @Parameter(names = {"-r", "--region"}, description = "Region (us-east-1/eu-west-1/...)")
    public String region = "us-east-1";

    public TransportCommandLineParameters parse(String[] args, ESLogger logger) {
        JCommander commander = new JCommander(this);
        try {
            commander.parse(args);
        } catch (ParameterException pe) {
            logger.error(pe.getMessage());
            commander.usage();
            System.exit(-1);
        }

        return this;
    }
}
