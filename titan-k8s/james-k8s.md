# Cassandra setup

To deploy cassandra:
helm install -n staging -f james-cql-values.yaml james-cassandra bitnami/cassandra 

To generate deployment template:
helm template -n staging -f james-cql-values.yaml james-cassandra bitnami/cassandra 

# 