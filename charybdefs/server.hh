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

#ifndef SERVER_HH
#define SERVER_HH

#ifdef __cplusplus

#include <string>

int error_inject(volatile int in_flight, std::string path, std::string method);

#endif

void start_server_thread(int port);

#endif
