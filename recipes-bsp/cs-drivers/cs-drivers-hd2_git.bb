DESCRIPTION = "Hardware drivers & libs for Coolstream HD2"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/license;md5=17a6b3d5436a55985b200c725761907a"


DEPENDS = "libbluray"
RDEPENDS_${PN} = "ffmpeg"
PROVIDES = "virtual/libstb-hal virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/libstb-hal virtual/dvb-driver"

# kernel modules are generally machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

Pn = "r1"

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"

SRC_URI = "git://github.com/tuxbox-neutrino/drivers-bin-cst.git;protocol=https \
	   file://license \
"

S = "${WORKDIR}/git"

# The compiled binaries don't provide sonames.
SOLIBS = "${SOLIBSDEV}"

# These are proprietary binaries generated elsewhere so don't check ldflags
INSANE_SKIP_${PN} = "ldflags already-stripped"
INSANE_SKIP_${PN}-dev = "ldflags"

ALLOW_EMPTY_cs-drivers-hd2 = "1"

# no idea why do_configure does not find the license file otherwise...
do_configure_prepend () {
	cp '${WORKDIR}/license' '${S}'
}

do_compile () {
}

do_install () {
	install -d ${D}/lib/modules/${KV}
	cp -r ${S}/${BOXTYPE}-3.x/drivers/3.10.93/extra ${D}/lib/modules/3.10.93
	# install -d ${D}${libdir}
	install -d ${D}/lib/firmware
	cp -r ${S}/${BOXTYPE}-3.x/firmware/* ${D}/lib/firmware
}

do_install_append () {
	if [ ${INCLUDE_ULDR} = "yes" ] || [ ${INCLUDE_U_BOOT} = "yes" ] || [ ${CLEAN_ENV} = "yes" ];then
	install -d ${D}${localstatedir}/update
	fi
	if [ ${INCLUDE_ULDR} = "yes" ];then
		cp ${S}/${BOXTYPE}-3.x/uldr.bin ${D}${localstatedir}/update/uldr.bin
	fi
 	if [ ${INCLUDE_U_BOOT} = "yes" ];then
		if [ ${BOXMODEL} = "link" ];then
			cp ${S}/${BOXTYPE}-3.x/u-boot.bin.link ${D}${localstatedir}/update/
		else
			cp ${S}/${BOXTYPE}-3.x/u-boot.bin ${D}${localstatedir}/update/
		fi
	fi
 	if [ ${CLEAN_ENV} = "yes" ];then
		touch ${D}${localstatedir}/update/.erase_env 
	fi
}

do_install_append_libc-uclibc () {
	cp -r ${S}/${BOXTYPE}-3.x/libs/* ${D}/lib/
}

do_install_append_libc-glibc () {
	cp -r ${S}/${BOXTYPE}-3.x/libs-eglibc/* ${D}/lib/
}

FILES_${PN} = " \
	${localstatedir}/update \
	/lib/* \
	/lib/firmware/* \
	/lib/modules/* \
"

# do not put the *.so into -dev package
FILES_${PN}-dev = ""
