COMPATIBLE_MACHINE = "coolstream-hd2"

require linux-libc-headers.inc

SRC_URI = " \
	http://www.kernel.org/pub/linux/kernel/v2.6/longterm/v2.6.34/linux-2.6.34.14.tar.xz \
	file://0001-ARM-6329-1-wire-up-sys_accept4-on-ARM.patch\
"

SRC_URI[md5sum] = "668ddc3a6ce6485b629c82c20fbd6cda"
SRC_URI[sha256sum] = "a800b5fa4727526c75cb3d40d3dd0e085766b5f1c63e6b22733539a83c44f8ea"
