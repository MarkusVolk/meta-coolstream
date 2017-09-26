SRCREV = "2a3bb4daf5778c5875674cd26a3c75b3d460a042"

PV = "1.0.26+"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/uclibc-git:"

SRC_URI = "git://uclibc-ng.org/git/uclibc-ng \
        file://uClibc.machine \
        file://uClibc.distro \
        file://obstack.cfg \
        file://locale.cfg \
"
S = "${WORKDIR}/git"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+\.\d+(\.\d+)*)"

