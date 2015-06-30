DESCRIPTION = "Hardware drivers & libs for Coolstream HD2"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/license;md5=17a6b3d5436a55985b200c725761907a"

COMPATIBLE_MACHINE = "coolstream-hd2"
DEPENDS = "libbluray"
PROVIDES = "virtual/stb-hal-libs"
RPROVIDES_${PN} = "virtual/stb-hal-libs"

# kernel modules are generally machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

Pn = "r1"

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"

SRC_URI = " \
	git://git.coolstreamtech.de/cst-public-drivers.git \
	file://cs-drivers.init_${BOXTYPE} \
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
	cp -r ${S}/${BOXTYPE}-3.x/drivers/${KV}/extra ${D}/lib/modules/${KV}
	cp ${S}/${BOXTYPE}-3.x/drivers/${KV}/modules.* ${D}/lib/modules/${KV}
	# install -d ${D}${libdir}
	install -d ${D}/lib/firmware
	cp -r ${S}/${BOXTYPE}-3.x/firmware/* ${D}/lib/firmware
	# init script
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/cs-drivers.init_${BOXTYPE} ${D}${sysconfdir}/init.d/cs-drivers
	rm ${D}/lib/modules/${KV}/modules.builtin
	rm ${D}/lib/modules/${KV}/modules.order
}

do_install_append () {
	if [ ${INCLUDE_ULDR} == "yes" ] || [ ${INCLUDE_U_BOOT} == "yes" ] || [ ${INCLUDE_ULDR} == "oc" ] || [ ${CLEAN_VAR} == "yes" ] || [ ${CLEAN_ENV} == "yes" ];then
	install -d ${D}${localstatedir}/update
	fi
	if [ ${INCLUDE_ULDR} == "yes" ];then
		cp ${S}/${BOXTYPE}-3.x/uldr.bin.500mhz ${D}${localstatedir}/update/uldr.bin
	fi
	if [ ${INCLUDE_ULDR} == "oc" ];then
		cp ${S}/${BOXTYPE}-3.x/uldr.bin ${D}${localstatedir}/update/
	fi
 	if [ ${INCLUDE_U_BOOT} == "yes" ];then
		cp ${S}/${BOXTYPE}-3.x/u-boot.bin ${D}${localstatedir}/update/
	fi
 	if [ ${CLEAN_VAR} == "yes" ];then
		touch ${D}${localstatedir}/update/var.bin 
	fi
 	if [ ${CLEAN_ENV} == "yes" ];then
		touch ${D}${localstatedir}/update/env.bin 
	fi
}

do_install_append_libc-uclibc () {
	cp -r ${S}/${BOXTYPE}-3.x/libs/* ${D}/lib/
}

do_install_append_libc-glibc () {
	cp -r ${S}/${BOXTYPE}-3.x/libs-eglibc/* ${D}/lib/
}

# initscript
inherit update-rc.d

INITSCRIPT_NAME = "cs-drivers"
INITSCRIPT_PARAMS = "start 50 S ."

FILES_${PN} = " \
	${localstatedir}/update \
	/lib/* \
	/lib/firmware/* \
	/lib/modules/* \
	${sysconfdir} \
"

# do not put the *.so into -dev package
FILES_${PN}-dev = ""
