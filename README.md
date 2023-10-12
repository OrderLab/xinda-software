## Descriptions

* `./cas/`:
  * contains the **official** source code of apache-cassandra-4.0.10
* `./docker-compose-linux-x86_64`:
  * you will need this executable to configure docker-compose on a new razor server
* `./hadoop-3.2.1`:
  * links to the repo containing the source code for hadoop-3.2.1. Some custom modifications have been made to "mrbench.java" to expore more fine-grained signals in an mrbench job. Details can be found inside that repo.
* `./ycsb-0.17.0`:
  * contains everything you need to run YCSB and the Cassandra/HBase bindings **(need to add mongoDB and etcd in the future)**
  * some custom modifications have been made to the HBase bindings
* `./charybdefs`:
  * Mostly duplicated from [OrderLab/charybdefs](https://github.com/OrderLab/charybdefs) with some minor changes. See the README inside `./charybdefs`.
