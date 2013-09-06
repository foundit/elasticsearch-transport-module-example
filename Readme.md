# Found Elasticsearch Transport client example

To run the example use ``mvn exec:java``:

    mvn compile exec:java -Dexec.args="--api-key=YOUR_CLUSTER_API_KEY --cluster-id=YOUR_CLUSTER_ID"

Accepted parameters inside ``-Dexec.args=".."``:

    Usage: <main class> [options]
      Options:
      * -a, --api-key
           API key from the cluster ACL.
      * -c, --cluster-id
           Cluster id
        -r, --region
           Region (us-east-1/eu-west-1/...)
           Default: us-east-1