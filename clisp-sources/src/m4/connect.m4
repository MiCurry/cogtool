dnl -*- Autoconf -*-
dnl Copyright (C) 1993-2004 Free Software Foundation, Inc.
dnl This file is free software, distributed under the terms of the GNU
dnl General Public License.  As a special exception to the GNU General
dnl Public License, this file may be distributed as part of a program
dnl that contains a configuration script generated by Autoconf, under
dnl the same distribution terms as the rest of that program.

dnl From Bruno Haible, Marcus Daniels, Sam Steingold.

AC_PREREQ(2.13)

AC_DEFUN([CL_CONNECT],
[AC_CHECK_FUNCS(connect)
if test $ac_cv_func_connect = yes; then
CL_PROTO([connect], [
for x in '' 'const'; do
for y in 'struct sockaddr *' 'void*'; do
for z in 'int' 'size_t' 'socklen_t'; do
if test -z "$have_connect_decl"; then
CL_PROTO_TRY([
#ifdef HAVE_UNISTD_H
#include <unistd.h>
#endif
#include <sys/types.h>
#include <sys/socket.h>
], [int connect (int fd, $x $y name, $z namelen);], [int connect();], [
cl_cv_proto_connect_arg2a="$x"
cl_cv_proto_connect_arg2b="$y"
cl_cv_proto_connect_arg3="$z"
have_connect_decl=1])
fi
done
done
done
if test -z "$have_connect_decl"; then
  echo "*** Missing autoconfiguration support for this platform." 1>&2
  echo "*** Please report this as a bug to the CLISP developers." 1>&2
  echo "*** When doing this, please also show your system's connect() declaration." 1>&2
  exit 1
fi
], [extern int connect (int, $cl_cv_proto_connect_arg2a $cl_cv_proto_connect_arg2b, $cl_cv_proto_connect_arg3);])
AC_DEFINE_UNQUOTED(CONNECT_CONST,$cl_cv_proto_connect_arg2a,[does declaration of connect() need const?])
AC_DEFINE_UNQUOTED(CONNECT_NAME_T,$cl_cv_proto_connect_arg2b,[type of `name' in connect() declaration])
AC_DEFINE_UNQUOTED(CONNECT_ADDRLEN_T,$cl_cv_proto_connect_arg3,[type of `addrlen' in connect() declaration])
fi])