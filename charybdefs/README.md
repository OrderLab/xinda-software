
CharybdeFS
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
