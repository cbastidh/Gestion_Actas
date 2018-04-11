#!/bin/sh
DEV_ROOT=`pwd`
export DEV_ROOT
echo DEV_ROOT = $DEV_ROOT



ramfs_size_mb=256
mount_point=$DEV_ROOT/build
ramfs_size_sectors=$((${ramfs_size_mb}*1024*1024/512))
ramdisk_dev=`hdid -nomount ram://${ramfs_size_sectors}`

newfs_hfs -v 'mdc ramdisk' ${ramdisk_dev}
mkdir -p ${mount_point}
mount -o noatime -t hfs ${ramdisk_dev} ${mount_point}

echo "remove with:"
echo "umount ${mount_point}"
echo "diskutil eject ${ramdisk_dev}"
