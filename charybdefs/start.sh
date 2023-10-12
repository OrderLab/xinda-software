#!/bin/bash

CUR_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

if [ $# -eq 2 ];then
  port=9191
elif [ $# -eq 3 ]; then
  port=$3
else
  echo "Usage: $0 MOUNT_POINT DATA_DIR [PORT_NUM]"
  exit 0
fi

if [ ! -d $1 -o ! -d $2 ]; then
  echo "Mount point or data directory does not exist"
  exit 1
fi

# Convert path to full path before we switch to current dir
mount_point=$(cd $1 && pwd)
data_dir=$(cd $2 && pwd)

cd $CUR_DIR

if [ ! -x ./charybdefs ]; then
  echo "Could not find charybdefs executable"
  exit 1
fi

if [ -f charybdefs.pid ]; then
  echo "CharybdeFS has already started. Stop it first."
  exit 0
fi

#sudo modprobe fuse
./charybdefs -f $mount_point -omodules=subdir,subdir=$data_dir --port=$port > charybdefs.output 2>&1 &
cdfs_pid=$!
sleep 1
if ps -p$cdfs_pid > /dev/null; then
  echo $cdfs_pid > charybdefs.pid
  echo "charybdefs started with PID $cdfs_pid"
else
  echo "charybdefs has exited"
fi
