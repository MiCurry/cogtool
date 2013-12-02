dnl -*- Autoconf -*-
dnl Copyright (C) 1993-2003 Free Software Foundation, Inc.
dnl This file is free software, distributed under the terms of the GNU
dnl General Public License.  As a special exception to the GNU General
dnl Public License, this file may be distributed as part of a program
dnl that contains a configuration script generated by Autoconf, under
dnl the same distribution terms as the rest of that program.

dnl From Bruno Haible, Marcus Daniels, Sam Steingold.

AC_PREREQ(2.57)

AC_DEFUN([CL_DIR_HEADER],
[AC_BEFORE([$0], [CL_CLOSEDIR])dnl
dnl This is mostly copied from AC_DIR_HEADER, AC_HEADER_DIRENT, AC_FUNC_CLOSEDIR_VOID.
dnl The closedir return check has been moved to CL_CLOSEDIR.
ac_header_dirent=no
for ac_hdr in dirent.h sys/ndir.h sys/dir.h ndir.h; do
  ac_safe=`echo "$ac_hdr" | sed 'y%./%__%'`
  AC_MSG_CHECKING([for $ac_hdr that defines DIR])
  AC_CACHE_VAL(ac_cv_header_dirent_$ac_safe,
    [AC_TRY_COMPILE([#include <sys/types.h>
     #include <$ac_hdr>], [DIR *dirp = 0;],
     eval "ac_cv_header_dirent_$ac_safe=yes",
     eval "ac_cv_header_dirent_$ac_safe=no")])dnl
  if eval "test \"`echo '$ac_cv_header_dirent_'$ac_safe`\" = yes"; then
    AC_MSG_RESULT(yes)
    ac_header_dirent=$ac_hdr
    break
  else
    AC_MSG_RESULT(no)
  fi
done
case "$ac_header_dirent" in
dirent.h) AC_DEFINE(DIRENT,,[have <dirent.h>?]) ;;
sys/ndir.h) AC_DEFINE(SYSNDIR,,[no <dirent.h>, use <sys/ndir.h>]) ;;
sys/dir.h) AC_DEFINE(SYSDIR,,[no <dirent.h>, use <sys/dir.h>]) ;;
ndir.h) AC_DEFINE(NDIR,,[no <dirent.h>, use <ndir.h>]) ;;
esac
# Two versions of opendir et al. are in -ldir and -lx on SCO Xenix.
if test $ac_header_dirent = dirent.h; then
AC_CHECK_LIB(dir, opendir, LIBS="$LIBS -ldir")
else
AC_CHECK_LIB(x, opendir, LIBS="$LIBS -lx")
fi
])