# clojure-test
clojure test REST API

saya tidak tau kenapa PUT tidak bisa
sehingga saya pakai POST

dari
PUT localhost:9010/transactionservice/transaction/$transaction_id
Body:
{"amount": double, "type": string, "parent_id": long}

transaction_id dimasukan dalam form params
menjadi
POST localhost:9010/transactionservice/transaction
Body :
{"transaction_id": long,"amount": double, "type": string, "parent_id": long}
