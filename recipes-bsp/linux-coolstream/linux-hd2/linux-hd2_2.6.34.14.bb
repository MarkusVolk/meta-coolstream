require linux.inc

DESCRIPTION = "Linux kernel for the Coolstream HD2 boxes"
COMPATIBLE_MACHINE = "coolstream-hd2"

PROVIDES = "hd2-kernel-binary"

PR = "r2"
SRCREV = "${AUTOREV}"
SRC_URI = " \
	git://c00lstreamtech.de/cst-public-drivers.git \
	file://COPYING.GPL \	
"

S = "${WORKDIR}"

do_install_append() {
	if test -e ${DEPLOY_DIR_IMAGE}; then
	cp  ${S}/git/${BOXTYPE}/vmlinux.ub.gz ${DEPLOY_DIR_IMAGE}
	else
	mkdir -p ${DEPLOY_DIR_IMAGE} && cp ${S}/git/${BOXTYPE}/vmlinux.ub.gz ${DEPLOY_DIR_IMAGE}
	fi
}
