dnl Copyright (C) 1993-2002 Free Software Foundation, Inc.
dnl This file is free software, distributed under the terms of the GNU
dnl General Public License.  As a special exception to the GNU General
dnl Public License, this file may be distributed as part of a program
dnl that contains a configuration script generated by Autoconf, under
dnl the same distribution terms as the rest of that program.

dnl From Bruno Haible, Marcus Daniels.

AC_PREREQ(2.13)

AC_DEFUN([CL_CC_NEED_CCPAUX],
[AC_REQUIRE([AC_PROG_CPP])
AC_CACHE_CHECK(whether CPP likes indented directives, cl_cv_prog_cc_indented, [
AC_EGREP_CPP([#.*foo],[ #define foo],
cl_cv_prog_cc_indented=no, cl_cv_prog_cc_indented=yes)
])
if test $cl_cv_prog_cc_indented = yes; then
  CC_NEED_CCPAUX=false
else
  CC_NEED_CCPAUX=true
fi
AC_SUBST(CC_NEED_CCPAUX)dnl
])