# clojure-test
clojure test REST API

untuk menjalankan gunakan command

./start.sh

POST localhost:9010/transactionservice/transaction

Body :

{"transaction_id": long,"amount": double, "type": string, "parent_id": long}
