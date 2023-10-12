#!/bin/bash

CUR_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

if [ $# -ne 1 ]; then
  echo "Usage: $0 MOUNT_POINT"
  exit 1
fi

# Sometimes the mount point can become "corrupted", e.g., the underlying directory
# is deleted and recreated. In this case, 'ls' or 'cd' will not work on the 
# mount point anymore, so we should not do a '-d' test.

# if [ ! -d $1 ]; then
#   echo "Mount point does not exist"
#   exit 1
# fi

# Similarly, we cannot get the full path using 'cd' when the mount point becomes
# corrupted. We can only use the path argument as is.
mount_point=$1
# Convert path to full path before we switch to current dir
# mount_point=$(cd $1 && pwd)

if [ ! -f $CUR_DIR/charybdefs.pid ]; then
  echo "No prior run found. Skip"
  exit 0
fi

cdfs_pid=$(cat $CUR_DIR/charybdefs.pid)

if [ -z "$cdfs_pid" ];then
  echo "Empty pid file. Skip"
  rm $CUR_DIR/charybdefs.pid
  exit 0
fi

kill -9 $cdfs_pid

if [ $? -eq 0 ]; then
  echo "Stopped charybdefs"
else
  echo "Failed to stop charybdefs"
fi

# remove the PID file and try to do unmount regardless of whether kill succeeded or not
rm $CUR_DIR/charybdefs.pid

# Add the '-z' option (lazy unmount) so that we can still unmount when the 
# mount point becomes corrupted.
fusermount -zu $mount_point
