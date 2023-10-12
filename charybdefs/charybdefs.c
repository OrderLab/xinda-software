/*
 * ** 27-12-2015
 * **
 * ** The author disclaims copyright to this source code.  In place of
 * ** a legal notice, here is a blessing:
 * **
 * **    May you do good and not evil.
 * **    May you find forgiveness for yourself and forgive others.
 * **    May you share freely, never taking more than you give.
 * **
 */

#define FUSE_USE_VERSION 29
#define PACKAGE_VERSION "0.1"

#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>
#include <assert.h>
#include <unistd.h>
#include <sys/types.h>

#include "charybde_ops.h"

#include "server.hh"

static struct fuse_operations charybde_oper = {
  .getattr     = charybde_getattr,
    .readlink    = charybde_readlink,
    .mknod       = charybde_mknod,
    .mkdir       = charybde_mkdir,
    .unlink      = charybde_unlink,
    .rmdir       = charybde_rmdir,
    .symlink     = charybde_symlink,
    .rename      = charybde_rename,
    .link        = charybde_link,
    .chmod       = charybde_chmod,
    .chown       = charybde_chown,
    .truncate    = charybde_truncate,
    .open        = charybde_open,
    .read        = charybde_read,
    .write       = charybde_write,
    .statfs      = charybde_statfs,
    .flush       = charybde_flush,
    .release     = charybde_release,
    .fsync       = charybde_fsync,
    .setxattr    = charybde_setxattr,
    .getxattr    = charybde_getxattr,
    .listxattr   = charybde_listxattr,
    .removexattr = charybde_removexattr,
    .opendir     = charybde_opendir,
    .readdir     = charybde_readdir,
    .releasedir  = charybde_releasedir,
    .fsyncdir    = charybde_fsyncdir,

    .init        = charybde_init,
    .destroy     = charybde_destroy,

    .access      = charybde_access,
    .create      = charybde_create,
    .ftruncate   = charybde_ftruncate,
    .fgetattr    = charybde_fgetattr,
    .lock        = charybde_lock,
    .utimens     = charybde_utimens,
    .bmap        = charybde_bmap,
    .ioctl       = charybde_ioctl,
    .poll        = charybde_poll,
    .write_buf   = charybde_write_buf,
    .read_buf    = charybde_read_buf,
    .flock       = charybde_flock,
    .fallocate   = charybde_fallocate,
};

enum {
  KEY_HELP,
  KEY_VERSION,
};

#define OPTION(t, p, v) { t, offsetof(struct cdfs_config, p), v }

static const struct fuse_opt option_spec[] = {
  OPTION("--port=%i",       port, 0),
  FUSE_OPT_KEY("-V",        KEY_VERSION),
  FUSE_OPT_KEY("--version", KEY_VERSION),
  FUSE_OPT_KEY("-h",        KEY_HELP),
  FUSE_OPT_KEY("--help",    KEY_HELP),
  FUSE_OPT_END
};

static void show_help(const char *program)
{
  printf("usage: %s mountpoint [options]\n\n", program);
  printf("File-system specific options:\n"
    "    --port=<i>             Port of the file system server to listen to\n"
    "                           (default: 9191)\n"
    "\n");
}

static int myfs_opt_proc(void *data, const char *arg, int key, struct fuse_args *outargs)
{
  switch(key) {
    case KEY_HELP:
      fprintf(stderr,
              "usage: %s mountpoint [options]\n\n"
              "general options:\n"
              "    -o opt,[opt...]        mount options\n"
              "    -h   --help            print help\n"
              "    -V   --version         print version\n\n"
              "File-system specific options:\n"
              "    --port=NUM             Port of the file system server to listen to\n"
              "                           (default: 9191)\n"
              , outargs->argv[0]);
      fuse_opt_add_arg(outargs, "-ho");
      fuse_main(outargs->argc, outargs->argv, &charybde_oper, NULL);
      exit(0);
    case KEY_VERSION:
      fprintf(stderr, "CharybdeFS version %s\n", PACKAGE_VERSION);
      fuse_opt_add_arg(outargs, "--version");
      fuse_main(outargs->argc, outargs->argv, &charybde_oper, NULL);
      exit(0);
  }
  return 1;
}

struct cdfs_config conf;

int main(int argc, char *argv[])
{
  struct fuse_args args = FUSE_ARGS_INIT(argc, argv);
  uid_t euid, ruid;
  ruid = getuid();
  euid = geteuid();
  printf("Real UID: %d, Effective UID: %d\n", ruid, euid);
  conf.port = 9191; // default port
  if (fuse_opt_parse(&args, &conf, option_spec, NULL) == -1)
    return 1;
  printf("Start CharybdeFS at %d\n", conf.port);
  return fuse_main(args.argc, args.argv, &charybde_oper, NULL);
}
