# Coolstream HD2 BSP default versions

# "upstream" also uses 4.8
GCCVERSION ?= "4.8%"

# set udev to 164 because the cst kernel lacks devtmpfs support 
PREFERRED_VERSION_udev = "164"

# currently usable kernel version, use the same for headers
LINUXLIBCVERSION ?= "2.6.34%"

PREFERRED_VERSION_nativesdk-linux-libc-headers = "3.16%"

PREFERRED_VERSION_ffmpeg = "2.3.3%"

PREFERRED_VERSION_luaposix = "31"
