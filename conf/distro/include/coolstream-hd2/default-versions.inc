#
# Default preferred versions
#
# currently usable kernel version, use the same for headers
LINUXLIBCVERSION ?= "3.10.67%"
PREFERRED_VERSION_nativesdk-linux-libc-headers = "3.16"
PREFERRED_VERSION_ffmpeg = "2.3.3%"
PREFERRED_VERSION_luaposix = "31"
# Force the python versions in one place
PYTHON_BASEVERSION ?= "2.7"
PREFERRED_VERSION_python ?= "2.7.3"
PREFERRED_VERSION_python-native ?= "2.7.3"

# Force the older version of liberation-fonts until we fix the fontforge issue
PREFERRED_VERSION_liberation-fonts ?= "1.04"

# due to the precompiled kernel we have to use an older udev
PREFERRED_VERSION_udev = "164"
