COMPATIBLE_MACHINE = "coolstream-hd2"

require linux-libc-headers.inc

SRC_URI = " \
	https://www.kernel.org/pub/linux/kernel/v3.x/linux-${KV}.tar.xz \
"

SRC_URI[md5sum] = "2c014d383bea74e3f4325255250595c2"
SRC_URI[sha256sum] = "ffd6e409dc4fef1f7bfe3ffba0383511ecdbfe2919527d9241cf640efc2bfb74"
