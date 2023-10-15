IMPORTANT
==========
(last edited by Ruiming, Oct 15th, 2023)

This repo duplicates (mostly) from [OrderLab/charybdefs](https://github.com/OrderLab/charybdefs), except for some custom changes:
* Modify inject_client.py at Line 67:
    * **Change** `print("Done. Injection clear time: " + datetime.now())` **to** `print("Done. Injection clear time: " , datetime.now())`
* Comment lines 173-176 in `server.cc`
* Comment line 35 in `start.sh`
  * :warning: Only if you get `kernel/fs/fuse/fuse.ko` when you
    ```
    grep -i fuse /lib/modules/`uname -r`/modules.builtin
    ```
* Add `-o nonempty -o allow_root` flag to `./start.sh` to enable double mounting (local-dir -> data-dir-on-container & local-dir -> fuser-dir)

Inject Slow Faults to Running Docker Containers
==========
(last edited by Ruiming, Oct 15th, 2023)

## Prerequisite
* Make sure that the `user_allow_other` flag is enabled in `/etc/fuse.conf` (:warning: need root access)
* Modify the docker-compose file to specify a local directory to mount the data directory on the container. Take Cassandra as an example:
```
(...)
   volumes:
      - /path/to/local/directory/cassandra:/var/lib/cassandra/data
(...)
```
* Modify the docker-compose file to specify a local user. If we ignore this step, the local directory will need root access (user:group set as root:root)
```
(...)
   user: ${CURRENT_UID}
(...)
```
## Tutorial
```
# Housecleaning
rm -rf /path/to/local/directory/cassandra && mkdir /path/to/local/directory/cassandra
rm -rf /path/to/fuser/directory && mkdir /path/to/fuser/directory

# Start charybdeFS first
cd /path/to/charybdefs
./start.sh /path/to/local/directory /path/to/fuser/directory # !!Attention!! it's not /path/to/local/directory/cassandra but /path/to/local/directory

# Start the Cassandra cluster
cd /path/to/docker-cassandra/
CURRENT_UID=$(id -u):$(id -g) docker-compose  up -d

# Then we can run YCSB benchmark on Cassandra while injecting any kinds of charybdefs slow faults

```


Configuration
==========
(last edited by Ruiming, Oct 12th, 2023)
```
# Compile
cd charybdefs
dpkg -s g++ cmake libfuse-dev python-thrift pkg-config
thrift -r --gen cpp server.thrift
thrift -r --gen py server.thrift
cmake CMakeLists.txt
make -j8
# Start charybdefs
./start.sh /data/ruiming/temp/fuser-ruiming /data/ruiming/temp/fuser

# Inject slow faults
./inject_client --io_error
./inject_client --delay 1000000 # 1s delay (unit: us)

# Clean up
./inject_client --dump
./inject_client --clear

# Stop charybdefs
./stop.sh /data/ruiming/temp/fuser-ruiming
```
> In `./start.sh $dir1 $dir2`: 
> 1. `dir1` and `dir2` are mounted automatically. No need to mount them by yourself.
> 2. Faults can ONLY affect `dir1`. We should trap distributed systems to use `dir1` as the datadir
> 3. `dir2` will not be affected at all.


CharybdeFS (Below are the same in [OrderLab/charybdefs](https://github.com/OrderLab/charybdefs))
==========

A fuse based fault injection filesystem
with a Thrift RPC interface for instrumentation developed by ScyllaDB.

This repo made some changes to the logging and added a CLI client to 
conveniently interact with the FUSE.

Building
========

```sh
(for CentOS)
$ yum install gcc-c++ cmake thrift libfuse-devel python-thrift thrift-devel 

(for Ubuntu)
$ sudo apt-get install -y g++ cmake libfuse-dev python-thrift pkg-config

$ thrift -r --gen cpp server.thrift
$ thrift -r --gen py server.thrift
$ cmake CMakeLists.txt
$ make -j8
```

Using
=====

Server Side
-----------

First, we should make sure the **fuse** kernel module is loaded (in recent 
Ubuntu releases, FUSE is loaded by default'):

```sh
$ grep -i fuse /lib/modules/`uname -r`/modules.builtin
```
If the grep shows output, `kernel/fs/fuse/fuse.ko`, then you don't need to explicitly
load it. Otherwise, run `sudo modprobe fuse`.

Then to use charybdefs, there needs to be two directories: one as the mount point to use
by applications (e.g., as their data directory), and the other is the backing 
store that stores the actual files, which application is unaware of. 

For instance, we can create a `~/fuser/` under home directory and a 
`/mnt/fuser-$USER` to mount `~/fuser`. And then jail ZooKeeper in the `/mnt/fuser-$USER/zookeeper`
by creating subdirectory `zookeeper` under `~/fuser` (which will be visible to
the mount point), and changing ZooKeeper's `conf/zoo.cfg` to set
`dataDir=/mnt/fuser-$USER/zookeeper`.

With the two directories, run charybdefs with 

```sh
./charybdefs /path/to/mount/ -f -omodules=subdir,subdir=/path/to/data
```

As an example,

```sh
mkdir ~/fuser
sudo mkdir /mnt/fuser-$USER
sudo chown -R $USER:$USR /mnt/fuser-$USER
./charybdefs /mnt/fuser-$USER -f -omodules=subdir,subdir=$HOME/fuser
```

Two scripts are provided to start and stop the FUSE server: `start.sh`, `stop.sh`.
Example usage: 

```bash
./start.sh /mnt/fuser-$USER $HOME/fuser
```

```bash
./stop.sh /mnt/fuser-$USER 
```

#### Non-default Port
By default, the filesystem Thrift server listens on 9191 port. If multiple users on the 
same machine will use the charybdefs simultaneously, it is important to 
**specify a non-default port**: 

```bash
./charybdefs /mnt/fuser-$USER -f -omodules=subdir,subdir=$HOME/fuser --port=9998
```

or

```bash
./start.sh /mnt/fuser-$USER $HOME/fuser 9998
```

Client Side
-----------
We have a handy client to inject faults to the server side dynamically. For example,

#### Inject errors

```sh
./inject_client --pattern .*zookeeper/version-2/snapshot.* --delay 8000000 write write_buf
```

#### Non-default Port

If you started charybdefs with a non-default port, it is important that you **specify
the same non-default port** in the client command. Otherwise, you might be messing up
with other users' injection!

```bash
./inject_client --port 9998 --pattern .*zookeeper/version-2/snapshot.* --delay 8000000 write write_buf
```

#### Dump injection points

```sh
./inject_client --dump
```

#### Clear errors

```sh
./inject_client --clear
```
