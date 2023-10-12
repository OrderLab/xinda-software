#!/usr/bin/env python3

import errno
import sys
import argparse

sys.path.append('gen-py')
from server import server
from server.ttypes import *

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from datetime import datetime

def connect(port):
    transport = TSocket.TSocket('127.0.0.1', port)
    transport = TTransport.TBufferedTransport(transport)
    protocol = TBinaryProtocol.TBinaryProtocol(transport)
    client = server.Client(protocol)
    transport.open()
    return client

parser = argparse.ArgumentParser(description="File system injection client")
parser.add_argument('--port', type=int, default=9191,
        help='CharybdeFS port to connect to')
parser.add_argument('--clear', action='store_true',
        help='clean all injections')
parser.add_argument('--dump', action='store_true',
        help='dump all injection points')
parser.add_argument('--full', action='store_true',
        help='simulate disk full')
parser.add_argument('--delay', type=int, default=0,
        help='add delay to the method')
parser.add_argument('--auto_delay', action='store_true',
        help='enable auto delay')
parser.add_argument('--io_error', action='store_true',
        help='simulating IO error')
parser.add_argument('--quota_error', action='store_true',
        help='simulating quota exceed error')
parser.add_argument('--random', action='store_true',
        help='simulating random errors')
parser.add_argument('--probability', type=int, default=0,
        help='inject error probability: specified as an integer in [0, 100000]')
parser.add_argument('--pattern',
        help='inject error path pattern')
parser.add_argument('--kill', action='store_true',
        help='whether to kill the caller process or not')
parser.add_argument('--one_time', action='store_true',
        help='stop injection after one fault is injected')
parser.add_argument('methods', metavar='M', nargs='*',
        help='the file system method to be injected')

def main():
    args = parser.parse_args()
    if args.probability < 0 or args.probability > 100000:
        sys.stderr.write("Invalid probability %d, must be in [0, 100000]\n" % (args.probability))
        sys.exit(1)
    if not (args.clear or args.dump or args.full or args.io_error or args.quota_error or args.delay > 0 or args.kill):
        sys.stderr.write("No action specified for injection\n\n")
        parser.print_help()
        sys.exit(1)
    client = connect(args.port)
    if args.clear:
        print("clearing all faults conditions")
        client.clear_all_faults()
        print("Done. Injection clear time: " , datetime.now())
        sys.exit(0)
    if args.dump:
        print("dumping all injection points")
        descrmap = client.get_faults()
        for method, descr in descrmap.iteritems():
            print("+ %s: %s" % (method, str(descr.__dict__)))
        sys.exit(0)

    if args.full:
        print("simulating disk full")
        if len(args.methods) > 0:
            client.set_fault(args.methods, args.random, errno.ENOSPC, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
        else:
            client.set_all_fault(args.random, errno.ENOSPC, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
    elif args.io_error:
        print("simulating IO error")
        if len(args.methods) > 0:
            client.set_fault(args.methods, args.random, errno.EIO, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
        else:
            client.set_all_fault(args.random, errno.EIO, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
    elif args.quota_error:
        print("simulating qutoa error")
        if len(args.methods) > 0:
            client.set_fault(args.methods, args.random, errno.EDQUOT, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
        else:
            client.set_all_fault(args.random, errno.EDQUOT, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
    else:
        print("delaying operations")
        if len(args.methods) > 0:
            client.set_fault(args.methods, args.random, 0, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
        else:
            client.set_all_fault(args.random, 0, args.probability, args.pattern, args.kill, args.delay, args.auto_delay, args.one_time)
    print("Done. Injection add time: ", datetime.now())

if __name__ == "__main__":
    main()
