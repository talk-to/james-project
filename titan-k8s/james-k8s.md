# James Docs

- k8s deployment: https://github.com/apache/james-project/blob/master/server/apps/distributed-app/docs/modules/ROOT/pages/run/run-kubernetes.adoc
- managing distributed james: https://github.com/apache/james-project/blob/master/src/site/markdown/server/manage-guice-distributed-james.md

# Cassandra setup

Deploy:
helm install -n staging -f james-cql-values.yaml james-cassandra bitnami/cassandra 

Generate template:
helm template -n staging -f james-cql-values.yaml james-cassandra bitnami/cassandra 

Create james keyspace:
CREATE KEYSPACE james_test WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
CREATE KEYSPACE james_test_cache WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

# RabbitMQ Setup

helm install -n staging -f james-rmq-values.yaml james-rmq bitnami/rabbitmq

# Opensearch Setup

Single node cluster

helm install -n staging -f james-opensearch-values.yaml james-opensearch opensearch/opensearch
helm install -n staging -f james-opensearch-dashboards.yaml james-opensearch-dashboards opensearch/opensearch-dashboards


# James setuo

refer titan-values.yaml

