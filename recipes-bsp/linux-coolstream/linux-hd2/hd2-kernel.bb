DESCRIPTION = "Linux kernel Binary for the Coolstream HD2 boxes"
COMPATIBLE_MACHINE = "coolstream-hd2"
PROVIDES = "hd2-kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "file://COPYING.GPL"

PR = "r1"


do_install() {
	install -d ${D}${localstatedir}/update/
	touch ${D}${localstatedir}/update/put_kernel_here
	if [ ${INCLUDE_KERNEL} == "yes" ];then
		cp ${DEPLOY_DIR_IMAGE}/zImage ${D}${localstatedir}/update/vmlinux.ub.gz
	fi
}

FILES_${PN} = "${localstatedir}/update/*"
