require linux.inc

DESCRIPTION = "Linux kernel for the Coolstream HD2 boxes"
COMPATIBLE_MACHINE = "coolstream-hd2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PROVIDES = "hd2-kernel-binary"

PR = "r2"
SRCREV = "${AUTOREV}"
PV = "2.15+${SRCPV}"
SRC_URI = " \
	git://git.slknet.de/git/cst-public-drivers.git;protocol=git \
	file://COPYING.GPL \	
"

S = "${WORKDIR}"

do_install_append() {
	install -d ${D}${localstatedir}/update
	if test -e ${DEPLOY_DIR_IMAGE}; then
	cp ${S}/git/${BOXTYPE}-3.x/vmlinux.ub.gz ${DEPLOY_DIR_IMAGE}
	else
	mkdir -p ${DEPLOY_DIR_IMAGE} && cp ${S}/git/${BOXTYPE}-3.x/vmlinux.ub.gz ${DEPLOY_DIR_IMAGE}
	fi
	if [ ${INCLUDE_KERNEL} == "yes" ];then
		cp ${S}/git/${BOXTYPE}-3.x/vmlinux.ub.gz ${D}${localstatedir}/update/
	fi
	if [ ${INCLUDE_ULDR} == "yes" ];then
		cp ${S}/git/${BOXTYPE}-3.x/uldr.bin ${D}${localstatedir}/update/
	fi
	if [ ${INCLUDE_ULDR} == "oc" ];then
		cp ${S}/git/${BOXTYPE}-3.x/uldr.bin.600mhz ${D}${localstatedir}/update/uldr.bin
	fi
 	if [ ${INCLUDE_U_BOOT} == "yes" ];then
		cp ${S}/git/${BOXTYPE}-3.x/u-boot.bin ${D}${localstatedir}/update/
	fi
}
